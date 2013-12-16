package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class DayEventSquare extends ShapeDrawable {

	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;
	private int viewWidth, viewHeight;
	int cols = 1;
	int myCol = 0;

	public DayEventSquare(AndroidCalendarEvent ev, View v, Calendar day){
		super(new RectShape());
		
		this.event = ev;
		this.viewWidth = v.getWidth();
		this.viewHeight = v.getHeight();
		//use time & screen res to set height
		//width based on screen width
		this.x1 = (int) (viewWidth*.15);
		this.x2 = (int) (viewWidth*.95);

		//from start time, figure out top of day
		
		GregorianCalendar tomorrow = new GregorianCalendar(day.get(GregorianCalendar.YEAR), day.get(GregorianCalendar.MONTH), day.get(GregorianCalendar.DATE)+1, 0, 0);
		final long epochOfDay = day.getTimeInMillis();
		final long endOfDay = tomorrow.getTimeInMillis();
		final long millisPerPixel =  (endOfDay - epochOfDay) / viewHeight;
		final long millis15Min = 900000;

		if(event.getStartDateAndTime().before(day)){
			this.y1 = 0;
		}
		else{
			this.y1 = (int) ((event.getStartDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			this.y1 = (int) (this.y1 - (this.y1 % (millis15Min / millisPerPixel)));

		}

		if(ev.getEndDateAndTime().after(tomorrow)){
			this.y2 = viewHeight;
		}
		else{
			this.y2 = (int) ((event.getEndDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			this.y2 = (int) (this.y2 - (this.y2 % (millis15Min / millisPerPixel)));
			
		}

		setBounds(x1, y1, x2, y2);
	}
	
	@Override
	public void draw(Canvas canvas) {
		getPaint().setStyle(Style.FILL);
		getPaint().setColor(0xAA943B3B);
		super.draw(canvas);
	}

	public AndroidCalendarEvent getEvent() {
		return event;
	}
	
	public void resize(int numColumns, int column){
		//Drawable area is from 15% of screen to 95%
		System.out.println("resizing " + event.getEventTitle() + ". ViewWidth: " + viewWidth + " " + numColumns + " " + column);
		int columnWidth = (int)((viewWidth * 0.8) / numColumns);
		this.x1 = (int) (column * columnWidth + viewWidth * 0.15);
		this.x2 = (int) ((column + 1) * columnWidth + viewWidth * 0.15);
		cols = numColumns;
		myCol = column;
		setBounds(x1, y1, x2, y2);
	}
	
	public int getNumColumns() {
		return cols;
	}
	
	public int getMyCol() {
		return myCol;
	}

	public boolean overlapsWithEvent(DayEventSquare sq){
		boolean returnVal = false;
		if(this.y1 == sq.y1){
			returnVal = true;
		}
		else if(this.y1 >= sq.y1 && this.y1 <= sq.y2) {
			returnVal = true;
		}
		else if(this.y2 >= sq.y1 && this.y2 <= sq.y2) {
			returnVal = true;
		}
		else if(this.y1 >= sq.y1 && this.y2 <= sq.y2 ) {
			returnVal = true;
		}
		else if(this.y1 <= sq.y1 && this.y2 >= sq.y2) {
			returnVal = true;
		}
		return returnVal;
	}
	
	public boolean handleTouch(View view, MotionEvent motion) {
		boolean ret = false;
		switch(motion.getAction()){
		case MotionEvent.ACTION_DOWN: {
			float posX = motion.getX();
			float posY = motion.getY();
			if(posX >= this.x1 && posX <= this.x2 && posY >= this.y1 && posY <= this.y2){
				ret = true;
			}
			break;
		}
		default:
			break;
		}
		return ret; //allow other listeners to work
	}
}