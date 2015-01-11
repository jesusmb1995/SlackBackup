package backup.data;

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
public class Message {

	private String text;
	private User user;
	private MessageSubtypes subtype;
	private boolean hidden;
	private Message subMessage;
	
	public Message(String text, User user, MessageSubtypes subtype, boolean hidden, Message subMessage) {
		this.text = text;
		this.user = user;
		this.subtype = subtype;
		this.hidden = hidden;
		this.subMessage = subMessage;
	}
	
	/**
	 * @param users List of users
	 * @param user Token user
	 */
	public Message(JSONObject message, UsersList users, User user) {
		if(message.has("user")) {
			String userId = message.getString("user");
			this.user = userId.equals(user.getId()) ? user : users.getUser(userId);
		} else
			this.user = null;
		this.text = message.has("text") ? message.getString("text"): null;
		this.subtype = message.has("subtype") ? MessageSubtypes.getSubtype(message.getString("subtype")): null;
		this.hidden = message.has("hidden") ? message.getBoolean("hidden") : false;
		this.subMessage = message.has("message") ? new Message(message.getJSONObject("message"), users, user) : null;
	}
	
	public boolean isHidden() {
		return hidden;
	}

	public Message getSubMessage() {
		return subMessage;
	}

	public MessageSubtypes getSubtype() {
		return subtype;
	}

	public String getText() {
		return text;
	}
	
	public User getUser() {
		return user;
	}
	
	/**
	 * @return Html message
	 */
	public String toString() {
		return text != null ? 
				
				text.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&")
				.replaceAll("<@((\\w|-)+)(\\|([^>]+))?>"															, "<a href=\"#\">@$4($1)</a>")	//Members
				.replaceAll("<#((\\w|-)+)(\\|([^>]+))?>"															, "<a href=\"#\">#$4($1)</a>")	//Channels
				.replaceAll("<!([^>]+)>"																			, "<a href=\"#\">$1</a>")		//Especial
				.replaceAll("<((\\w|-|/|:|\\.)*(://|mailto:)(\\w|-|/|:|\\.|@)+)(\\|((\\w|-|/|:|\\.|@| )+))?>"	, "<a href=\"$1\">$6($1)</a>")	//Links	<- SIGUE SIN FUNCIONAR
																																				//Formatting (Ingores mrkdwn_in):
				.replaceAll("```(.+?)```"																		, "<pre>$1</pre>")					//Preformatted	
				.replaceAll("`(.+?)`"																			, "<code>$1</code>")				//Code	
				.replaceAll("_(.+?)_"																			, "<i>$1</i>")						//Italic
				.replaceAll("\\*(.+?)\\*"																		, "<b>$1</b>")						//Bold		
				
				: "";
	}
}
