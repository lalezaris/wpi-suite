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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class UserPickerFragment extends DialogFragment {
	
	private final List<String> selectedUsers = new ArrayList<String>();
	
	private final List<String> allUsers = new ArrayList<String>();

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
		
		final ArrayAdapter<String> selectedUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, selectedUsers);
		ListView selectedUserList = (ListView) view.findViewById(R.id.user_list);
		selectedUserList.setAdapter(selectedUsersAdapter);

		ArrayAdapter<String> allUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, allUsers);
		final AutoCompleteTextView userEntry = (AutoCompleteTextView) view.findViewById(R.id.user_entry);
		userEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
				String selected = (String)adapterView.getItemAtPosition(position);
                userEntry.setText(selected);
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
		return selectedUsers;
	}
	
	public void setSelectedUsers(List<String> users) {
		selectedUsers.clear();
		selectedUsers.addAll(users);
	}
	
	public void updateAllUsersList(List<User> users) {
		allUsers.clear();
		for(User u :users) {
			allUsers.add(u.getUsername()); 
		}
	}
	//TODO: Add the ability to delete a user by clicking on them
}
