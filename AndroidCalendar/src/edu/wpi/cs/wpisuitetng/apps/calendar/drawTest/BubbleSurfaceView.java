package edu.wpi.cs.wpisuitetng.apps.calendar.drawTest;

import java.util.ArrayList;
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
	
	public BubbleSurfaceView(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		
		events = new ArrayList<AndroidCalendarEvent>();

		events.add(new AndroidCalendarEvent("Pizza party", new GregorianCalendar(2013, 12, 2, 15, 0),
				new GregorianCalendar(2013, 12, 2, 15, 30), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));

		events.add(new AndroidCalendarEvent("Ice Cream party", new GregorianCalendar(2013, 12, 2, 6, 0),
				new GregorianCalendar(2013, 12, 2, 9, 0), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));

		events.add(new AndroidCalendarEvent("MQP Meeting", new GregorianCalendar(2013, 12, 2, 3, 0),
				new GregorianCalendar(2013, 12, 2, 4, 0), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));

		events.add(new AndroidCalendarEvent("Last Day of Classes", new GregorianCalendar(2013, 12, 2, 11, 0),
				new GregorianCalendar(2013, 12, 2, 13, 0), "HERE", new ArrayList<User>(),
				new GregorianCalendar(), null, "Things"));
		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		this.setOnTouchListener(this);//listens to itself
		Canvas canvas = sh.lockCanvas();
		canvas.drawColor(Color.WHITE);
		
		for(AndroidCalendarEvent e : this.events){
			EventSquare sq = new EventSquare(e, this);
			
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