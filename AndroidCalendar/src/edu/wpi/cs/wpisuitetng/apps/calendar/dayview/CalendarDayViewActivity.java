package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListAdapter;

public class CalendarDayViewActivity extends CalendarCommonMenuActivity {
	
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dayview);
		
	}

}
