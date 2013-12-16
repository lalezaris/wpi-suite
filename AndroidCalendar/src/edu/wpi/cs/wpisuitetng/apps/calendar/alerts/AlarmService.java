package edu.wpi.cs.wpisuitetng.apps.calendar.alerts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.apps.calendar.common.AlertOptions;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmService {

	public static final String EVENT = "edu.wpi.cs.wpisuitetng.apps.calendar.alerts.EVENT";
	private Context context;
	private PendingIntent mAlarmSender;
	private List<AndroidCalendarEvent> events;
	private AlarmManager am;

	public AlarmService(Context context, List<AndroidCalendarEvent> notifyEvents) {
		System.out.println("new AlarmService()");
		this.context = context;
		mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(this.context, AlarmReceiver.class), 0);
		events = new ArrayList<AndroidCalendarEvent>(notifyEvents);
		am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}

	public void startAlarm(){
		System.out.println("in startAlarm()");
		List<Calendar> calList = new ArrayList<Calendar>();

		for(AndroidCalendarEvent e : events){
			calList.addAll(getAlertTime(e));

			System.out.println("e: " + e.getEventTitle());
			Intent intent = new Intent(context, AlarmReceiver.class);
			intent.putExtra("event", e);

			mAlarmSender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			for(Calendar c: calList){
				System.out.println("c: " + c.getTime().toString());
				long time = c.getTimeInMillis();
				am.set(AlarmManager.RTC_WAKEUP, time, mAlarmSender);
				System.out.println("Set alarm for " + e.getEventTitle() + " at " + c.getTimeInMillis());
			}
		}


	}

	private List<Calendar> getAlertTime(AndroidCalendarEvent e) {
		List<Calendar> calList = new ArrayList<Calendar>();

		if(!e.getAlerts().isEmpty()){
			for(AlertOptions ao : e.getAlerts()){
				Calendar cal = e.getStartDateAndTime();
				switch(ao){
				case FIFTEEN_BEFORE:
					cal.add(Calendar.MINUTE, -15);
					break;
				case FIVE_BEFORE:
					cal.add(Calendar.MINUTE, -5);
					break;
				case FORTYFIVE_BEFORE:
					cal.add(Calendar.MINUTE, -45);
					break;
				case ON_START:
					break;
				case SIXTY_BEFORE:
					cal.add(Calendar.HOUR, -1);
					break;
				case TEN_BEFORE:
					cal.add(Calendar.MINUTE, -10);
					break;
				case THIRTY_BEFORE:
					cal.add(Calendar.MINUTE, -30);
					break;
				default:
					break;
				}

				if(cal.after(Calendar.getInstance())){
					calList.add(cal);
					System.out.println("Added time " + cal.getTime().toString() + " to calList");
				}
			}
		}
		System.out.println("calList.size(): " + calList.size());
		return calList;
	}
}