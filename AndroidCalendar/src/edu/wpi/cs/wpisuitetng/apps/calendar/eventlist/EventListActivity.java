package edu.wpi.cs.wpisuitetng.apps.calendar.eventlist;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.layout;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.NewEventPageRequestObserver;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EventListActivity extends CalendarCommonMenuActivity {

	private ArrayAdapter adapter;
	List<String> events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		ListView list = (ListView)findViewById(R.id.list_view);
		
		events = new ArrayList<String>();
		
		events.add("Dummy Event 1");
		
		final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.GET);
		request.addObserver(new EventListRequestObserver(this));
		request.send();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events);
		list.setAdapter(adapter);
	}

	public void updateEventList(final AndroidCalendarEvent[] events) {
		this.events.clear();
		adapter.notifyDataSetChanged();
		for(AndroidCalendarEvent e : events) {
			this.events.add(e.getEventTitle());
			adapter.notifyDataSetChanged();
		}
	}
}
