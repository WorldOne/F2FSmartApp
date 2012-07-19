package com.worldone.f2fsmartapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.worldone.f2fsmartapp.bases.BaseActivity;
import com.worldone.f2fsmartapp.location.CurrentLocation;
import com.worldone.f2fsmartapp.utils.ActivityManager;
import com.worldone.f2fsmartapp.utils.NonSecureInterface;

public class MainLauncherActivity extends BaseActivity implements
		NonSecureInterface {
	private Button login;
	private ProgressDialog progress;
	private Login loginAsync;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addMainLayout(R.layout.launcher_view,0);

		getActivityManager().setupActionBarLauncher();

        CurrentLocation.startLocationManager(this);

		login = (Button) findViewById(R.id.login_btn);
		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				loginAsync = new Login();
				loginAsync.execute();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loginAsync.cancel(true);
	}
	
	

	public class Login extends AsyncTask<Void, Void, Void> {
		private Exception exception;

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(MainLauncherActivity.this);
			progress.setMessage(getString(R.string.dialog_loading_message));
			progress.setCancelable(true);
			progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					loginAsync.cancel(true);
				}
			});
			progress.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// create here http client instance with user session and store it
			// in an application holder
			// *****simulating login****//
			if(!isCancelled()){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					exception = e;
				} catch (Exception e) {
					exception = e;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
				if (progress.isShowing()) {
					progress.dismiss();
				}
				if (exception == null) {
					MainActivity t = new MainActivity();
					if (getActivityManager().canIGoFurther(t)) {
						Intent intent = new Intent(MainLauncherActivity.this,
								MainActivity.class);
						startActivity(intent);
					}
				} else {
					ActivityManager.sessionManager.destroySession();
					// display error dialog
				}
			}
	

	}

}
