/**
 * 
 */
package edu.wpi.cs.wpisuitetng.marvin.loginactivity;

/**
 * @author Sam Lalezari
 *
 */
public class MarvinUserData {

	private static String username;
	private static String project;

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
