/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

/**
 * @author Nathan Longnecker
 *
 */
public class CalendarViewDateChangeListener implements OnDateChangeListener {

	public CalendarMonthViewActivity controller;
	
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
