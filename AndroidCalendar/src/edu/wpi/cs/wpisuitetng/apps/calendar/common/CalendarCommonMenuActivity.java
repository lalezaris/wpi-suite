/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
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
	
	protected Class<?> previousActivity;
	protected Toast toast;
	
	/* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
    }

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
		intent.putExtra(edu.wpi.cs.wpisuitetng.apps.calendar.startup.StartupActivity.AUTO_LOGIN, "false");
		
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
		final Intent intent = new Intent(this, previousActivity);
		
		//TODO Put Extra if there is a date selected
		
		//Starts the next activity
		startActivity(intent);
		//Ends this activity so it stops using system resources.
		finish();
	}
}
