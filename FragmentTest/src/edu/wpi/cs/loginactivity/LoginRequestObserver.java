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
package edu.wpi.cs.loginactivity;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observes the login request to the core, and informs the LoginControllerActivity of the request result
 * 
 * @author Nathan Longnecker
 * @version Oct 13, 2013
 */
public class LoginRequestObserver implements RequestObserver {

	LoginControllerActivity controller;
	
	/**
	 * Constructor
	 * @param controller The parent activity that handles the login
	 */
	public LoginRequestObserver(LoginControllerActivity controller){
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		
		final Request req = (Request) iReq;
		
		final ResponseModel response = req.getResponse();
		
		if(response.getStatusCode() == 200){
			controller.loginSuccess(response);
		}
		else {
			controller.loginFail("Login Failed!\n" + response.getStatusCode() + " " + response.getStatusMessage());
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		controller.loginFail("Login Failed!\n" + response.getStatusCode() + " " + response.getStatusMessage());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.loginFail("Login Failed!\n" + exception.toString());
	}

}
