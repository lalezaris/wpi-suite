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
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * The Class DatePickerFragment.
 *
 * @author Sam Lalezari
 * @version Nov 10, 2013
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	private final AndroidCalendarEvent currentEvent;
	private final EventAttributes startOrEnd;
	
	/**
	 * Instantiates a new date picker fragment.
	 *
	 * @param currentEvent the current event
	 * @param startOrEnd is it a start or end date
	 */
	public DatePickerFragment(AndroidCalendarEvent currentEvent, EventAttributes startOrEnd) {
		this.currentEvent = currentEvent;
		this.startOrEnd = startOrEnd;
	}

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int year;
		int month;
		int day;
		if(startOrEnd == EventAttributes.Start) {
			year = currentEvent.getStartDateAndTime().get(Calendar.YEAR);
			month = currentEvent.getStartDateAndTime().get(Calendar.MONTH);
			day = currentEvent.getStartDateAndTime().get(Calendar.DAY_OF_MONTH);
		}
		else {
			year = currentEvent.getEndDateAndTime().get(Calendar.YEAR);
			month = currentEvent.getEndDateAndTime().get(Calendar.MONTH);
			day = currentEvent.getEndDateAndTime().get(Calendar.DAY_OF_MONTH);
		}
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/* (non-Javadoc)
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		if(startOrEnd == EventAttributes.Start) { // If its picking a start date, set the start date
			currentEvent.setStartDate(year, month, day);
			currentEvent.setEndDate(year, month, day);
		}
		else { // Else, set the end date
			currentEvent.setEndDate(year, month, day);
		}
		currentEvent.notifyObservers(startOrEnd);
	}
}
