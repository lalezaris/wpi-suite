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
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class DayEventSquare extends ShapeDrawable {

	private final AndroidCalendarEvent event;
	int x1, y1, x2, y2;
	private final int viewWidth, viewHeight;
	int cols = 1;
	int myCol = 0;

	public DayEventSquare(AndroidCalendarEvent ev, View v, Calendar day){
		super(new RectShape());
		
		event = ev;
		viewWidth = v.getWidth();
		viewHeight = v.getHeight();
		//use time & screen res to set height
		//width based on screen width
		x1 = (int) (viewWidth*.15);
		x2 = (int) (viewWidth*.95);

		//from start time, figure out top of day
		
		final GregorianCalendar tomorrow = new GregorianCalendar(day.get(java.util.Calendar.YEAR), day.get(java.util.Calendar.MONTH), day.get(java.util.Calendar.DATE)+1, 0, 0);
		final long epochOfDay = day.getTimeInMillis();
		final long endOfDay = tomorrow.getTimeInMillis();
		final long millisPerPixel =  (endOfDay - epochOfDay) / viewHeight;
		final long millis15Min = 900000;

		if(event.getStartDateAndTime().before(day)){
			y1 = 0;
		}
		else{
			y1 = (int) ((event.getStartDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			y1 = (int) (y1 - (y1 % (millis15Min / millisPerPixel)));

		}

		if(ev.getEndDateAndTime().after(tomorrow)){
			y2 = viewHeight;
		}
		else{
			y2 = (int) ((event.getEndDateAndTime().getTimeInMillis() - epochOfDay) / millisPerPixel);
			y2 = (int) (y2 - (y2 % (millis15Min / millisPerPixel)));
			
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
		final int columnWidth = (int)((viewWidth * 0.8) / numColumns);
		x1 = (int) (column * columnWidth + viewWidth * 0.15);
		x2 = (int) ((column + 1) * columnWidth + viewWidth * 0.15);
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
		if(y1 == sq.y1){
			returnVal = true;
		}
		else if(y1 >= sq.y1 && y1 <= sq.y2) {
			returnVal = true;
		}
		else if(y2 >= sq.y1 && y2 <= sq.y2) {
			returnVal = true;
		}
		else if(y1 >= sq.y1 && y2 <= sq.y2 ) {
			returnVal = true;
		}
		else if(y1 <= sq.y1 && y2 >= sq.y2) {
			returnVal = true;
		}
		return returnVal;
	}
	
	public boolean handleTouch(View view, MotionEvent motion) {
		boolean ret = false;
		switch(motion.getAction()){
		case MotionEvent.ACTION_DOWN: {
			final float posX = motion.getX();
			final float posY = motion.getY();
			if(posX >= x1 && posX <= x2 && posY >= y1 && posY <= y2){
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