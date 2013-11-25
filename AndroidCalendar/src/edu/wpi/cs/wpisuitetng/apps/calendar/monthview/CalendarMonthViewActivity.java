package edu.wpi.cs.wpisuitetng.apps.calendar.monthview;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CalendarMonthViewActivity extends CalendarCommonMenuActivity {
	
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_month_view);
		
		ListView list = (ListView)findViewById(R.id.list_view);
		
		List<String> strings = new ArrayList<String>();
		
		strings.add("Event 1");
		strings.add("Event 2");
		strings.add("Event 3");
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
		list.setAdapter(adapter);
	}

}
