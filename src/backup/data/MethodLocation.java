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
public class MethodLocation {
	public static final String url = "https://slack.com/api/";
	private StringBuilder builder;
	
	public MethodLocation(Token token, Methods method) {
		builder = new StringBuilder(MethodLocation.url);
		builder.append(method.toString());
		builder.append('?');
		builder.append(token.toString());
	}
	
	public void addParam(Param param) {
		builder.append('&');
		builder.append(param.toString());
	}
	
	public void addParam(String param) {
		builder.append('&');
		builder.append(param);
	}
	
	public String toString() {
		return builder.toString();
	}
}
