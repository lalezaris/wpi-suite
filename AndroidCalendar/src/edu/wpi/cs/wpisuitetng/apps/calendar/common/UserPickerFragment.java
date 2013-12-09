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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class UserPickerFragment extends DialogFragment {
	
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
		
		final UserArrayAdapter selectedUsersAdapter = new UserArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, selectedUsers);
		ListView selectedUserList = (ListView) view.findViewById(R.id.user_list);
		selectedUserList.setAdapter(selectedUsersAdapter);

		UserArrayAdapter allUsersAdapter = new UserArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, allUsers);
		final UserAutoCompleteTextView userEntry = (UserAutoCompleteTextView) view.findViewById(R.id.user_entry);
		userEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
				User selected = (User)adapterView.getItemAtPosition(position);
                userEntry.setText(selected.getName());
                selectedUsers.add(selected);
                selectedUsersAdapter.notifyDataSetChanged();
			}
		});
		userEntry.setAdapter(allUsersAdapter);
		
		Button doneEditingAttendeesButton = (Button) view.findViewById(R.id.save_attendees_button);
		doneEditingAttendeesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		return view;
	}
	
	public List<String> getSelectedUsers() {
		List<String> usernames = new ArrayList<String>();
		
		for(User u : selectedUsers){
			usernames.add(u.getUsername());
		}
		
		return usernames;
	}
	
	public void updateAllUsersList(List<User> users) {
		allUsers.clear();
		allUsers.addAll(users); 
	}
	
	public void doneEditingAttendees(View view) {
		System.out.println("DONE EDITING");
		getDialog().dismiss();
	}
	//TODO: Add the ability to delete a user by clicking on them
}
