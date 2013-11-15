package edu.wpi.cs.wpisuitetng.apps.calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class NewEventPage extends Activity {
	
	private Button datePickerButton;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_page);
	
		datePickerButton = (Button) findViewById(R.id.date_picker_button);
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
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	/**Shows the time picker dialog
	 * @param v the current view
	 */
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}

}
