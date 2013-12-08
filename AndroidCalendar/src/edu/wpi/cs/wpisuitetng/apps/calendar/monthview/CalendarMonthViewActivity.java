package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

public class CalendarMonthViewActivity extends CalendarCommonMenuActivity {
	
	private ArrayAdapter<AndroidCalendarEvent> adapter;
	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();
	private CalendarView calendar;
	private EventListFragment listFragment;
	private int currentMonth;
	private int currentDayOfMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_month_view);
		
		FragmentManager fragManager = getFragmentManager();
		
		listFragment = (EventListFragment) fragManager.findFragmentById(R.id.list_fragment);
		calendar = (CalendarView)findViewById(R.id.calendar_view);
		
		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		sendRequestForAllEventsInMonth(currentMonth);
		
		adapter = new ArrayAdapter<AndroidCalendarEvent>(this, android.R.layout.simple_list_item_1, events);
		listFragment.setListAdapter(adapter);
		
		CalendarViewDateChangeListener listener = new CalendarViewDateChangeListener(this);
		
		calendar.setOnDateChangeListener(listener);
	}

	public void sendRequestForAllEventsInMonth(int month) {
		Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startmonth/" + month + "/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new CalendarMonthViewRequestObserver(this));
		request.send();
	}

	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));

		filterTodaysEvents();
	}

	public void onSelectedDayChange(final int year, final int month, final int dayOfMonth) {
		currentDayOfMonth = dayOfMonth;
		if(currentMonth == month) {
			filterTodaysEvents();
		}
		else {
			currentMonth = month;
			sendRequestForAllEventsInMonth(month);
		}
	}

	private void filterTodaysEvents() {
		ArrayList<AndroidCalendarEvent> dayEvents = new ArrayList<AndroidCalendarEvent>();
		for(AndroidCalendarEvent event : allEvents) {
			if(event.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == currentDayOfMonth) {
				dayEvents.add(event);
			}
		}
		
		updateTodaysEventsDisplay(dayEvents);
	}
	
	public void updateTodaysEventsDisplay(final List<AndroidCalendarEvent> todaysEvents) {
		
		runOnUiThread(new Runnable() {
            public void run() {
            	events.clear();
            	events.addAll(todaysEvents);
            	adapter.notifyDataSetChanged();
            }
		});
	}
	

}
