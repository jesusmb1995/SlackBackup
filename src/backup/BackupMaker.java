package backup;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.json.JSONException;

import backup.data.ChannelHistory;
import backup.data.ChannelsList;
import backup.data.ChatRoom;
import backup.data.ChatRoomHistory;
import backup.data.ChatRoomList;
import backup.data.File;
import backup.data.FileFilter;
import backup.data.FileGroups;
import backup.data.FilesByUser;
import backup.data.FilesList;
import backup.data.GroupHistory;
import backup.data.GroupList;
import backup.data.Im;
import backup.data.ImHistory;
import backup.data.ImList;
import backup.data.Token;
import backup.data.User;
import backup.data.UsersList;

import utils.HTMLUtils;
import utils.URLReader;
import utils.ZipUtils;

/**
 *  Copyright (c) 2015-2016 Jesús Martín Berlanga. All rights reserved.
 *  SlackBackup is licensed under GNU General Public License (GPLv3)
 *  
 *  This file is part of SlackBackup.
 *
 *  SlackBackup is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  SlackBackup is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with SlackBackup.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Jesús Martín Berlanga
 */
public class BackupMaker {

	private static class BackupConfig {
		Token token;
		UsersList users;
		boolean getPublicChannels 	= false, 
				getPrivateChannels 	= false, 
				getPrivateMessages 	= false,  
				getFiles 			= false,  
				fileFilter 			= false,
				negateTypes				;
		User filterByUser 			= null;
		FileGroups[] filterByGroups = null;
		String[] types				= null;
		Integer filterBySize		= null;
		String outputFolder 		= null;
		String teamName					;
		
		public BackupConfig(String[] args) throws JSONException, IOException {
			if(args.length != 2 && args.length != 3 && args.length != 6 && args.length != 7)
				ExitCodes.exit(ExitCodes.EX_USAGE);
			
			token = new Token(args[0]);
			users = new UsersList(token);
			teamName = User.getTeamName(token);
			whatExport(args[1]);
			
			if(args.length == 6 || args.length == 7) {
				fileFilter = true;
				filterByUser = args[2].equals("all") ? null : users.getUserByName(args[2]);
				if(!args[3].equals("all")) initiliazeFilterByGroups(args[3]);
				if(!args[4].equals("all")) {
					initializeNegateTypes(args[4].charAt(0));
					types = args[4].substring(1).split("\\|");
				}
				filterBySize =  args[5].equals("none") ? null : Integer.parseInt(args[5], 10);
			}
			
			int nArgs;
			if(args.length == (nArgs = 3) || args.length == (nArgs = 7)) {
				outputFolder = args[nArgs-1];
				if(!new java.io.File(outputFolder).isDirectory())
					ExitCodes.exit(ExitCodes.EX_NOINPUT);
			}
		}
		
		private void whatExport(String arg) {
			for(int i = 0; i < arg.length(); i++) switch (arg.charAt(i)) 
			{
				case 'c':	getPublicChannels 	= true;break;
				case 'g':	getPrivateChannels 	= true;break;
				case 'p':	getPrivateMessages 	= true;break;
				case 'f':	getFiles			= true;break;
				default:	ExitCodes.exit(ExitCodes.EX_USAGE);
			}		
		}
		
		private void initiliazeFilterByGroups(String arg) {
			String[] groups = arg.split("\\|");
			filterByGroups = new FileGroups[groups.length];
			for(int i = 0; i < filterByGroups.length; i++) {
				filterByGroups[i] = FileGroups.validGroup(groups[i]);
				if(filterByGroups[i] == null) ExitCodes.exit(ExitCodes.EX_USAGE);
			}
		}
		
		private void initializeNegateTypes(char argCharAt0) {
			switch (argCharAt0)
			{
				case '+':	negateTypes 	= false				;break;
				case '-':	negateTypes 	= true				;break;
				default:	ExitCodes.exit(ExitCodes.EX_USAGE)	;break;
			}
		}
	}
	
