package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.UserPickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewEventPage extends CalendarCommonMenuActivity {
	
	private Button startDatePickerButton, startTimePickerButton, endDatePickerButton, endTimePickerButton, attendeesPickerButton; //alertPickerButton, 
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
		
		startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		attendeesPickerButton = (Button) findViewById(R.id.attendees_button);
		//alertPickerButton = (Button) findViewById(R.id.alert_button);
		title = (EditText) findViewById(R.id.event_title_field);
		location = (EditText) findViewById(R.id.location_field);
		description = (EditText) findViewById(R.id.description_field);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event_page, menu);
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
			case R.id.cancel_current_item:
				startView(edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class);
				break;
			default:
				selectedItem = false;
				break;
			}
		}

		return selectedItem;
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
	/*
	public void showAlertPickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(alertPickerButton, "Alert");
	    newFragment.show(getFragmentManager(), "timePicker");
	}*/
	
	/**Shows the attendees picker dialog
	 * @param v the current view
	 */
	public void showAttendeesPickerDialog(View v) {
	    attendees.show(getFragmentManager(), "userPicker");
	}
	
	public void saveEvent(View v) {
		if(title.getText().toString() == null || title.getText().toString().isEmpty()) {
			toast.setText("Your event must have a title!");
			toast.show();
			return;
		}
		try {
			Calendar start = new GregorianCalendar(startDateFrag.getDate().getYear(), startDateFrag.getDate().getMonth(), startDateFrag.getDate().getDay(), startTimeFrag.getTime().getHour(), startTimeFrag.getTime().getMinute());
			Calendar end = new GregorianCalendar(endDateFrag.getDate().getYear(), endDateFrag.getDate().getMonth(), endDateFrag.getDate().getDay(), endTimeFrag.getTime().getHour(), endTimeFrag.getTime().getMinute());
			
			AndroidCalendarEvent newEvent = new AndroidCalendarEvent(title.getText().toString(), start, end, location.getText().toString(), attendees.getSelectedUsers(), start, "ice cream", description.getText().toString()); 
			
			System.out.println("Sending Request for unique id");
			// Create and send the login request
			final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.PUT);
			request.setBody(newEvent.toJSON());
			request.addObserver(new NewEventPageRequestObserver()); // TODO: will probably want to update event list model or something
			request.send();
			
			startView(edu.wpi.cs.wpisuitetng.apps.calendar.monthview.CalendarMonthViewActivity.class);
		}
		catch (NullPointerException e) {
			toast.setText("Please enter start and end dates and times for this event!");
			toast.show();
		}
	}
}
