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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.DayEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


/** RequestObserver for individual days.
 * @author Mark Fitzgibbon
 *
 */
public class CalendarDayViewRequestObserver implements RequestObserver {

	private final CalendarDayViewActivity controller;
	
	/** Set the controller
	 * @param controller
	 */
	public CalendarDayViewRequestObserver(CalendarDayViewActivity controller) {
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.out.println("EventlistRequest Failed");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest arg0) {
		// TODO Auto-generated method stub
		System.out.println("EventlistRequest Error");

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub

		final AndroidCalendarEvent[] events = new Gson().fromJson(iReq.getResponse().getBody(), AndroidCalendarEvent[].class);
        final List<AndroidCalendarEvent> ev = new ArrayList<AndroidCalendarEvent>();
        for(AndroidCalendarEvent e : events){
        	if(e.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            	ev.add(e);
        	}
        }
		
		controller.runOnUiThread(new Runnable() {
            public void run() {
            	controller.setContentView(new DayEventSurfaceView(controller, ev, new GregorianCalendar(Calendar.getInstance().get(java.util.Calendar.YEAR), Calendar.getInstance().get(java.util.Calendar.MONTH), Calendar.getInstance().get(java.util.Calendar.DATE), 0, 0)));
            }
		});
		
	}

}
