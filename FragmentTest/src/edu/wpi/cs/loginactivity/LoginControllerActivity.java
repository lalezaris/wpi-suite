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
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class LoginControllerActivity extends FragmentActivity {
	EditText usernameField;
	EditText passwordField;
	EditText projectField;
	EditText serverUrlField;
	public final static String USERNAME = "edu.wpi.cs.loginactivity.USERNAME";
	public final static String PASSWORD = "edu.wpi.cs.loginactivity.PASSWORD";
	public final static String SERVERURL = "edu.wpi.cs.loginactivity.SERVERURL";
	TextView responseText;
	Toast toast;
	
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
	public void login(View v) {
		
		if(!this.isNetworkAvailable()){
			DialogFragment bnet = new BadNetworkConnectionFragment();
			bnet.show(getSupportFragmentManager(), "BadNet");
		}
		else{
		
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration(serverUrlField.getText().toString()));
		
		// Form the basic auth string
		String basicAuth = "Basic ";
		String password = passwordField.getText().toString();
		String credentials = usernameField.getText().toString() + ":" + password;
		basicAuth += Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

		// Create and send the login request
		Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
		request.addHeader("Authorization", basicAuth);
		request.addObserver(new LoginRequestObserver(this));
		
		responseText.setText("Sending Login Request...");
		
		request.send();
		}
	}

	public void loginSuccess(ResponseModel response) {
		// Save the cookies
		List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String cookieParts[];
		String cookieNameVal[];
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
			Request projectSelectRequest = Network.getInstance().makeRequest("login", HttpMethod.PUT);
			projectSelectRequest.addObserver(new AndroidProjectSelectRequestObserver(this));
			projectSelectRequest.setBody(projectField.getText().toString());
			projectSelectRequest.send();
		}
		else {
			//TODO Could not login No Cookies
		}
		
		
	}

	public void loginFail(final String errorMessage) {
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText(errorMessage);
			}
		});
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void projectSelectSuccessful(ResponseModel response) {
		// Save the cookies
		List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String cookieParts[];
		String cookieNameVal[];
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
		
		
		String username = usernameField.getText().toString();
		String password = passwordField.getText().toString();
		String server = serverUrlField.getText().toString();
		
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText("Login Successful!");
			}
		});
		
		Intent intent = new Intent(this, PostBoardActivity.class);
		intent.putExtra(USERNAME, username);
		intent.putExtra(PASSWORD, password);
		intent.putExtra(SERVERURL, server);
		startActivity(intent);
	}

	public void projectSelectFailed(String errorMessage) {
		toast.setText(errorMessage);
		toast.show();
	}
} 