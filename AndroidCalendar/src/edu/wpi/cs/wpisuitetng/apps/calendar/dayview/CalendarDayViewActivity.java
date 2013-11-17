package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.layout;
import edu.wpi.cs.wpisuitetng.apps.calendar.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CalendarDayViewActivity extends Activity {
	
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dayview);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_day_view, menu);
		return true;
	}

}
