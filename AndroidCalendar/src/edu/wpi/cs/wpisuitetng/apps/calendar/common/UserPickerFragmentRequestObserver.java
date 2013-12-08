package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.Arrays;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UserPickerFragmentRequestObserver implements RequestObserver {
	
	private UserPickerFragment controller;
	
	public UserPickerFragmentRequestObserver(UserPickerFragment controller) {
		this.controller = controller;
	}

	@Override
	public void fail(IRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseError(IRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		User[] returnedUsers = new Gson().fromJson(iReq.getResponse().getBody(), User[].class);
        controller.updateAllUsersList(Arrays.asList(returnedUsers));
	}

}
