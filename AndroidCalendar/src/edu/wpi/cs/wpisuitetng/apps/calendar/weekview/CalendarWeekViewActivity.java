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
package edu.wpi.cs.wpisuitetng.apps.calendar.weekview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.WeekEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;

public class CalendarWeekViewActivity extends CalendarCommonMenuActivity {

	private static final List<AndroidCalendarEvent> allEvents = new ArrayList<AndroidCalendarEvent>();
	private int currentWeek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		sendRequestForEventsInWeek(currentWeek);
		this.setContentView(new WeekEventSurfaceView(this, new ArrayList<AndroidCalendarEvent>()));
		
	}
	
	public void sendRequestForEventsInWeek(int week){
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startWeekNum/"+ week + "/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new CalendarWeekViewRequestObserver(this));
		request.send();
	}
	
	public void updateAllEventsList(AndroidCalendarEvent[] events) {
		allEvents.clear();
		allEvents.addAll(Arrays.asList(events));
		
	}
	

}