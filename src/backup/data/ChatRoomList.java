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
public abstract class ChatRoomList {
	private List<ChatRoom> groups;
	
	public ChatRoomList(Token token) throws JSONException, IOException {
		this.groups = new LinkedList<ChatRoom>();
		
		MethodLocation listIt = new MethodLocation(token, getUsedMethod());
		JSONObject obj = new JSONObject(URLReader.getUrlSource(listIt.toString()));
		
		JSONArray array = obj.getJSONArray(getUsedArray());
		for(int i = 0 ; i < array.length() ; i++)	{
			JSONObject subObj = array.getJSONObject(i);
			groups.add(new ChatRoom(subObj.getString("id"), subObj.getString("name")));
		}
	}
	
	public abstract Methods getUsedMethod();
	public abstract String getUsedArray();
	
	public List<ChatRoom> getGroups() {
		return groups;
	}
}
