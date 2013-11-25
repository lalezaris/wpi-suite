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
import edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.EventTime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * @author Sam Lalezari
 * @version Nov 10, 2013
 */
public class TimePickerFragment extends DialogFragment implements
OnTimeSetListener {

	private Button timePickerButton;
	private String buttonText;
	private EventTime time;

	/**
	 * @return the date
	 */
	public EventTime getTime() {
		return time;
		
	}

	public TimePickerFragment(Button button, String text) {
		timePickerButton = button;
		buttonText = text;
		time = null;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}

	/* (non-Javadoc)
	 * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		
		String minuteString;
		if (minute < 10){
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}
		
		if(DateFormat.is24HourFormat(getActivity())){
			timePickerButton.setText(buttonText + ": " + hour + ":" + minuteString);
		} else {
			
			String amOrPm = "AM";
			
			if(hour > 12){
				hour = hour - 12;
				amOrPm = "PM";
			}
			timePickerButton.setText(buttonText + ": " + hour + ":" + minuteString + " " + amOrPm);
		}
		time = new EventTime(hour, minute);
			
	}

}
