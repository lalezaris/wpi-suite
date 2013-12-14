package edu.wpi.cs.wpisuitetng.apps.calendar.common;

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
	
	private String text;
	AlertOptions(String s){
		text = s;
	}
	
	@Override
	public String toString(){
		return text;
	}

	public static List<String> stringValues() {
		List<String> strings = new ArrayList<String>();
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
}
