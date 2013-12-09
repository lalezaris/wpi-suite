package edu.wpi.cs.wpisuitetng.apps.calendar.models;

import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class AndroidCalendarEvent extends AbstractModel {
	
	public static final String ID = "edu.wpi.cs.wpisuitetng.calendar.models.ID";
	
	private long uniqueId;
	private String eventTitle;
	private Calendar startDateAndTime;
	private Calendar endDateAndTime;
	private String location;
	private List<String> attendees;
	private String eventOwner;
	private String description;
	private int startMonth;
	private int startWeekNum;
	private int startYear;
	private int startDay;
	private int endMonth;
	private int endWeekNum;
	private int endYear;
	private int endDay;
	
	public AndroidCalendarEvent(String eventTitle, Calendar startDateAndTime,
			Calendar endDateAndTime, String location, List<String> attendees, String description) {
		this.eventTitle = eventTitle;
		this.startDateAndTime = startDateAndTime;
		this.endDateAndTime = endDateAndTime;
		this.location = location;
		this.attendees = attendees;
		this.description = description;
		
		updateFields();
	}
	
	public void updateFields() {
		startMonth = startDateAndTime.get(Calendar.MONTH);
		endMonth = endDateAndTime.get(Calendar.MONTH);
		
		startWeekNum = startDateAndTime.get(Calendar.WEEK_OF_YEAR);
		endWeekNum = endDateAndTime.get(Calendar.WEEK_OF_YEAR);
		
		startYear = startDateAndTime.get(Calendar.YEAR);
		endYear = endDateAndTime.get(Calendar.YEAR);
		
		startDay = startDateAndTime.get(Calendar.DAY_OF_MONTH);
		endDay = endDateAndTime.get(Calendar.DAY_OF_MONTH);		
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
		String jsonString = "";
		try {
			jsonString = (new Gson()).toJson(this, AndroidCalendarEvent.class);
		}
		catch (NullPointerException e) {
			jsonString = "Error Jsoning " + eventTitle;
		}
		return jsonString;
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
		updateFields();
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
		updateFields();
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
	public List<String> getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
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
	
	/**
	 * @return the startMonth
	 */
	public int getStartMonth() {
		return startMonth;
	}

	/**
	 * @return the startWeekNum
	 */
	public int getStartWeekNum() {
		return startWeekNum;
	}

	/**
	 * @return the startYear
	 */
	public int getStartYear() {
		return startYear;
	}

	/**
	 * @return the startDay
	 */
	public int getStartDay() {
		return startDay;
	}

	/**
	 * @return the endMonth
	 */
	public int getEndMonth() {
		return endMonth;
	}

	/**
	 * @return the endWeekNum
	 */
	public int getEndWeekNum() {
		return endWeekNum;
	}

	/**
	 * @return the endYear
	 */
	public int getEndYear() {
		return endYear;
	}

	/**
	 * @return the endDay
	 */
	public int getEndDay() {
		return endDay;
	}

	
	public String toString() {
		return eventTitle;
	}

	public String getEventOwner() {
		return eventOwner;
	}

	public void setEventOwner(String eventOwner) {
		this.eventOwner = eventOwner;
	}

}
