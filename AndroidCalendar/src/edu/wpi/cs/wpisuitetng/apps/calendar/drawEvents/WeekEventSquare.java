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

public class WeekEventSquare {

	private ShapeDrawable shape;
	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;

	public WeekEventSquare(AndroidCalendarEvent ev, View v, Calendar day){
		

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