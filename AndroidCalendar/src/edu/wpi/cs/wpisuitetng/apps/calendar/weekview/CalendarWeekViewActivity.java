package edu.wpi.cs.wpisuitetng.apps.calendar.weekview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.dayview.CalendarDayViewRequestObserver;
import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.WeekEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListAdapter;

public class CalendarWeekViewActivity extends CalendarCommonMenuActivity {

	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();
	private int currentWeek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		sendRequestForEventsInWeek(currentWeek);
		this.setContentView(new WeekEventSurfaceView(this, new ArrayList<AndroidCalendarEvent>()));
		
	}
	
	public void sendRequestForEventsInWeek(int week){
		Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startWeekNum/"+ week + "/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new CalendarWeekViewRequestObserver(this));
		request.send();
	}
	
	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));
		
	}
	

}