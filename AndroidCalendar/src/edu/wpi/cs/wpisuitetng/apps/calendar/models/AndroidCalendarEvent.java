package edu.wpi.cs.wpisuitetng.apps.calendar.models;

import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class AndroidCalendarEvent extends AbstractModel {
	
	private long uniqueId;
	
	private String eventTitle;
	private Calendar startDateAndTime;
	private Calendar endDateAndTime;
	private String location;
	private List<User> attendees;
	private int alertTime;
	private Object recurrence;
	private String description;
	
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
		System.out.println("toJSON in AndroidCalendarEvent called");
		return null;
	}
	
}
