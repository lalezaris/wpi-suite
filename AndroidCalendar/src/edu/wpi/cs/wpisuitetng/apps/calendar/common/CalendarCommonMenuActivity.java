
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
 * @author Nathan Longnecker
 *
 */
@SuppressLint("ShowToast")
public abstract class CalendarCommonMenuActivity extends Activity {
	
	public static final String CALLING_ACTIVITY = "edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity.CALLING_ACTIVITY";
	protected String previousActivity = "";
	private Toast toast;
	
	/* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
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

	protected void startView(Class<?> view) {
		final Intent intent = new Intent(this, view);
		
		//TODO Put Extra if there is a date selected
		
		//Starts the next activity
		startActivity(intent);
		//Ends this activity so it stops using system resources.
		finish();
	}
	
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
	
	public void sendToastMessage(String message) {
		toast.setText(message);
		toast.show();
	}
}
