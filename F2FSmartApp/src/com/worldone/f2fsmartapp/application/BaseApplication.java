package com.worldone.f2fsmartapp.application;

import android.app.Application;

import com.worldone.f2fsmartapp.client.AppHttpClient;
import com.worldone.f2fsmartapp.models.LocationData;

public class BaseApplication extends Application{

	private AppHttpClient appHttpClient;
	private LocationData mLocationData;
	
	public AppHttpClient getAppHttpClient() {
		return appHttpClient;
	}

	public void setAppHttpClient(AppHttpClient appHttpClient) {
		this.appHttpClient = appHttpClient;
	}

	public LocationData getLocationData() {
		return mLocationData;
	}

	public void setLocationData(LocationData locationData) {
		mLocationData = locationData;
	}

	
}
