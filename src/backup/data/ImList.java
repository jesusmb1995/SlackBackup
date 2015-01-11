package backup.data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
public class ImList {

	private List<Im> ims;
	
	public ImList(Token token, UsersList users) throws JSONException, IOException {
		this.ims = new LinkedList<Im>();
		
		MethodLocation listIt = new MethodLocation(token, Methods.IM_LIST);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(listIt.toString()));
		
		JSONArray array = obj.getJSONArray("ims");
		for(int i = 0 ; i < array.length() ; i++)	{
			JSONObject subObj = array.getJSONObject(i);
			String id = subObj.getString("id");
			String userId = subObj.getString("user");
			// String name = users.getUserName(userId);
			User user = users.getUser(userId);
			if(user != null) 
				ims.add(new Im(id, user));
				//ims.add(new Im(id, new User(userId, name)));
		}		
	}

	public List<Im> getIms() {
		return ims;
	}
}
