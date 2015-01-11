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
public class File {

	private String id, name, url, type;
	private User user;
	private int size;
	
	public File(String id, String name, String url, String type, User user, int size) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.type = type;
		this.user = user;
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public User getUser() {
		return user;
	}
	
	public int getSize() {
		return size;
	}

	public String getUrl() {
		return url;
	}
}
