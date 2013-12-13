package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.EventAttributes;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ViewEventPage extends AbstractEventPage {
	
	private Button saveEventButton, startDatePickerButton, endDatePickerButton, startTimePickerButton, endTimePickerButton, attendeesButton;
	private EditText title, location, description;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);
		
		currentEvent = (AndroidCalendarEvent) getIntent().getExtras().getSerializable(AndroidCalendarEvent.EVENT);
		//currentEvent = CommonCalendarData.currentEvent;//CommonCalendarData.getInstance().getCurrentEvent();
		currentEvent.addObserver(this);
		
		startDatePickerButton = (Button) findViewById(R.id.start_date_picker_button);
		startTimePickerButton = (Button) findViewById(R.id.start_time_picker_button);
		endDatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
		endTimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
		attendeesButton = (Button) findViewById(R.id.attendees_button);
		saveEventButton = (Button) findViewById(R.id.save_button);
		title = (EditText) findViewById(R.id.event_title_field);
		location = (EditText) findViewById(R.id.location_field);
		description = (EditText) findViewById(R.id.description_field);
	    
		switchToViewMode();
		update(currentEvent, EventAttributes.All);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		if(this.currentEvent.getEventOwner().equals(MarvinUserData.getUsername())) {
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
		currentEvent.deleteObservers();
		final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent/" + currentEvent.getUniqueId(), HttpMethod.DELETE);
		request.addObserver(new DeleteEventRequestObserver(this));
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
		attendeesButton.setEnabled(false);
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
		attendeesButton.setEnabled(true);
		saveEventButton.setVisibility(Button.VISIBLE);
	}
}
