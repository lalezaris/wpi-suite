/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * @author Sam Lalezari
 *
 */
public class ViewEventPageRequestObserver implements RequestObserver {

	private ViewEventPage controller;
	public ViewEventPageRequestObserver(ViewEventPage controller) {
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest arg0) {
		Request request = (Request) arg0;
		
		ResponseModel response = request.getResponse();
		
		if(response.getStatusCode() != 200){
			controller.errorGettingEvent("Received " + request.getResponse().getStatusCode() + " error from server: " + request.getResponse().getStatusMessage());
		}
		
		final AndroidCalendarEvent[] events = new Gson().fromJson(arg0.getResponse().getBody(), AndroidCalendarEvent[].class);
		
		if(events.length != 0) {
			controller.runOnUiThread(new Runnable() {
				public void run() {
					controller.setEvent(events[0]);
				}
			});
		} else {
			System.out.println("No events returned");
		}
	}

}
