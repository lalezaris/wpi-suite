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
package edu.wpi.cs.wpisuitetng.apps.calendar.alerts;

import java.util.ArrayList;
import java.util.List;

public enum AlertOptions {
	//{"On Event Start", "5 Minutes Before", "10 Minutes Before", "15 Minutes Before", "30 Minutes Before", "45 Minutes Before", "60 Minutes Before"};
	
	ON_START ("On Event Start"),
	FIVE_BEFORE("5 Minutes Before"),
	TEN_BEFORE("10 Mintues Before"),
	FIFTEEN_BEFORE("15 Minutes Before"),
	THIRTY_BEFORE("30 Minutes Before"),
	FORTYFIVE_BEFORE("45 Minutes Before"),
	SIXTY_BEFORE("60 Minutes Before");
	
	private final String text;
	AlertOptions(String s){
		text = s;
	}
	
	@Override
	public String toString(){
		return text;
	}

	public static List<String> stringValues() {
		final List<String> strings = new ArrayList<String>();
		for(AlertOptions value : values()){
			strings.add(value.toString());
		}
		
		return strings;
	}

	public static AlertOptions getEnum(String string) {
		
		for(AlertOptions value : values()){
			if(string.equalsIgnoreCase(value.toString())){
				return value;
			}
		}
		return null;
	}

	public static boolean[] getCheckedItems(
			List<AlertOptions> selectedAlerts) {
		
		final ArrayList<Boolean> checkedList = new ArrayList<Boolean>();

		for(AlertOptions v:values()){
			boolean hasAdded = false;
			
			for(AlertOptions selected : selectedAlerts){
				if(v.toString().equals(selected.toString())){
					checkedList.add(true);
					hasAdded = true;
					break;
				}
			}
			
			if(!hasAdded){
			checkedList.add(false);
			}
		}

		final boolean[] returnList = new boolean[checkedList.size()];
		
		for(int i = 0; i < returnList.length; i++){
			returnList[i] = checkedList.get(i).booleanValue();
		}
		
		final String string = "";
		for(boolean b:returnList){
			string.concat(Boolean.valueOf(b).toString() + ", ");
		}

		return returnList;
	}
}
