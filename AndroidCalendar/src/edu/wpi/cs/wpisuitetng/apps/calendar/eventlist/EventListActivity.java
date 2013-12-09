package edu.wpi.cs.wpisuitetng.apps.calendar.eventlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class EventListActivity extends CalendarCommonMenuActivity {

	private ArrayAdapter<AndroidCalendarEvent> adapter;
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		EventListFragment listFragment = (EventListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
		
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new EventListRequestObserver(this));
		request.send();
		
		adapter = new ArrayAdapter<AndroidCalendarEvent>(this, android.R.layout.simple_list_item_1, events);
		listFragment.setListAdapter(adapter);
	}

	public void updateEventList(final AndroidCalendarEvent[] events) {
		this.events.clear();
		adapter.notifyDataSetChanged();
		this.events.addAll(Arrays.asList(events));
		adapter.notifyDataSetChanged();
	}
}
