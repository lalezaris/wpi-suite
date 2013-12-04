/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.app.ListFragment;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

/**
 * @author Nathan Longnecker
 *
 */
public class EventListFragment extends ListFragment {
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long rowId) {
		AndroidCalendarEvent event = (AndroidCalendarEvent)l.getItemAtPosition(position);
		
		final Intent intent = new Intent(getActivity(), edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.ViewEventPage.class);
		
		intent.putExtra(AndroidCalendarEvent.ID, event.getUniqueId());

		System.out.println("getUniqueId: "+ event.getUniqueId());
		//Starts the next activity
		startActivity(intent);
	}
}
