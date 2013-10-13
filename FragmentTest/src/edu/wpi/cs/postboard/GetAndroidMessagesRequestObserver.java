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

public class GetAndroidMessagesRequestObserver implements RequestObserver {
	
	PostBoardActivity controller;
	
	public GetAndroidMessagesRequestObserver(PostBoardActivity controller){
		this.controller = controller;
	}
	@Override
	public void fail(IRequest iReq, Exception exception) {
		controller.refreshFail("Refresh Failed!\n" + exception.toString());
		
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.refreshFail("Refresh Failed!\n" + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
		
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		PostBoardMessage[] messages = PostBoardMessage.fromJsonArray(iReq.getResponse().getBody());
		controller.receivedMessages(messages);
	}

}
