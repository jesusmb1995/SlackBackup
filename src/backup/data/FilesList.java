package backup.data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Copyright (c) 2015 Jesús Martín Berlanga. All rights reserved.
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
public class FilesList {

	private List<FilesByUser> usersFiles;
	
	public FilesList(Token token, FileFilter filter, UsersList users) throws JSONException, IOException {
		this.usersFiles = new LinkedList<FilesByUser>();
		FilesListIterator it = new FilesListIterator(token, filter);

		while(it.hasNext()) {
			JSONArray array = it.next();
			for(int i = 0 ; i < array.length() ; i++)	{
				JSONObject subObj = array.getJSONObject(i);
				
				File file = new File (
								subObj.getString("id"),
								subObj.has("name") ? subObj.getString("name") : null,
										
								//subObj.getString("url"), //Deprecated: Use url_private and url_private_download instead
								subObj.getString("url_private_download"),	
								
								subObj.getString("filetype"),
								users.getUser(subObj.getString("user")),
								subObj.getInt("size"));
				if(filter.accepted(file)) 
					insertFile(file);	
			}
		}
	}
	
	private void insertFile(File file) {
		FilesByUser storeHere = null;
		for(FilesByUser filesPerUser : usersFiles)
			if(filesPerUser.getOwner().equals(file.getUser())) {
				storeHere = filesPerUser; break;
			}
		
		if(storeHere == null) {
			List<File> files = new LinkedList<File>();
			files.add(file);
			usersFiles.add(new FilesByUser(files ,file.getUser()));
		}
		else 
			storeHere.getFiles().add(file);
	}

	public List<FilesByUser> getUsersFiles() {
		return usersFiles;
	}
}
