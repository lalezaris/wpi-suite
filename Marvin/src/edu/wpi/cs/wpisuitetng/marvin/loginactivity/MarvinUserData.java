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
}
