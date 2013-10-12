package edu.wpi.cs.coreconnectfragments;

import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends FragmentActivity implements LoginFragment.OnItemSelectedListener{
	EditText usernameField;
	EditText passwordField;
	EditText serverUrlField;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		usernameField = (EditText) findViewById(R.id.username_text);
		passwordField = (EditText) findViewById(R.id.password_text);
		serverUrlField = (EditText) findViewById(R.id.server_text);
    }

    // if the wizard generated an onCreateOptionsMenu you can delete
    // it, not needed for this tutorial

  @Override
  public void onRssItemSelected(String link) {
    DetailFragment fragment = (DetailFragment) getSupportFragmentManager()
            .findFragmentById(R.id.detailFragment);
        if (fragment != null && fragment.isInLayout()) {
          fragment.setText(link);
        } 
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
		request.addObserver(new LoginRequestObserver());
		
		System.out.println("Sending request");
		
		request.send();
		
		System.out.println("Request sent");

		// Send data to Activity
	}
} 