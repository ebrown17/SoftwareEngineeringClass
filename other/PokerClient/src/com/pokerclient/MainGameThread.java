package com.pokerclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainGameThread extends Thread {
	
	
	private static final String TAG = MainGameThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainTablePanel gamePanel;
	
	

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	private String serverport;
	private String username1;

	
	
	public MainGameThread(SurfaceHolder surfaceHolder, MainTablePanel gamePanel, String portnumber, String username) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		
		this.serverport = portnumber;
		this.username1 = username;
	}

	@Override
	public void run() {
		
		DatagramSocket socket = null;
		DatagramPacket p = null;
		DatagramPacket packet = null;
		byte[] sendData = null;
		byte[] receiveData = null;
		Canvas canvas;
		String user=username1;
		String porter = serverport;
		
		boolean firstBet = true;
		int prevBet = 0;
		int mainPot = 0;
		String prevUser = "";
		
while (running) {
			
			canvas = null;
			try {
					canvas = this.surfaceHolder.lockCanvas();
					synchronized (surfaceHolder) 
					{
						this.gamePanel.drawBackground(canvas)	;
					}
				}
				finally {
						if (canvas != null) 
						{
							surfaceHolder.unlockCanvasAndPost(canvas);
						}	
						}
					try
					{
						
					socket = new DatagramSocket();
					InetAddress IPAddress = InetAddress.getByName("98.244.62.6");
					
					int port = Integer.parseInt(porter);
					int chips = 0;
					sendData = new byte[200];
					receiveData = new byte[200];
					 
					String users = "0".concat(user);
					sendData = users.getBytes();
					packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					socket.send(packet);
					 

					while(true)
					{
						
					receiveData = new byte[200];
					packet = new DatagramPacket(receiveData, receiveData.length);
					socket.receive(packet);
					boolean tit =true;

					String data = new String(packet.getData());
					

					
/**********************************************
* 		Phase 0 - Receiving all player info
***********************************************/
					// Here assign curplayer info ( what should be displayed)
					if(data.substring(0,1).trim().equals("0"))  //NEW
					{
						this.gamePanel.clrPlayers();
						this.gamePanel.clrPot();
						
						int p1coins = 0;
						int p2coins = 0;
						int p3coins = 0;
						int p4coins = 0;
						
						String p1user = null;
						String p2user = null;
						String p3user = null;
						String p4user = null;
						
						if(!data.substring(21,31).trim().equals(""))
						{
							p1coins = Integer.parseInt(data.substring(21,31).trim());
							p1user = data.substring(1,21).trim();
						}
						if(!data.substring(51,61).trim().equals(""))
						{
							p2coins = Integer.parseInt(data.substring(51,61).trim());
							p2user = data.substring(31,51).trim();
						}
						if(!data.substring(81,91).trim().equals(""))
						{
							p3coins = Integer.parseInt(data.substring(81,91).trim());
							p3user = data.substring(61,81).trim();
						}
						if(!data.substring(111,121).trim().equals(""))
						{
							p4coins = Integer.parseInt(data.substring(111,121).trim());
							p4user = data.substring(91,111).trim();
						}
						
						if(p1coins > 0)
						{
							this.gamePanel.setPlayer(1, p1coins, p1user);
						}
						if(p2coins > 0)
						{
							this.gamePanel.setPlayer(2, p2coins, p2user);
						}
						if(p3coins > 0)
						{
							this.gamePanel.setPlayer(3, p3coins, p3user);
						}
						if(p4coins > 0)
						{
							this.gamePanel.setPlayer(4, p4coins, p4user);
						}
						
						this.gamePanel.setPlayerCoords(canvas);
		
					}
					 
					
/***************************************
* 		Phase A - A player bet
***************************************/					
					// someone bet, display updated info.
					if(data.substring(0,1).trim().equals("A"))  //NEW
					{
						firstBet = false;
						
						prevBet = Integer.parseInt(data.substring(21,31).trim());
						prevUser = data.substring(1,21).trim();
						mainPot = Integer.parseInt(data.substring(31,41).trim());
						this.gamePanel.setMainPot(mainPot); 
						this.gamePanel.setPrevBet(prevUser,prevBet);
						this.gamePanel.decrPlayerCoin(prevUser, prevBet);
						
						
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawPlayerBet(canvas);
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
										
									}	
									}
						sleep(3000); // to display for 3 seconds before processing next packet
						
					}

/***************************************
* 		Phase D - A player folded
***************************************/
					//player folded, display accordingly
					if((data.substring(0,1).equals("D")))
					{
						firstBet = false;
						String foldPlr = data.substring(1, 21).trim();
						
						this.gamePanel.setPlayerFold(foldPlr);
						this.gamePanel.setPrevBet(foldPlr, 0);
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawPlayerFold(canvas); 
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
										
									}	
									}
						sleep(3000); // to display for 3 seconds before processing next packet
					}
					
					
					
