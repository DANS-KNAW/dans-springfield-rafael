/* 
* MediaFragmentCleanerTask.java
* 
* Copyright (c) 2013 Noterik B.V.
* 
* This file is part of Rafael, related to the Noterik Springfield project.
*
* Rafael is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Rafael is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Rafael.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.springfield.rafael.mediafragment.restlet;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import org.springfield.rafael.mediafragment.config.GlobalConfiguration;

/**
 * Cleaner that periodically cleans the generated fragments from the 
 * temporary folder
 * 
 * @author Pieter van Leeuwen
 * @copyright Copyright: Noterik B.V. 2013
 * @package org.springfield.rafael.mediafragment.restlet
 *
 */
public class MediaFragmentCleanerTask extends TimerTask {
	
	public void run() {
		GlobalConfiguration conf = GlobalConfiguration.getInstance();
		String tempPath = conf.getProperty("temp-mediafragment-path");
		long ttl = Long.parseLong(conf.getProperty("temp-mediafragment-ttl"));
		long allowedTTL = new Date().getTime() + ttl;
		
		File directory = new File(tempPath);
		if (directory.exists()) {
			File[] mediafragments = directory.listFiles();
			for (int i = 0; i < mediafragments.length; i++) {
				if (mediafragments[i].lastModified() < allowedTTL) {
					mediafragments[i].delete();
				}
			}
		}
	}
}
