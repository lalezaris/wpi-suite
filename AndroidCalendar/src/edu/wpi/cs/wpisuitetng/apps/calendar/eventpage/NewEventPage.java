package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.id;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.layout;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.menu;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.DatePickerFragment;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.TimePickerFragment;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class NewEventPage extends Activity {
	
	private Button startDatePickerButton, startTimePickerButton, endDatePickerButton, endTimePickerButton, alertPickerButton;
	
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
		alertPickerButton = (Button) findViewById(R.id.alert_button);
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
	    DialogFragment newFragment = new DatePickerFragment(startDatePickerButton, "Start Date");
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showEndTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(endTimePickerButton, "End Time");
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the date picker dialog
	 * @param v the current view
	 */
	public void showEndDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(endDatePickerButton, " End Date");
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showStartTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(startTimePickerButton, "Start Time");
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	/**Shows the alert time picker dialog
	 * @param v the current view
	 */
	public void showAlertPickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(alertPickerButton, "Alert");
	    newFragment.show(getFragmentManager(), "timePicker");
	}

}
