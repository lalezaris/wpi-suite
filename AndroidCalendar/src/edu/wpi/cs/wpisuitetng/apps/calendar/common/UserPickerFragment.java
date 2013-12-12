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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class UserPickerFragment extends DialogFragment {
	
	private final List<String> selectedUsers = new ArrayList<String>();
	private final List<String> allUsers = new ArrayList<String>();
	private final List<String> allUnselectedUsers = new ArrayList<String>();
	
	private String currentUser, eventOwner;
	private ArrayAdapter<String> allUnselectedUsersAdapter;
	private ListView selectedUserList;
	private ArrayAdapter<String> selectedUsersAdapter;
	private TextView attendeesList;

	public UserPickerFragment(TextView attendeesList, String currentUser, String eventOwner) {
		this.currentUser = currentUser;
		this.eventOwner = eventOwner;
		this.attendeesList = attendeesList;
		updateAttendeesString();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_picker, container);
		getDialog().setTitle("Attendees");
		
		final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		request.addObserver(new UserPickerFragmentRequestObserver(this));
		request.send();
		
		selectedUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, selectedUsers);
		selectedUserList = (ListView) view.findViewById(R.id.user_list);
		selectedUserList.setAdapter(selectedUsersAdapter);
		registerForContextMenu(selectedUserList);
		
		allUnselectedUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, allUnselectedUsers);
		final AutoCompleteTextView userEntry = (AutoCompleteTextView) view.findViewById(R.id.user_entry);
		userEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
				String selected = (String)adapterView.getItemAtPosition(position);
                userEntry.setText("");
                addSelectedUser(selected);
			}
		});
		userEntry.setAdapter(allUnselectedUsersAdapter);
		
		Button doneEditingAttendeesButton = (Button) view.findViewById(R.id.save_attendees_button);
		doneEditingAttendeesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		return view;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
		
		OnMenuItemClickListener listener = new OnMenuItemClickListener() {
	        @Override
	        public boolean onMenuItemClick(MenuItem item) {
	            onContextItemSelected(item);
	            return true;
	        }
	    };

	    for (int i = 0, n = menu.size(); i < n; i++) {
	        menu.getItem(i).setOnMenuItemClickListener(listener);
	    }
	}
	
	@Override
    public boolean onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()){
		case R.id.delete_entry:
			delete_entry(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
    }
	
	private void delete_entry(int position) {
		String username = selectedUserList.getItemAtPosition(position).toString();
		removeSelectedUser(username);
	}
	
	public List<String> getSelectedUsers() {
		return selectedUsers;
	}
	
	public void setSelectedUsers(List<String> users) {
		selectedUsers.clear();
		selectedUsers.addAll(users);
		updateAllUnselectedUsers();
	}
	
	public void updateAllUsersList(List<User> users) {
		allUsers.clear();
		for(User u :users) {
			if(!u.getUsername().equals(currentUser)) {
				allUsers.add(u.getUsername());
			}
		}
		updateAllUnselectedUsers();
	}
	
	private void addSelectedUser(String user) {
		selectedUsers.add(user);
		updateAllUnselectedUsers();
	}
	private void removeSelectedUser(String user) {
		selectedUsers.remove(user);
		updateAllUnselectedUsers();
	}
	
	private void updateAllUnselectedUsers() {
		if(allUnselectedUsersAdapter != null) {
			allUnselectedUsersAdapter.clear();
			allUnselectedUsersAdapter.addAll(allUsers);
			for(String s: allUsers) {
				if(selectedUsers.contains(s) || s.equals(currentUser)) {
					allUnselectedUsersAdapter.remove(s);
				}
			}
			getActivity().runOnUiThread(new Runnable() {
	            public void run() {
            		selectedUsersAdapter.notifyDataSetChanged();
            		updateAttendeesString();
	            }
			});
		}
	}
	
	private void updateAttendeesString() {
		String usersList = "Event Owner: " + eventOwner + "\nOther Attendees: ";
		for(int i = 0; i < selectedUsers.size(); i++) {
			if(i == 0) {
				usersList += selectedUsers.get(i);
			}
			else {
				usersList += ", " + selectedUsers.get(i);
			}
		}
		attendeesList.setText(usersList);
	}
}