/***************************************
* 		Phase 1 - cards dealt
***************************************/
					if((data.substring(0,1)).equals("1"))
					{
						firstBet = true;
						final String card1 =  data.substring(1, 5).trim();
						final String card2 =  data.substring(5, 9).trim();
						final String chipCount = data.substring(9,19).trim();
						
						chips = Integer.parseInt(chipCount);						
						this.gamePanel.chipSet(chipCount);
						
						this.gamePanel.card1Set(card1);
						this.gamePanel.card2Set(card2);	
						
						this.gamePanel.unsetBet();
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawHand(canvas)	;
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
										
									}	
									}
	
					}

					
/***************************************
 * 			Phase 2
 ***************************************/
					 
					if((data.substring(0,1)).equals("2"))
					{	this.gamePanel.unsetBet();
					
						
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.handBet(canvas)	;
									
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
									}	
									}
						
						while(tit){
							
							if(this.gamePanel.getBetting() && !this.gamePanel.isAdding())
							{	
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.handBetStart(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
												this.gamePanel.setOkAdd();
												this.gamePanel.setIsAdding();
											}	
											}
								
							}
							
							//canceling
							if(this.gamePanel.getCancel() && this.gamePanel.isAdding())
							{
								this.gamePanel.unsetBet();
								this.gamePanel.clrAdding();
								this.gamePanel.clrCancel();
								
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.handBet(canvas)	;
											
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							
							
							//kara code
							if(this.gamePanel.isAdding())
							{
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.handBetStart(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
											}	
											}
								
							}
							
							if(this.gamePanel.getFolding())
							{
								String pn ="1";

								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								this.gamePanel.clrFolding();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawHand(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
								
							}
							
							if((data.substring(21, 31).trim().equals("0") || firstBet) && this.gamePanel.getChecking())
							{
								String pn ="2" + "0";
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet();
								this.gamePanel.clrChecking();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawHand(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							//NEW
							if(this.gamePanel.getCalling())
							{
								String amtCalling;
								if(firstBet)
								{
									prevBet = 0;
								}
								
								
								amtCalling = Integer.toString(this.gamePanel.callingAmt(prevBet));
								
								
								String pn ="2" + amtCalling;
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet();
								this.gamePanel.clrChecking();
								this.gamePanel.clrCalling();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawHand(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							
							
							//END NEW
							if(this.gamePanel.getCurBetAmt() > 0 && this.gamePanel.getBetting())
							{
								this.gamePanel.okToBet();
							}
							
							//end kara
							
							
							if(this.gamePanel.canBet()  && !this.gamePanel.getFolding()) // put in allin check? or handled?
							{
								String pn ="2" + this.gamePanel.getCurBetAmt();
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawHand(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
								
							}
						}
					}

/***************************************
* 			Phase 3
***************************************/
					
					if((data.substring(0,1)).equals("3"))
					{
						firstBet = true;
						final String card3 =  data.substring(1, 5).trim();
						final String card4 =  data.substring(5,9).trim();
						final String card5 =   data.substring(9,13).trim();
						
						
						this.gamePanel.card3Set(card3);
						this.gamePanel.card4Set(card4);
						this.gamePanel.card5Set(card5); 
						this.gamePanel.unsetBet();
						
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawFlop(canvas)	;
								}
							}
							finally {
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
										this.gamePanel.okToBet();
									}	
									}
									
						
					}
					
/***************************************
* 			Phase 4
***************************************/
					
					if(data.substring(0,1).equals("4") && !this.gamePanel.getFolding())
					{	
						this.gamePanel.unsetBet();
						
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.flopBet(canvas)	;
									
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
									}	
									}
						
						
						
						while(tit){
							
							if(this.gamePanel.getBetting() && !this.gamePanel.isAdding())
							{	//this.gamePanel.unsetBet();
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.flopBetStart(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
												this.gamePanel.setOkAdd();
												this.gamePanel.setIsAdding();
											}	
											}
								
							}
							
							//canceling
							if(this.gamePanel.getCancel() && this.gamePanel.isAdding())
							{
								this.gamePanel.unsetBet();
								this.gamePanel.clrAdding();
								this.gamePanel.clrCancel();
								
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.flopBet(canvas)	;
											
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							
							//kara code
							if(this.gamePanel.isAdding())
							{
								//this.gamePanel.unsetBet();
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.flopBetStart(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												//this.gamePanel.unsetBet();
											}	
											}
								
							}
							
							if(this.gamePanel.getFolding())
							{
								String pn ="1";

								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								this.gamePanel.clrChecking();
								this.gamePanel.clrCalling();
								this.gamePanel.clrFolding();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawFlop(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							
							if((data.substring(21, 31).trim().equals("0") || firstBet) && this.gamePanel.getChecking())
							{
								String pn ="4" + "0";
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								this.gamePanel.clrChecking();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawFlop(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							//NEW
							if(this.gamePanel.getCalling())
							{
								String amtCalling;
								if(firstBet)
								{
									prevBet = 0;
								}
								
								
								amtCalling = Integer.toString(this.gamePanel.callingAmt(prevBet));
								
								
								String pn ="4" + amtCalling;
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								this.gamePanel.clrChecking();
								this.gamePanel.clrCalling();
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawFlop(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
							}
							
							
							//END NEW
							
							if(this.gamePanel.getCurBetAmt() > 0 && this.gamePanel.getBetting())
							{
								this.gamePanel.okToBet();
							}
							
							//end kara
							
							
							if(this.gamePanel.canBet() && !this.gamePanel.getFolding())
							{
								
								String pn ="4" + this.gamePanel.getCurBetAmt();
								for (int i = pn.length(); i<10; i++)
								{
									pn = pn.concat(" ");
								}
								sendData = pn.getBytes();
								packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
								socket.send(packet);
								tit=false;
								// CLEAR VARIABLES
								this.gamePanel.clrAdding();
								this.gamePanel.unsetBet();
								this.gamePanel.clearCurBet(); //kara
								
								//kara
								canvas = null;
								try {
										canvas = this.surfaceHolder.lockCanvas();
										synchronized (surfaceHolder) 
										{
											this.gamePanel.drawFlop(canvas)	;
										}
									}
									finally { 
											if (canvas != null) 
											{
												surfaceHolder.unlockCanvasAndPost(canvas);
												this.gamePanel.unsetBet();
											}	
											}
								
							}
						}
					}
					
/***************************************
* 			Phase 5
***************************************/
					
					if((data.substring(0,1)).equals("5"))
					{
						firstBet = true;
						final String card6 =  data.substring(1, 5).trim();
						this.gamePanel.card6Set(card6);
						this.gamePanel.unsetBet();
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawTurn(canvas)	;
								}
							}
							finally {
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
									}	
									}
					}
					
