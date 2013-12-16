package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.Arrays;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UserPickerFragmentRequestObserver implements RequestObserver {
	
	private UserPickerFragment controller;
	private CalendarCommonMenuActivity activity;

	public UserPickerFragmentRequestObserver(UserPickerFragment controller, CalendarCommonMenuActivity activity) {
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest arg0, Exception arg1) {
		if(activity != null)
			activity.sendToastMessage("Unable to retrieve users.");
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
		User[] returnedUsers = new Gson().fromJson(iReq.getResponse().getBody(), User[].class);
		controller.updateAllUsersList(Arrays.asList(returnedUsers));
	}
}