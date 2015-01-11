package debug;

import java.io.IOException;

import org.json.JSONException;

import backup.data.ChatRoom;
import backup.data.GroupHistory;
import backup.data.Token;
import backup.data.UsersList;

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
public class GroupHistoryTester {

	public static void main(String[] args) throws JSONException, IOException {
		Token token = new Token("your token here"); 
		GroupHistory hist = new GroupHistory
				(
						token,
						new ChatRoom("ROOMID", "room-name"),
						new UsersList(token)
				);
		//for(Message m : hist.getMessages())
		//		System.out.println(m);
		//for(Message m : hist.getMessages())
		//	System.out.println(m.getText());
		System.out.print(hist);
	}

}
