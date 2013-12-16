package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents.DayEventSurfaceView;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


public class CalendarDayViewRequestObserver implements RequestObserver {

	private CalendarDayViewActivity controller;
	
	public CalendarDayViewRequestObserver(CalendarDayViewActivity controller) {
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

		AndroidCalendarEvent[] events = new Gson().fromJson(iReq.getResponse().getBody(), AndroidCalendarEvent[].class);
        final ArrayList<AndroidCalendarEvent> ev = new ArrayList<AndroidCalendarEvent>();
        for(AndroidCalendarEvent e : events){
        	ev.add(e);
        }
		
		
		controller.runOnUiThread(new Runnable() {
            public void run() {
            	controller.setContentView(new DayEventSurfaceView(controller, ev, new GregorianCalendar(Calendar.getInstance().get(GregorianCalendar.YEAR), Calendar.getInstance().get(GregorianCalendar.MONTH), Calendar.getInstance().get(GregorianCalendar.DATE), 0, 0)));
            }
		});
		
	}

}
