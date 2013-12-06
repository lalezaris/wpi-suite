package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.UserPickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewEventPage extends Activity {
	
	private Button startDatePickerButton, startTimePickerButton, endDatePickerButton, endTimePickerButton, alertPickerButton, attendeesPickerButton;
	private EventDate startDate, endDate;
	private EventTime startTime, endTime;
	private DatePickerFragment startDateFrag, endDateFrag;
	private TimePickerFragment startTimeFrag, endTimeFrag;
	private final UserPickerFragment attendees = new UserPickerFragment();
	private EditText title, location, description;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);

		/*
		System.out.println("Sending Request for unique id");
		// Create and send the login request
		final Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/titleis/not/here", HttpMethod.GET);
		request.send();
		 */
		
		
		startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		attendeesPickerButton = (Button) findViewById(R.id.attendees_button);
		alertPickerButton = (Button) findViewById(R.id.alert_button);
		title = (EditText) findViewById(R.id.event_title_field);
		location = (EditText) findViewById(R.id.location_field);
		description = (EditText) findViewById(R.id.description_field);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event_page, menu);
		return true;
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showStartDatePickerDialog(View v) {
	    startDateFrag = new DatePickerFragment(startDatePickerButton, "Start Date");
	    startDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showEndTimePickerDialog(View v) {
	    endTimeFrag = new TimePickerFragment(endTimePickerButton, "End Time");
	    endTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showEndDatePickerDialog(View v) {
	    endDateFrag = new DatePickerFragment(endDatePickerButton, " End Date");
	    endDateFrag.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showStartTimePickerDialog(View v) {
	    startTimeFrag = new TimePickerFragment(startTimePickerButton, "Start Time");
	    startTimeFrag.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the alert time picker dialog
	 * @param v the current view
	 */
	public void showAlertPickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(alertPickerButton, "Alert");
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the attendees picker dialog
	 * @param v the current view
	 */
	public void showAttendeesPickerDialog(View v) {
	    attendees.show(getFragmentManager(), "userPicker");
	}
	
	public void saveEvent(View v){
		Calendar start = new GregorianCalendar(startDateFrag.getDate().getYear(), startDateFrag.getDate().getMonth(), startDateFrag.getDate().getDay(), startTimeFrag.getTime().getHour(), startTimeFrag.getTime().getMinute());
		Calendar end = new GregorianCalendar(endDateFrag.getDate().getYear(), endDateFrag.getDate().getMonth(), endDateFrag.getDate().getDay(), endTimeFrag.getTime().getHour(), endTimeFrag.getTime().getMinute());
		
		AndroidCalendarEvent newEvent = new AndroidCalendarEvent(title.getText().toString(), start, end, location.getText().toString(), attendees.getSelectedUsers(), start, "ice cream", description.getText().toString()); 
		
		System.out.println("Sending Request for unique id");
		// Create and send the login request
		final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.PUT);
		request.setBody(newEvent.toJSON());
		request.addObserver(new NewEventPageRequestObserver()); // TODO: will probably want to update event list model or something
		request.send();
		 
	}
	

}
