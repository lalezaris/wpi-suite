package edu.wpi.cs.wpisuitetng.apps.calendar.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartupActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Create a new intent to start the next activity.
		 * When invoked, this intent will start LoginControllerActivity
		 * in the Marvin Project.
		 */
		final Intent intent = new Intent(this, edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.class);
		
		/** Change this line to point to the activity that you want to start when Marvin has finished with login**/
		Class<?> activity = edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class;
		
		/* Marvin will handle logging in to the server and setting up the network connection,
		 * and then will start the Activity that we give it with putExtra().
		 */
		intent.putExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.DEFAULT_ACTIVITY, activity);
		
		//Starts the next activity
		startActivity(intent);
		
		//Ends this activity so it stops using system resources.
		finish();
	}
}