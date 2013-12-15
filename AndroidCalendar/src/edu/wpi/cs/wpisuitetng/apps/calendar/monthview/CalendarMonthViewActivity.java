package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.alerts.MyReceiver;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.apps.calendar.startup.StartupActivity;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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
		Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startmonth/" + month, HttpMethod.GET);
		request.addObserver(new CalendarMonthViewRequestObserver(this));
		request.send();
	}

	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));

		filterTodaysEvents();
		
		updateNotifications();
	}

	/**
	 *  Checks the events, and updates the Android notifications 
	 */
	private void updateNotifications() {
		// TODO Auto-generated method stub

		Notification.Builder mBuilder = new Notification.Builder(this)
		.setSmallIcon(R.drawable.notification_icon)
		.setContentTitle("WPI Suite Calendar")
		.setContentText("Sample content text")
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
		.setVibrate(new long[]{0, 1000, 1000, 1000});
		
		Intent resultIntent = new Intent(this, StartupActivity.class);
		
		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder.setContentIntent(resultPendingIntent);
		
		int mNotificationId = 001;
		
		NotificationManager mNotifyMgr = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
//		Intent myIntent = new Intent(CalendarMonthViewActivity.this, MyReceiver.class);
//	    PendingIntent pendingIntent = PendingIntent.getBroadcast(CalendarMonthViewActivity.this, 0, myIntent,0);
//	    
//	    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//	    alarmManager.set(AlarmManager.RTC, alertCalendar.getTimeInMillis(), pendingIntent);
	   
		
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
