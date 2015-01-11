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
public enum FileGroups implements Param {
	
	ALL("all"),
	POSTS("posts"),
	SNIPPETS("snippets"),
	IMAGES("images"),
	GDOCS("gdocs"),
	ZIPS("zips"),
	PDFS("pdfs")
	;
	
    private final String name;

    private FileGroups(final String name) {
        this.name = name;
    }
    
    public static FileGroups getGroup(String group) {
    	FileGroups toEnum = null;
    	
    	for (FileGroups fGroup : FileGroups.values())
    		  if(fGroup.toString().equals(group)) {toEnum = fGroup; break;}  
    	
    	if(toEnum == null) toEnum = FileGroups.ALL;
  	
    	return toEnum;
    }
    
    /**
     * @return Null if the group is not valid, otherwise returns the group
     */
	public static FileGroups validGroup(String group) {
    	FileGroups toEnum = null;
    	for (FileGroups fGroup : FileGroups.values())
    		  if(fGroup.name.equals(group)) {toEnum = fGroup; break;}  
    	return toEnum;
	}
    
    @Override
    public String toString() {
        return "types=".concat(name);
    }
    
    public static String toString(FileGroups[] groups) {
    	StringBuilder builder = new StringBuilder("types=");
    	for(int i = 0; i < groups.length; i++) {
    		builder.append(groups[i].name);
    		if(i != groups.length -1) builder.append(",");
    	}
    	return builder.toString();
    }

}
