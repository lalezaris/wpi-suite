/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.alerts.AlarmService;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventListFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The calendar month view, also the home page of the app
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class CalendarMonthViewActivity extends CalendarCommonMenuActivity {
	
	private ArrayAdapter<AndroidCalendarEvent> adapter;
	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();
	private CalendarView calendar;
	private EventListFragment listFragment;
	private int currentMonth;
	private int currentDayOfMonth;

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_month_view);
		
		final FragmentManager fragManager = getFragmentManager();
		
		listFragment = (EventListFragment) fragManager.findFragmentById(R.id.list_fragment);
		calendar = (CalendarView)findViewById(R.id.calendar_view);
		
		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		sendRequestForAllEventsInMonth(currentMonth);
		
		adapter = new ArrayAdapter<AndroidCalendarEvent>(this, android.R.layout.simple_list_item_1, events);
		listFragment.setListAdapter(adapter);
		
		final CalendarViewDateChangeListener listener = new CalendarViewDateChangeListener(this);
		
		calendar.setOnDateChangeListener(listener);
		
	}

	/**
	 * Requests all events in the current month
	 * @param month The number of the current month
	 */
	public void sendRequestForAllEventsInMonth(int month) {
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/"/*startmonth/" + month*/, HttpMethod.GET);
		request.addObserver(new CalendarMonthViewRequestObserver(this));
		request.send();
	}

	/** Updates the list of events
	 * @param events The list of events to update
	 */
	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));

		filterTodaysEvents();
		updateNotifications();
	}

	/** Updates the list of notifications
	 *  Checks the events, and updates the Android notifications 
	 */
	private void updateNotifications() {
		
		List<AndroidCalendarEvent> notifyEvents = new ArrayList<AndroidCalendarEvent>();
		
		notifyEvents = getFutureEvents();
		notifyEvents = getEventsWithAlert(notifyEvents);
		
		System.out.println("in updateNotifications()");
		for(AndroidCalendarEvent e : notifyEvents){
			System.out.println("in updateNotifications() for loop: e: " + e.getEventTitle());
			List<AndroidCalendarEvent> l = new ArrayList<AndroidCalendarEvent>();
					l.add(e);
			AlarmService as = new AlarmService(this, l);
			as.startAlarm();
		}
	}

	private List<AndroidCalendarEvent> getEventsWithAlert(
			List<AndroidCalendarEvent> notifyEvents) {
		
		final List<AndroidCalendarEvent> eventlist = new ArrayList<AndroidCalendarEvent>();
		
		for(AndroidCalendarEvent e : notifyEvents){
			if(!e.getAlerts().isEmpty()){
				eventlist.add(e);
			}
		}
		
		return eventlist;
	}

	/**
	 * Gets the events that happen in the future
	 * @return A list of events in the future
	 */
	private List<AndroidCalendarEvent> getFutureEvents() {
		final Calendar now = Calendar.getInstance();
		final List<AndroidCalendarEvent> eventlist = new ArrayList<AndroidCalendarEvent>();
		for(AndroidCalendarEvent e : allEvents){
			if(now.before(e.getStartDateAndTime())){
				eventlist.add(e);
			}
		}
		return eventlist;
	}

	/**
	 * Changes the current day
	 * @param year The year changed to
	 * @param month The month changed to
	 * @param dayOfMonth The day changed to
	 */
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

	/**
	 * Filters todays events out of the full eventlist
	 */
	private void filterTodaysEvents() {
		final List<AndroidCalendarEvent> dayEvents = new ArrayList<AndroidCalendarEvent>();
		for(AndroidCalendarEvent event : allEvents) {
			if(event.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == currentDayOfMonth && event.getStartDateAndTime().get(Calendar.MONTH) == currentMonth) {
				dayEvents.add(event);
			}
		}
		
		updateTodaysEventsDisplay(dayEvents);
	}
	
	/**
	 * Updates the UI to display today's events
	 * @param todaysEvents The events to display in the list of todays events
	 */
	public void updateTodaysEventsDisplay(final List<AndroidCalendarEvent> todaysEvents) {
		// Updates to the UI must be run on the UI thread
		runOnUiThread(new Runnable() {
            public void run() {
            	events.clear();
            	events.addAll(todaysEvents);
            	adapter.notifyDataSetChanged();
            }
		});
	}
	

}
