package edu.wpi.cs.wpisuitetng.apps.calendar.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartupActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Intent intent = new Intent(this, edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.class);
		
		/**Change this to point to the activity that you want to start when marvin has finished with login**/
		Class<?> activity = edu.wpi.cs.wpisuitetng.apps.calendar.CalendarActivity.class;
		
		intent.putExtra(edu.wpi.cs.wpisuitetng.marvin.loginactivity.LoginControllerActivity.DEFAULT_ACTIVITY, activity);
		startActivity(intent);
	}
}
