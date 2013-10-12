package edu.wpi.cs.postboard;

import edu.wpi.cs.controlpanel.ControlPanelActivity;
import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.loginactivity.LoginControllerActivity;
import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage;
import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class PostBoardActivity extends Activity {
	
	private String username;
	private String password;
	private String serverUrl;
	
	PostBoardModel model;
	EditText submitMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_board);
		// Show the Up button in the action bar.
		
		submitMessage = (EditText) findViewById(R.id.submit_message);
		setupActionBar();
		
		Intent intent = getIntent();
		
		username = intent.getStringExtra(LoginControllerActivity.USERNAME);		
		password = intent.getStringExtra(LoginControllerActivity.PASSWORD);		
		serverUrl = intent.getStringExtra(LoginControllerActivity.SERVERURL);
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
		getMenuInflater().inflate(R.menu.post_board, menu);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			openControlPanel();
			return true;
		case R.id.logout_item:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openControlPanel() {
		Intent intent = new Intent(this, ControlPanelActivity.class);
		intent.putExtra(LoginControllerActivity.USERNAME, username);
		intent.putExtra(LoginControllerActivity.PASSWORD, password);
		intent.putExtra(LoginControllerActivity.SERVERURL, serverUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		
	}

	public void refresh(View v) {
		final Request request = Network.getInstance().makeRequest("postboard/postboardmessage", HttpMethod.GET); // GET == read
		request.addObserver(new GetAndroidMessagesRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	public void submit(View v) {
		String message = submitMessage.getText().toString();
		
	}
	
	public void refreshFail() {
		
	}
	
	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(PostBoardMessage[] messages) {
		// Empty the local model to eliminate duplications
		model.emptyModel();
		
		// Make sure the response was not null
		if (messages != null) {
			
			// add the messages to the local model
			//model.addMessages(messages);
		}
	}
}
