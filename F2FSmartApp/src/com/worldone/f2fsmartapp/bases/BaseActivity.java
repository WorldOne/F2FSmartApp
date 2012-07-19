package com.worldone.f2fsmartapp.bases;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.worldone.f2fsmartapp.R;
import com.worldone.f2fsmartapp.utils.ActionBar;
import com.worldone.f2fsmartapp.utils.ActivityManager;

public abstract class BaseActivity extends Activity {
    private View view;
    
	private String TAG = this.getClass().getSimpleName();
	
	private ActivityManager mManager = ActivityManager.createManagerInstance(this);
	
	public ActivityManager getActivityManager() {
		return mManager;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(ActivityManager.haveInternet(getApplicationContext()) == false) {
    		
    		Log.i(TAG, "No connection");
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		getActivityManager().setActionBar(new ActionBar(this));
	}
	
	/**
	 * Adds layout below action bar.
	 * @param layoutId
	 */
	public void addMainLayout(int layoutId, int type){
        ViewGroup parent = (ViewGroup) findViewById(R.id.parent_layout);
		view = LayoutInflater.from(getBaseContext()).inflate(layoutId,
                parent, false);
		 RelativeLayout.LayoutParams params =  (RelativeLayout.LayoutParams) view.getLayoutParams();
		 if(type == 1)  params.addRule(RelativeLayout.BELOW, R.id.main_actionbar);
		 parent.addView(view, 1);
        
	}
	
}
