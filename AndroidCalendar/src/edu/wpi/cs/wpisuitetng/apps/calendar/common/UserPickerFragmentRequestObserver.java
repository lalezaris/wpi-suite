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
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.Arrays;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Observes request for all users to the server.
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class UserPickerFragmentRequestObserver implements RequestObserver {
	
	private UserPickerFragment controller;
	private CalendarCommonMenuActivity activity;

	/**
	 * Constructor.
	 * @param controller The fragment to notify when the request is completed
	 * @param activity The activity that the controller is a part of
	 */
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
