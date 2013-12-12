package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;

public class CommonCalendarData {
	
	/*
	 * This may not be the best way to pass event information
	 * from intent to intent, but it's simpler
	 * than bundling it up using Parcelable and faster
	 * than serializing to be passed as an extra.
	 */
	private static CommonCalendarData instance;
	private AndroidCalendarEvent currentEvent;

	private CommonCalendarData() {
		// TODO Auto-generated constructor stub
	}
	
	// Singleton Pattern
	public static CommonCalendarData getInstance() {
		if(instance == null) {
			instance = new CommonCalendarData();
		}
		return instance;
	}

	public AndroidCalendarEvent getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(AndroidCalendarEvent currentEvent) {
		this.currentEvent = currentEvent;
	}
	
}