/***************************************
* 			Phase 6
***************************************/
					
					if((data.substring(0,1)).equals("6") && !this.gamePanel.getFolding())
					{	this.gamePanel.unsetBet();
					
					canvas = null;
					try {
							canvas = this.surfaceHolder.lockCanvas();
							synchronized (surfaceHolder) 
							{
								this.gamePanel.turnBet(canvas)	;
								
							}
						}
						finally { 
								if (canvas != null) 
								{
									surfaceHolder.unlockCanvasAndPost(canvas);
									this.gamePanel.unsetBet();
								}	
								}
					
					
					
					while(tit){
						
						if(this.gamePanel.getBetting() && !this.gamePanel.isAdding())
						{	
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.turnBetStart(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
											this.gamePanel.setOkAdd();
											this.gamePanel.setIsAdding();
										}	
										}
							
						}
						
						//canceling
						if(this.gamePanel.getCancel() && this.gamePanel.isAdding())
						{
							this.gamePanel.unsetBet();
							this.gamePanel.clrAdding();
							this.gamePanel.clrCancel();
							
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.turnBet(canvas)	;
										
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						//kara code
						if(this.gamePanel.isAdding())
						{
							//this.gamePanel.unsetBet();
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.turnBetStart(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											//this.gamePanel.unsetBet();
										}	
										}
							
						}
						
						if(this.gamePanel.getFolding())
						{
							String pn ="1";

							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrFolding();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawTurn(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						if((data.substring(21, 31).trim().equals("0") || firstBet) && this.gamePanel.getChecking())
						{
							String pn ="6" + "0";
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrChecking();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawTurn(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						//NEW
						if(this.gamePanel.getCalling())
						{
							String amtCalling;
							if(firstBet)
							{
								prevBet = 0;
							}
							
							
							amtCalling = Integer.toString(this.gamePanel.callingAmt(prevBet));
							
							
							String pn ="6" + amtCalling;
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrChecking();
							this.gamePanel.clrCalling();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawTurn(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						
						//END NEW
						
						if(this.gamePanel.getCurBetAmt() > 0 && this.gamePanel.getBetting())
						{
							this.gamePanel.okToBet();
						}
						
						//end kara
						
						
						if(this.gamePanel.canBet() && !this.gamePanel.getFolding())
						{
							
							String pn ="6" + this.gamePanel.getCurBetAmt();
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawTurn(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
							
						}
					}
					}
					
					
/***************************************
* 			Phase 7
***************************************/
					
					if((data.substring(0,1)).equals("7"))
					{
						firstBet = true;
						final String card7 =  data.substring(1, 5).trim();
						this.gamePanel.card7Set(card7);
						this.gamePanel.unsetBet();
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawRiver(canvas);
								}
							}
							finally {
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
									}	
									}
						
						
						
					}
					
/***************************************
* 			Phase 8
***************************************/
					
					if((data.substring(0,1)).equals("8") && !this.gamePanel.getFolding())
					{	this.gamePanel.unsetBet();

					canvas = null;
					try {
							canvas = this.surfaceHolder.lockCanvas();
							synchronized (surfaceHolder) 
							{
								this.gamePanel.riverBet(canvas)	;
								
							}
						}
						finally { 
								if (canvas != null) 
								{
									surfaceHolder.unlockCanvasAndPost(canvas);
									this.gamePanel.unsetBet();
								}	
								}
					
					
					
					while(tit){
						
						if(this.gamePanel.getBetting() && !this.gamePanel.isAdding())
						{	
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.riverBetStart(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
											this.gamePanel.setOkAdd();
											this.gamePanel.setIsAdding();
										}	
										}
							
						}
						
						//canceling
						if(this.gamePanel.getCancel() && this.gamePanel.isAdding())
						{
							this.gamePanel.unsetBet();
							this.gamePanel.clrAdding();
							this.gamePanel.clrCancel();
							
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.riverBet(canvas)	;
										
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						//kara code
						if(this.gamePanel.isAdding())
						{
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.riverBetStart(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											
										}	
										}
							
						}
						
						if(this.gamePanel.getFolding())
						{
							String pn ="1";

							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrFolding();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawRiver(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						
						if((data.substring(21, 31).trim().equals("0") || firstBet) && this.gamePanel.getChecking())
						{
							String pn ="8" + "0";
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrChecking();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawRiver(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						//NEW
						if(this.gamePanel.getCalling())
						{
							String amtCalling;
							if(firstBet)
							{
								prevBet = 0;
							}
							
							
							amtCalling = Integer.toString(this.gamePanel.callingAmt(prevBet));
							
							
							String pn ="8" + amtCalling;
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							this.gamePanel.clrChecking();
							this.gamePanel.clrCalling();
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawRiver(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
						}
						
						
						//END NEW
						
						if(this.gamePanel.getCurBetAmt() > 0 && this.gamePanel.getBetting())
						{
							this.gamePanel.okToBet();
						}
						
						//end kara
						
						
						if(this.gamePanel.canBet() && !this.gamePanel.getFolding())
						{
							
							String pn ="8" + this.gamePanel.getCurBetAmt();
							for (int i = pn.length(); i<10; i++)
							{
								pn = pn.concat(" ");
							}
							sendData = pn.getBytes();
							packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
							socket.send(packet);
							tit=false;
							// CLEAR VARIABLES
							this.gamePanel.clrAdding();
							this.gamePanel.unsetBet();
							this.gamePanel.clearCurBet(); //kara
							
							//kara
							canvas = null;
							try {
									canvas = this.surfaceHolder.lockCanvas();
									synchronized (surfaceHolder) 
									{
										this.gamePanel.drawRiver(canvas)	;
									}
								}
								finally { 
										if (canvas != null) 
										{
											surfaceHolder.unlockCanvasAndPost(canvas);
											this.gamePanel.unsetBet();
										}	
										}
							
						}
					}
					}
					
					if((data.substring(0,1)).equals("9") || (data.substring(0,1)).equals("W"))
					{	
//						if((data.substring(1, 21).trim()).equals(username1.trim()))  //dont need to set chips, there is an update
//						{
//							chips = chips +Integer.parseInt(data.substring(21,31).trim());
//							this.gamePanel.chipSet(Integer.toString(chips));
//						}
						String userWon = data.substring(1, 21).trim();
						String chipsWon;
						if(data.substring(21,31).trim().length() > 0)
						{
							chipsWon = data.substring(21,31).trim();
						}
						else chipsWon = "0";
						String phaseType = data.substring(0,1);
						
						canvas = null;
						try {
								canvas = this.surfaceHolder.lockCanvas();
								synchronized (surfaceHolder) 
								{
									this.gamePanel.drawWhoWon(canvas, userWon, chipsWon, phaseType)	;
								}
							}
							finally { 
									if (canvas != null) 
									{
										surfaceHolder.unlockCanvasAndPost(canvas);
										this.gamePanel.unsetBet();
									}	
									}
						sleep(2500);
						
					}					
					
					if((data.substring(0,1).equals("S")))
					{
						
						//System.out.println(data.substring(1, 21).trim() + " and " + data.substring(21,41).trim() + " both win " + data.substring(41, 51).trim() );
						
					}
					
					
					}
				}catch (Exception e) 
		        {
					System.err.println("error!!");
			    }
	
				}

		}

	}



