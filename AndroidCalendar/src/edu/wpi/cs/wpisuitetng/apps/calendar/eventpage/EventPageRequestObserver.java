/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * @author Sam Lalezari
 *
 */
public class EventPageRequestObserver implements RequestObserver {
	
	private CalendarCommonMenuActivity activity;

	public EventPageRequestObserver(CalendarCommonMenuActivity activity) {
		this.activity = activity;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		if(activity != null)
			activity.sendToastMessage("Unable to save event.");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest arg0) {
		if(activity != null)
			activity.sendToastMessage("An error occurred.");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		if(activity != null)
			activity.sendToastMessage("Event saved!");
	}

}
