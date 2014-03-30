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
 *******************************************************************************
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.app.ListFragment;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

/**
 * The Class EventListFragment displays a list of all events.
 * It's used under the month view and in event list view.
 *
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class EventListFragment extends ListFragment {

	/* (non-Javadoc)
	 * @see android.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long rowId) {
		AndroidCalendarEvent event = (AndroidCalendarEvent)l.getItemAtPosition(position);

		// Get the intent used for starting the next activity.
		final Intent intent = new Intent(getActivity(), edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.ViewEventPage.class);

		// Get the calling activity for the back button.
		if(this.getActivity().getLocalClassName().toLowerCase().equals("eventlist.eventlistactivity".toLowerCase())){
			intent.putExtra(CalendarCommonMenuActivity.CALLING_ACTIVITY, "list");
		}
		intent.putExtra(AndroidCalendarEvent.EVENT, event);
		
		//Starts the next activity
		startActivity(intent);
		this.getActivity().finish();
	}
}
