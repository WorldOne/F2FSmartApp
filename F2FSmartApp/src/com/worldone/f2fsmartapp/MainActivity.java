package com.worldone.f2fsmartapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldone.f2fsmartapp.bases.BaseActivity;
import com.worldone.f2fsmartapp.location.CurrentLocation;
import com.worldone.f2fsmartapp.location.LocationDetailsManager;
import com.worldone.f2fsmartapp.utils.ActivityManager;
import com.worldone.f2fsmartapp.utils.SecureInterface;

public class MainActivity extends BaseActivity implements SecureInterface{

	private static String TAG = "MainActivity";
	private Button displayCamera, sendPhoto;
	private TextView infoLatitude, infoLongitude, infoUser, infoTime;
	private String FOTO = Environment.getExternalStorageDirectory().getName() + File.separatorChar + "tmp.jpg";

	private int fotoBytes = 125000;
	private int fotoShort = 400;
	private int fotoLong = 480;
	
	private int CAMERA_PIC_REQUEST = 1;
	public MainActivity(){ }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMainLayout(R.layout.activity_main,1); 
		
        getActivityManager().setupActionBarMain();
        addActionBarHeader(getString(R.string.actionbar_header_text));
        
        sendPhoto = (Button) findViewById(R.id.send_photo);
        displayCamera = (Button) findViewById(R.id.camera_btn);
        infoLatitude = (TextView) findViewById(R.id.info_view_latitude);
        infoLongitude = (TextView) findViewById(R.id.info_view_longtitude);
        infoTime = (TextView) findViewById(R.id.info_view_time);
        infoUser = (TextView) findViewById(R.id.info_view_user);

        infoLatitude.setText("Latitude: "+ActivityManager.locationData.getLocation().getLatitude());
        infoLongitude.setText("Longitude: "+ActivityManager.locationData.getLocation().getLongitude());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		Date date = new Date(ActivityManager.locationData.getLocation().getTime());
		infoTime.setText(sdf.format(date));
		infoUser.setText("***DEMO***");
		
        displayCamera.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
					Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		            File photo = new File(FOTO);
		            try {
		                if(photo.exists() == false) {
		                    photo.getParentFile().mkdirs();
		                    photo.createNewFile();
		                }
		            } catch (IOException e) {
		                Log.e(TAG, "Could not create file.", e);
		            }
		           
		            Uri _fileUri = Uri.fromFile(photo);
		            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);
		    		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		    	}
				
				
		
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addActionBarHeader(String text){
    	TextView header = (TextView) getActivityManager().getActionBar().getParentView().getChildAt(0);
    	header.setText(text);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
            	sendPhoto.setVisibility(View.VISIBLE);
                ImageView image = (ImageView) findViewById(R.id.photo_full);
                Bitmap resized = scaleToTargetSize(getAndResizeBitmap());
                image.setImageBitmap(resized);
            } else {
            	Log.i(TAG,"Camera problem, while taking picture.");
            }
        }
    }
    
    protected Bitmap getAndResizeBitmap(){
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(FOTO, options);
        int h, w;
        if (options.outHeight > options.outWidth){
        	h = (int) FloatMath.ceil(options.outHeight/(float) fotoShort);
        	w = (int) FloatMath.ceil(options.outWidth/(float) fotoLong);
        } else {
        	w = (int) FloatMath.ceil(options.outHeight/(float) fotoShort);
        	h = (int) FloatMath.ceil(options.outWidth/(float) fotoLong);
        }
        if(h>1 || w>1){
        	options.inSampleSize = (h > w) ? h : w;
        }
        options.inJustDecodeBounds=false;
        bitmap =BitmapFactory.decodeFile(FOTO, options);
        return bitmap;
    }

    protected int bitmapBytes(Bitmap bitmap){
    	return bitmap.getRowBytes()*bitmap.getHeight();
    }
   
    protected Bitmap scaleToTargetSize(Bitmap thumbnail){
    	int givenBytes = thumbnail.getRowBytes()*thumbnail.getHeight();
    	int givenPixels = thumbnail.getHeight()*thumbnail.getWidth();
    	int bytes_p_pixel = givenBytes/givenPixels;
    	
    	int targetPixels = (int) (fotoBytes/(1.0*bytes_p_pixel));
    	double factor = Math.sqrt(targetPixels/(1.0*givenPixels));
    	
    	int newWidth = (int)(thumbnail.getWidth()*factor);
    	int newHeight = (int)(thumbnail.getHeight()*factor);
    	return Bitmap.createScaledBitmap(thumbnail, newWidth, newHeight, true);
    }
    
    
}
