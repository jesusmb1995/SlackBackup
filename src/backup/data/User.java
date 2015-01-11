package backup.data;

import java.io.IOException;

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
public class User implements Param {
	
	private String id, name;

	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "user=".concat(id);
	}
	
	/** 
	 * @return Current user 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static User who(Token token) throws JSONException, IOException {
		MethodLocation auth = new MethodLocation(token, Methods.AUTH);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(auth.toString()));
		
		return new User(obj.getString("user_id"), obj.getString("user"));
	}
	
	public static String getTeamName(Token token) throws JSONException, IOException {
		MethodLocation auth = new MethodLocation(token, Methods.AUTH);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(auth.toString()));
		return obj.getString("team");
	}
	
	/*
	 /
	 * @return Bidimensional array containing the User in the first index and 
	 * the TeamName in the second one 
	 /
	public static Object[] whoAndTeam(Token token) throws JSONException, IOException {
		MethodLocation auth = new MethodLocation(token, Methods.AUTH);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(auth.toString()));
		User user = new User(obj.getString("user_id"), obj.getString("user"));
		return new Object[]{user, obj.getString("team")};
	}
	*/
	
	public boolean equals(User user) {
		return id.equals(user.getId());
	}
}
