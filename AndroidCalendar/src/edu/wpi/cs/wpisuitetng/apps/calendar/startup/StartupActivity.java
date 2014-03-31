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
package edu.wpi.cs.wpisuitetng.apps.calendar.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * The startup activity. Every Android module implementing Marvin will need to implement a startup activity.
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class StartupActivity extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Create a new intent to start the next activity.
		 * When invoked, this intent will start LoginControllerActivity
		 * in the Marvin Project.
		 */
		final Intent intent = new Intent(this, edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.class);
		
		/** Change this line to point to the activity that you want to start when Marvin has finished with login **/
		final Class<?> activity = edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class;
		
		/* Marvin will handle logging in to the server and setting up the network connection,
		 * and then will start the Activity that we give it with putExtra().
		 */
		intent.putExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.DEFAULT_ACTIVITY, activity);
		
		/* If the AUTO_LOGIN extra was provided, we will use it.
		 * Otherwise, we default to allowing Marvin to login automatically.
		 */
		final boolean auto_login = getIntent().getBooleanExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.AUTO_LOGIN, true);
		
		// Add the extra to tell Marvin whether we want to allow automatic login or not.
		intent.putExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.AUTO_LOGIN, auto_login);
		
		//Starts the next activity. In this case, starts Marvin's LoginControllerActivity.
		startActivity(intent);
		
		//Ends this activity so it stops using system resources.
		finish();
	}
}
