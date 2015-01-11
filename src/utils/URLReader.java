package utils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URL;

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
public class URLReader {
	 public static String getUrlSource(String url) throws IOException {
		 URL yahoo = new URL(url);
         URLConnection yc = yahoo.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(
                 yc.getInputStream(), "UTF-8"));
         String inputLine;
         StringBuilder a = new StringBuilder();
         while ((inputLine = in.readLine()) != null)
             a.append(inputLine);
         in.close();

         return a.toString();
     }
	 
	 public static byte[] getFileAsByteArray(String urlS, int size) throws IOException {
		 	URL url = new URL(urlS);
		    URLConnection connection = url.openConnection();
		    InputStream in = connection.getInputStream();
		    ByteArrayOutputStream tmpOut = new ByteArrayOutputStream(size);
		    
		    byte[] buf = new byte[512];
		    while (true) {
		        int len = in.read(buf);
		        if (len == -1) {
		            break;
		        }
		        tmpOut.write(buf, 0, len);
		    }
		    in.close();
		    tmpOut.close(); // No effect, but good to do anyway to keep the metaphor alive
		    
		    return tmpOut.toByteArray();
	}
}
