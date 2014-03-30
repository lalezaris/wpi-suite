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
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.dayview.CalendarDayViewActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.eventlist.EventListActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.weekview.CalendarWeekViewActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Contains all the common functionality for each menu,
 * to maintain a constant menu experience.
 * 
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
@SuppressLint("ShowToast")
public abstract class CalendarCommonMenuActivity extends Activity {
	
	/** The Constant CALLING_ACTIVITY. */
	public static final String CALLING_ACTIVITY = "edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity.CALLING_ACTIVITY";
	
	protected String previousActivity = "";
	private Toast toast;
	
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        
        if(this.getIntent().hasExtra(CALLING_ACTIVITY)){
        	previousActivity = this.getIntent().getStringExtra(CALLING_ACTIVITY);
        } 
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_common_menu, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Starts an activity based on which item is selected.
		boolean selectedItem = true;
		if(!super.onOptionsItemSelected(item)) {
			switch(item.getItemId()) {
			case android.R.id.home:
				System.out.println("Calling return to previous");
				returnToPreviousActivity();
				break;
			case R.id.new_event_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.NewEventPage.class);
				break;
			case R.id.day_view_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.dayview.CalendarDayViewActivity.class);
				break;
			case R.id.week_view_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.weekview.CalendarWeekViewActivity.class);
				break;
			case R.id.month_view_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class);
				break;
			case R.id.list_view_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.eventlist.EventListActivity.class);
				break;
			case R.id.logout_item:
				logout();
				break;
			default:
				selectedItem = false;
				break;
			}
		}

		return selectedItem;
	}
	
	/**
	 * Logout.
	 */
	private void logout() {
		//Sets up the intent to start the startup activity
		final Intent intent = new Intent(this, edu.wpi.cs.wpisuitetng.apps.calendar.startup.StartupActivity.class);
		
		//Tells the startup activity not to login automatically
		intent.putExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.AUTO_LOGIN, false);
		
		//Starts the next activity
		startActivity(intent);
		//Ends this activity so it stops using system resources.
		finish();
	}

	/**
	 * Start the specified view
	 *
	 * @param view the view
	 */
	protected void startView(Class<?> view) {
		final Intent intent = new Intent(this, view);
		
		//Starts the next activity
		startActivity(intent);
		//Ends this activity so it stops using system resources.
		finish();
	}
	
	/**
	 * Return to previous activity.
	 */
	protected void returnToPreviousActivity() {
		Class<?> activity = CalendarMonthViewActivity.class;
		
		if(previousActivity.toLowerCase().equals("list")){
			activity = EventListActivity.class;
		} else if(previousActivity.toLowerCase().equals("week")){
			activity = CalendarWeekViewActivity.class;
		} else if(previousActivity.toLowerCase().equals("day")){
			activity = CalendarDayViewActivity.class;
		}
		
		final Intent intent = new Intent(this, activity);
		startActivity(intent);
		//Ends this activity so it stops using system resources.
		finish();
	}
	
	/**
	 * Send toast message.
	 *
	 * @param message the message
	 */
	public void sendToastMessage(String message) {
		toast.setText(message);
		toast.show();
	}
}
