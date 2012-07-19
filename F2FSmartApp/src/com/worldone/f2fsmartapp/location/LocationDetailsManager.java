package com.worldone.f2fsmartapp.location;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.worldone.f2fsmartapp.utils.ActivityManager;

public class LocationDetailsManager extends BroadcastReceiver {
	private static String TAG = "LocationDetailsManager";
	public static final int LOCATION_TIMER = 30 * 1000;

	public static final String ALARM_ACTION = "LocationDetailsManager";
	public static final String ALARM_KEY = "locAlarm";
	public static final String ALARM_VALUE = "location";

	private static Location currentLocation;
	private static String provider;
	private static Context ctx;
	private static LocationManager locationManager;
	private static LocationListener locationListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onProviderDisabled(String provider) {

		}

		public void onLocationChanged(Location location) {
			try {
				getCurrentLocation(location);
			} catch (Exception e) {

			}
		}
	};

	@Override
	public void onReceive(final Context context, Intent intent) {
		ctx = context;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		provider = getTheBestLocationProvider();

		if (provider != null) {
			locationManager.requestLocationUpdates(provider, LOCATION_TIMER, 15,
					locationListener);

			Location gotLoc = locationManager.getLastKnownLocation(provider);
			getCurrentLocation(gotLoc);
			
		}

		// else {
		// Toast t = Toast.makeText(context,
		// "please turn on GPS",
		// Toast.LENGTH_LONG);
		// t.setGravity(Gravity.CENTER, 0, 0);
		// t.show();
		//
		// Intent settinsIntent = new Intent(
		// android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// settinsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// _context.startActivity(settinsIntent);
		// }
	}

	/**
	 * Return Location object with a current location geo data.
	 * @param pLocation
	 */
	@SuppressWarnings("static-access")
	private static void getCurrentLocation(Location pLocation) {

		locationManager.requestLocationUpdates(provider, 90 * 1000,
				15, locationListener);
		currentLocation = pLocation;
		
		ActivityManager am = new ActivityManager(ctx);
		am.locationData.setLocation(currentLocation);
		
//		Log.i(TAG, "----------LOCATION--------");
//		Log.i(TAG, "LATITUDE: "+currentLocation.getLatitude());
//		Log.i(TAG, "LONGTITUDE: "+currentLocation.getLongitude());
//		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
//		Date resultdate = new Date(currentLocation.getTime());
//		Log.i(TAG, "CORRECT TIME: "+sdf.format(resultdate));
//		Log.i(TAG, "----------LOCATION--END------");
	}

	/**
	 * Returns the best available provider. Also able to switch through them.
	 * @return
	 */
	private String getTheBestLocationProvider() {
		boolean gps = false;
		boolean network = false;

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			gps = true;
		}
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			network = true;
		}

		if (gps && network)
			return provider = LocationManager.GPS_PROVIDER;
		if (!gps && network)
			return provider = LocationManager.NETWORK_PROVIDER;
		if (gps && !network)
			return provider = LocationManager.GPS_PROVIDER;
		if (!gps && !network)
			return provider = null;
		return provider;

	}

	public static void stopLocationListener() {
		locationManager.removeUpdates(locationListener);
	}
}