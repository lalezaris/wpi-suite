package edu.wpi.cs.wpisuitetng.modules.defecttracker.search.controllers;

import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.search.observers.RetrieveAllDefectsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.search.views.SearchDefectsView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.Request.RequestMethod;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;

/**
 * Controller to handle retrieving all defects from the server and
 * displaying them in the {@link SearchDefectsView}
 */
public class RetrieveAllDefectsController {

	/** The search defects view */
	protected SearchDefectsView view;
	
	/** The defects data retrieved from the server */
	protected Defect[] data = null;
	
	/**
	 * Constructs a new RetrieveAllDefectsController
	 * 
	 * @param view the search defects view
	 */
	public RetrieveAllDefectsController(SearchDefectsView view) {
		this.view = view;
	}
	
	/**
	 * Sends a request for all of the defects
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveAllDefectsRequestObserver(this);
		Request request;
		try {
			request = Network.getInstance().makeRequest("defecttracker/defect", RequestMethod.GET);
			request.addObserver(requestObserver);
			request.send();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called by the {@link RetrieveAllDefectsRequestObserver} when the
	 * response is received
	 * 
	 * @param defects an array of defects returned by the server
	 */
	public void receivedData(Defect[] defects) {	
		if (defects.length > 0) {
			// save the data
			this.data = defects;
			
			// set the column names
			String[] columnNames = {"ID", "Title", "Description", "Creator", "Assignee", "Created", "Last Modified"};
			view.getSearchPanel().getResultsPanel().getModel().setColumnNames(columnNames);
			
			// put the data in the table
			Object[][] entries = new Object[defects.length][columnNames.length];
			for (int i = 0; i < defects.length; i++) {
				entries[i][0] = String.valueOf(defects[i].getId());
				entries[i][1] = defects[i].getTitle();
				entries[i][2] = defects[i].getDescription();
				entries[i][3] = defects[i].getCreator().getName();
				if (defects[i].getAssignee() != null) {
					entries[i][4] = defects[i].getAssignee().getName();
				}
				else {
					entries[i][4] = "";
				}
				entries[i][5] = defects[i].getCreationDate();
				entries[i][6] = defects[i].getLastModifiedDate();
			}
			view.getSearchPanel().getResultsPanel().getModel().setData(entries);
			view.getSearchPanel().getResultsPanel().getModel().fireTableStructureChanged();
		}
		else {
			// do nothing, there are no defects
		}
	}
	
	/**
	 * This method is called by the {@link RetrieveAllDefectsRequestObserver} when an
	 * error occurs retrieving the defects from the server.
	 */
	public void errorReceivingData() {
		JOptionPane.showMessageDialog(view, "An error occurred retrieving defects from the server.", "Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}
}