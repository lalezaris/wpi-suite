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
		System.out.println("Fail");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Error");
		System.out.println(iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("Success");
		// TODO Auto-generated method stub
		PostBoardMessage[] messages = PostBoardMessage.fromJsonArray(iReq.getResponse().getBody());
		controller.receivedMessages(messages);
	}

}
