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
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.WeekEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


public class CalendarWeekViewRequestObserver implements RequestObserver {

	private final CalendarWeekViewActivity controller;
	
	public CalendarWeekViewRequestObserver(CalendarWeekViewActivity controller) {
		this.controller = controller;
	}
	
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.out.println("EventlistRequest Failed");
	}

	@Override
	public void responseError(IRequest arg0) {
		// TODO Auto-generated method stub
		System.out.println("EventlistRequest Error");

	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub

		final AndroidCalendarEvent[] events = new Gson().fromJson(iReq.getResponse().getBody(), AndroidCalendarEvent[].class);
        final List<AndroidCalendarEvent> ev = new ArrayList<AndroidCalendarEvent>();
        for(AndroidCalendarEvent e : events){
        	ev.add(e);
        }
		
		
		controller.runOnUiThread(new Runnable() {
            public void run() {
            	
            	controller.setContentView(new WeekEventSurfaceView(controller, ev));
            }
		});
		
	}

}
