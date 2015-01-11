package backup.data;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.URLReader;

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
public class FilesListIterator {

	private Token token;
	private FileFilter filter;
	private JSONArray current;
	private int currentPage, maxPage;
	private boolean hasMore;
	
	public FilesListIterator(Token token, FileFilter filter) throws JSONException, IOException {
		this.token = token;
		this.filter = filter;
		
		MethodLocation listIt = new MethodLocation(token, Methods.FILES_LIST);
		FileGroups[] groups = filter.getGroups();
		User user = filter.getUser();
		if(user != null) listIt.addParam(user.toString());
		if(groups != null) listIt.addParam(FileGroups.toString(filter.getGroups()));
		JSONObject obj = new JSONObject(URLReader.getUrlSource(listIt.toString()));
		
		current = obj.getJSONArray("files");
			JSONObject paging = obj.getJSONObject("paging");
		currentPage = 1;//paging.getInt("page");
		maxPage = paging.getInt("pages");
		hasMore = currentPage < maxPage;
	}
	
	public boolean hasNext() {
		return current != null;
	}
	
	public JSONArray next() throws JSONException, IOException {
		JSONArray files = current;
		current = null;
		if(hasMore) {
			MethodLocation listIt = new MethodLocation(token, Methods.FILES_LIST);
			FileGroups[] groups = filter.getGroups();
			User user = filter.getUser();
			if(user != null) listIt.addParam(user.toString());
			if(groups != null) listIt.addParam(FileGroups.toString(groups));
			listIt.addParam("page="+currentPage++);
			JSONObject obj = new JSONObject(URLReader.getUrlSource(listIt.toString()));
			
			current = obj.getJSONArray("files");
			//	JSONObject paging = obj.getJSONObject("paging");
			//currentPage = paging.getInt("page");
			hasMore = currentPage < maxPage;
		}
		return files;
	}
}