package com.pokerclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;





import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class TableTexas extends Activity
{
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle intent = getIntent().getExtras();
		String portnumber = intent.getString("tables"); 
		
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//setContentView(R.layout.table_texas);
		
		String loginData = portnumber;
		String username1 = ((Username)this.getApplication()).getSomeVariable();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new MainTablePanel(this,  portnumber,  username1));  
		
		
		
		
	}
	
	 @Override
		protected void onDestroy() {
			
			super.onDestroy();
			
		}

		@Override
		protected void onStop() {
			
			super.onStop();
			
		}
}
	/*public class TablePlay implements Runnable 
    {
		private String serverport;
		private String username1;
    	public TablePlay(String serverport, String username1)
    	{
    		this.serverport = serverport;
    		this.username1 = username1;
    	}

		
		
		public void run()
		{
			DatagramSocket socket = null;
			DatagramPacket p = null;
			DatagramPacket packet = null;
			byte[] sendData = null;
			byte[] receiveData = null;
			
			
			try
			{
				
				
				
				
			socket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("98.244.62.6");
			
			int port = Integer.parseInt(serverport);			
			sendData = new byte[100];
			receiveData = new byte[100];
			 
			String user = "0".concat(username1);
			sendData = user.getBytes();
			packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			socket.send(packet);
			 

			while(true)
			{
				
			receiveData = new byte[100];
			packet = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(packet);


			String data = new String(packet.getData());
			//System.out.println("FROM SERVER:" + data);

			 
			

			// dealt cards
			if((data.substring(0,1)).equals("1"))
			{
				final String card1 =  "c"+data.substring(1, 5).trim();
				final String card2 =  "c"+data.substring(5, 9).trim();
				
				
				
				
				twoCards = new TableTexasHand( card1,card2);
				setContentView(twoCards);
				
				//twoCards = new TableTexasHand(card1);
				//System.out.println("Player cards in hand are "+ data.substring(1, 5).trim() + " and " + data.substring(5, 9).trim());
				
			}
			
			//player bets
			if((data.substring(0,1)).equals("2"))
			{
				
				System.out.println("Player turn to "+ data.substring(1, 4).trim());
				System.out.println("Enter bet amount or fold by entering f ");
				 packetnumber =
						new BufferedReader(new InputStreamReader(System.in));
				 String bet = packetnumber.readLine();
				 if(bet.equals("f"))
				 {
					 pn = "1";
					 sendData = pn.getBytes();
					 sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					 clientSocket.send(sendPacket);
				 
				 }
				 else
				 {
					 pn="2";
				 
				 
				 pn = pn.concat(bet);
				 len =pn.length();
					
						
						for(int i =len; i <10; i++)
						{
							pn =pn.concat("  ");
							i++;
						}
						
					
				 sendData = pn.getBytes();
				 sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				 clientSocket.send(sendPacket);
				 
				 }
			}

			if((data.substring(0,1)).equals("4"))
			{
				
				System.out.println("Player turn to "+ data.substring(1, 4).trim());
				System.out.println("Enter bet amount or fold by entering f ");
				 packetnumber =
						new BufferedReader(new InputStreamReader(System.in));
				 String bet = packetnumber.readLine();
				 if(bet.equals("f"))
				 {
					 pn = "1";
					 sendData = pn.getBytes();
					 sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					 clientSocket.send(sendPacket);
				 
				 }
				 else
				 {
					 pn="4";
				 
				 
				 pn = pn.concat(bet);
				 len =pn.length();
					
					
					for(int i =len; i <10; i++)
					{
						pn =pn.concat("  ");
						i++;
					}
					
				
					sendData = pn.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					clientSocket.send(sendPacket);
				 }
			}

			if((data.substring(0,1)).equals("6"))
			{
				
				System.out.println("Player turn to "+ data.substring(1, 4).trim());
				System.out.println("Enter bet amount or fold by entering f ");
				 packetnumber =
						new BufferedReader(new InputStreamReader(System.in));
				 String bet = packetnumber.readLine();
				 if(bet.equals("f"))
				 {
					 pn = "1";
					 sendData = pn.getBytes();
					 sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					 clientSocket.send(sendPacket);
				 
				 }
				 else
				 {
					 pn="6";
				 
				 
				 pn = pn.concat(bet);
				 len =pn.length();
					
					
					for(int i =len; i <10; i++)
					{
						pn =pn.concat("  ");
						i++;
					}
					
				
					sendData = pn.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					clientSocket.send(sendPacket);
				 }
			}

			if((data.substring(0,1)).equals("8"))
			{
				
				System.out.println("Player turn to "+ data.substring(1, 4).trim());
				System.out.println("Enter bet amount or fold by entering f ");
				 packetnumber =
						new BufferedReader(new InputStreamReader(System.in));
				 String bet = packetnumber.readLine();
				 if(bet.equals("f"))
				 {
					 pn = "1";
					 sendData = pn.getBytes();
					 sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					 clientSocket.send(sendPacket);
				 
				 }
				 else
				 {
					 pn="8";
				 
				 
				 pn = pn.concat(bet);
				 len =pn.length();
					
					
					for(int i =len; i <10; i++)
					{
						pn =pn.concat("  ");
						i++;
					}
					
				
					sendData = pn.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					clientSocket.send(sendPacket);
				 }
			}

			if((data.substring(0,1).equals("D")))
			{
				
				System.out.println("Player "+ data.substring(1, 21).trim() +  " folded! " );
				
			}

			if((data.substring(0,1).equals("A")))
			{
				
				System.out.println("Player "+ data.substring(1, 21).trim() + " bet " + data.substring(21,31).trim() + " the pot size is" + data.substring(31, 41).trim());
				
			}

			if((data.substring(0,1).equals("W")))
			{
				
				System.out.println("Player "+ data.substring(1, 21).trim() + " won because everyone folded " );
					
			}

			if((data.substring(0,1)).equals("3"))
			{
				System.out.println(data);
				System.out.println("Flop cards are "+ data.substring(1, 5).trim() + " and " + data.substring(5,9).trim()+ " and " + data.substring(9,13).trim());
				
			}

			if((data.substring(0,1)).equals("5"))
			{
				
				System.out.println("Turn card is "+ data.substring(1, 5).trim() );
				
			}
			if((data.substring(0,1)).equals("7"))
			{
				
				System.out.println("River card is "+ data.substring(1, 5).trim() );
				
			}

			if((data.substring(0,1)).equals("9"))
			{
				
				System.out.println(data.substring(1, 21).trim() + " won " + data.substring(21,31).trim() );
				
			}

			if((data.substring(0,1).equals("S")))
			{
				
				System.out.println(data.substring(1, 21).trim() + " and " + data.substring(21,41).trim() + " both win " + data.substring(41, 51).trim() );
				
			}
			}
			}
			catch (Exception e) 
	        {
		    }
			
			}
		}
		

    };*/
	
	

