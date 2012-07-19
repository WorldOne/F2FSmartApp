package com.worldone.f2fsmartapp.utils;

import android.app.Activity;

import com.worldone.f2fsmartapp.application.BaseApplication;
import com.worldone.f2fsmartapp.client.AppHttpClient;


public class SessionManager {

	private Activity mActivity;
	private BaseApplication baseApplication;
	
	public SessionManager(Activity activity){
		this.mActivity = activity;
	}
	
	public boolean isLogged() {

//		if (mActivity != null
//				&& (baseApplication = (BaseApplication) mActivity
//						.getApplication()) != null) {
//			return ((baseApplication.getAppHttpClient() == null)) ? false
//					: true;
//		} else {
//			return false;
//		}
		return true;
	}
	
	public void createSession(AppHttpClient appHttpClient){
		baseApplication = (BaseApplication) mActivity.getApplication();
		baseApplication.setAppHttpClient(appHttpClient);
	}
	
	public void destroySession(){
		baseApplication.setAppHttpClient(null);
	}
	
	
	
	
}
