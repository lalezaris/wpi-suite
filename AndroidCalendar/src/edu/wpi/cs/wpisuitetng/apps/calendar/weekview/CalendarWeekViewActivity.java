package edu.wpi.cs.wpisuitetng.apps.calendar.weekview;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class CalendarWeekViewActivity extends CalendarCommonMenuActivity {
	
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_week_view);
		
	
	}

}