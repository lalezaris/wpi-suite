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

/**
 * The Class AlarmReceiver, receives an alarm and adds it to the notification panel
 * @author Sam Lalezari
 * @version March 30, 2014
 */
public class AlarmReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		AndroidCalendarEvent e;
		if(intent.hasExtra("event")){ // If there is an event passed from the intent
			e = (AndroidCalendarEvent) intent.getSerializableExtra("event"); // get the event
		} else {
			e = null; // don't get the event
		}
		Notification.Builder mBuilder;
		if(e != null){ // if there is event information
			mBuilder = new Notification.Builder(context) //Build a new notification based on the event
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle(e.getEventTitle())
			.setContentText(e.getDescription())
			.setSubText(e.getStartDateAndTime().getTime().toString())
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setVibrate(new long[]{0, 1000, 1000, 1000});
		} else { // Else, if theres no event information
			mBuilder = new Notification.Builder(context)
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle("WPI Suite Calendar")
			.setContentText("You have an upcoming event!")
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setVibrate(new long[]{0, 1000, 1000, 1000});
		}
		
		Intent resultIntent = new Intent(context, StartupActivity.class);
        
		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(context, (int) e.getUniqueId(), resultIntent, 0);

		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotifyMgr = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotifyMgr.notify((int) e.getUniqueId(), mBuilder.build());

	}

}
