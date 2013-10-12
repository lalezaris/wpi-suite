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
