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

import com.google.gson.Gson;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * The request observer for the eventlist activity
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class EventListRequestObserver implements RequestObserver {

	EventListActivity controller;
	
	/**
	 * Constructor
	 * @param controller The controller to notify when a request has completed
	 */
	public EventListRequestObserver(EventListActivity controller) {
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		controller.sendToastMessage("Error retrieving list of events");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest arg0) {
		controller.sendToastMessage("Error retrieving list of events");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest arg0) {
		AndroidCalendarEvent[] events = new Gson().fromJson(arg0.getResponse().getBody(), AndroidCalendarEvent[].class);
		
		final AndroidCalendarEvent[] finalEvents = events;
		controller.runOnUiThread(new Runnable() {
            public void run() {
            	controller.updateEventList(finalEvents);
            }
		});
		
	}

}
