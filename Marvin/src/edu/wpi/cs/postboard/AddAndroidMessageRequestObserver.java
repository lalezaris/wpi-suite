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
package edu.wpi.cs.postboard;

import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AddAndroidMessageRequestObserver implements RequestObserver {
	
	PostBoardActivity controller;

	/**
	 * Constructor
	 * @param postBoardActivity The parent activity that handles the post board
	 */
	public AddAndroidMessageRequestObserver(PostBoardActivity postBoardActivity) {
		controller = postBoardActivity;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.refreshFail("Submit Message Failed!\n" + exception.toString());
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.addMessageFail("Submit Message Failed!\n" + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message out of the response body
		final String message = PostBoardMessage.fromJson(response.getBody()).toString();
		
		// Pass the messages back to the controller
		controller.addMessageToModel(message);
	}

}