	/**
	 * @param args
	 * 
	 * TOKEN WHAT_EXPORT [USER GROUPS TYPES MAX_SIZE] [OUTPUT_FOLDER]
	 * 
	 * WHAT_EXPORT Is a combination of "c" (Public Channels), 
	 * "g" (Private Channels), "p" (Private Messages) and "f" (Files)
	 * 
	 * USER "all"/username, Only download files from a user
	 * 
	 * GROUPS "all" or a combination of "posts", "snippets", "images", 
	 * "gdocs", "zips" and "pdfs". Use "|" as a separator. 
	 * 
	 * TYPES "all" or a combination of file types separated with "|". zip, mp4, etc. .
	 * + at the beginning means to include them, - to exclude.
	 * 
	 * MAX_SIZE "none" or max size per file in bytes
	 * 
	 * OUTPUT_FOLDER Folder where to save the resultant .zip file. Must include a directory separator in the last character.
	 */
	public static void main(String[] args) {
		
		if(args.length == 1 && args[0].equals("--help"))
			showHelp();
		else if(args.length == 1 && args[0].equals("-h"))
			showShortHelp();
		else { 
			FileOutputStream dest = null;
			ZipOutputStream out = null;
			try {
				BackupConfig config = new BackupConfig(args);
				dest = new FileOutputStream(destination(config.outputFolder, config.teamName));
				out = new ZipOutputStream(new BufferedOutputStream(dest)); 
				
				if(config.getPrivateMessages) 	backUpPrivateConversations	(config, out);
				if(config.getPrivateChannels)  	backUpPrivateChannels		(config, out);
				if(config.getPublicChannels) 	backUpPublicChannels		(config, out);
				if(config.getFiles) 			backUpFiles					(config, out);		
				
				tryToCloseOutputStreams(out, dest);
			} 
			catch (JSONException e) {
				tryToCloseOutputStreams(out, dest);
				System.err.println(e);
				ExitCodes.exit(ExitCodes.EX_SOFTWARE);
			} 
			catch (IOException e) {
				tryToCloseOutputStreams(out, dest);
				System.err.println(e);
				ExitCodes.exit(ExitCodes.EX_IOERR);
			}
			System.out.println("SlackBackup finished successfully.");
		}
		
		ExitCodes.exit(ExitCodes.EX_OK);
	}
	
	private static void tryToCloseOutputStreams(OutputStream... streams) {
		for(OutputStream st : streams)
			try {
				st.close();
			} catch (IOException e) {
				System.err.println(e);
				ExitCodes.exit(ExitCodes.EX_IOERR, " Error trying to close the output file stream.");
			}
	}
	
	private static void showShortHelp() {
		System.out.println("Usage: java -jar SlackBackup.jar TOKEN WHAT_EXPORT [USER GROUPS TYPES MAX_SIZE] [OUTPUT_FOLDER]");
	}
	private static void showHelp() {
		showShortHelp();
		System.out.println("Usage: java -jar SlackBackup.jar TOKEN WHAT_EXPORT [USER GROUPS TYPES MAX_SIZE] [OUTPUT_FOLDER]");
		System.out.println("Makes a backup of a Slack team.");
		System.out.println("'java -jar SlackBackup.jar yourtoken cgpf' will export everything.\n");
		System.out.println("A TOKEN can be obtained in the Slack API website.\n");
		System.out.println("WHAT_EXPORT,\t[cgpf]+");
			System.out.println("\tc,\tpublic channels");
			System.out.println("\tg,\tprivate channels");
			System.out.println("\tp,\tprivate conversations");
			System.out.println("\tf,\tfiles\n");
		System.out.println("USER,\tall|$UserName,\tfilters files by user name\n");
		System.out.println("GROUPS,\tall|$Groups,\tfilters files by predefined groups"); //[+-]
			System.out.println("\tWhere $Groups is a combination of the available groups separated with '|':");
				System.out.println("\t\tposts");
				System.out.println("\t\tsnippets");
				System.out.println("\t\timages");
				System.out.println("\t\tgdocs");
				System.out.println("\t\tzips");
				System.out.println("\t\tpdfs\n");
		System.out.println("TYPES,\tall|[+-]$FileTypes,\tfilters files by type");
			System.out.println("\tWhere $FileTypes is a combination of file types separated with '|'.");
			System.out.println("\tWith '+' will export those filetypes.");
			System.out.println("\tWith '-' will export the files which filetype is different.\n");
		System.out.println("MAX_SIZE,\tnone|$MaxSizeInBytes,\tfilters files by size\n");
		System.out.println("OUTPUT_FOLDER,\tdestination folder, must include a directory separator in the last character.\n");
		System.out.println("Exit Codes:");
			System.out.println("\tEX_OK\t(0),\tfinished successfully");
			System.out.println("\tEX_USAGE\t(64),\tincorrect usage");
			System.out.println("\tEX_NOINPUT\t(66),\tcan not read the input folder");
			System.out.println("\tEX_SOFTWARE\t(70),\terror while processing data from server");
			System.out.println("\tEX_IOERR\t(74),\terror trying to write or read\n\n");
		System.out.println("Version: 1.1");
		System.out.println("SlackBackup (c) 2015-2016 by Jesús Martín Berlanga\n");
		System.out.println("SlackBackup is licensed is licensed under");
		System.out.println("GNU General Public License (GPLv3).\n\n");
		System.out.println("http://slackbackup.bdevel.org");
	}
	
