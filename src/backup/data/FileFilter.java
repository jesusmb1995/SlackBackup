package backup.data;

/**
 *  Copyright (c) 2015-2016 Jesús Martín Berlanga. All rights reserved.
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
public class FileFilter {

	private FileGroups[] groups = null;
	private String[] types = null;
	private Boolean negateTypes = null;
	private Long maxSize = null;
	private User user = null;

	public FileGroups[] getGroups() {
		return groups;
	}
	
	public User getUser() {
		return user;
	}

	public void addFilter(FileGroups[] groups) {
		this.groups = groups;
	}
	
	public void addFilter(String[] types, boolean negate) {
		this.types = types;
		this.negateTypes = negate;
	}
	
	/**
	 * @param maxSize Must be positive
	 */
	public void addFilter(Integer maxSize) {
		if(maxSize != null)
			this.maxSize = Long.valueOf(maxSize.intValue());
	}
	
	public void addFilter(User user) {
		this.user = user;
	}
	
	/**
	 * @return true If the file is OK
	 */
	public boolean accepted(File file) {	
		return 		(maxSize == null ||							file.getSize() < maxSize			) 	&&
					(types == null   || (negateTypes != null && 	acceptedType(file.getType())   ))	 ;
	}
	
	private boolean acceptedType(String type) {
		return negateTypes ? !typeIsSelected(type) : typeIsSelected(type);
	}
	
	private boolean typeIsSelected(String type) {
		boolean found = false;
		for(String selectedTypes : types)
			if(type.equals(selectedTypes)) { found = true; break; }
		return found;
	}
}
