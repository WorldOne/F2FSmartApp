package com.worldone.f2fsmartapp.utils;

import com.worldone.f2fsmartapp.R;
import com.worldone.f2fsmartapp.models.LocationData;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ActivityManager {

	public static Activity mActivity;
	public static SessionManager sessionManager; //logged user session object
	public static LocationData locationData; //static reference to location object
	public ActionBar actionBar;
	

	public static ActivityManager createManagerInstance(Activity activity){
		return new ActivityManager(activity);
	}
	
	public ActivityManager(Activity activity){
		mActivity = activity;
		sessionManager = new SessionManager(mActivity);
		
	}
	
	public ActivityManager(Context ctx) {
		locationData = new LocationData();
	}

	public ActionBar getActionBar() {
		return actionBar;
	}

	public void setActionBar(ActionBar actionBar) {
		this.actionBar = actionBar;
	}
	
	
	
	/**
	 * Check if there are available the Internet connection. Good for the background tasks. 
	 * @param ctx
	 * @return
	 */
	public static boolean haveInternet(Context ctx) {

	    NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

	    if (info == null || !info.isConnected()) {
	    	ActivityManager.sessionManager.destroySession();
	        return false;
	    }
	    if (info.isRoaming()) {
	        return true;
	    }
	    return true;
	}
	
	
	
	 public void setupActionBarLauncher(){
	    	//first element
	    	getActionBar().addElement(mActivity,mActivity.getApplicationContext(),R.layout.button);
	        getActionBar().getView().setBackgroundResource(R.drawable.logo);
	        RelativeLayout.LayoutParams params =  (RelativeLayout.LayoutParams) getActionBar().getView().getLayoutParams();
	        params.addRule(RelativeLayout.CENTER_VERTICAL);
	        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        params.leftMargin = 10;
	        getActionBar().getButton().setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					mActivity.finish();
				}
			});
	        getActionBar().addToPosition(0);		
	        //second element
	       
	    }
	 
	 public void setupActionBarMain(){
		 //setupActionBarLauncher();
		    getActionBar().addElement(mActivity,mActivity.getApplicationContext(),R.layout.textview);
	        RelativeLayout.LayoutParams params =  (RelativeLayout.LayoutParams) getActionBar().getView().getLayoutParams();
	        params.addRule(RelativeLayout.CENTER_VERTICAL);
	        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
	        //params.leftMargin = 10
	        getActionBar().addToPosition(0);
		 
	 }

	public boolean canIGoFurther(Activity activity) {
		if (activity instanceof SecureInterface && sessionManager.isLogged()) {
			return true;
		}
		return false;
	}
	 
	
	
	
	
}
