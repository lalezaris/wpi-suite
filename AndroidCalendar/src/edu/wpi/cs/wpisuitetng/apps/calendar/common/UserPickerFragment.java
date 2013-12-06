package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserPickerFragment extends DialogFragment {
	
	private final List<User> selectedUsers = new ArrayList<User>();
	
	private final List<User> allUsers = new ArrayList<User>();

	public UserPickerFragment() {
		//final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		//request.send();
		//TODO: Add current user to the list of users
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_picker, container);
		getDialog().setTitle("Attendees");
		
		final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		request.send();
		//TODO: Add listener to this request and store it in all users
		
		//TODO: Add an adapter so the users list is displayed in the attendees selector
		return view;
	}
	
	public List<User> getSelectedUsers() {
		return selectedUsers;
	}
	
	//TODO: Add the ability to delete a user by clicking on them
}
