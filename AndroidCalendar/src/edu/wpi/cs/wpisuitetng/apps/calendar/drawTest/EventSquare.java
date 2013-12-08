package edu.wpi.cs.wpisuitetng.apps.calendar.drawTest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class EventSquare {

	private ShapeDrawable shape;
	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;

	public EventSquare(AndroidCalendarEvent ev, View v){
		this.event = ev;
		//use time & screen res to set height
		//width based on screen width
		this.x1 = 0;
		this.x2 = v.getWidth();

		//get start time
		//set top of square
		//get end time
		//set bottom of square
		//

		//from start time, figure out top of day
		GregorianCalendar testCompare = new GregorianCalendar(2013, 12, 2, 0, 0);
		GregorianCalendar testCompareEnd = new GregorianCalendar(2013, 12, 3, 0, 0);
		long epochOfDay = testCompare.getTimeInMillis();
		long endOfDay = testCompareEnd.getTimeInMillis();
		long millisPerPixel =  (endOfDay - epochOfDay)/v.getHeight();
		System.out.println("Milliseconds per pixel: " + millisPerPixel);

		if(event.getStartDateAndTime().before(testCompare)){
			this.y1 = 0;
		}
		else{
			this.y1 = (int) ((event.getStartDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);

		}
		//	y1 = 0;

		if(ev.getEndDateAndTime().after(testCompareEnd)){
			this.y2 = v.getHeight();
		}
		else{
			this.y2 = (int) ((event.getEndDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
		}

		shape = new ShapeDrawable(new RectShape());
		shape.getPaint().setColor(Color.BLUE);
		shape.setBounds(x1, y1, x2, y2);
		System.out.println("Height is " + (this.y2 - this.y1));

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


	
	
	public boolean handleTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch(arg1.getAction()){
		case MotionEvent.ACTION_DOWN: {
			float posX = arg1.getX();
			float posY = arg1.getY();
			if(posX >= this.x1 && posX <= this.x2 && posY >= this.y1 && posY <= this.y2){
				System.out.println("Ping");
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