package debug;

import java.io.IOException;

import org.json.JSONException;

import backup.data.Im;
import backup.data.ImHistory;
import backup.data.Message;
import backup.data.Token;
import backup.data.User;

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
public class ImHistoryTesterData {

	public static void main(String[] args) {
		try {
			ImHistory history = new ImHistory(
					new Token("your token here"),
					new Im("YOUR ID", new User("USLACKBOT", "SLACKBOT")) );
			
			for(Message m : history.getMessages())
				System.out.println("Subtype: " + m.getSubtype() + "; User: " + m.getUser());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

}
