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
public class HistoryIterator {

	private Token token;
	private Methods historyMethod;
	private Param param;
	private JSONArray current;
	private boolean hasMore;

	public HistoryIterator(Token token, Methods historyMethod, Param param) 
			throws JSONException, IOException 
	{
		this.token = token;
		this.historyMethod = historyMethod;
		this.param = param;
		
		MethodLocation history = new MethodLocation(token, historyMethod);
		history.addParam(param);
		JSONObject obj = new JSONObject(URLReader.getUrlSource(history.toString()));
		hasMore = obj.getBoolean("has_more");
		current = obj.getJSONArray("messages");
	}

	public boolean hasNext() {
		return current != null;
	}

	public JSONArray next() throws JSONException, IOException {
		JSONArray messages = current;
		
		current = null;
		/*If the response includes has_more then the client can make another call, 
		 * using the ts value of the final messages as the latest param to get the 
		 * next page of messages.
		 */
		if(hasMore) {
			String ts = messages.getJSONObject((messages.length()-1)).getString("ts");
			MethodLocation history = new MethodLocation(token, historyMethod);
			history.addParam(param);
			history.addParam("latest="+ts);
			JSONObject obj = new JSONObject(URLReader.getUrlSource(history.toString()));
			hasMore = obj.getBoolean("has_more");
			current = obj.getJSONArray("messages");
		}
		
		return messages;
	}
}
