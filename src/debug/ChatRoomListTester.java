package debug;

import java.io.IOException;

import org.json.JSONException;

import backup.data.ChannelsList;
import backup.data.ChatRoom;
import backup.data.GroupList;
import backup.data.Token;

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
public class ChatRoomListTester {

	public static void main(String[] args) { try {
		
		Token token = new Token("your token here");
		GroupList gList = new GroupList(token);
		ChannelsList cList = new ChannelsList(token);
		
		System.out.println("Group List: ");
		for(ChatRoom room : gList.getGroups())
			System.out.println(room.getName() + " " + room.getId());	
		System.out.println();
		System.out.println("Channel List: ");
		for(ChatRoom room : cList.getGroups())
			System.out.println(room.getName() + " " + room.getId());	
		
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

}
