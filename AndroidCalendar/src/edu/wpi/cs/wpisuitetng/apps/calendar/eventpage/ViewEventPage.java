package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CommonCalendarData;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.UserPickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewEventPage extends CalendarCommonMenuActivity{
	
	private Button startDatePickerButton, startTimePickerButton, endDatePickerButton, endTimePickerButton, saveEventButton, attendeesButton; //alertPickerButton,
	private EventDate startDate, endDate;
	private EventTime startTime, endTime;
	private DatePickerFragment startDateFrag, endDateFrag;
	private TimePickerFragment startTimeFrag, endTimeFrag;
	private EditText title, location, description;
	private final UserPickerFragment attendees = new UserPickerFragment(MarvinUserData.getUsername());
	private AndroidCalendarEvent event;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);
		
		this.event = CommonCalendarData.getInstance().getCurrentEvent();
		
		startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		//alertPickerButton = (Button) findViewById(R.id.alert_button);
		saveEventButton = (Button) findViewById(R.id.save_button);
		title = (EditText) findViewById(R.id.event_title_field);
		location = (EditText) findViewById(R.id.location_field);
		description = (EditText) findViewById(R.id.description_field);
		attendeesButton = (Button) findViewById(R.id.attendees_button);
		endDateFrag = new DatePickerFragment(endDatePickerButton, " End Date");
	    startTimeFrag = new TimePickerFragment(startTimePickerButton, "Start Time");
	    endTimeFrag = new TimePickerFragment(endTimePickerButton, "End Time");
	    startDateFrag = new DatePickerFragment(startDatePickerButton, "Start Date");

		switchToViewMode();
		updateFields();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_common_menu, menu);
		
		if(this.event.getEventOwner().equals(MarvinUserData.getUsername())) {
			getMenuInflater().inflate(R.menu.view_event_page, menu);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean selectedItem = true;
		if(!super.onOptionsItemSelected(item)) {
			switch(item.getItemId()) {
			case R.id.edit_event_item:
				switchToEditMode();
				break;
			case R.id.delete_current_item:
				delete();
				break;
			default:
				selectedItem = false;
				break;
			}
		}

		return selectedItem;
	}

	private void delete() {
		final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent/" + event.getUniqueId(), HttpMethod.DELETE);
		request.send();
		startView(edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class);
	}

	private void switchToViewMode() {
		startDatePickerButton.setEnabled(false);
		startTimePickerButton.setEnabled(false);
		endDatePickerButton.setEnabled(false);
		endTimePickerButton.setEnabled(false);
		//alertPickerButton.setEnabled(false);
		title.setEnabled(false);
		location.setEnabled(false);
		description.setEnabled(false);
		saveEventButton.setVisibility(Button.INVISIBLE);
		attendeesButton.setEnabled(false);
		
	}
	private void switchToEditMode() {
		startDatePickerButton.setEnabled(true);
		startTimePickerButton.setEnabled(true);
		endDatePickerButton.setEnabled(true);
		endTimePickerButton.setEnabled(true);
		//alertPickerButton.setEnabled(true);
		title.setEnabled(true);
		location.setEnabled(true);
		description.setEnabled(true);
		attendeesButton.setEnabled(true);

		saveEventButton.setVisibility(Button.VISIBLE);
		
	}

	/*
	private void getEventFromDatabase() {
		final ViewEventPageRequestObserver requestObserver = new ViewEventPageRequestObserver(this);
		if(eventId != -1){
			System.out.println("uniqueId: "+ eventId);
			final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent/" + eventId , HttpMethod.GET);
			request.addObserver(requestObserver);
			request.send();
			
		} else {
			//TODO: something to do if it can't get the id form the intent
		}
	}

	public void errorGettingEvent(String error) {
		//TODO: something to do if error getting event from database
		
	}

	public void setEvent(AndroidCalendarEvent event) {
		this.event = event;
		
		updateFields();
	}*/

	private void updateFields() {
		title.setText(event.getEventTitle());
		String time;
		
		String monthString = new DateFormatSymbols().getMonths()[event.getStartMonth()];
		startDatePickerButton.setText("Start Date: " + monthString + " " + event.getStartDay() + ", " + event.getStartYear());
		
		monthString = new DateFormatSymbols().getMonths()[event.getEndMonth()];
		endDatePickerButton.setText("End Date: " + monthString + " " + event.getEndDay() + ", " + event.getEndYear());
		
		if(DateFormat.is24HourFormat(this)){
			time = String.format("%tk:%tM", event.getStartDateAndTime(), event.getStartDateAndTime());
			startTimePickerButton.setText("Start Time: " +  time );
			time = String.format("%tk:%tM", event.getEndDateAndTime(), event.getEndDateAndTime());
			endTimePickerButton.setText("End Time: " + time );
		} else {
			time = String.format("%tl:%tM %Tp", event.getStartDateAndTime(), event.getStartDateAndTime(), event.getStartDateAndTime());
			startTimePickerButton.setText("Start Time: " + time);
			time = String.format("%tl:%tM %Tp", event.getEndDateAndTime(), event.getEndDateAndTime(), event.getStartDateAndTime());
			endTimePickerButton.setText("End Time: " +  time);
		}
		
		location.setText(event.getLocation());
		description.setText(event.getDescription());
		attendees.setSelectedUsers(event.getAttendees());
	}
	
	/**Shows the attendees picker dialog
	 * @param v the current view
	 */
	public void showAttendeesPickerDialog(View v) {
	    attendees.show(getFragmentManager(), "userPicker");
	}
	
	public void saveEvent(View v) {
		if(title.getText().toString() == null || title.getText().toString().isEmpty()) {
			sendToastMessage("Your event must have a title!");
			return;
		}
		try {
			Calendar start = event.getStartDateAndTime();
			Calendar end = event.getEndDateAndTime();
			
			EventDate startEventDate = new EventDate(event.getStartYear(), event.getStartMonth(), event.getStartDay());
			EventDate endEventDate = new EventDate(event.getEndYear(), event.getEndMonth(), event.getEndDay());
			
			EventTime startEventTime = new EventTime(event.getStartDateAndTime().get(Calendar.HOUR_OF_DAY), event.getStartDateAndTime().get(Calendar.MINUTE));
			EventTime endEventTime = new EventTime(event.getEndDateAndTime().get(Calendar.HOUR_OF_DAY), event.getEndDateAndTime().get(Calendar.MINUTE));
			
			List<String> attendeesList = event.getAttendees();

			if((startDateFrag.getDate() != null) && !(startDateFrag.getDate().equals(startEventDate))){
				System.out.println("Updating start with date");
				start.set(Calendar.YEAR, startDateFrag.getDate().getYear());
				start.set(Calendar.MONTH, startDateFrag.getDate().getMonth());
				start.set(Calendar.DAY_OF_MONTH, startDateFrag.getDate().getDay());
			}

			if((endDateFrag.getDate() != null) && !(endDateFrag.getDate().equals(endEventDate))){
				System.out.println("Updating end with date");
				end.set(Calendar.YEAR, endDateFrag.getDate().getYear());
				end.set(Calendar.MONTH, endDateFrag.getDate().getMonth());
				end.set(Calendar.DAY_OF_MONTH, endDateFrag.getDate().getDay());
			}
			
			
			if((endTimeFrag.getTime() != null) && !(endTimeFrag.getTime().equals(endEventTime))){
				System.out.println("Updating end with time");
				end.set(Calendar.HOUR_OF_DAY, endTimeFrag.getTime().getHour());
				end.set(Calendar.MINUTE, endTimeFrag.getTime().getMinute());
			}

			if((startTimeFrag.getTime() != null) && !(startTimeFrag.getTime().equals(startEventTime))){
				System.out.println("Updating start with time");
				start.set(Calendar.HOUR_OF_DAY, startTimeFrag.getTime().getHour());
				start.set(Calendar.MINUTE, startTimeFrag.getTime().getMinute());
			}
			
			
			if(!event.getAttendees().containsAll(attendees.getSelectedUsers())){
				 attendeesList = attendees.getSelectedUsers();
			}
			
			event.setStartDateAndTime(start);
			event.setEndDateAndTime(end);
			event.setAttendees(attendeesList);
			
			event.setEventTitle(title.getText().toString());
			event.setLocation(location.getText().toString());
			event.setDescription(description.getText().toString());
			
			// AndroidCalendarEvent newEvent = new AndroidCalendarEvent(title.getText().toString(), start, end, location.getText().toString(), attendeesList, start, "ice cream", description.getText().toString()); 
			
			// Create and send the login request
			final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.POST);
			request.setBody(event.toJSON());
			request.addObserver(new NewEventPageRequestObserver(this)); // TODO: will probably want to update event list model or something
			request.send();
			
			switchToViewMode();
		}
		catch (NullPointerException e) {
			sendToastMessage("Please enter start and end dates and times for this event!");
		}
	}

	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showStartDatePickerDialog(View v) {
	    startDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showEndTimePickerDialog(View v) {
	    endTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showEndDatePickerDialog(View v) {
	    endDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showStartTimePickerDialog(View v) {
	    startTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the alert time picker dialog
	 * @param v the current view
	 */
	/*
	public void showAlertPickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(alertPickerButton, "Alert");
	    newFragment.show(getFragmentManager(), "timePicker");
	}*/

}
