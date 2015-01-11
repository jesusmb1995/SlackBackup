package utils;

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
public class HTMLUtils {

	public static final String htmlUtf8Header = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" /></head>";
	
	public static String insertBody(String body) {
		return HTMLUtils.htmlUtf8Header + "<body>" + body + "</body></html>";
	}
	
	public static String getTabIndent(int indents) {
		StringBuilder indentBuilder = new StringBuilder();
		for(int i = 0; i < indents; i++)
			indentBuilder.append("&nbsp;");
		return indentBuilder.toString();
	}
	
	public static void nextTabIndent(StringBuilder builder) {
		builder.append("&nbsp;");
	}
	
	public static String addTag(String pre, String text, String post) {
		return pre + text + post;
	}
	
	public static void addTag(String pre, StringBuilder builder, String post) {
		builder.append(post).insert(0, pre);
	}

}
