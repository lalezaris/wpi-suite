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

public class LoginRequestObserver implements RequestObserver {

	LoginControllerActivity controller;
	
	public LoginRequestObserver(LoginControllerActivity controller){
		this.controller = controller;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		
		Request req = (Request) iReq;
		
		ResponseModel response = req.getResponse();
		
		if(response.getStatusCode() == 200){
			controller.loginSuccess(response);
		}
		else {
			controller.loginFail("Login Failed!\n" + response.getStatusCode() + " " + response.getStatusMessage());
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		ResponseModel response = (ResponseModel)iReq.getResponse();
		controller.loginFail("Login Failed!\n" + response.getStatusCode() + " " + response.getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.loginFail("Login Failed!\n" + exception.toString());
	}

}
