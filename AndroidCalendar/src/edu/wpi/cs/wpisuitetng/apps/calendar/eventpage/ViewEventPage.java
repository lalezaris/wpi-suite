package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.layout;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.menu;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;

public class ViewEventPage extends NewEventPage {
	
	private Button startDatePickerButton, startTimePickerButton, endDatePickerButton, endTimePickerButton, saveEventButton; //alertPickerButton,
	private EventDate startDate, endDate;
	private EventTime startTime, endTime;
	private DatePickerFragment startDateFrag, endDateFrag;
	private TimePickerFragment startTimeFrag, endTimeFrag;
	private EditText title, location, description;
	private long eventId;
	private AndroidCalendarEvent event;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);
		
		final Intent intent = getIntent();
		
		eventId = intent.getLongExtra(AndroidCalendarEvent.ID, -1);

		System.out.println("eventId: "+ eventId);
		
		startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		//alertPickerButton = (Button) findViewById(R.id.alert_button);
		saveEventButton = (Button) findViewById(R.id.save_button);
		title = (EditText) findViewById(R.id.event_title_field);
		location = (EditText) findViewById(R.id.location_field);
		description = (EditText) findViewById(R.id.description_field);
		
		switchToViewMode();
		getEventFromDatabase();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_common_menu, menu);
		getMenuInflater().inflate(R.menu.view_event_page, menu);
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
		// TODO Auto-generated method stub
		System.out.println("Delete Item now");
		final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent/" + eventId, HttpMethod.DELETE);
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

		saveEventButton.setVisibility(Button.VISIBLE);
		
	}

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
	}

	private void updateFields() {
		title.setText(event.getEventTitle());
		
		String monthString = new DateFormatSymbols().getMonths()[event.getStartMonth()];
		startDatePickerButton.setText("Start Date: " + monthString + " " + event.getStartDay() + ", " + event.getStartYear());
		
		monthString = new DateFormatSymbols().getMonths()[event.getEndMonth()];
		endDatePickerButton.setText("End Date: " + monthString + " " + event.getEndDay() + ", " + event.getEndYear());
		
		if(DateFormat.is24HourFormat(this)){
			startTimePickerButton.setText("Start Time: " + event.getStartDateAndTime().get(Calendar.HOUR_OF_DAY) + ":" + event.getStartDateAndTime().get(Calendar.MINUTE));
			endTimePickerButton.setText("End Time: " + event.getEndDateAndTime().get(Calendar.HOUR_OF_DAY) + ":" + event.getEndDateAndTime().get(Calendar.MINUTE));
		} else {
			startTimePickerButton.setText("Start Time: " + event.getStartDateAndTime().get(Calendar.HOUR) + ":" + event.getStartDateAndTime().get(Calendar.MINUTE));
			endTimePickerButton.setText("End Time: " + event.getEndDateAndTime().get(Calendar.HOUR) + ":" + event.getEndDateAndTime().get(Calendar.MINUTE));
		}
		
		location.setText(event.getLocation());
		description.setText(event.getDescription());
	}

}
