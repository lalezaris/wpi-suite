package edu.wpi.cs.coreconnectfragments;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class LoginRequestObserver implements RequestObserver {

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		System.out.println("Success!");
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		System.out.println(iReq.getBody());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		System.out.println(exception);
	}

}
