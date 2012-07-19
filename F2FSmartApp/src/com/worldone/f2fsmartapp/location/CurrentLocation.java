package com.worldone.f2fsmartapp.location;

import com.worldone.f2fsmartapp.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class CurrentLocation {

	public static void startLocationManager(Activity activity){
		 activity.startService(new Intent(activity, LocationService.class));
	}
	
	public static void stopLocationManager(Activity activity){
		AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(activity, LocationDetailsManager.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, R.id.locationIntent, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager.cancel(pendingIntent);
	}
	
}