	private static void backUpPrivateConversations(BackupConfig config, ZipOutputStream out) 
			throws JSONException, IOException 
	{
		System.out.print("\nSearching for private conversations... ");
		ImList list = new ImList(config.token, config.users);
		System.out.print(list.getIms().size() + " conversations have been found.\n");
		
		int i = 1;
		for(Im im : list.getIms()) {
			System.out.println(	"\t["+ i++ +"/"+list.getIms().size()
								+"]\tConversation with " + im.getUser().getName());
			ImHistory history = new ImHistory(config.token, im);
			byte[] data = HTMLUtils.insertBody(history.toString()).getBytes("UTF-8");
			ZipUtils.storeData(out, "private_conversations/"+im.getUser().getName()+".html", data, data.length);
		}
	}
	
	private static void backUpPrivateChannels(BackupConfig config, ZipOutputStream out) 
			throws JSONException, IOException 
	{
		System.out.print("\nSearching for private channels... ");
		ChatRoomList list = new GroupList(config.token);
		System.out.print(list.getGroups().size() + " channels have been found.\n");
		
		int i = 1;
		for(ChatRoom room : list.getGroups()) {
			System.out.println(	"\t["+ i++ +"/"+list.getGroups().size()
								+"]\tConversation with " + room.getName());
			
			ChatRoomHistory history = new GroupHistory(config.token, room, config.users);
			byte[] data = HTMLUtils.insertBody(history.toString()).getBytes("UTF-8");
			ZipUtils.storeData(out, "private_channels/"+room.getName()+".html", data, data.length);
		}
	}
	
	private static void backUpPublicChannels(BackupConfig config, ZipOutputStream out) 
			throws JSONException, IOException 
	{
		System.out.print("\nSearching for public channels... ");
		ChatRoomList list = new ChannelsList(config.token);
		System.out.print(list.getGroups().size() + " channels have been found.\n");
		
		int i = 1;
		for(ChatRoom room : list.getGroups()) {
			System.out.println(	"\t["+ i++ +"/"+list.getGroups().size()
								+"]\tConversation with " + room.getName());
			
			ChatRoomHistory history = new ChannelHistory(config.token, room, config.users);
			byte[] data = HTMLUtils.insertBody(history.toString()).getBytes("UTF-8");
			ZipUtils.storeData(out, "public_channels/"+room.getName()+".html", data, data.length);
		}
	}

	private static void backUpFiles(BackupConfig config, ZipOutputStream out) 
			throws JSONException, IOException 
	{
		FileFilter filter = new FileFilter();
		if(config.fileFilter) {
			filter.addFilter(config.filterByGroups);
			filter.addFilter(config.filterByUser);
			filter.addFilter(config.filterBySize);
			filter.addFilter(config.types, config.negateTypes);
		}
		
		System.out.print("\nSearching for files... ");
		FilesList list = new FilesList(config.token, filter, config.users);
		System.out.print(list.getUsersFiles().size() + " users owning files have been found.\n");
		Set<String> addedEntries = new HashSet<String>();
		
		for(FilesByUser files : list.getUsersFiles()) {
			System.out.println("\t" + files.getOwner().getName() + " files:");
			int i = 1;
			for(File file : files.getFiles()) {
				String fileEntry = provideFileEntry(addedEntries, file.getName());
				System.out.println(	"\t\t["+ i++ +"/"+files.getFiles().size()
									+"]\t" + fileEntry);
				try {
					byte[] data = URLReader.getFileAsByteArray(file.getUrl(), file.getSize(), config.token.getKey());
					ZipUtils.storeData(out, "files/"+files.getOwner().getName()+"/"+fileEntry, data, data.length);
				} catch (IOException e) {
					System.out.println("\t\tDownload failed! Continuing with next files...");
				}
			}
		}
	}
	
	private static String provideFileEntry(Set<String> entries, String fileName) {
		String newEntry = fileName;
		for(int i = 2; !entries.add(newEntry); i++) {
			StringBuilder builder = new StringBuilder();
			
			String[] formatSplit = fileName.split("\\.");
			for(int j = 0; j < formatSplit.length-1; j++) {
				if(j != 0) builder.append(".");
				builder.append(formatSplit[j]);
			}
			
			builder.append(" (").append(i).append(")");
			if(formatSplit.length >  1)
				builder.append(".").append(formatSplit[formatSplit.length-1]);
			
			newEntry = builder.toString();
		}
		return newEntry;
	}

	private static String destination(String folder, String teamName) {
		StringBuilder builder = new StringBuilder();
		if(folder != null) builder.append(folder);
		builder.append(teamName).append(new SimpleDateFormat("_dd-MM-yy-HH-mm").format(new Date())).append(".zip");
		return builder.toString();
	}
}