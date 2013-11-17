/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.entitymanagers;

import edu.wpi.cs.wpisuitetng.Session;
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

	public AndroidCalendarEventEntityManager(Data data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedGet(Session arg0, String[] arg1)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session arg0, String arg1, String arg2)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session arg0) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteEntity(Session arg0, String arg1)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model[] getAll(Session arg0) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model[] getEntity(Session arg0, String arg1)
			throws NotFoundException, WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model makeEntity(Session arg0, String arg1)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Session arg0, Model arg1) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Model update(Session arg0, String arg1) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}


}
