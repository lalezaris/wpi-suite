package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.DayEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewRequestObserver;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.ListAdapter;

public class CalendarDayViewActivity extends CalendarCommonMenuActivity {
	
	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();
	private CalendarView calendar;
	private EventListFragment listFragment;
	private int currentMonth;
	private int currentDayOfMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fragManager = getFragmentManager();

		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		sendRequestForAllEventsInMonth(currentMonth);
		
	}
	
	public void sendRequestForAllEventsInMonth(int month) {
		Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startmonth/" + month + "/attendees/" + MarvinUserData.getUsername() , HttpMethod.GET);
		request.addObserver(new CalendarDayViewRequestObserver(this));
		request.send();
	}

	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));

		filterTodaysEvents();
		
	}

	

	private void filterTodaysEvents() {
		ArrayList<AndroidCalendarEvent> dayEvents = new ArrayList<AndroidCalendarEvent>();
		for(AndroidCalendarEvent event : allEvents) {
			if(event.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == currentDayOfMonth) {
				dayEvents.add(event);
			}
		}
		
		
	}
	
	public void updateTodaysEventsDisplay(final List<AndroidCalendarEvent> todaysEvents) {
		
		runOnUiThread(new Runnable() {
            public void run() {
            	events.clear();
            	events.addAll(todaysEvents);
            	//controller.setContentView(new BubbleSurfaceView(controller, ev));
            }
		});
	}

}
