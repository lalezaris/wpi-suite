package edu.wpi.cs.wpisuitetng.apps.calendar.eventlist;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class EventListRequestObserver implements RequestObserver {

	EventListActivity controller;
	
	public EventListRequestObserver(EventListActivity controller) {
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
	public void responseSuccess(IRequest arg0) {
		// TODO Auto-generated method stub

		System.out.println("EventlistRequest Succeeded");
		AndroidCalendarEvent[] events = new Gson().fromJson(arg0.getResponse().getBody(), AndroidCalendarEvent[].class);
		
		final AndroidCalendarEvent[] finalEvents = events;
		controller.runOnUiThread(new Runnable() {
            public void run() {
            	controller.updateEventList(finalEvents);
            }
		});
		
	}

}
