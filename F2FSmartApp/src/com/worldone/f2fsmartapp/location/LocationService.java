package com.worldone.f2fsmartapp.location;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.worldone.f2fsmartapp.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

	private String TAG = this.getClass().getSimpleName();

	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;
	private int refreshInterval = 10 * 60 * 1000; // how often refresh alarm (10 min)
	private Calendar time;

	@Override
	public void onCreate() {
		super.onCreate();
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(
				LocationDetailsManager.ALARM_ACTION);
		intent.putExtra(LocationDetailsManager.ALARM_KEY,
				LocationDetailsManager.ALARM_VALUE);
		pendingIntent = PendingIntent.getBroadcast(this, R.id.locationIntent, intent, 0);
	};

	@Override
	public void onStart(Intent intent, int startId) {
		try {
			time = Calendar.getInstance();
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					time.getTimeInMillis(), refreshInterval, pendingIntent);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
			Date resultdate = new Date(time.getTimeInMillis());
			System.out.println(sdf.format(resultdate));
		} catch (NumberFormatException e) {
			Log.i(TAG, "Wrong number format");
		} catch (Exception e) {
			Log.i(TAG, "ERROR");
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		this.alarmManager.cancel(pendingIntent);
		LocationDetailsManager.stopLocationListener();
		super.onDestroy();
	}
}