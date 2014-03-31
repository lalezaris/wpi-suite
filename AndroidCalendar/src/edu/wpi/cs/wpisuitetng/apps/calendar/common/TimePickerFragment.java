/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sam Lalezari, Nathan Longnecker
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.Calendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

/**
 * Displays  a time picker for choosing start and end times.
 * @author Sam Lalezari
 * @version Nov 10, 2013
 */
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
	
	private final AndroidCalendarEvent currentEvent;
	private final EventAttributes startOrEnd;

	/**
	 * Instantiates a new time picker fragment.
	 *
	 * @param currentEvent the current event
	 * @param startOrEnd the start or end
	 */
	public TimePickerFragment(AndroidCalendarEvent currentEvent, EventAttributes startOrEnd) {
		this.currentEvent = currentEvent;
		this.startOrEnd = startOrEnd;
	}

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int hour;
		int minute;
		final boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());
		if(startOrEnd == EventAttributes.Start) { // If choosing a start time, default to the current time.
			hour = currentEvent.getStartDateAndTime().get(Calendar.HOUR_OF_DAY);
			minute = currentEvent.getStartDateAndTime().get(Calendar.MINUTE);
		}
		else { // Same if choosing a end time, but default the end time to the current time.
			hour = currentEvent.getEndDateAndTime().get(Calendar.HOUR_OF_DAY);
			minute = currentEvent.getEndDateAndTime().get(Calendar.MINUTE);
		}
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, is24HourFormat);
	}

	/* (non-Javadoc)
	 * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		if(startOrEnd == EventAttributes.Start) { //If setting a start time 
			currentEvent.setStartTime(hour, minute); // Set start time
			currentEvent.setEndTime(hour+1, minute); // Set default end time one hour later than the start time
		}
		else { // Else, if setting the end time
			currentEvent.setEndTime(hour, minute); // Set end time
		}
		currentEvent.notifyObservers(startOrEnd); // Notify observers that the time was updated.
	}
}
