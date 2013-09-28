package com.pokerclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	String mResHolder0 = "0";
	String [] tables;
	 
	 
	
	Handler handler0 = new Handler()
    {
            @Override public void handleMessage(Message msg)
            {
            	tables = mResHolder0.split(",");
            	  
            	Intent i = new Intent(MainActivity.this, TableList.class);
		   		i.putExtra("strings", tables);
		   		startActivity(i);	
      			
            	
                
               // mBackground1.stop();
            }
    };
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        
    }
    
    public void tableTextSelect(View view)
    {
    	Runnable r = new GetTables();
		new Thread(r).start();
    }
    
    public void logoutMenu(View view)
    {
    	Intent logout = new Intent("com.pokerclient.LOGIN_CREATE");
		startActivity(logout);
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
    
	public class GetTables implements Runnable 
    {
    	
    	

		
		
		public void run()
		{
			DatagramSocket socket = null;
			DatagramPacket p = null;
			DatagramPacket packet = null;
			byte[] sendbuf = null;
			byte[] recbuf = null;
		
			try
			{
					sendbuf = new byte[100];
					recbuf = new byte[100];
					String message = "20";
					int server_port = 5554;
	        
	        
					socket = new DatagramSocket();
	        		InetAddress IpAddress = InetAddress.getByName("98.244.62.6");
		        
		        	
		    	   	sendbuf =message.getBytes();
		    	   	p = new DatagramPacket(sendbuf, sendbuf.length, IpAddress,server_port);
		    	   	socket.send(p);
		    	
		    	   	
		    	   	packet = new DatagramPacket(recbuf, recbuf.length);
			   		socket.receive(packet);
			   		final  String data = new String(packet.getData());   	
			   		socket.close();
			   		
			   			
			   		mResHolder0=data;
    			   		
    			   		
    			   	// Get a message object to be sent to our handler.
    			   	Message myMsg = handler0.obtainMessage();

    			   		

    			   	// Send the handler message to the UI thread.
    			   	handler0.sendMessage(myMsg);	   		
			   		
			   	
			   		
	        }
	        catch (Exception e) 
	        {
		    }
	        
	        
		}

    };		

    
}
