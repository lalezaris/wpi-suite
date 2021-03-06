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
package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.Calendar;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;

/** The CalendarDayViewActivity class begins a view for individual days in the calendar.
 * @author Mark Fitzgibbon
 *
 */

public class CalendarDayViewActivity extends CalendarCommonMenuActivity {
	private int currentMonth;
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		sendRequestForAllEventsInMonth(currentMonth);
		
	}
	
	/** Sends a request to the server for the events in a specific month
	 * @param month
	 */
	public void sendRequestForAllEventsInMonth(int month) {
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startmonth/" + month + "/attendees/" + MarvinUserData.getUsername(), HttpMethod.GET);
		request.addObserver(new CalendarDayViewRequestObserver(this));
		request.send();
	}

}
