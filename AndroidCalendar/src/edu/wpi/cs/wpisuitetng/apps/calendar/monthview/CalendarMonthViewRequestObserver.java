package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import com.google.gson.Gson;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class CalendarMonthViewRequestObserver implements RequestObserver {

	private CalendarMonthViewActivity controller;
	
	public CalendarMonthViewRequestObserver(
			CalendarMonthViewActivity calendarMonthViewActivity) {
		controller = calendarMonthViewActivity;
	}

	@Override
	public void fail(IRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.out.println("FAIL in Calendar Month View Request Observer");
	}

	@Override
	public void responseError(IRequest arg0) {
		// TODO Auto-generated method stub
		System.out.println("ERROR in Calendar Month View Request Observer");
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		AndroidCalendarEvent[] events = new Gson().fromJson(iReq.getResponse().getBody(), AndroidCalendarEvent[].class);
        controller.updateAllEventsList(events);
	}

}
