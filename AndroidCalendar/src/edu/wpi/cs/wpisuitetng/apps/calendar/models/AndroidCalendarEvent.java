package edu.wpi.cs.wpisuitetng.apps.calendar.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.apps.calendar.alerts.AlertOptions;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The AndroidCalendarEvent model
 * @author Nathan Longnecker
 *
 */
public class AndroidCalendarEvent extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1969524869295007920L;

	public static final String EVENT = "edu.wpi.cs.wpisuitetng.calendar.models.EVENT";
	public static final String ID = "edu.wpi.cs.wpisuitetng.calendar.models.ID";
	
	private long uniqueId;
	private String eventTitle;
	private Calendar startDateAndTime;
	private Calendar endDateAndTime;
	private List<AlertOptions> alerts;
	private String location;
	private List<String> attendees;
	private String eventOwner;
	private String description;
	
	public AndroidCalendarEvent(String eventOwner, String eventTitle, Calendar startDateAndTime,
			Calendar endDateAndTime, String location, List<String> attendees, String description) {
		this.eventOwner = eventOwner;
		this.eventTitle = eventTitle;
		this.startDateAndTime = startDateAndTime;
		this.endDateAndTime = endDateAndTime;
		this.location = location;
		this.attendees = attendees;
		this.description = description;
		this.alerts = new ArrayList<AlertOptions>();
	}

	public AndroidCalendarEvent(String eventOwner) {
		this.eventOwner = eventOwner;
		this.eventTitle = "";
		this.startDateAndTime = new GregorianCalendar();
		this.endDateAndTime = new GregorianCalendar();
		this.endDateAndTime.add(Calendar.HOUR_OF_DAY, 1);
		this.location = "";
		this.attendees = new ArrayList<String>();
		this.description = "";
		this.alerts = new ArrayList<AlertOptions>();
	}
	
	// Methods to ease updating fields
	/**
	 * Sets the end date of the event
	 * @param year The year
	 * @param month The number of the month (Use the Calendar enum)
	 * @param day The day of the month
	 */
	public void setStartDate(int year, int month, int day) {
		startDateAndTime.set(Calendar.YEAR, year);
		startDateAndTime.set(Calendar.MONTH, month);
		startDateAndTime.set(Calendar.DAY_OF_MONTH, day);
		setChanged();
	}
	
	/**
	 * Sets the start time, using twenty-four hour time
	 * @param hour The hour in 24 hour time
	 * @param minute The minute of the hour
	 */
	public void setStartTime(int hour, int minute) {
		startDateAndTime.set(Calendar.HOUR_OF_DAY, hour);
		startDateAndTime.set(Calendar.MINUTE, minute);
		setChanged();
	}
	
	/**
	 * Sets the end date of the event
	 * @param year The year
	 * @param month The number of the month (Use the Calendar enum)
	 * @param day The day of the month
	 */
	public void setEndDate(int year, int month, int day) {
		endDateAndTime.set(Calendar.YEAR, year);
		endDateAndTime.set(Calendar.MONTH, month);
		endDateAndTime.set(Calendar.DAY_OF_MONTH, day);
		setChanged();
	}
	
	/**
	 * Sets the end time, using twenty-four hour time
	 * @param hour The hour in 24 hour time
	 * @param minute The minute of the hour
	 */
	public void setEndTime(int hour, int minute) {
		endDateAndTime.set(Calendar.HOUR_OF_DAY, hour);
		endDateAndTime.set(Calendar.MINUTE, minute);
		setChanged();
	}
	
	//Methods for use by db4o
	public int getStartMonth() {
		return startDateAndTime.get(Calendar.MONTH);
	}
	
	public int getStartWeekNum() {
		return startDateAndTime.get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getDay() {
		return startDateAndTime.get(Calendar.DAY_OF_MONTH);
	}
	
	//Getters and Setters
	/**
	 * @return the eventTitle
	 */
	public String getEventTitle() {
		return eventTitle;
	}

	/**
	 * @param eventTitle the eventTitle to set
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
		setChanged();
	}

	/**
	 * @return the startDateAndTime
	 */
	public Calendar getStartDateAndTime() {
		return startDateAndTime;
	}

	/**
	 * @param startDateAndTime the startDateAndTime to set
	 */
	public void setStartDateAndTime(Calendar startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
		setChanged();
	}

	/**
	 * @return the endDateAndTime
	 */
	public Calendar getEndDateAndTime() {
		return endDateAndTime;
	}

	/**
	 * @param endDateAndTime the endDateAndTime to set
	 */
	public void setEndDateAndTime(Calendar endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
		setChanged();
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
		setChanged();
	}

	/**
	 * @return the attendees
	 */
	public List<String> getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
		setChanged();
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
		setChanged();
	}

	/**
	 * @return the uniqueId
	 */
	public long getUniqueId() {
		return uniqueId;
	}
	
	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
		setChanged();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return eventTitle;
	}

	/**
	 * @return the eventOwner
	 */
	public String getEventOwner() {
		return eventOwner;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Not Implemented.
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// Intentionally not implemented. When we delete an AndroidCalendarEvent, we remove it from the database
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	
	@Override
	public Boolean identify(Object o) {
		return uniqueId == ((AndroidCalendarEvent)o).getUniqueId();
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		return (new Gson()).toJson(this, AndroidCalendarEvent.class);
	}

	public List<AlertOptions> getAlerts() {
		return alerts;
	}

	public void setAlert(ArrayList<AlertOptions> selectedAlerts) {
		alerts.clear();
		alerts.addAll(selectedAlerts);
		setChanged();
	}
}
