/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.apps.calendar.entitymanagers;

import java.util.ArrayList;
import java.util.Arrays;
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
		
		return db.retrieveAll(new AndroidCalendarEvent(null)).size();
	}
	
	@Override
	public String advancedGet(Session s, String[] args) throws WPISuiteException {

		//remove the null last argument
		if(args[args.length-1] == null) {
			args = Arrays.copyOfRange(args, 0, args.length-1);
		}

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
		String[] fieldNameList = fieldArrayList.toArray(new String[0]);
		
		List<Model> modelList = new ArrayList<Model>();

		if(fieldArrayList.size() > 0 && givenValueList.size() > 0){
			try {
				modelList = db.andRetrieve(AndroidCalendarEvent.class, fieldNameList, givenValueList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelList = db.retrieveAll(new AndroidCalendarEvent(null), s.getProject());
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
		
		// Filter out event the current user does not belong to
		modelList = checkIfBelongsTo(s.getUsername(), modelList);
		
		// If appropriate, sort the array before returning
		Model[] models = modelList.toArray(new Model[0]);
		if(models.length > 0) {
			if(models[0] instanceof AndroidCalendarEvent) {
				models = modelList.toArray(new AndroidCalendarEvent[0]);
				androidCalendarEventQuickSort((AndroidCalendarEvent[])models, 0, models.length - 1);
			}
		}
		
		return new Gson().toJson(models);
	}

	
	private List<Model> checkIfBelongsTo(String username, List<Model> list) {
		String name = username;
		List<Model> modelList = new ArrayList<Model>();
		List<Model> models = list;
		
		for(Model m : models){
			AndroidCalendarEvent event = (AndroidCalendarEvent) m;
			List<String> attendeeList = event.getAttendees();
			
			if(name.toLowerCase().equals(event.getEventOwner().toLowerCase())){
				modelList.add(m);
				continue;
			}
			
			for(String uname : attendeeList){
				if(name.toLowerCase().equals(uname.toLowerCase())){
					modelList.add(m);
					break;
				}
			}
		}
		
		return modelList;
	}

	/**
	 * Checks to see if the given username value is in the attendees list
	 * @param username the username to check for
	 * @param list the list of events
	 * @return A new list containing only events that contain "username" in the attendees list
	 */
	private List<Model> checkIfIsAttendee(Object username, List<Model> list) {
		String name = (String) username;
		List<Model> modelList = new ArrayList<Model>();
		
		for(Model m : list){
			AndroidCalendarEvent event = (AndroidCalendarEvent) m;
			List<String> attendeeList = event.getAttendees();
			
			if(name.toLowerCase().equals(event.getEventOwner().toLowerCase())){
				modelList.add(m);
				continue;
			}
			
			for(String uname : attendeeList){
				if(name.toLowerCase().equals(uname.toLowerCase())){
					modelList.add(m);
					break;
				}
			}
		}
		
		return modelList;
	}

    public void androidCalendarEventQuickSort(AndroidCalendarEvent[] array, int pivotIndex, int rangeIndex)
    {
        if(pivotIndex<rangeIndex)
        {
            int q = partition(array, pivotIndex, rangeIndex);
            androidCalendarEventQuickSort(array, pivotIndex, q);
            androidCalendarEventQuickSort(array, q+1, rangeIndex);
        }
    }

    private int partition(AndroidCalendarEvent[] array, int pivotIndex, int rangeIndex) {

        AndroidCalendarEvent x = array[pivotIndex];
        int i = pivotIndex - 1 ;
        int j = rangeIndex + 1 ;

        while (true) {
            i++;
            while ( i < rangeIndex && array[i].getStartDateAndTime().before(x.getStartDateAndTime()))
                i++;
            j--;
            while (j > pivotIndex && x.getStartDateAndTime().before(array[j].getStartDateAndTime()))
                j--;

            if (i < j)
                swap(array, i, j);
            else
                return j;
        }
    }

    private void swap(AndroidCalendarEvent[] array, int i, int j) {
        AndroidCalendarEvent temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.println("Deleting item with id: " + id);
		Model modelToDelete = getEntity(s, id)[0];
		boolean returnVal;
		if(modelToDelete == null) {
			returnVal = false;
			System.out.println("Could not find item with id: " + id);
		}
		else {
			returnVal = db.delete(modelToDelete) != null;
			System.out.println("Deleted item with id " + id + ":" + returnVal);
		}
		return returnVal;
	}

	@Override
	public Model[] getAll(Session arg0) throws WPISuiteException {
		List<AndroidCalendarEvent> events = db.retrieveAll(new AndroidCalendarEvent(null));//db.retrieveAll(new AndroidCalendarEvent(), arg0.getProject());
		
		AndroidCalendarEvent[] eventsArray = events.toArray(new AndroidCalendarEvent[0]);
		
		androidCalendarEventQuickSort(eventsArray, 0, eventsArray.length - 1);
		
		return eventsArray;
	}

	@Override
	public Model[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException {
		final long longId = Long.parseLong(id);
		
		List<Model> events;
		
		events = db.retrieve(AndroidCalendarEvent.class, "uniqueId", longId, s.getProject());
				
		return events.toArray(new Model[0]);
	}

	@Override
	public Model makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		final AndroidCalendarEvent newEvent = new Gson().fromJson(content, AndroidCalendarEvent.class);
		
		newEvent.setUniqueId(getUniqueId());
		
		db.save(newEvent, s.getProject());
		
		System.out.println("Saving event " + newEvent.getEventTitle() + " with uniqueId " + newEvent.getUniqueId() + " and owner: " + newEvent.getEventOwner());
		return newEvent;
	}

	private long getUniqueId() {
		final List<AndroidCalendarEvent> events = db.retrieveAll(new AndroidCalendarEvent(null));
		long id = 0;
		
		for(AndroidCalendarEvent e: events){
			if(id == e.getUniqueId()){
				id++;
			}
		}
		
		return id;
	}

	@Override
	public void save(Session arg0, Model arg1) throws WPISuiteException {
		// TODO Auto-generated method stub
		System.out.println("Save called in AndroidCalendarEventEntityManager");
	}

	@Override
	public Model update(Session arg0, String arg1) throws WPISuiteException {
		
		AndroidCalendarEvent event = new Gson().fromJson(arg1, AndroidCalendarEvent.class);
		
		System.out.println();
		System.out.println("event: " + event.toJSON());
		
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "eventTitle", event.getEventTitle());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "startDateAndTime", event.getStartDateAndTime());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "endDateAndTime", event.getEndDateAndTime());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "location", event.getLocation());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "attendees", event.getAttendees());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "description", event.getDescription());
		
		return event;
	}


}
