package edu.wpi.cs.controlpanel;

import edu.wpi.cs.loginactivity.LoginControllerActivity;
import edu.wpi.cs.fragmenttest.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ControlPanelActivity extends Activity {
	
	private String username;
	private String password;
	private String serverUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);
		// Show the Up button in the action bar.
		setupActionBar();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.control_panel_layout);
		
		Intent intent = getIntent();
		
		username = intent.getStringExtra(LoginControllerActivity.USERNAME);		
		password = intent.getStringExtra(LoginControllerActivity.PASSWORD);		
		serverUrl = intent.getStringExtra(LoginControllerActivity.SERVERURL);

		TextView usernameText = new TextView(this);
		TextView passwordText = new TextView(this);
		TextView serverUrlText = new TextView(this);

		usernameText.setLayoutParams(new LinearLayout.LayoutParams(
		        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  
		passwordText.setLayoutParams(new LinearLayout.LayoutParams(
		        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  
		serverUrlText.setLayoutParams(new LinearLayout.LayoutParams(
		        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  

		usernameText.setText("Username: " + username);
		passwordText.setText("Password: " + password);
		serverUrlText.setText("Server URL: " + serverUrl);
		
		layout.addView(usernameText);
		layout.addView(passwordText);
		layout.addView(serverUrlText);
		
		setTitle(username+"'s Control Panel");
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control_panel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
//			NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
