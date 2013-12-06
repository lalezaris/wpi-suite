/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.entitymanagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

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

		ArrayList<String> fieldArrayList = new ArrayList<String>();
		List<Object> givenValueList = new ArrayList<Object>();
		
		ArrayList<String> extraFields = new ArrayList<String>();
		List<Object> extraValues = new ArrayList<Object>();
		
		for(int i=2; i < args.length; i += 2){
			Boolean addToRetrieve = true;
			String field = args[i].toLowerCase();
			Object value;

			if(field.equals("uniqueid")) {
				value = Long.parseLong(args[i+1]);
			} if (field.equals("attendees")){
				value = args[i+1];
				addToRetrieve = false;
			}
			else {
				value = Integer.parseInt(args[3]);
			}

			if(addToRetrieve){
				fieldArrayList.add(field);
				givenValueList.add(value);
			} else {
				extraFields.add(field);
				extraValues.add(value);
			}
		}
		String[] fieldNameList = (String[]) fieldArrayList.toArray();
		
		List<Model> modelList = new ArrayList<Model>();
		
		try {
			modelList = db.andRetrieve(AndroidCalendarEvent.class, fieldNameList, givenValueList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(extraFields.size() == extraValues.size()){
			for(int i = 0; i < extraFields.size(); i++){
				String field = extraFields.get(i);
				Object value = extraValues.get(i);
				
				if(field.equals("attendees")){
					modelList = checkIfIsAttendee(value, modelList);
				} else {
					// Do nothing
				}
			}
		}
		
		return new Gson().toJson(modelList.toArray());
	}

	private List<Model> checkIfIsAttendee(Object value, List<Model> list) {
		String name = (String) value;
		List<Model> modelList = list;
		
		for(Model m : modelList){
			AndroidCalendarEvent event = (AndroidCalendarEvent) m;
			List<User> attendeeList = event.getAttendees();
			
			for(User u : attendeeList){
				if(!name.toLowerCase().equals(u.getName().toLowerCase())){
					modelList.remove(m);
				}
			}
		}
		
		return modelList;
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
		
		newEvent.setUniqueId(Count());
		
		db.save(newEvent, s.getProject());
		
		System.out.println("Saving event " + newEvent.getEventTitle() + " with uniqueId " + newEvent.getUniqueId());
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
