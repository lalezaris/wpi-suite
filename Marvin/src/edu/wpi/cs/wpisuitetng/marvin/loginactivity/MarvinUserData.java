/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.marvin.loginactivity;

/**
 * This class holds a user's log in information so that it 
 * can be accessed by an application.
 * 
 * @author Sam Lalezari
 * @version March 30, 2014
 */
public class MarvinUserData {

	private static String username = null;
	private static String project = null;

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	protected static void setUsername(String username) {
		MarvinUserData.username = username;
	}

	/**
	 * @return the project
	 */
	public static String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	protected static void setProject(String project) {
		MarvinUserData.project = project;
	}
}
