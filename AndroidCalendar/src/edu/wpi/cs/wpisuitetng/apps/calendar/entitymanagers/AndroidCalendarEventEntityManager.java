/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.entitymanagers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractEntityManager;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * @author Nathan Longnecker
 *
 */
public class AndroidCalendarEventEntityManager implements EntityManager<Model> {

	Data db;
	
	public AndroidCalendarEventEntityManager(Data data) {
		db = data;
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
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {

		System.out.println("AdvancedGet called in AndroidCalendarEventEntityManager");
		
		String application = args[0];
		System.out.println("application = " + application);
		
		String model = args[1];
		System.out.println("model = " + model);
		
		String getType = args[2];
		System.out.println("getType = " + getType);
		
		int getTypeFrom = Integer.parseInt(args[3]);
		System.out.println("getTypeFrom = " + getTypeFrom);
		
		String[] fieldNameList = {getType};
		List<Object> givenValueList = new ArrayList<Object>();
		givenValueList.add(getTypeFrom);
		List<Model> modelList = new ArrayList<Model>();
		
		try {
			System.out.println("Trying orRetrieve()");
			modelList = db.andRetrieve(AndroidCalendarEvent.class, fieldNameList, givenValueList);
			System.out.println("Finished orRetrieve()");
		} catch (IllegalArgumentException e) {
			System.out.println("Caught IllegalArgumentException: " + e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Caught IllegalAccessException: " + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("Caught InvocationTargetException: " + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Caught Exception: " + e.toString());
		}
		
		
		System.out.println("Returning GSON String");
		return new Gson().toJson(modelList.toArray());
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
		List<AndroidCalendarEvent> events = db.retrieveAll(new AndroidCalendarEvent());//db.retrieveAll(new AndroidCalendarEvent(), arg0.getProject());
		System.out.println(events.size());
		return events.toArray(new AndroidCalendarEvent[0]);
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
		db.save(newEvent, s.getProject());
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
