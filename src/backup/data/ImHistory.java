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
public class ImHistory extends History {

	private LinkedList<Message> messages;
	
	public ImHistory(Token token, Im channel) throws JSONException, IOException {
		HistoryIterator it = new HistoryIterator(token, Methods.IM_HISTORY, channel);
		messages = new LinkedList<Message>();
		
		User user = User.who(token);
		List<User> user2ListContainer = new LinkedList<User>();
		user2ListContainer.add(channel.getUser());
		UsersList user2Cotainer = new UsersList(user2ListContainer);
		
		while(it.hasNext()) {
			JSONArray messagesObjects = it.next();
			for(int i = 0 ; i < messagesObjects.length() ; i++) {
				JSONObject messageObject = messagesObjects.getJSONObject(i);
				messages.addFirst(new Message(messageObject, user2Cotainer, user));
			}
		}
	}

	public List<Message> getMessages() {
		return messages;
	}
}
