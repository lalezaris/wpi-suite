/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
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

/**
 * The Class UserPickerFragment allows the user to pick users assigned to the 
 * WPI-Suite project. Can select one or multiple users to assign to the event.
 *
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class UserPickerFragment extends DialogFragment {
	
	private final List<String> selectedUsers = new ArrayList<String>();
	private final List<String> allUsers = new ArrayList<String>();
	private final List<String> allUnselectedUsers = new ArrayList<String>();
	private String currentUser;
	private ArrayAdapter<String> allUnselectedUsersAdapter;
	private ListView selectedUserList;
	private ArrayAdapter<String> selectedUsersAdapter;
	private AndroidCalendarEvent currentEvent;

	/**
	 * Instantiates a new user picker fragment.
	 *
	 * @param currentEvent the current event
	 * @param currentUser the current user
	 */
	public UserPickerFragment(AndroidCalendarEvent currentEvent, String currentUser) {
		this.currentUser = currentUser;
		this.currentEvent = currentEvent;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Sets the view for this fragment
		View view = inflater.inflate(R.layout.fragment_user_picker, container);
		getDialog().setTitle("Attendees");
		selectedUsers.addAll(currentEvent.getAttendees());
		
		//Requests a list of all users from the core
		final Request request = Network.getInstance().makeRequest("core/user/", HttpMethod.GET);
		request.addObserver(new UserPickerFragmentRequestObserver(this, (CalendarCommonMenuActivity) getActivity()));
		request.send();
		
		// Binds the list view to the selectedUsers list so the changes to the list are visible on the listview
		selectedUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, selectedUsers);
		selectedUserList = (ListView) view.findViewById(R.id.user_list);
		selectedUserList.setAdapter(selectedUsersAdapter);
		registerForContextMenu(selectedUserList);
		
		// Binds the unselected users list to the dropdown menu
		allUnselectedUsersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, allUnselectedUsers);
		final AutoCompleteTextView userEntry = (AutoCompleteTextView) view.findViewById(R.id.user_entry);
		userEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			/* (non-Javadoc)
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
				String selected = (String)adapterView.getItemAtPosition(position);
                userEntry.setText("");
                addSelectedUser(selected);
			}
		});
		userEntry.setAdapter(allUnselectedUsersAdapter);
		
		// When the user is done editing the list of attendees, close the dialog
		Button doneEditingAttendeesButton = (Button) view.findViewById(R.id.save_attendees_button);
		doneEditingAttendeesButton.setOnClickListener(new OnClickListener() {
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		return view;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		//Inflate the view
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
		
		OnMenuItemClickListener listener = new OnMenuItemClickListener() {
	        /* (non-Javadoc)
	         * @see android.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
	         */
	        @Override
	        public boolean onMenuItemClick(MenuItem item) {
	            onContextItemSelected(item);
	            return true;
	        }
	    };

	    //Add onClickListeners to each menu item
	    for (int i = 0, n = menu.size(); i < n; i++) {
	        menu.getItem(i).setOnMenuItemClickListener(listener);
	    }
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
    public boolean onContextItemSelected(MenuItem item) {
		//Allow the user to delete items from the list by holding down on that entry
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()) {
		case R.id.delete_entry:
			delete_entry(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
    }
	
	/**
	 * Remove users from the selected users list
	 *
	 * @param position the index of the user in the list
	 */
	private void delete_entry(int position) {
		String username = selectedUserList.getItemAtPosition(position).toString();
		removeSelectedUser(username);
	}
	
	/**
	 * Update all users list. Called by the UserPickerFragmentRequestObserver
	 * when the request to the server has been completed.
	 *
	 * @param users the users
	 */
	public void updateAllUsersList(List<User> users) {
		allUsers.clear();
		for(User u :users) {
			if(!u.getUsername().equals(currentUser)) {
				allUsers.add(u.getUsername());
			}
		}
		updateAllUnselectedUsers();
	}
	
	/**
	 * Adds the selected user to the selectedUsers list.
	 *
	 * @param user the user
	 */
	private void addSelectedUser(String user) {
		selectedUsers.add(user);
		currentEvent.setAttendees(selectedUsers);
		currentEvent.notifyObservers(EventAttributes.Attendees);
		updateAllUnselectedUsers();
	}
	
	/**
	 * Removes the selected user from the selectedUsers list.
	 *
	 * @param user the user
	 */
	private void removeSelectedUser(String user) {
		selectedUsers.remove(user);
		currentEvent.setAttendees(selectedUsers);
		currentEvent.notifyObservers(EventAttributes.Attendees);
		updateAllUnselectedUsers();
	}
	
	/**
	 * Update all unselected users.
	 */
	private void updateAllUnselectedUsers() {
		if(allUnselectedUsersAdapter != null) {
			allUnselectedUsersAdapter.clear();
			allUnselectedUsersAdapter.addAll(allUsers);
			for(String s: allUsers) {
				if(selectedUsers.contains(s) || s.equals(currentUser)) {
					allUnselectedUsersAdapter.remove(s);
				}
			}
			
			//Display the changes on the UI by scheduling the update in the UI thread
			getActivity().runOnUiThread(new Runnable() {
	            public void run() {
            		selectedUsersAdapter.notifyDataSetChanged();
	            }
			});
		}
	}
}
