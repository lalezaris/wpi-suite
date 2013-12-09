package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


public class WeekEventSurfaceView extends SurfaceView  
implements SurfaceHolder.Callback, OnTouchListener {
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	List<AndroidCalendarEvent> events;
	List<DayEventSquare> squares = new ArrayList<DayEventSquare>();
	
	public WeekEventSurfaceView(Context context, ArrayList<AndroidCalendarEvent> listOfEvents) {
		super(context);
		
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		
	
	}
	public void surfaceCreated(SurfaceHolder holder) {
		this.setOnTouchListener(this);//listens to itself
		Canvas canvas = sh.lockCanvas();
		canvas.drawColor(Color.WHITE);
		
		int pixelsPerHr = this.getHeight()/24;
		
		//Draw the 24 hours
		paint.setColor(Color.GRAY);
		paint.setTextSize(20); 
		for(int i = 1; i <= 24; i++){
			canvas.drawRect(0, i*pixelsPerHr, this.getWidth(), (i*pixelsPerHr)+1, paint);
			canvas.drawText(i + ":00", 0, i*pixelsPerHr, paint);
		}
		
		
		sh.unlockCanvasAndPost(canvas);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		for(DayEventSquare s : squares){
			if(s.handleTouch(arg0, arg1)){
				
				Context c = getContext();
				Intent i = new Intent(c, edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.ViewEventPage.class);
				
				i.putExtra(AndroidCalendarEvent.ID, s.getEvent().getUniqueId());

				//Starts the next activity
				c.startActivity(i);
				
				return true;//end loop on first correct touch
			}
		}
		return false;
	}
	
	
}