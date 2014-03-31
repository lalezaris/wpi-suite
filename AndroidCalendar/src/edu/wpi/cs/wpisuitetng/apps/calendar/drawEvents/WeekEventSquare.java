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
package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class WeekEventSquare extends ShapeDrawable {

	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;
	private final int viewWidth;

	public WeekEventSquare(AndroidCalendarEvent ev, View v, Calendar day) {
		super(new RectShape());
		event = ev;
		
		viewWidth = v.getWidth();
		v.getHeight();

		final int leftMargin = (int) (viewWidth*.15);

		final int offset = (int) ((viewWidth * 0.8) / 7);
		
		switch(event.getStartDateAndTime().get(Calendar.DAY_OF_WEEK)){
		case Calendar.SUNDAY:
			x1 = leftMargin + offset*0;
			x2 = leftMargin + offset*1;
			break;
		case Calendar.MONDAY:
			x1 = leftMargin + offset*1;
			x2 = leftMargin + offset*2;
			break;
		case Calendar.TUESDAY:
			x1 = leftMargin + offset*2;
			x2 = leftMargin + offset*3;
			break;
		case Calendar.WEDNESDAY:
			x1 = leftMargin + offset*3;
			x2 = leftMargin + offset*4;
			break;
		case Calendar.THURSDAY:
			x1 = leftMargin + offset*4;
			x2 = leftMargin + offset*5;
			break;
		case Calendar.FRIDAY:
			x1 = leftMargin + offset*5;
			x2 = leftMargin + offset*6;
			break;
		case Calendar.SATURDAY:
			x1 = leftMargin + offset*6;
			x2 = leftMargin + offset*7;
			break;
		}

		//from start time, figure out top of day

		final GregorianCalendar testCompareEnd = new GregorianCalendar(day.get(java.util.Calendar.YEAR), day.get(java.util.Calendar.MONTH), day.get(java.util.Calendar.DATE)+1, 0, 0);
		final long epochOfDay = day.getTimeInMillis();
		final long endOfDay = testCompareEnd.getTimeInMillis();
		final long millisPerPixel =  (endOfDay - epochOfDay)/v.getHeight();


		if(event.getStartDateAndTime().before(day)){
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

		getPaint().setStyle(Style.FILL);
		getPaint().setColor(0xAA943B3B);
		setBounds(x1, y1, x2, y2);
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