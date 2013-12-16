package edu.wpi.cs.wpisuitetng.apps.calendar.drawEvents;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class WeekEventSquare extends ShapeDrawable {

	private AndroidCalendarEvent event;
	int x1, y1, x2, y2;
	private int viewWidth, viewHeight;

	public WeekEventSquare(AndroidCalendarEvent ev, View v, Calendar day) {
		super(new RectShape());
		this.event = ev;
		
		viewWidth = v.getWidth();
		viewHeight = v.getHeight();

		int leftMargin = (int) (viewWidth*.15);

		int offset = (int) ((viewWidth * 0.8) / 7);
		
		switch(this.event.getStartDateAndTime().get(Calendar.DAY_OF_WEEK)){
		case Calendar.SUNDAY:
			this.x1 = leftMargin + offset*0;
			this.x2 = leftMargin + offset*1;
			break;
		case Calendar.MONDAY:
			this.x1 = leftMargin + offset*1;
			this.x2 = leftMargin + offset*2;
			break;
		case Calendar.TUESDAY:
			this.x1 = leftMargin + offset*2;
			this.x2 = leftMargin + offset*3;
			break;
		case Calendar.WEDNESDAY:
			this.x1 = leftMargin + offset*3;
			this.x2 = leftMargin + offset*4;
			break;
		case Calendar.THURSDAY:
			this.x1 = leftMargin + offset*4;
			this.x2 = leftMargin + offset*5;
			break;
		case Calendar.FRIDAY:
			this.x1 = leftMargin + offset*5;
			this.x2 = leftMargin + offset*6;
			break;
		case Calendar.SATURDAY:
			this.x1 = leftMargin + offset*6;
			this.x2 = leftMargin + offset*7;
			break;
		}

		//from start time, figure out top of day

		GregorianCalendar testCompareEnd = new GregorianCalendar(day.get(GregorianCalendar.YEAR), day.get(GregorianCalendar.MONTH), day.get(GregorianCalendar.DATE)+1, 0, 0);
		long epochOfDay = day.getTimeInMillis();
		long endOfDay = testCompareEnd.getTimeInMillis();
		long millisPerPixel =  (endOfDay - epochOfDay)/v.getHeight();


		if(event.getStartDateAndTime().before(day)){
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