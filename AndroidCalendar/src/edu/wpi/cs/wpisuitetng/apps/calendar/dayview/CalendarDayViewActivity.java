package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.Calendar;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.marvin.loginactivity.MarvinUserData;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.os.Bundle;

public class CalendarDayViewActivity extends CalendarCommonMenuActivity {
	private int currentMonth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		sendRequestForAllEventsInMonth(currentMonth);
		
	}
	
	public void sendRequestForAllEventsInMonth(int month) {
		Request request = Network.getInstance().makeRequest("Advanced/androidcalendar/androidcalendarevent/startmonth/" + month + "/attendees/" + MarvinUserData.getUsername() , HttpMethod.GET);
		request.addObserver(new CalendarDayViewRequestObserver(this));
		request.send();
	}

}
