/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
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

/**
 * @author Nathan Longnecker
 *
 */
public abstract class AbstractEventPage extends CalendarCommonMenuActivity implements Observer {

	protected AndroidCalendarEvent currentEvent;
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showStartTimePickerDialog(View v) {
	    TimePickerFragment startTimeFrag = new TimePickerFragment(currentEvent, EventAttributes.Start);
	    startTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showStartDatePickerDialog(View v) {
	    DatePickerFragment startDateFrag = new DatePickerFragment(currentEvent, EventAttributes.Start);
	    startDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showEndTimePickerDialog(View v) {
	    TimePickerFragment endTimeFrag = new TimePickerFragment(currentEvent, EventAttributes.End);
	    endTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showEndDatePickerDialog(View v) {
	    DatePickerFragment endDateFrag = new DatePickerFragment(currentEvent, EventAttributes.End);
	    endDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the attendees picker dialog
	 * @param v the current view
	 */
	public void showAttendeesPickerDialog(View v) {
		UserPickerFragment attendees = new UserPickerFragment(currentEvent, MarvinUserData.getUsername());
	    attendees.show(getFragmentManager(), "userPicker");
	}
	
	public void saveEvent(View v) {
		EditText title = (EditText) findViewById(R.id.event_title_field);
		currentEvent.setEventTitle(title.getText().toString());
		EditText location = (EditText) findViewById(R.id.location_field);
		currentEvent.setLocation(location.getText().toString());
		EditText description = (EditText) findViewById(R.id.description_field);
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
	@Override
	public void update(Observable observable, Object data) {
		currentEvent = (AndroidCalendarEvent) observable;
		EventAttributes attributes = (EventAttributes) data;
		
		switch(attributes) {
		case Attendees:
			updateAttendeesList(currentEvent.getEventOwner(), currentEvent.getAttendees());
			break;
		case End:
			updateEventEnd(currentEvent.getEndDateAndTime());
			break;
		case Start:
			updateEventStart(currentEvent.getStartDateAndTime());
			break;
		default:
			updateEventTitle(currentEvent.getEventTitle());
			updateEventStart(currentEvent.getStartDateAndTime());
			updateEventEnd(currentEvent.getEndDateAndTime());
			updateAttendeesList(currentEvent.getEventOwner(), currentEvent.getAttendees());
			updateEventLocation(currentEvent.getLocation());
			updateEventDescription(currentEvent.getDescription());
			break;
		}
	}

	private void updateEventTitle(String eventTitle) {
		EditText title = (EditText) findViewById(R.id.event_title_field);
		title.setText(eventTitle);
	}
	
	private void updateEventStart(Calendar startDateAndTime) {
		Button startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		updateButtonDateText(startDatePickerButton, "Start Date", startDateAndTime);
		
		Button startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		updateButtonTimeText(startTimePickerButton, "Start Time", startDateAndTime);
	}
	
	private void updateEventEnd(Calendar endDateAndTime) {
		Button endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		updateButtonDateText(endDatePickerButton, "End Date", endDateAndTime);
		
		Button endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		updateButtonTimeText(endTimePickerButton, "End Time", endDateAndTime);
	}
	
	private void updateButtonDateText(Button button, String buttonText, Calendar time) {
		String monthString = "";
		int day = time.get(Calendar.DAY_OF_MONTH);
		int year = time.get(Calendar.YEAR);

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

	private void updateButtonTimeText(Button button, String buttonText, Calendar time) {
		int minute = time.get(Calendar.MINUTE);
		String minuteString;
		if (minute < 10){
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}
		
		if(DateFormat.is24HourFormat(this)){
			int hour = time.get(Calendar.HOUR_OF_DAY);
			button.setText(buttonText + ": " + hour + ":" + minuteString);
		} else {
			int hour = time.get(Calendar.HOUR);
			
			String amOrPm = "PM";
			if(time.get(Calendar.AM_PM) == Calendar.AM) {
				amOrPm = "AM";
			}
			button.setText(buttonText + ": " + hour + ":" + minuteString + " " + amOrPm);
		}
	}

	private void updateAttendeesList(String eventOwner, List<String> selectedUsers) {
		TextView attendeesList = (TextView) findViewById(R.id.attendees_text_view);
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

	private void updateEventLocation(String eventLocation) {
		EditText location = (EditText) findViewById(R.id.location_field);
		location.setText(eventLocation);
	}

	private void updateEventDescription(String eventDescription) {
		EditText description = (EditText) findViewById(R.id.description_field);
		description.setText(eventDescription);
	}

}
