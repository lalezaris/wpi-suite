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
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.alerts.AlertOptions;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventAttributes;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.UserPickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** The skeleton of the detailed event page, so the view event, edit event, and new event can extend it
 * @author Nathan Longnecker
 * @author Sam Lalezari
 * @version March 30, 2014
 */
public class EventPage extends CalendarCommonMenuActivity implements Observer {

	protected AndroidCalendarEvent currentEvent;
	
	/** Shows the time picker dialog
	 * @param v the current view
	 */
	public void showStartTimePickerDialog(View v) {
	    final TimePickerFragment startTimeFrag = new TimePickerFragment(currentEvent, EventAttributes.Start);
	    startTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/** Shows the date picker dialog
	 * @param v the current view
	 */
	public void showStartDatePickerDialog(View v) {
	    final DatePickerFragment startDateFrag = new DatePickerFragment(currentEvent, EventAttributes.Start);
	    startDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/** Shows the time picker dialog
	 * @param v the current view
	 */
	public void showEndTimePickerDialog(View v) {
	    final TimePickerFragment endTimeFrag = new TimePickerFragment(currentEvent, EventAttributes.End);
	    endTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/** Shows the date picker dialog
	 * @param v the current view
	 */
	public void showEndDatePickerDialog(View v) {
	    final DatePickerFragment endDateFrag = new DatePickerFragment(currentEvent, EventAttributes.End);
	    endDateFrag.show(getFragmentManager(), "datePicker");
	}

	/** Shows the alert time picker dialog
	 * @param v the current view
	 */
	public void showAlertPickerDialog(View v) {
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		final ArrayList<String> alertOptions = new ArrayList<String>();
		alertOptions.addAll(AlertOptions.stringValues());
		
		final List<AlertOptions> selectedAlerts = new ArrayList<AlertOptions>();
		selectedAlerts.addAll(currentEvent.getAlerts());
		final boolean[] checkedItems = AlertOptions.getCheckedItems(selectedAlerts);
		
		final String string = "";
		for(boolean b:checkedItems){
			string.concat(Boolean.valueOf(b).toString() + ", ");
		}
		
		
		alertDialogBuilder.setTitle("Alert Time");
		
		alertDialogBuilder.setMultiChoiceItems(alertOptions.toArray(new String[0]), checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
		
			/* (non-Javadoc)
			 * @see android.content.DialogInterface.OnMultiChoiceClickListener#onClick(android.content.DialogInterface, int, boolean)
			 */
			@Override
			public void onClick(DialogInterface arg0, int itemIndex, boolean isChecked) {
				if(isChecked){
					selectedAlerts.add(AlertOptions.getEnum(alertOptions.get(itemIndex)));
				} else if (selectedAlerts.contains(AlertOptions.getEnum(alertOptions.get(itemIndex)))) {
					selectedAlerts.remove(AlertOptions.getEnum(alertOptions.get(itemIndex)));
				}
			}
		});
		
		alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				currentEvent.setAlert(selectedAlerts);
				selectedAlerts.clear();
				currentEvent.notifyObservers(EventAttributes.Alert);
			}
		});
		alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing
			}
		});
		final AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	/** Shows the attendees picker dialog
	 * @param v the current view
	 */
	public void showAttendeesPickerDialog(View v) {
		final UserPickerFragment attendees = new UserPickerFragment(currentEvent, MarvinUserData.getUsername());
	    attendees.show(getFragmentManager(), "userPicker");
	}
	
	/**
	 * Saves the edits by sending the event to the server
	 * @param v the current view
	 */
	public void saveEvent(View v) {
		final EditText title = (EditText) findViewById(R.id.event_title_field);
		currentEvent.setEventTitle(title.getText().toString());
		final EditText location = (EditText) findViewById(R.id.location_field);
		currentEvent.setLocation(location.getText().toString());
		final EditText description = (EditText) findViewById(R.id.description_field);
		currentEvent.setDescription(description.getText().toString());
		
		if(currentEvent.getEventTitle().isEmpty()) {
			sendToastMessage("Your event must have a title!");
		}
		else if(currentEvent.getEndDateAndTime().before(currentEvent.getStartDateAndTime()) ||
				currentEvent.getEndDateAndTime().equals(currentEvent.getStartDateAndTime())) {
			sendToastMessage("Event must end after it starts");
		}
		else {
			currentEvent.deleteObservers();
			final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.POST);
			request.setBody(currentEvent.toJSON());
			request.addObserver(new EventPageRequestObserver(this));
			request.send();

			returnToPreviousActivity();
		}
	}
	
	//Update and helper methods
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable observable, Object data) {
		currentEvent = (AndroidCalendarEvent) observable;
		final EventAttributes attributes = (EventAttributes) data;
		
		switch(attributes) {
		case Attendees:
			updateAttendeesList(currentEvent.getEventOwner(), currentEvent.getAttendees());
			break;
		case End:
			updateEventEnd(currentEvent.getEndDateAndTime());
			break;
		case Start:
			updateEventStart(currentEvent.getStartDateAndTime());
			updateEventEnd(currentEvent.getEndDateAndTime());
			break;
		case Alert:
			updateEventAlert(currentEvent.getAlerts());
			break;
		default:
			updateEventTitle(currentEvent.getEventTitle());
			updateEventStart(currentEvent.getStartDateAndTime());
			updateEventEnd(currentEvent.getEndDateAndTime());
			updateAttendeesList(currentEvent.getEventOwner(), currentEvent.getAttendees());
			updateEventLocation(currentEvent.getLocation());
			updateEventDescription(currentEvent.getDescription());
			updateEventAlert(currentEvent.getAlerts());
			break;
		}
	}

	/** Updates the alert in the currentEvent object
	 * @param alerts The list of alerts to set
	 */
	private void updateEventAlert(List<AlertOptions> alerts) {
		if(alerts != null && !alerts.isEmpty()) {
			Collections.sort(alerts);
			
			System.out.println("alerts: " + alerts +"\nalerts.size(): "+ alerts.size());
			final TextView alertText = (TextView) findViewById(R.id.alert_text_view);
			String string = "Alerts: ";
			for(AlertOptions a : alerts) {
				if(a.equals(alerts.get(alerts.size()-1))){
					string += (a.toString());
				} else {
					string += (a.toString() + ", ");
				}
			}
			alertText.setText(string);
		}
	}

	/**
	 * Updates the title of the current event
	 * @param eventTitle The new title
	 */
	private void updateEventTitle(String eventTitle) {
		final EditText title = (EditText) findViewById(R.id.event_title_field);
		title.setText(eventTitle);
	}
	
	/** Updates the start time of the current event
	 * @param startDateAndTime The start time of the event
	 */
	private void updateEventStart(Calendar startDateAndTime) {
		final Button startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		updateButtonDateText(startDatePickerButton, "Start Date", startDateAndTime);
		
		final Button startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		updateButtonTimeText(startTimePickerButton, "Start Time", startDateAndTime);
	}
	
	/** Updates the end time of the current event
	 * @param endDateAndTime The end time of the event
	 */
	private void updateEventEnd(Calendar endDateAndTime) {
		final Button endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		updateButtonDateText(endDatePickerButton, "End Date", endDateAndTime);
		
		final Button endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		updateButtonTimeText(endTimePickerButton, "End Time", endDateAndTime);
	}
	
	/** Updates the text on the view based on the new times
	 * @param button The button to update
	 * @param buttonText The text of that button
	 * @param time The date to set on the button
	 */
	private void updateButtonDateText(Button button, String buttonText, Calendar time) {
		String monthString = "";
		final int day = time.get(Calendar.DAY_OF_MONTH);
		final int year = time.get(Calendar.YEAR);

		switch(time.get(Calendar.MONTH)){
		case Calendar.JANUARY: monthString = "January";
				break;
		case Calendar.FEBRUARY: monthString = "February";
				break;
		case Calendar.MARCH: monthString = "March";
				break;
		case Calendar.APRIL: monthString = "April";
				break;
		case Calendar.MAY: monthString = "May";
				break;
		case Calendar.JUNE: monthString = "June";
				break;
		case Calendar.JULY: monthString = "July";
				break;
		case Calendar.AUGUST: monthString = "August";
				break;
		case Calendar.SEPTEMBER: monthString = "September";
				break;
		case Calendar.OCTOBER: monthString = "October";
				break;
		case Calendar.NOVEMBER: monthString = "November";
				break;
		case Calendar.DECEMBER: monthString = "December";
				break;
		}

		button.setText(buttonText + ": " + monthString + " " + day + ", " + year);
	}

	/** Updates the text on the view based on the new times
	 * @param button The button to update
	 * @param buttonText The text of that button
	 * @param time The time to set on the button
	 */
	private void updateButtonTimeText(Button button, String buttonText, Calendar time) {
		final int minute = time.get(Calendar.MINUTE);
		String minuteString;
		if (minute < 10){
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}
		
		if(DateFormat.is24HourFormat(this)){
			final int hour = time.get(Calendar.HOUR_OF_DAY);
			button.setText(buttonText + ": " + hour + ":" + minuteString);
		} else {
			final int hour = time.get(Calendar.HOUR);
			
			String amOrPm = "PM";
			if(time.get(Calendar.AM_PM) == Calendar.AM) {
				amOrPm = "AM";
			}
			button.setText(buttonText + ": " + hour + ":" + minuteString + " " + amOrPm);
		}
	}

	/** Updates the list of attendees on the button
	 * @param eventOwner The current event owner
	 * @param selectedUsers The list of users going to the event
	 */
	private void updateAttendeesList(String eventOwner, List<String> selectedUsers) {
		final TextView attendeesList = (TextView) findViewById(R.id.attendees_text_view);
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

	/** Updates the location of the current event
	 * @param eventLocation The location of the event
	 */
	private void updateEventLocation(String eventLocation) {
		final EditText location = (EditText) findViewById(R.id.location_field);
		location.setText(eventLocation);
	}

	/** Updates the description of the current event
	 * @param eventDescription The new event description
	 */
	private void updateEventDescription(String eventDescription) {
		final EditText description = (EditText) findViewById(R.id.description_field);
		description.setText(eventDescription);
	}

}
