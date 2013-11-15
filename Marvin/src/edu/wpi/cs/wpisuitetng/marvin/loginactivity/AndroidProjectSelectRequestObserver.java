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
package edu.wpi.cs.wpisuitetng.marvin.loginactivity;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observes the project select request to the core, and informs the LoginControllerActivity of the request result
 * 
 * @author Nathan Longnecker
 * @version Oct 13, 2013
 */
public class AndroidProjectSelectRequestObserver implements RequestObserver {

	LoginControllerActivity controller;
	
	/**
	 * Creates a new AndroidProjectSelectRequestObserver
	 * @param controller the LoginControllerActivity to use.
	 */
	public AndroidProjectSelectRequestObserver(
			LoginControllerActivity controller) {
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.projectSelectFailed("Project Select Failed!\n" + exception.toString());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		controller.projectSelectFailed("Project Select Failed!\n" + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("project select Success");
		// cast observable to a Request
		final Request request = (Request) iReq;

		// get the response from the request
		final ResponseModel response = request.getResponse();
		
		// check the response code
		if (response.getStatusCode() == 200) {
			controller.projectSelectSuccessful(response);
		}
		else { // login failed
			controller.projectSelectFailed("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
	}

}
