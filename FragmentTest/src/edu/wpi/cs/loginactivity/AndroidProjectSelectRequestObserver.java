package edu.wpi.cs.loginactivity;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AndroidProjectSelectRequestObserver implements RequestObserver {

	LoginControllerActivity controller;
	
	public AndroidProjectSelectRequestObserver(
			LoginControllerActivity controller) {
		this.controller = controller;
	}

	@Override
	public void fail(IRequest arg0, Exception arg1) {
		System.out.println("project select Failed");

	}

	@Override
	public void responseError(IRequest arg0) {
		System.out.println("project select Error");
		// TODO Auto-generated method stub

	}

	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("project select Success");
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code
		if (response.getStatusCode() == 200) {
			controller.projectSelectSuccessful(response);
		}
		else { // login failed
			controller.projectSelectFailed("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}

	}

}
