/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

import java.util.Observable;

/**
 * @author Sam Lalezari
 *
 */
public class EventDate extends Observable {

	private int year, month, day;

	
	public EventDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	

}
