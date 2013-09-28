package com.pokerclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.pokerclient.Login_Create.Login;
import com.pokerclient.MainActivity.GetTables;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TableList extends ListActivity 
{
	private String tables;
	private String [] array;
	String mResHolder0 = "0";
	
	Handler handler0 = new Handler()
    {
            @Override public void handleMessage(Message msg)
            {
            	tables = mResHolder0;
            	  
            	Intent i = new Intent(TableList.this, TableTexas.class);
		   		i.putExtra("tables", tables);
		   		startActivity(i);	
      			
            	
                
               // mBackground1.stop();
            }
    };
	
	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		 array = intent.getStringArrayExtra("strings");
		
		setListAdapter(new ArrayAdapter<String>(this,R.layout.row,array));
		ListView list = getListView();
		
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int tablenumber,
					long arg3) {
				// TODO Auto-generated method stub
				/*Intent login = new Intent("com.pokerclient.LOGIN_CREATE");
    			startActivity(login);*/
				
				
				 String loginData = array[tablenumber];
				 
				 Runnable r = new TableJoin(loginData);
				 new Thread(r).start();
				
			}
			
		});
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	public class TableJoin implements Runnable 
    {
		private String data;
    	public TableJoin(String data)
    	{
    		this.data = data.trim();
    		
    	}

		
		
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
					String message = "3".concat(data);
					int server_port = 5554;
	        
	        
					socket = new DatagramSocket();
	        		InetAddress IpAddress = InetAddress.getByName("98.244.62.6");
		        
		        	
		    	   	sendbuf =message.getBytes();
		    	   	p = new DatagramPacket(sendbuf, sendbuf.length, IpAddress,server_port);
		    	   	socket.send(p);
		    	
		    	   	
		    	   	packet = new DatagramPacket(recbuf, recbuf.length);
			   		socket.receive(packet);
			   		final  String data1 = new String(packet.getData());   	
			   		socket.close();
			   		
			   		String tits = data1.substring(0, 4);	
			   		mResHolder0=tits;
    			   		
    			   		
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
