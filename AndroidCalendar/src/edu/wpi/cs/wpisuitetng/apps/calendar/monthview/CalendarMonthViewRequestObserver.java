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

import com.google.gson.Gson;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** The request observer for the calendar month view activity
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class CalendarMonthViewRequestObserver implements RequestObserver {

	private final CalendarMonthViewActivity controller;
	
	/** The constructor.
	 * @param calendarMonthViewActivity The activity to alert when the request has finished
	 */
	public CalendarMonthViewRequestObserver(
			CalendarMonthViewActivity calendarMonthViewActivity) {
		controller = calendarMonthViewActivity;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		if(controller != null){
			controller.sendToastMessage("Error retrieving events");
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest arg0) {
		if(controller != null){
			controller.sendToastMessage("Error retrieving events");
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final AndroidCalendarEvent[] events = new Gson().fromJson(iReq.getResponse().getBody(), AndroidCalendarEvent[].class);
        controller.updateAllEventsList(events);
	}

}
