/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Nathan Longnecker
 *
 */
public class CalendarCommonMenuActivity extends Activity {

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
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void logout() {
		final Intent intent = new Intent(this, edu.wpi.cs.wpisuitetng.apps.calendar.startup.StartupActivity.class);
		
		//Starts the next activity
		startActivity(intent);
	}

	private void startView(Class<?> view) {
		final Intent intent = new Intent(this, view);
		
		//TODO Put Extra if there is a date selected
		
		//Starts the next activity
		startActivity(intent);
	}
}
