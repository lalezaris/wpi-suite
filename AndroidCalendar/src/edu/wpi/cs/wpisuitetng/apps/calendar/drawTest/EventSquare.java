package edu.wpi.cs.wpisuitetng.apps.calendar.drawTest;

import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class EventSquare implements OnTouchListener{
	
	private ShapeDrawable shape;
	private AndroidCalendarEvent event;
	int x, y, width, height;
	
	public EventSquare(){
		shape = new ShapeDrawable(new RectShape());
		shape.getPaint().setColor(Color.RED);
		shape.setBounds(10, 10, 300, 300);
		this.x = 10;
		this.y = 10;
		this.width = 300-10;
		this.height = 300-10;
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

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch(arg1.getAction()){
		case MotionEvent.ACTION_DOWN: {
			float posX = arg1.getX();
			float posY = arg1.getY();
			if(posX >= this.x && posX <= this.width && posY >= this.y && posY <= this.height){
				System.out.println("Ping");
			}
		}
		}
		return true;
	}
	
	
}