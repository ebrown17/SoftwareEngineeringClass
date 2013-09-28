package com.pokerclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;





import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Create extends Activity
{
	
	private EditText	login,password;
			
	int length, length2;
	String mResHolder0 = "0";
	String mResHolder1 = "0";
	String mResHolder2 = "0";
	String dataSend;
	
	 
	
	Handler handler0 = new Handler()
    {
            @Override public void handleMessage(Message msg)
            {
                TextView mResult1 = (TextView)findViewById(R.id.textView1);
                mResult1.setText(mResHolder0);
	   			Intent mainMenu = new Intent("com.pokerclient.MainActivity");
    			startActivity(mainMenu);
               // mBackground1.stop();
            }
    };
    Handler handler1 = new Handler()
    {
            @Override public void handleMessage(Message msg)
            {
                TextView mResult1 = (TextView)findViewById(R.id.textView1);
                mResult1.setText(mResHolder1);
               // mBackground1.stop();
            }
    };
    Handler handler2 = new Handler()
    {
            @Override public void handleMessage(Message msg)
            {
                TextView mResult2 = (TextView)findViewById(R.id.textView1);
                mResult2.setText(mResHolder2);
               // mBackground1.stop();
            }
    };
	
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_create);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
	 
	 
	 




	public void loginAttempt(View v)
	 {
		
		 login = (EditText) findViewById(R.id.editText1);
		 password =(EditText) findViewById(R.id.editText2);
		 String username = login.getText().toString();
		 String pw = password.getText().toString();
		 
		 if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pw))
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 length = username.length();
		 length2 = pw.length();
		 
		 if(length>20 || length < 4)
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 else
		 {
			 for(int i =length; i < 20;i++)
			 {
				 username = username.concat(" ");				 
			 }
			 ((Username) this.getApplication()).setSomeVariable(username);
		 }
		 
		 if(length2>20 || length2 < 4)
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 else
		 {
			 for(int i =length2; i < 20;i++)
			 {
				 pw = pw.concat(" ");
			 }
		 }
		 
		 String loginData = username.concat(pw);
		 
		 Runnable r = new Login(loginData);
		 new Thread(r).start();
		 
		 
	 }
	 
	 public void createAttempt(View v)
	 {
		 login = (EditText) findViewById(R.id.editText1);
		 password =(EditText) findViewById(R.id.editText2);
		 String username = login.getText().toString();
		 String pw = password.getText().toString();
		 
		 if(username.matches("") || pw.matches(""))
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 length = username.length();
		 length2 = password.length();
		 
		 if(length>20 || length < 4)
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 else
		 {
			 for(int i =length; i < 20;i++)
			 {
				 username = username.concat(" ");
				 
				 
			 }
		 }
		 
		 if(length2>20 || length2 < 4)
		 {
			 TextView textview= (TextView)findViewById(R.id.textView1);
			  textview.setText("Your username and password must be 4 to 20 characters long");
			 return;
		 }
		 else
		 {
			 for(int i =length2; i < 20;i++)
			 {
				 pw= pw.concat(" ");
			 }
		 }
		 
		 String createData = username.concat(pw);
		 
		 Runnable t = new Create(createData);
		 new Thread(t).start();
		 
	 }
	 
	 
	    public class Login implements Runnable 
	    {
	    	
	    	private String send;

			public Login(String send)
			{
	    	       this.send = send;
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
    					String message = "1"+send;
    					int server_port = 5554;
    	        
    	        
    					socket = new DatagramSocket();
    	        		InetAddress IpAddress = InetAddress.getByName("98.244.62.6"); //hardcoded server ip
    		        
    		        	
    		    	   	sendbuf =message.getBytes();
    		    	   	p = new DatagramPacket(sendbuf, sendbuf.length, IpAddress,server_port);
    		    	   	socket.send(p);
    		    	
    		    	   	
    		    	   	packet = new DatagramPacket(recbuf, recbuf.length);
    			   		socket.receive(packet);
    			   		socket.close();
    			   		final  String data = new String(packet.getData());   	
    			   		
    			   		if(data.substring(0,1).equals("1"))
    			   		{
    			   			mResHolder1="Login Success";
        			   		
        			   		
        			   		// Get a message object to be sent to our handler.
        			   		Message myMsg = handler0.obtainMessage();

        			   		// Set the data into our handler message.
        			   		// myMsg.obj = "sum is: ";

        			   		// Send the handler message to the UI thread.
        			   		handler0.sendMessage(myMsg);
    			   		}
    			   		
    			   		else if(data.substring(0,1).equals("0"))
    			   		{	
    			   		mResHolder1="Bad username or password";
    			   		
    			   		
    			   		// Get a message object to be sent to our handler.
    			   		Message myMsg = handler1.obtainMessage();

    			   		// Set the data into our handler message.
    			   		// myMsg.obj = "sum is: ";

    			   		// Send the handler message to the UI thread.
    			   		handler1.sendMessage(myMsg);
    			   		}
    			   		else
    			   		{
    			   			mResHolder1="Could not connect to server";
        			   		
        			   		
        			   		// Get a message object to be sent to our handler.
        			   		Message myMsg = handler1.obtainMessage();

        			   		// Set the data into our handler message.
        			   		// myMsg.obj = "sum is: ";

        			   		// Send the handler message to the UI thread.
        			   		handler1.sendMessage(myMsg);
    			   		}
    		       
    	        }
    	        catch (Exception e) 
    	        {
    		    }
    	        
    	        
    		}

	    };	
	 
	    public class Create implements Runnable 
	    {
	    	
	    	private String send;

			public Create(String send)
			{
	    	       this.send = send;
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
    					String message = "0"+send;
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
    			   		if(data.substring(0,1).equals("1"))
    			   		{
    			   			mResHolder2="Account created successfully";
        			   		
        			   		
        			   		// Get a message object to be sent to our handler.
        			   		Message myMsg = handler2.obtainMessage();

        			   		// Set the data into our handler message.
        			   		// myMsg.obj = "sum is: ";

        			   		// Send the handler message to the UI thread.
        			   		handler2.sendMessage(myMsg);
    			   			
    			   		}
    			   		
    			   		else if(data.substring(0,1).equals("0"))
    			   		{	
    			   		mResHolder2="Account creation failed";
    			   		
    			   		
    			   		// Get a message object to be sent to our handler.
    			   		Message myMsg = handler2.obtainMessage();

    			   		// Set the data into our handler message.
    			   		// myMsg.obj = "sum is: ";

    			   		// Send the handler message to the UI thread.
    			   		handler2.sendMessage(myMsg);
    			   		}
    			   		else
    			   		{
    			   			mResHolder2="Could not connect to server";
        			   		
        			   		
        			   		// Get a message object to be sent to our handler.
        			   		Message myMsg = handler2.obtainMessage();

        			   		// Set the data into our handler message.
        			   		// myMsg.obj = "sum is: ";

        			   		// Send the handler message to the UI thread.
        			   		handler2.sendMessage(myMsg);
    			   		}
    			   		
    	        }
    	        catch (Exception e) 
    	        {
    		    }
    	        
    	        
    		}

	    };	
	 
		 @Override
			protected void onPause() {
				// TODO Auto-generated method stub
				super.onPause();
				TextView textview = (TextView)findViewById(R.id.textView1);
				textview.setText("");
				login = (EditText)findViewById(R.id.editText1);
				login.setText("User name");
				password = (EditText)findViewById(R.id.editText2);
				password.setText("Password");
				finish();
			}
	 
}