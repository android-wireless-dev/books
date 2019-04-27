package com.androidbook.myfirstandroidapp;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MyFirstAndroidAppActivity extends Activity {
	
	private static final String DEBUG_TAG = "MyFirstAppLogging";
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.i(DEBUG_TAG, "In the onCreate() method of the MyFirstAndroidAppActivity Class");
        //forceError();
        playMusicFromWeb();
        //getLocation();
    }
    
    public void forceError() {
    	if (true)
    	{
    		throw new Error("Whoops");
    	}
    }
    
    public void playMusicFromWeb() {
        try {
            Uri file = Uri.parse("http://www.perlgurl.org/podcast/archives"
                + "/podcasts/PerlgurlPromo.mp3");
            mp = MediaPlayer.create(this, file);
            mp.start();
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Player failed", e);
        }
    }
    
    public void getLocation() {
        try {
            LocationManager locMgr = (LocationManager)
                getSystemService(LOCATION_SERVICE);
            Location recentLoc = locMgr.
                getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(recentLoc != null)
            {
            	Log.i(DEBUG_TAG, "loc: " + recentLoc.toString());
            }
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Location failed", e);
        }
    }


	@Override
	protected void onStop() {
	    if (mp != null) {
	        mp.stop();
	        mp.release();
	    }
	    super.onStop();
	}
    
    

}