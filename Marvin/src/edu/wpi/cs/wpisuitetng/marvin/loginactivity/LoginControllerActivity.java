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
package edu.wpi.cs.wpisuitetng.marvin.loginactivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import edu.wpi.cs.marvin.R;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The login Activity. Handles logging into the WPI Suite core, then starts the main activity
 * 
 * @author Mark Fitzgibbon
 * @author Sam Lalezari
 * @author Nathan Longnecker
 * @version Oct 13, 2013
 */
public class LoginControllerActivity extends Activity {
	private EditText usernameField;
	private EditText passwordField;
	private EditText projectField;
	private EditText serverUrlField;
	private CheckBox rememberMe;
	private Intent recievedIntent;
	
//AUTO_LOGIN and DEFAULT_ACTIVITY are URIs for data being passed into Marvin from the calling intent
	public static final String AUTO_LOGIN = "edu.wpi.cs.wpisuitetng.marvin.loginactivity.AUTO_LOGIN";
	public static final String DEFAULT_ACTIVITY = "edu.wpi.cs.wpisuitetng.marvin.loginactivity.DEFAULT_ACTIVITY";
	// Name of the file which stores the users login data
	public static final String PersistentLoginFileName = "LoginData"; 
	public static final boolean persistCookies = true;
	private TextView responseText;
	Toast toast;
	
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);
        
        // Gets the boolean from the calling intent to determine if auto-login is enabled
        final boolean automaticallyLogin = getIntent().getBooleanExtra(LoginControllerActivity.AUTO_LOGIN, false);
		
		usernameField = (EditText) findViewById(R.id.username_text);
		passwordField = (EditText) findViewById(R.id.password_text);
		projectField = (EditText) findViewById(R.id.project_text);
		serverUrlField = (EditText) findViewById(R.id.server_text);
		responseText = (TextView) findViewById(R.id.responseText);
		rememberMe = (CheckBox) findViewById(R.id.rememberMe_checkBox);
		recievedIntent = getIntent();
		
		// Read the login information from the user data file 
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(openFileInput(PersistentLoginFileName));
			
			int nextChar;
			String username = "";
			while((nextChar = in.read()) != '\n') {
				username += (char)nextChar;
			}
			usernameField.setText(username);
			
			String password = "";
			while((nextChar = in.read()) != '\n') {
				password += (char)nextChar;
			}
			passwordField.setText(password); 
			
			String project = "";
			while((nextChar = in.read()) != '\n') {
				project += (char)nextChar;
			}
			projectField.setText(project);
			
			String serverUrl = "";
			while((nextChar = in.read()) != '\n') {
				serverUrl += (char)nextChar;
			}
			serverUrlField.setText(serverUrl);
			
			final boolean rememberMeIsChecked = (in.read() == 't');
			rememberMe.setChecked(rememberMeIsChecked);
			
			// Automatically logs the user in if there is user data saved, and auto-login is enabled
			if(rememberMeIsChecked && automaticallyLogin) { 
				login(null);
			}
			
		} catch (FileNotFoundException e) {
			//This exception is okay it just means that remember me was unchecked on the last login
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			responseText.setText(e.toString());
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    //May also be triggered from the Activity
	/**
     * Logs the user in to the core
     *
     * @param v the parent view
     */
    public void login(View v) {
    	
    	BufferedOutputStream out = null;
    	try {
    		if(rememberMe.isChecked()) { // If remember me is selected, save the user data for future logins
    			
    			// Insecure method of writing username/password as plain text
    			out = new BufferedOutputStream(openFileOutput(PersistentLoginFileName, Context.MODE_PRIVATE));
    			final String outputString = usernameField.getText().toString() + "\n" +
    									passwordField.getText().toString() + "\n" + 
    									projectField.getText().toString() + "\n" + 
    									serverUrlField.getText().toString() + "\n" +
    									rememberMe.isChecked() + "\n";
    			out.write(outputString.getBytes());
    		}
    		else { // Else, delete the user data if it was saved.
    			this.deleteFile(PersistentLoginFileName);
    		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			responseText.setText(e.toString());
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	final String url = serverUrlField.getText().toString();
    	try {
    		// try to convert the URL text to a URL object
    		@SuppressWarnings("unused")
			URL coreURL = new URL(url);
			
	    	// Establish a network connection with the given server URL
			Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration(url));
			
			// Form the basic authorization string
			String basicAuth = "Basic ";
			final String password = passwordField.getText().toString();
			final String credentials = usernameField.getText().toString() + ":" + password;
			basicAuth += Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

			// Create and send the login request
			final Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
			request.addHeader("Authorization", basicAuth);
			request.addObserver(new LoginRequestObserver(this));
			
			// Provide user feedback 
			responseText.setText("Sending Login Request...");
			
			// Send the login request to the server
			request.send();
		} catch (MalformedURLException e1) { // failed, bad URL
			responseText.setText("The server address \"" + url + "\" is not a valid URL!");
		} catch (Exception e) {
			responseText.setText(e.toString());
		}
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
			responseText.setText("Could not login; No Cookies");
		}
	}

	/**
	 * Called by the LoginRequestObserver when the login fails
	 * @param errorMessage The error message to display
	 */
	public void loginFail(final String errorMessage) {
		/* 
		 * The UI can not be updated by separate threads, so we must call runOnUiThread() to
		 * update the responseText. The loginFail() is not running on the UI thread, because
		 * the function is called by the LoginRequestObserver.
		 */
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
		
		/* Sets the username and password fields in the MarvinUserData class so that the 
		information can be accessed by other projects. */
		final String username = usernameField.getText().toString();
		MarvinUserData.setUsername(username);
		final String project = projectField.getText().toString();
		MarvinUserData.setProject(project);
		
		/* 
		 * The UI can not be updated by separate threads, so we must call runOnUiThread() to
		 * update the responseText. This function is not running on the UI thread, because
		 * it is called by the LoginRequestObserver.
		 */
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText("Login Successful!");
			}
		});
		
/*		Get the default starting activity from the intent passed to Marvin by the user's application. */
		if(recievedIntent.hasExtra(DEFAULT_ACTIVITY)) {
			final Bundle bundledExtras = getIntent().getExtras();
			if(bundledExtras.isEmpty()) {
				System.err.println("Bundle was empty!");
			}
			else {
				final Class<?> nextActivity = (Class<?>) bundledExtras.get(DEFAULT_ACTIVITY);
				final Intent intent = new Intent(this, nextActivity);
				startActivity(intent);
			}
		}
		else {
			System.err.println("Login Controller's Recieved Intent has no Extras!");
		}
		//Ends this activity so it stops using system resources.
		finish();
	}

	/**
	 * Called by the AndroidProjectSelectRequestObserver when the project selection fails
	 * @param errorMessage The error message to display
	 */
	public void projectSelectFailed(final String errorMessage) {
		/* 
		 * The UI can not be updated by separate threads, so we must call runOnUiThread() to
		 * update the responseText. This function is not running on the UI thread, because
		 * it is called by the LoginRequestObserver.
		 */
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText(errorMessage);
			}
		});
	}
} 