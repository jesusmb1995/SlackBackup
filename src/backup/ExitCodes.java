package backup;

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
public class ExitCodes {

	public static final byte 
		EX_OK 		=	0,
		EX_USAGE	=	64,
		EX_NOINPUT	= 	66,
		EX_SOFTWARE	= 	70,
		EX_IOERR	= 	74;

	public static void exit(byte ExitCode, String... extra) {
		switch(ExitCode) 
		{
			case ExitCodes.EX_USAGE:
				printError(	"Incorrect usage."										,
							"Try 'java -jar SlackBackup.jar --help'."				,
							extra													);
				break;
			case ExitCodes.EX_NOINPUT:
				printError(	"Can not read the input folder."						,
							"The input folder did not exist or was not readable."	,
							extra													);
				break;	
			case ExitCodes.EX_SOFTWARE:
				printError(	"Error while processing data from server."				,
							"Report the problem to the program's author."			,
							extra													);
				break;
			case ExitCodes.EX_IOERR:
				printError(	"Error trying to write or read."						,
							null													,
							extra													);
				break;
		}
		
		System.exit(ExitCode);
	}
	
	
	public static void printError(String description, String extra, String... extraMessages) {
		System.err.println("SlackBackup: " + description);
		if(extra != null) System.err.println("SlackBackup+: " + extra);
		for(String extraM : extraMessages)
			System.err.println("SlackBackup+: " + extraM);
	}
	
}
