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

import java.util.ArrayList;
import java.util.Calendar;
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


public class DayEventSurfaceView extends SurfaceView
		implements SurfaceHolder.Callback, OnTouchListener {
	
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final List<DayEventSquare> eventSquares = new ArrayList<DayEventSquare>();
	
	private final List<AndroidCalendarEvent> events = new ArrayList<AndroidCalendarEvent>();
	private final Calendar currentDay;
	
	public DayEventSurfaceView(Context context, List<AndroidCalendarEvent> listOfEvents, Calendar currentDay) {
		super(context);
		
		getHolder().addCallback(this);
		paint.setStyle(Style.FILL);
		
		events.addAll(listOfEvents);
		this.currentDay = currentDay;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.setOnTouchListener(this);//listens to itself
		final Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		
		final int pixelsPerHr = this.getHeight()/24;
		
		//Draw the 24 hours
		paint.setColor(Color.GRAY);
		paint.setTextSize(20); 
		for(int i = 1; i <= 24; i++) {
			canvas.drawRect(0, i * pixelsPerHr, this.getWidth(), (i * pixelsPerHr) + 1, paint);
			canvas.drawText(i + ":00", 0, i * pixelsPerHr, paint);
		}
		
		for(AndroidCalendarEvent e : events){
			eventSquares.add(new DayEventSquare(e, this, currentDay));
		}
		
		final List<DayEventSquare> addedEvents = new ArrayList<DayEventSquare>();
		for(DayEventSquare s: eventSquares) {
			//check for an overlap
			List<DayEventSquare> overlappingEvents = new ArrayList<DayEventSquare>();
			for(DayEventSquare added : addedEvents) {
				if(s.overlapsWithEvent(added)) {
					overlappingEvents.add(added);
				}
			}
			if(!overlappingEvents.isEmpty()) {
				overlappingEvents.add(s);
			}
			System.out.println("Overlaps: " + overlappingEvents.size());
			// Figure out how many columns we have
			int numColumns = overlappingEvents.size();
			for(DayEventSquare square : overlappingEvents) {
				if(square.getNumColumns() > numColumns) {
					numColumns = square.getNumColumns();
				}
			}
			System.out.println("num columns: " + numColumns);
			//Figure out which columns are already taken
			boolean[] colsTaken = new boolean[numColumns];
			/*
			for(int i = 0; i < overlappingEvents.size(); i++) {
				colsTaken[overlappingEvents.get(i).getMyCol()] = true;
				System.out.println(i + ": " + colsTaken[i]);
			}*/
			//Assign events to columns that are not taken yet
			for(DayEventSquare square : overlappingEvents) {
				for(int i = 0; i < numColumns; i++) {
					if(!colsTaken[i]) {
						square.resize(numColumns, i);
						colsTaken[i] = true;
						break;
					}
				}
			}
			
			addedEvents.add(s);
		}
		
		for(DayEventSquare sq : eventSquares){
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
		
		holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		for(DayEventSquare s : eventSquares){
			if(s.handleTouch(arg0, arg1)){
				
				Context c = getContext();
				Intent i = new Intent(c, edu.wpi.cs.wpisuitetng.apps.calendar.eventpage.ViewEventPage.class);
				
				i.putExtra(AndroidCalendarEvent.EVENT, s.getEvent());
				i.putExtra(CalendarCommonMenuActivity.CALLING_ACTIVITY, "day");

				//Starts the next activity
				c.startActivity(i);
				
				return true;//end loop on first correct touch
			}
		}
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}