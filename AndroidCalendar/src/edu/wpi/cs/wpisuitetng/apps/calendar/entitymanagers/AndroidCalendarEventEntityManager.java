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
 * Handles the data for the AndroidCalendar module on the server side 
 * @author Nathan Longnecker
 * @author Sam Lalezari
 * @version March 30, 2014
 */
public class AndroidCalendarEventEntityManager implements EntityManager<Model> {

	Data db;
	
	/**
	 * Constructor.
	 * @param data The database where the information is stored
	 */
	public AndroidCalendarEventEntityManager(Data data) {
		db = data;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() {
		// TODO: there must be a faster way to do this with db4o
		// note that this is not project-specific
		return db.retrieveAll(new AndroidCalendarEvent(null)).size();
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(edu.wpi.cs.wpisuitetng.Session, java.lang.String[])
	 */
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
		
		//Iterate through the input to identify search parameters
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

		//Construct an advanced db4o query based on the given input
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
		
		// Filter out events that the current user does not belong to
		modelList = checkIfBelongsTo(s.getUsername(), modelList);
		
		// If appropriate, sort the array before returning
		Model[] models = modelList.toArray(new Model[0]);
		if(models.length > 0) {
			if(models[0] instanceof AndroidCalendarEvent) {
				models = modelList.toArray(new AndroidCalendarEvent[0]);
				androidCalendarEventQuickSort((AndroidCalendarEvent[])models, 0, models.length - 1);
			}
		}
		
		//Send the data back to the client
		return new Gson().toJson(models);
	}

	
	/**
	 * Checks if the user belongs to each event in the list
	 * @param username The current user's name
	 * @param list The list of events to filter
	 * @return Returns a list of events that the user belongs to
	 */
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

    /**
     * Uses the quicksort algorithm to sort the given array in place
     * @param array The array to sort
     * @param pivotIndex Pivot index
     * @param rangeIndex Range index
     */
    public void androidCalendarEventQuickSort(AndroidCalendarEvent[] array, int pivotIndex, int rangeIndex)
    {
        if(pivotIndex < rangeIndex)
        {
            int q = partition(array, pivotIndex, rangeIndex);
            androidCalendarEventQuickSort(array, pivotIndex, q);
            androidCalendarEventQuickSort(array, q+1, rangeIndex);
        }
    }

    /**
     * A part of the quicksort algorithm
     * @param array The array to sort
     * @param pivotIndex Pivot index
     * @param rangeIndex Range index
     * @return Returns the location of the partition
     */
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

    /** Swaps two values in the given array
     * @param array The array to swap
     * @param i Index of the first value
     * @param j Index of the second value
     */
    private void swap(AndroidCalendarEvent[] array, int i, int j) {
        AndroidCalendarEvent temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session arg0, String arg1, String arg2)
			throws WPISuiteException {
		// This method is not used
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2)
			throws WPISuiteException {
		// This method is not used
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session arg0) throws WPISuiteException {
		// This method is not used
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		/* Deletes the given event. However, db4o does not always find the object
		 * that we want it to delete, so sometimes this deletion method will fail.
		 */
		
		//System.out.println("Deleting item with id: " + id);
		Model modelToDelete = getEntity(s, id)[0];
		boolean returnVal;
		if(modelToDelete == null) {
			returnVal = false;
			//System.out.println("Could not find item with id: " + id);
		}
		else {
			returnVal = db.delete(modelToDelete) != null;
			//System.out.println("Deleted item with id " + id + ":" + returnVal);
		}
		return returnVal;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Model[] getAll(Session arg0) throws WPISuiteException {
		List<AndroidCalendarEvent> events = db.retrieveAll(new AndroidCalendarEvent(null));//db.retrieveAll(new AndroidCalendarEvent(), arg0.getProject());
		
		AndroidCalendarEvent[] eventsArray = events.toArray(new AndroidCalendarEvent[0]);
		
		androidCalendarEventQuickSort(eventsArray, 0, eventsArray.length - 1);
		
		return eventsArray;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Model[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException {
		final long longId = Long.parseLong(id);
		
		List<Model> events;
		
		events = db.retrieve(AndroidCalendarEvent.class, "uniqueId", longId, s.getProject());
				
		return events.toArray(new Model[0]);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Model makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final AndroidCalendarEvent newEvent = new Gson().fromJson(content, AndroidCalendarEvent.class);
		
		newEvent.setUniqueId(getUniqueId());
		
		db.save(newEvent, s.getProject());
		
		System.out.println("Saving event " + newEvent.getEventTitle() + " with uniqueId " + newEvent.getUniqueId() + " and owner: " + newEvent.getEventOwner());
		return newEvent;
	}

	/**
	 * Calculates a new unique id for a method when it is first being stored
	 * @return Returns a unique id
	 */
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

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session arg0, Model arg1) throws WPISuiteException {
		// This method is not used
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Model update(Session arg0, String arg1) throws WPISuiteException {
		//Updates an event in the database
		AndroidCalendarEvent event = new Gson().fromJson(arg1, AndroidCalendarEvent.class);
		
		//System.out.println();
		//System.out.println("event: " + event.toJSON());
		
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "eventTitle", event.getEventTitle());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "startDateAndTime", event.getStartDateAndTime());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "endDateAndTime", event.getEndDateAndTime());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "location", event.getLocation());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "attendees", event.getAttendees());
		db.update(AndroidCalendarEvent.class, "uniqueid", event.getUniqueId(), "description", event.getDescription());
		
		return event;
	}


}
