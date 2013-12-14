package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class DayEventSquare {

	private ShapeDrawable shape;
	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;
	int viewWidth, viewHeight;
	
	public enum Position{
		LEFT, RIGHT
	}
	
	Position pos;

	public DayEventSquare(AndroidCalendarEvent ev, View v, Calendar day){
		this.event = ev;
		this.viewWidth = v.getWidth();
		this.viewHeight = v.getHeight();
		//use time & screen res to set height
		//width based on screen width
		this.x1 = (int) (v.getWidth()*.15);
		this.x2 = (int) (v.getWidth()*.425);
		this.pos = Position.LEFT;

		//get start time
		//set top of square
		//get end time
		//set bottom of square
		//

		//from start time, figure out top of day
		
		GregorianCalendar testCompareEnd = new GregorianCalendar(day.get(GregorianCalendar.YEAR), day.get(GregorianCalendar.MONTH), day.get(GregorianCalendar.DATE)+1, 0, 0);
		long epochOfDay = day.getTimeInMillis();
		long endOfDay = testCompareEnd.getTimeInMillis();
		long millisPerPixel =  (endOfDay - epochOfDay)/v.getHeight();
		long millis15Min = 900000;

		if(event.getStartDateAndTime().before(day)){
			this.y1 = 0;
		}
		else{
			this.y1 = (int) ((event.getStartDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			this.y1 = (int) (this.y1 - (this.y1 % (millis15Min/millisPerPixel)));

		}
		//	y1 = 0;

		if(ev.getEndDateAndTime().after(testCompareEnd)){
			this.y2 = v.getHeight();
		}
		else{
			this.y2 = (int) ((event.getEndDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			this.y2 = (int) (this.y2 - (this.y2 % (millis15Min/millisPerPixel)));
			
		}

		shape = new ShapeDrawable(new RectShape());
		shape.getPaint().setColor(Color.CYAN);
		shape.setBounds(x1, y1, x2, y2);

	}

	public ShapeDrawable getShape() {
		return shape;
	}

	public void setShape(ShapeDrawable shape) {
		this.shape = shape;
	}

	public AndroidCalendarEvent getEvent() {
		return event;
	}

	public void setEvent(AndroidCalendarEvent event) {
		this.event = event;
	}
	
	public Position getPos(){
		return this.pos;
	}
	
	public void setPos(Position pos){
		this.pos = pos;
		if(this.pos == Position.LEFT){
			//redraw for left
			this.x1 = (int) (viewWidth*.15);
			this.x2 = (int) (viewWidth*.425);
		}
		else{
			//redraw for right
			this.x1 = (int) (viewWidth*.45);
			this.x2 = (int) (viewWidth* .85);
		}
		shape.setBounds(x1, y1, x2, y2);
	}


	public boolean overlapWithSquare(DayEventSquare sq){
		if(this.y1 >= sq.y1 && this.y2 <= sq.y2 ){
			//sq occurs inside this
			return true;
		}
		else if(this.y1 <= sq.y1 && this.y2 >= sq.y2){
			//this occurs inside sq
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public boolean handleTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch(arg1.getAction()){
		case MotionEvent.ACTION_DOWN: {
			float posX = arg1.getX();
			float posY = arg1.getY();
			if(posX >= this.x1 && posX <= this.x2 && posY >= this.y1 && posY <= this.y2){
				//System.out.println("Ping");
				
				
				return true;
			}
			else{
				return false;//allow other listeners to work

			}
		}
		default:
			return false;//allow other listeners to work


		}

	}


}