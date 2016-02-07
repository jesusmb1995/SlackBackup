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
public enum Methods {
	
	USERS_LIST("users.list"),
	IM_LIST("im.list"),
	IM_HISTORY("im.history"),
	GROUPS_LIST("groups.list"),
	GROUPS_HISTORY("groups.history"),
	CHANNELS_LIST("channels.list"),
	CHANNELS_HISTORY("channels.history"),
	FILES_LIST("files.list"),
	AUTH("auth.test")
	;
	
    private final String text;

    private Methods(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
