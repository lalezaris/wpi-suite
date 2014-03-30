package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.common.CalendarCommonMenuActivity;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
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
	List<WeekEventSquare> squares = new ArrayList<WeekEventSquare>();
	
	public WeekEventSurfaceView(Context context, ArrayList<AndroidCalendarEvent> listOfEvents) {
		super(context);
		
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		
		events = new ArrayList<AndroidCalendarEvent>();	
		events.addAll(listOfEvents);
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
		
		int leftMargin = (int) (getWidth()*.15);
		int offset = (int) ((getWidth() * 0.8) / 7);
		canvas.drawText("Sun.", leftMargin + offset*0, 20, paint);
		canvas.drawText("Mon.", leftMargin + offset*1, 20, paint);
		canvas.drawText("Tues.", leftMargin + offset*2, 20, paint);
		canvas.drawText("Wed.", leftMargin + offset*3, 20, paint);
		canvas.drawText("Thurs.", leftMargin + offset*4, 20, paint);
		canvas.drawText("Fri.", leftMargin + offset*5, 20, paint);
		canvas.drawText("Sat.", leftMargin + offset*6, 20, paint);
		
		
		for(AndroidCalendarEvent e : this.events){
			Calendar eventDay = new GregorianCalendar(e.getStartDateAndTime().get(Calendar.YEAR), e.getStartDateAndTime().get(Calendar.MONTH), e.getStartDateAndTime().get(Calendar.DAY_OF_MONTH));
			WeekEventSquare sq = new WeekEventSquare(e, this, eventDay);
			squares.add(sq);
			
			paint.setStyle(Style.FILL);
			sq.draw(canvas);//draws shape inside EventSquare objects
			
			paint.setColor(Color.BLACK); 
			paint.setTextSize(28); 
			paint.setTextAlign(Align.LEFT);
			int width = sq.x2 - sq.x1;
			
			int numChars = paint.breakText(sq.getEvent().getEventTitle(), true, width, null);
			int start = (sq.getEvent().getEventTitle().length() - numChars)/2;
			canvas.drawText(sq.getEvent().getEventTitle(), start, start + numChars, sq.x1 + 5, sq.y2 - 10, paint);
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
		for(WeekEventSquare s : squares){
			if(s.handleTouch(arg0, arg1)){
				
				Context c = getContext();
				Intent i = new Intent(c, edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.ViewEventPage.class);
				
				i.putExtra(AndroidCalendarEvent.EVENT, s.getEvent());
				i.putExtra(CalendarCommonMenuActivity.CALLING_ACTIVITY, "week");
				//Starts the next activity
				c.startActivity(i);
				
				return true;//end loop on first correct touch
			}
		}
		return false;
	}
	
	
}