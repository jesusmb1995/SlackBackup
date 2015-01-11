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
public class UsersList {
	
	private List<User> users;
	
	public UsersList(Token token) throws JSONException, IOException {
		this.users = new LinkedList<User>();
		
		MethodLocation listIt = new MethodLocation(token, Methods.USERS_LIST);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(listIt.toString()));
		
		JSONArray array = obj.getJSONArray("members");
		for(int i = 0 ; i < array.length() ; i++)	{
			JSONObject subObj = array.getJSONObject(i);
			users.add(new User(subObj.getString("id"), subObj.getString("name")));
		}
	}
	
	public UsersList(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}
	
	/** 
	 * @param userId User ID
	 * @return A user name
	 */
	public String getUserName(String userId) {
		String name = null;
		for(User user : users) if(user.getId().equals(userId)) {
			name = user.getName();
			break;
		}
		return name;
	}
	
	public User getUser(String userId) {
		User sendUser = null;
		for(User user : users) 
			if(user.getId().equals(userId)) {sendUser=user; break;}
		return sendUser;
	}
	
	public User getUserByName(String userName) {
		User sendUser = null;
		for(User user : users)
			if(user.getName().equals(userName)) {sendUser=user; break;}
		return sendUser;
	}
}
