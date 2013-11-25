package edu.wpi.cs.wpisuitetng.apps.calendar.models;

import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class AndroidCalendarEvent extends AbstractModel {
	
	private long uniqueId;
	private String eventTitle;
	private Calendar startDateAndTime;
	private Calendar endDateAndTime;
	private String location;
	private List<User> attendees;
	private Calendar alertTime;
	private Object recurrence;
	private String description;
	
	public AndroidCalendarEvent(String eventTitle, Calendar startDateAndTime,
			Calendar endDateAndTime, String location, List<User> attendees,
			Calendar alertTime, Object recurrence, String description) {
		this.eventTitle = eventTitle;
		this.startDateAndTime = startDateAndTime;
		this.endDateAndTime = endDateAndTime;
		this.location = location;
		this.attendees = attendees;
		this.alertTime = alertTime;
		this.recurrence = recurrence;
		this.description = description;
	}
	
	public AndroidCalendarEvent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean identify(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return (new Gson()).toJson(this, AndroidCalendarEvent.class);
	}
	
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
	}

	/**
	 * @return the attendees
	 */
	public List<User> getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	/**
	 * @return the alertTime
	 */
	public Calendar getAlertTime() {
		return alertTime;
	}

	/**
	 * @param alertTime the alertTime to set
	 */
	public void setAlertTime(Calendar alertTime) {
		this.alertTime = alertTime;
	}

	/**
	 * @return the recurrence
	 */
	public Object getRecurrence() {
		return recurrence;
	}

	/**
	 * @param recurrence the recurrence to set
	 */
	public void setRecurrence(Object recurrence) {
		this.recurrence = recurrence;
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
	}

}
