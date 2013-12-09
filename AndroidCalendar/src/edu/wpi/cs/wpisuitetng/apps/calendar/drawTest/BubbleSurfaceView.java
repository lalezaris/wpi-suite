package edu.wpi.cs.wpisuitetng.apps.calendar.drawTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


public class BubbleSurfaceView extends SurfaceView  
implements SurfaceHolder.Callback, OnTouchListener {
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	List<AndroidCalendarEvent> events;
	List<EventSquare> squares = new ArrayList<EventSquare>();
	
	public BubbleSurfaceView(Context context, ArrayList<AndroidCalendarEvent> listOfEvents) {
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
		
		for(AndroidCalendarEvent e : this.events){
			EventSquare sq = new EventSquare(e, this, new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),0,0));
			
			squares.add(sq);
			sq.getShape().draw(canvas);//draws shape inside EventSquare objects
		}
		
		
		//canvas.drawCircle(100, 200, 50, paint);
		
		sh.unlockCanvasAndPost(canvas);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		for(EventSquare s : squares){
			if(s.handleTouch(arg0, arg1)){
				return true;//end loop on first correct touch
			}
		}
		return false;
	}
	
	
}