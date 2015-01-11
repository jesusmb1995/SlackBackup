package backup.data;

import java.util.List;
import utils.HTMLUtils;;

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
public abstract class History {
	
	public abstract List<Message> getMessages();
	
	/** @return Html document */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		User lastUser = null;
		boolean lastUserParaClosed = true;
		for(Message m : getMessages()) {
			//Only print the user name in a root message in order not to spam the chat
			User rootUser = m.getUser();
			if(rootUser == null)
				builder.append("<br>");
			else if(lastUser != rootUser) {
				if(!lastUserParaClosed) {
					builder.append("</p>"); 
					lastUserParaClosed = true;
				} 
				
				builder.append("<p><b>").append(rootUser.getName()).append("</b>:");//.append("</b>:<br>");
				lastUser = rootUser;
				lastUserParaClosed = false;
			} 
				
			Message parent = m, subM;
			StringBuilder tabBuilder = new StringBuilder(), textBuilder;
			do {
				subM = m.getSubMessage();
				textBuilder = new StringBuilder(parent.toString());
				
				if(m.isHidden())
					HTMLUtils.addTag(MessageSubtypes.HIDDEN_STYLE[0], textBuilder, MessageSubtypes.HIDDEN_STYLE[1]);
				
				MessageSubtypes subType = parent.getSubtype();
				if(subType != null) {
					String[] style = MessageSubtypes.getHtmlStyle(parent.getSubtype());
					if(style[0] != null && style[1] != null)
						HTMLUtils.addTag(style[0], textBuilder, style[1]);
				}
				
				textBuilder.insert(0, "<br>").insert(0, tabBuilder.toString());
				builder.append(textBuilder);
				HTMLUtils.nextTabIndent(tabBuilder);
			} 
			while((parent = subM) != null);
		}
		return builder.toString();
	}
}
