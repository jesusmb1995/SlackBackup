package backup.data;

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
public enum MessageSubtypes {
	
	SOMETHING("something"),
	CHANGED("message_changed"),
	DELETED("message_deleted"),
	
	CHANEL_JOIN("channel_join"),
	CHANEL_LEAVE("channel_leave"),
	CHANEL_TOPIC("channel_topic"),
	CHANEL_PURPOSE("channel_purpose"),
	CHANEL_ARCHIVE("channel_archive"),
	CHANEL_UNARCHIVE("channel_unarchive"),
	
	GROUP_JOIN("group_join"),
	GROUP_LEAVE("group_leave"),
	GROUP_TOPIC("group_topic"),
	GROUP_PURPOSE("group_purpose"),
	GROUP_ARCHIVE("group_archive"),
	GROUP_UNARCHIVE("group_unarchive")
	;
	
    private final String text;

    private MessageSubtypes(final String text) {
        this.text = text;
    }
    
    public static MessageSubtypes getSubtype(String subtype) {
    	MessageSubtypes toEnum = null;
    	
    	for (MessageSubtypes sub : MessageSubtypes.values())
    		  if(sub.toString().equals(subtype)) {toEnum = sub; break;}  
    	
    	if(toEnum == null) toEnum = MessageSubtypes.SOMETHING;
  	
    	return toEnum;
    }
    
    /** @return Bidimensional array */
    public static String[] getHtmlStyle(MessageSubtypes subtype) {
    	String[] tags = new String[2];
    	switch(subtype) 
    	{
    		case SOMETHING:			tags[0]=	""											;tags[1]=	""					;break;
    		case CHANEL_ARCHIVE:	tags[0]=	"<i style=\"color:red\">"					;tags[1]=	"</i>"				;break;
    		case CHANEL_JOIN:		tags[0]=	"<small><i style=\"color:green\">"			;tags[1]=	"</i></small>"		;break;
    		case CHANEL_LEAVE:		tags[0]=	"<small><i style=\"color:FireBrick\">"		;tags[1]=	"</i></small>"		;break;
    		case CHANEL_PURPOSE:	tags[0]=	"<i style=\"color:SlateGray\">"				;tags[1]=	"</i>"				;break;
    		case CHANEL_TOPIC:		tags[0]=	"<i style=\"color:LightCoral\">"			;tags[1]=	"</i>"				;break;
    		case CHANEL_UNARCHIVE:	tags[0]=	"<i style=\"color:SeaGreen\">"				;tags[1]=	"</i>"				;break;
    		case CHANGED:			tags[0]=	"<font color=style=\"#1C1C1C\">"			;tags[1]=	"</font>"			;break;
    		case DELETED:			tags[0]=	"<del style=\"color:burlywood\">"			;tags[1]=	"</del>"			;break;
    		case GROUP_ARCHIVE:		tags[0]=	"<i style=\"color:red\">"					;tags[1]=	"</i>"				;break;
    		case GROUP_JOIN:		tags[0]=	"<small><i style=\"color:green\">"			;tags[1]=	"</i></small>"		;break;
    		case GROUP_LEAVE:		tags[0]=	"<small><i style=\"color:FireBrick\">"		;tags[1]=	"</i></small>"		;break;
    		case GROUP_PURPOSE:		tags[0]=	"<i style=\"color:SlateGray\">"				;tags[1]=	"</i>"				;break;
    		case GROUP_TOPIC:		tags[0]=	"<i style=\"color:LightCoral\">"			;tags[1]=	"</i>"				;break;
    		case GROUP_UNARCHIVE:	tags[0]=	"<i style=\"color:SeaGreen\">"				;tags[1]=	"</i>"				;break;
    	}
    	return tags;
    }
    
    public static final String[] HIDDEN_STYLE = { "<sup>Hidden</sup> <small><i style=\"color:#D8D8D8\">", "</i></small>" };

    @Override
    public String toString() {
        return text;
    }
}
