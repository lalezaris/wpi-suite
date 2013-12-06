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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

public class UserPickerFragment extends DialogFragment implements OnItemSelectedListener {
	
	private final List<User> selectedUsers = new ArrayList<User>();
	
	private final List<User> allUsers = new ArrayList<User>();

	public UserPickerFragment() {
		//final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		//request.send();
		//TODO: Get the current user and add it to the list of users
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_picker, container);
		getDialog().setTitle("Attendees");
		
		final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		request.addObserver(new UserPickerFragmentRequestObserver(this));
		request.send();

		UserArrayAdapter allUsersAdapter = new UserArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, allUsers);
		UserAutoCompleteTextView userEntry = (UserAutoCompleteTextView) view.findViewById(R.id.user_entry);
		userEntry.setOnItemSelectedListener(this);
		userEntry.setAdapter(allUsersAdapter);
		
		UserArrayAdapter selectedUsersAdapter = new UserArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, selectedUsers);
		ListView selectedUserList = (ListView) view.findViewById(R.id.user_list);
		selectedUserList.setAdapter(selectedUsersAdapter);
		
		return view;
	}
	
	public List<User> getSelectedUsers() {
		return selectedUsers;
	}
	
	public void updateAllUsersList(List<User> users) {
		allUsers.clear();
		allUsers.addAll(users);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		System.out.println("OnItemSelected");
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//Do Nothing
		System.out.println("Item Not Selected");
	}
	
	//TODO: Add the ability to delete a user by clicking on them
}
