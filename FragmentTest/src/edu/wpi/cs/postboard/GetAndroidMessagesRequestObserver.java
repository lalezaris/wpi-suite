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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		PostBoardMessage[] messages = PostBoardMessage.fromJsonArray(iReq.getResponse().getBody());
		controller.receivedMessages(messages);
	}

}
