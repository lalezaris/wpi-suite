package edu.wpi.cs.postboard;

import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AndroidAddMessageRequestObserver implements RequestObserver {
	
	PostBoardActivity controller;

	public AndroidAddMessageRequestObserver(PostBoardActivity postBoardActivity) {
		this.controller = postBoardActivity;
	}

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
