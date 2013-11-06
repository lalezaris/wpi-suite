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

/**
 * Observes the get post board messages request to the core, and informs the PostBoardActivity of the request result
 * 
 * @author Nathan Longnecker
 * @version Oct 13, 2013
 */
public class GetAndroidMessagesRequestObserver implements RequestObserver {
	
	PostBoardActivity controller;
	
	/**
	 * Constructor
	 * @param controller The parent activity that handles the post board
	 */
	public GetAndroidMessagesRequestObserver(PostBoardActivity controller){
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.refreshFail("Refresh Failed!\n" + exception.toString());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		controller.refreshFail("Refresh Failed!\n" + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final PostBoardMessage[] messages = PostBoardMessage.fromJsonArray(iReq.getResponse().getBody());
		controller.receivedMessages(messages);
	}

}
