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
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	private Button timePickerButton;
	private String buttonText;
	private EventTime time;

	/**
	 * @return the date
	 */
	public EventTime getTime() {
		return time;
	}
	
	public void setTime(EventTime time) {
		this.time = time;
		updateButtonText();
	}

	public TimePickerFragment(Button button, String text) {
		timePickerButton = button;
		buttonText = text;
		// If no time is provided, default to right now
		final Calendar c = Calendar.getInstance();
		time = new EventTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}
	
	public TimePickerFragment(Button button, String text, EventTime time) {
		timePickerButton = button;
		buttonText = text;
		this.time = time;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, time.getHour(), time.getMinute(), DateFormat.is24HourFormat(getActivity()));
	}

	/* (non-Javadoc)
	 * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		time = new EventTime(hour, minute);
		updateButtonText();
	}
	
	private void updateButtonText() {
		String minuteString;
		if (time.getMinute() < 10){
			minuteString = "0" + time.getMinute();
		} else {
			minuteString = "" + time.getMinute();
		}
		
		if(DateFormat.is24HourFormat(getActivity())){
			timePickerButton.setText(buttonText + ": " + time.getHour() + ":" + minuteString);
		} else {
			int displayHour = time.getHour();
			if(time.getHour() == 0){
				displayHour = 12;
			}
			String amOrPm = "AM";
			
			if(time.getHour() > 12){
				displayHour = time.getHour() - 12;
				amOrPm = "PM";
			}
			timePickerButton.setText(buttonText + ": " + displayHour + ":" + minuteString + " " + amOrPm);
		}
	}
}
