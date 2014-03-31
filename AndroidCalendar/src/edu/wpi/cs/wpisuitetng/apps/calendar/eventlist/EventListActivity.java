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

/**
 * The activity for the eventlist page
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class EventListActivity extends CalendarCommonMenuActivity {

	private ArrayAdapter<AndroidCalendarEvent> adapter;
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();;
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		final EventListFragment listFragment = (EventListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
		
		//Requests all events that the current user is a member of
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new EventListRequestObserver(this));
		request.send();
		
		//Binds the list of events to the view so updates to the list can be displayed in the view
		adapter = new ArrayAdapter<AndroidCalendarEvent>(this, android.R.layout.simple_list_item_1, events);
		listFragment.setListAdapter(adapter);
	}

	/**
	 * Updates the event list. Called from the EventListRequestObserver.
	 * This method must be called from the UI thread.
	 * @param events The new list of events to add to the list
	 */
	public void updateEventList(final AndroidCalendarEvent[] events) {
		this.events.clear();
		adapter.notifyDataSetChanged();
		this.events.addAll(Arrays.asList(events));
		adapter.notifyDataSetChanged();
	}
}
