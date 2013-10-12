package edu.wpi.cs.loginactivity;

import edu.wpi.cs.controlpanel.ControlPanelActivity;
import edu.wpi.cs.postboard.PostBoardActivity;
import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LoginControllerActivity extends FragmentActivity {
	EditText usernameField;
	EditText passwordField;
	EditText serverUrlField;
	public final static String USERNAME = "edu.wpi.cs.loginactivity.USERNAME";
	public final static String PASSWORD = "edu.wpi.cs.loginactivity.PASSWORD";
	public final static String SERVERURL = "edu.wpi.cs.loginactivity.SERVERURL";
	TextView responseText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);
		
		usernameField = (EditText) findViewById(R.id.username_text);
		passwordField = (EditText) findViewById(R.id.password_text);
		serverUrlField = (EditText) findViewById(R.id.server_text);
		responseText = (TextView) findViewById(R.id.responseText);
    }
    
    //May also be triggered from the Activity
	public void login(View v) {
		
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

	public void loginSuccess(ResponseModel response) {

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

	public void loginFail(final String errorMessage) {
		runOnUiThread(new Runnable() {
			public void run() {
				responseText.setText(errorMessage);
			}
		});
	}
} 