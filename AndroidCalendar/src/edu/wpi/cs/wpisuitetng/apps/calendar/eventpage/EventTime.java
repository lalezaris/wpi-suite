/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.eventpage;

/**
 * @author Sam Lalezari
 *
 */
public class EventTime {

	private int hour, minute;

	public EventTime(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}
	
	public boolean equals(EventTime event){
		if(event.getHour() == hour && event.getMinute() == minute){
			return true;
		}
		
		return false;
	}
}
