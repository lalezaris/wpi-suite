/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.postboard;

import edu.wpi.cs.controlpanel.ControlPanelActivity;
import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.loginactivity.LoginControllerActivity;
import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

@SuppressLint("ShowToast")
public class PostBoardActivity extends Activity {
	
	private String username;
	private String password;
	private String serverUrl;
	
	TextView model;
	EditText submitMessage;
	Toast toast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_board);
		// Show the Up button in the action bar.
		
		submitMessage = (EditText) findViewById(R.id.submit_message);
		model = (TextView) findViewById(R.id.messages_view);
		model.setMovementMethod(new ScrollingMovementMethod());
		
		setupActionBar();
		
		Intent intent = getIntent();
		
		Context context = getApplicationContext();
		CharSequence text = "Hello toast!";
		int duration = Toast.LENGTH_SHORT;

		toast = Toast.makeText(context, text, duration);
		
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
		
		// Make sure there is text
		if (message.length() > 0) {
			// Clear the text field
			submitMessage.setText("");
			
			message = username + " (from mobile): " + message;
			
			// Send a request to the core to save this message
			final Request request = Network.getInstance().makeRequest("postboard/postboardmessage", HttpMethod.PUT); // PUT == create
			request.setBody(new PostBoardMessage(message).toJSON()); // put the new message in the body of the request
			request.addObserver(new AndroidAddMessageRequestObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
	}
	
	public void refreshFail(String errorMessage) {
		toast.setText(errorMessage);
		toast.show();
	}
	
	public void addMessageFail(String errorMessage) {
		toast.setText(errorMessage);
		toast.show();
	}
	
	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(PostBoardMessage[] messages) {
		// Empty the local model to eliminate duplications
		
		// Make sure the response was not null
		if (messages != null) {
			String modelText = "";
			
			for(int i = 0; i < messages.length; i++) {
				modelText += messages[i] + "\n";
			}
			
			final String finalModelText = modelText;
			// add the messages to the local model
			runOnUiThread(new Runnable() {
				public void run() {
					model.setText(finalModelText);
				}
			});
			
			scrollToBottom();
		}
	}

	/**
	 * Scrolls the text view to the bottom when a new message is added
	 */
	private void scrollToBottom() {
		//Set scroll to the latest message
		final Layout layout = model.getLayout();
		if(layout != null){
			int scrollChange = layout.getLineBottom(model.getLineCount()-1) 
					- model.getScrollY() - model.getHeight();
			if(scrollChange >0){
				model.scrollBy(0, scrollChange);
			}
		}
	}

	public void addMessageToModel(String message) {
		String modelText = model.getText().toString();
		modelText += message + "\n";
	
		final String finalModelText = modelText;
		// add the messages to the local model
		runOnUiThread(new Runnable() {
			public void run() {
				model.setText(finalModelText);
			}
		});
		
		scrollToBottom();
	}
}
