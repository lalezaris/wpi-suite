/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Mark Fitzgibbon
 * 		Sam Lalezari
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.loginactivity;

import java.util.List;
import edu.wpi.cs.postboard.PostBoardActivity;
import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The login Activity. Handles logging into the WPI Suite core, then starts the postboard activity
 * 
 * @author Mark Fitzgibbon
 * @author Sam Lalezari
 * @author Nathan Longnecker
 * @version Oct 13, 2013
 */
@SuppressLint("ShowToast")
public class LoginControllerActivity extends FragmentActivity {
	EditText usernameField;
	EditText passwordField;
	EditText projectField;
	EditText serverUrlField;
	public static final String USERNAME = "edu.wpi.cs.loginactivity.USERNAME";
	public static final String PASSWORD = "edu.wpi.cs.loginactivity.PASSWORD";
	public static final String SERVERURL = "edu.wpi.cs.loginactivity.SERVERURL";
	TextView responseText;
	Toast toast;
	
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);
		
		usernameField = (EditText) findViewById(R.id.username_text);
		passwordField = (EditText) findViewById(R.id.password_text);
		projectField = (EditText) findViewById(R.id.project_text);
		serverUrlField = (EditText) findViewById(R.id.server_text);
		responseText = (TextView) findViewById(R.id.responseText);

		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
    }
    
    //May also be triggered from the Activity
	/**
     * Logs the user in to the core
     *
     * @param v the parent view
     */
    public void login(View v) {
    	
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration(serverUrlField.getText().toString()));
		
		// Form the basic auth string
		String basicAuth = "Basic ";
		final String password = passwordField.getText().toString();
		final String credentials = usernameField.getText().toString() + ":" + password;
		basicAuth += Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

		// Create and send the login request
		final Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
		request.addHeader("Authorization", basicAuth);
		request.addObserver(new LoginRequestObserver(this));
		
		responseText.setText("Sending Login Request...");
		
		request.send();
	}

	/**
	 * Called when the login is successful.
	 *
	 * @param response the ResponseModel from the request observer
	 */
	public void loginSuccess(ResponseModel response) {
		// Save the cookies
		final List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String[] cookieParts;
		String[] cookieNameVal;
		if (cookieList != null) { // if the server returned cookies
			for (String cookie : cookieList) { // for each returned cookie
				cookieParts = cookie.split(";"); // split the cookie
				if (cookieParts.length >= 1) { // if there is at least one part to the cookie
					cookieNameVal = cookieParts[0].split("="); // split the cookie into its name and value
					if (cookieNameVal.length == 2) { // if the split worked, add the cookie to the default NetworkConfiguration
						NetworkConfiguration config = Network.getInstance().getDefaultNetworkConfiguration();
						config.addCookie(cookieNameVal[0], cookieNameVal[1]);
						Network.getInstance().setDefaultNetworkConfiguration(config);
					}
					else {
						System.err.println("Received unparsable cookie: " + cookie);
					}
				}
				else {
					System.err.println("Received unparsable cookie: " + cookie);
				}
			}
			
			System.out.println(Network.getInstance().getDefaultNetworkConfiguration().getRequestHeaders().get("cookie").get(0));
	
			// Select the project
			final Request projectSelectRequest = Network.getInstance().makeRequest("login", HttpMethod.PUT);
			projectSelectRequest.addObserver(new AndroidProjectSelectRequestObserver(this));
			projectSelectRequest.setBody(projectField.getText().toString());
			projectSelectRequest.send();
		}
		else {
			//TODO Could not login No Cookies
		}
	}

	/**
	 * Called by the LoginRequestObserver when the login fails
	 * @param errorMessage The error message to display
	 */
	public void loginFail(final String errorMessage) {
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText(errorMessage);
			}
		});
	}
	
	/**
	 * Called by the AndroidProjectSelectRequestObserver when project selection is successful
	 * @param response The response from the server
	 */
	public void projectSelectSuccessful(ResponseModel response) {
		// Save the cookies
		final List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String[] cookieParts;
		String[] cookieNameVal;
		if (cookieList != null) { // if the server returned cookies
			for (String cookie : cookieList) { // for each returned cookie
				cookieParts = cookie.split(";");
				if (cookieParts.length >= 1) {
					cookieNameVal = cookieParts[0].split("=");
					if (cookieNameVal.length == 2) {
						NetworkConfiguration config = Network.getInstance().getDefaultNetworkConfiguration();
						config.addCookie(cookieNameVal[0], cookieNameVal[1]);
						Network.getInstance().setDefaultNetworkConfiguration(config);
					}
					else {
						System.err.println("Received unparsable cookie: " + cookie);
					}
				}
				else {
					System.err.println("Received unparsable cookie: " + cookie);
				}
			}

			System.out.println(Network.getInstance().getDefaultNetworkConfiguration().getRequestHeaders().get("cookie").get(0));
		}
		else {
			//TODO Project selection failed
		}
		
		
		final String username = usernameField.getText().toString();
		final String password = passwordField.getText().toString();
		final String server = serverUrlField.getText().toString();
		
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText("Login Successful!");
			}
		});
		
		final Intent intent = new Intent(this, PostBoardActivity.class);
		intent.putExtra(USERNAME, username);
		intent.putExtra(PASSWORD, password);
		intent.putExtra(SERVERURL, server);
		startActivity(intent);
	}

	/**
	 * Called by the AndroidProjectSelectRequestObserver when the project selection fails
	 * @param errorMessage The error message to display
	 */
	public void projectSelectFailed(final String errorMessage) {
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText(errorMessage);
			}
		});
	}
} 