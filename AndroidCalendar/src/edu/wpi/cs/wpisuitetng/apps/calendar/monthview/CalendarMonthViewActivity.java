package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CalendarMonthViewActivity extends CalendarCommonMenuActivity {
	
	private ArrayAdapter<AndroidCalendarEvent> adapter;
	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	List<AndroidCalendarEvent> events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_month_view);
		
		ListView list = (ListView)findViewById(R.id.list_view);
		CalendarView calendar = (CalendarView)findViewById(R.id.calendar_view);
		
		events = new ArrayList<AndroidCalendarEvent>();
		
		events.add(new AndroidCalendarEvent("Pizza party", new GregorianCalendar(2013, 12, 2),
				new GregorianCalendar(), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));
		
		events.add(new AndroidCalendarEvent("Ice Cream party", new GregorianCalendar(2013, 12, 3),
				new GregorianCalendar(), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));
		
		events.add(new AndroidCalendarEvent("MQP Meeting", new GregorianCalendar(2013, 12, 2),
				new GregorianCalendar(), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));
		
		events.add(new AndroidCalendarEvent("Last Day of Classes", new GregorianCalendar(2013, 12, 19),
				new GregorianCalendar(), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));
		
		allEvents.addAll(events);
		
		adapter = new ArrayAdapter<AndroidCalendarEvent>(this, android.R.layout.simple_list_item_1, events);
		list.setAdapter(adapter);
		
		CalendarViewDateChangeListener listener = new CalendarViewDateChangeListener(this);
		
		calendar.setOnDateChangeListener(listener);
	}

	public void onSelectedDayChange(final int year, final int month, final int dayOfMonth) {
		System.out.println("Date Changed!");
		ArrayList<AndroidCalendarEvent> dayEvents = new ArrayList<AndroidCalendarEvent>();
		for(AndroidCalendarEvent event : allEvents) {
			if(event.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
				dayEvents.add(event);
				System.out.println(event.getEventTitle() + " is today");
			}
			else {
				System.out.println(event.getEventTitle() + " is not today");
			}
		}
		
		final List<AndroidCalendarEvent> todaysEvents = dayEvents;
		runOnUiThread(new Runnable() {
            public void run() {
            	events.clear();
            	events.addAll(todaysEvents);
            	adapter.notifyDataSetChanged();
            }
		});
	}

}
