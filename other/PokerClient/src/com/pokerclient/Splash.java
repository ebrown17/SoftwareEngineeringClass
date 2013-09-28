package com.pokerclient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Splash extends Activity {
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        
	        Thread timer = new Thread()
	        {   
	        	
	        	public void run()
	        	{
	        		try
	        		{
	        			sleep(1000);
	        		}
	        		catch(InterruptedException e)
	        		{e.printStackTrace();
	        			
	        		}finally
	        		{
	        			Intent login = new Intent("com.pokerclient.LOGIN_CREATE");
	        			startActivity(login);
	        		}
	        	}
	        };
	        timer.start(); 
	    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}