package backup.data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

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
public abstract class ChatRoomHistory extends History{
	
	private LinkedList<Message> messages;

	public ChatRoomHistory(Token token, ChatRoom room, UsersList users) throws JSONException, IOException {
		HistoryIterator it = new HistoryIterator(token, getUsedMethod(), room);
		messages = new LinkedList<Message>();
		User user = User.who(token);
		while(it.hasNext()) {
			JSONArray messagesObjects = it.next();
			for(int i = 0 ; i < messagesObjects.length() ; i++) 
				messages.addFirst(new Message(messagesObjects.getJSONObject(i), users, user));
		}
	}
	
	public abstract Methods getUsedMethod();
	
	@Override
	public List<Message> getMessages() {
		return messages;
	}
}
