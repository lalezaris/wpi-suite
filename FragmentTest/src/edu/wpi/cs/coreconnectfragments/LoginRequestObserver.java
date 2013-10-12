package edu.wpi.cs.coreconnectfragments;

import android.app.Activity;
import android.content.Intent;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class LoginRequestObserver implements RequestObserver {

	MainActivity controller;
	
	public LoginRequestObserver(MainActivity controller){
		this.controller = controller;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		
		Request req = (Request) iReq;
		
		ResponseModel response = req.getResponse();
		
		// TODO Auto-generated method stub
		System.out.println("Success!");
		System.out.println(iReq.toString());
		
		if(response.getStatusCode() == 200){
			controller.loginSuccess(response);
		}
		
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		System.out.println(iReq.toString());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		System.out.println(exception);
	}

}
