package edu.wpi.cs.loginactivity;

import edu.wpi.cs.controlpanel.ControlPanelActivity;
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


public class LoginControllerActivity extends FragmentActivity {
	EditText usernameField;
	EditText passwordField;
	EditText serverUrlField;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);
		
		usernameField = (EditText) findViewById(R.id.username_text);
		passwordField = (EditText) findViewById(R.id.password_text);
		serverUrlField = (EditText) findViewById(R.id.server_text);
    }
    
    //May also be triggered from the Activity
	public void login(View v) {
		// Create fake data
		// String newTime = String.valueOf(System.currentTimeMillis());
		
		System.out.println("UpdateDetail called!");
		
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration(serverUrlField.getText().toString()));
		
		System.out.println("Set Default Network Configuration");
		
		// Form the basic auth string
		String basicAuth = "Basic ";
		String password = passwordField.getText().toString();
		String credentials = usernameField.getText().toString() + ":" + password;
		basicAuth += Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
		System.out.println("Encoded BasicAuth: " + basicAuth);

		// Create and send the login request
		Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
		request.addHeader("Authorization", basicAuth);
		request.addObserver(new LoginRequestObserver(this));
		
		System.out.println("Sending request");
		
		request.send();
		
		System.out.println("Request sent");

		// Send data to Activity
	}

	public void loginSuccess(ResponseModel response) {
		Intent intent = new Intent(this, ControlPanelActivity.class);
		startActivity(intent);
	}
} 