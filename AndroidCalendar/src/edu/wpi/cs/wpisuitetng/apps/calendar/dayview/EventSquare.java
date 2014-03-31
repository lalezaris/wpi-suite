/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Sam Lalezari
 * 		Mark Fitzgibbon
 * 		Nathan Longnecker
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.apps.calendar.dayview;

import java.util.Calendar;
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

	public EventSquare(AndroidCalendarEvent ev, View v, Calendar day){
		event = ev;
		//use time & screen res to set height
		//width based on screen width
		x1 = 0;
		x2 = v.getWidth();

		//get start time
		//set top of square
		//get end time
		//set bottom of square
		//

		//from start time, figure out top of day
		
		final GregorianCalendar testCompareEnd = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, 0, 0);
		final long epochOfDay = day.getTimeInMillis();
		final long endOfDay = testCompareEnd.getTimeInMillis();
		final long millisPerPixel =  (endOfDay - epochOfDay)/v.getHeight();
		//System.out.println("Day of: " + day.DATE);

		if(event.getStartDateAndTime().before(day)) {
			y1 = 0;
		}
		else{
			y1 = (int) ((event.getStartDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);

		}
		//	y1 = 0;

		if(ev.getEndDateAndTime().after(testCompareEnd)){
			y2 = v.getHeight();
		}
		else{
			y2 = (int) ((event.getEndDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
		}

		shape = new ShapeDrawable(new RectShape());
		shape.getPaint().setColor(Color.BLUE);
		shape.setBounds(x1, y1, x2, y2);
		System.out.println("Height is " + (y2 - y1));

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
			final float posX = arg1.getX();
			final float posY = arg1.getY();
			if(posX >= x1 && posX <= x2 && posY >= y1 && posY <= y2){
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