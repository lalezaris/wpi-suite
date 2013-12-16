package edu.wpi.cs.wpisuitetng.apps.calendar.alerts;

import edu.wpi.cs.wpisuitetng.apps.calendar.R;
import edu.wpi.cs.wpisuitetng.apps.calendar.models.AndroidCalendarEvent;
import edu.wpi.cs.wpisuitetng.apps.calendar.startup.StartupActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		AndroidCalendarEvent e;
		if(intent.hasExtra("event")){
			System.out.println("has e");
			e = (AndroidCalendarEvent) intent.getSerializableExtra("event");
		} else {
			System.out.println("does not has e");
			e = null;
		}
		Notification.Builder mBuilder;
		if(e != null){
			System.out.println("In onReceive, e was NOT null");
			mBuilder = new Notification.Builder(context)
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle(e.getEventTitle())
			.setContentText(e.getDescription())
			.setSubText(e.getStartDateAndTime().getTime().toString())
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setVibrate(new long[]{0, 1000, 1000, 1000});
		} else {
			System.out.println("In onReceive, e was null");
			mBuilder = new Notification.Builder(context)
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle("WPI Suite Calendar")
			.setContentText("You have an upcoming event!")
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setVibrate(new long[]{0, 1000, 1000, 1000});
		}
		
		Intent resultIntent = new Intent(context, StartupActivity.class);

		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);

		int mNotificationId = 001;

		NotificationManager mNotifyMgr = 
				(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

		mNotifyMgr.notify(mNotificationId, mBuilder.build());

	}

}
