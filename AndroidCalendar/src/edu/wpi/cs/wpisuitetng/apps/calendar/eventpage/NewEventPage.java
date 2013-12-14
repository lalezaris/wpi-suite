package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.AlertOptions;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.UserPickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


public class NewEventPage extends AbstractEventPage {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);
		
//		finalAlerts = new ArrayList<AlertOptions>();
		
		currentEvent = new AndroidCalendarEvent(MarvinUserData.getUsername());
		currentEvent.addObserver(this);
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
				returnToPreviousActivity();
				break;
			default:
				selectedItem = false;
				break;
			}
		}
		return selectedItem;
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
			final Request request = Network.getInstance().makeRequest("androidcalendar/androidcalendarevent", HttpMethod.PUT);
			request.setBody(currentEvent.toJSON());
			request.addObserver(new EventPageRequestObserver(this));
			request.send();

			returnToPreviousActivity();
		}
	}
}
