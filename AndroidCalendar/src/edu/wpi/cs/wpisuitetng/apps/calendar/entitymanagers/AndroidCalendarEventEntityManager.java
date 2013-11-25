/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.entitymanagers;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractEntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * @author Nathan Longnecker
 *
 */
public class AndroidCalendarEventEntityManager extends AbstractEntityManager {

	Data db;
	
	public AndroidCalendarEventEntityManager(Data data) {
		super(data);
		// TODO Auto-generated constructor stub
		System.out.println("AndroidCalendarEventEntityManager constructor called in AndroidCalendarEventEntityManager");
	}
	
	@Override
	public int Count() {
		// TODO: there must be a faster way to do this with db4o
		// note that this is not project-specific - ids are unique across projects
		
		return db.retrieveAll(new AndroidCalendarEvent()).size();
	}
	
	@Override
	public String advancedGet(Session arg0, String[] arg1)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("AdvancedGet called in AndroidCalendarEventEntityManager");
		String args = "";
		for(int i = 0; i < arg1.length; i++) {
			args += arg1[i];
		}
		System.out.println("Called advancedGet! Session: " + arg0 + " Arguments " + args);
		return null;
	}

	@Override
	public String advancedPost(Session arg0, String arg1, String arg2)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("AdvancedPost called in AndroidCalendarEventEntityManager");
		return null;
	}

	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("AdvancedPut called in AndroidCalendarEventEntityManager");
		return null;
	}

	@Override
	public void deleteAll(Session arg0) throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("DeleteAll called in AndroidCalendarEventEntityManager");
	}

	@Override
	public boolean deleteEntity(Session arg0, String arg1)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("DeleteEntity called in AndroidCalendarEventEntityManager");
		return false;
	}

	@Override
	public Model[] getAll(Session arg0) throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("GetAll called in AndroidCalendarEventEntityManager");
		return null;
	}

	@Override
	public Model[] getEntity(Session arg0, String arg1)
			throws NotFoundException, WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("getEntity called in AndroidCalendarEventEntityManager, Session: " + arg0 + " arg1: " + arg1);
		return null;
	}

	@Override
	public Model makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		final AndroidCalendarEvent newEvent = new Gson().fromJson(content, AndroidCalendarEvent.class);
		
		System.out.println("makeEntity called in AndroidCalendarEventEntityManager");
		return newEvent;
	}

	@Override
	public void save(Session arg0, Model arg1) throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("Save called in AndroidCalendarEventEntityManager");
	}

	@Override
	public Model update(Session arg0, String arg1) throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("Update called in AndroidCalendarEventEntityManager");
		return null;
	}


}
