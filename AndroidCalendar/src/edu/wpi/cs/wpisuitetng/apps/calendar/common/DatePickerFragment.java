/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sam Lalezari
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.Calendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.EventDate;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * @author Sam Lalezari
 * @version Nov 10, 2013
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	private Button datePickerButton;
	private String buttonText;
	private EventDate date;

	/**
	 * @return the date
	 */
	public EventDate getDate() {
		return date;
	}
	
	public void setDate(EventDate date) {
		this.date = date;
		updateButtonText();
	}
	
	public DatePickerFragment(Button button, String text) {
		datePickerButton = button;
		buttonText = text;
		//If no date is provided, default to the current date
		final Calendar c = Calendar.getInstance();
		date = new EventDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}

	public DatePickerFragment(Button button, String text, EventDate date) {
		datePickerButton = button;
		buttonText = text;
		this.date = date;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, date.getYear(), date.getMonth(), date.getDay());
	}

	/* (non-Javadoc)
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		date = new EventDate(year, month, day);
		updateButtonText();
	}

	private void updateButtonText() {
		String monthString = "";

		switch(date.getMonth()){
		case 0: monthString = "January";
				break;
		case 1: monthString = "February";
				break;
		case 2: monthString = "March";
				break;
		case 3: monthString = "April";
				break;
		case 4: monthString = "May";
				break;
		case 5: monthString = "June";
				break;
		case 6: monthString = "July";
				break;
		case 7: monthString = "August";
				break;
		case 8: monthString = "September";
				break;
		case 9: monthString = "October";
				break;
		case 10: monthString = "November";
				break;
		case 11: monthString = "December";
				break;
		}

		datePickerButton.setText(buttonText + ": " + monthString + " " + date.getDay() + ", " + date.getYear());
	}
}
