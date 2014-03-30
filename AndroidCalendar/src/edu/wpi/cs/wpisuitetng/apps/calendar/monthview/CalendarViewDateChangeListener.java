/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

/**
 * The listener to detect when the date was changed in the calendar view
 * @author Nathan Longnecker
 * @version March 30, 2014
 */
public class CalendarViewDateChangeListener implements OnDateChangeListener {

	public CalendarMonthViewActivity controller;
	
	/** Constructor
	 * @param calendarMonthViewActivity The controller to alert when a change is detected
	 */
	public CalendarViewDateChangeListener(
			CalendarMonthViewActivity calendarMonthViewActivity) {
		controller = calendarMonthViewActivity;
	}

	/* (non-Javadoc)
	 * @see android.widget.CalendarView.OnDateChangeListener#onSelectedDayChange(android.widget.CalendarView, int, int, int)
	 */
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		controller.onSelectedDayChange(year, month, dayOfMonth);
	}

}
