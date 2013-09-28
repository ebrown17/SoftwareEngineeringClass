import java.io.*;
import java.net.*;


public class Table_Texas implements Runnable{
	User[] Players= new User[5];
	int[] active_players= new int[5];
	public Deck Table_Deck= new Deck();
	int[] dealt_cards= new int[52];
	int i;
	int player_count;
	int table_phase;
	int dealer_chip;
	int pot;
	int winner1;
	int winner2;
	int winning_hand;
	int HR;
	TwoPlusTwo evaluator = new TwoPlusTwo();
	public Table_Texas()
	{
		
		
	}
	
	
	/// deals cards
	public int[] Deal(int[] Dealt_Cards)
	{
		for (int i=0; i<Dealt_Cards.length; i++)
		{
			Dealt_Cards[i]=Table_Deck.Draw_Card();
			for (int j=0; j<i; j++)
			{
				if (Dealt_Cards[i]==Dealt_Cards[j])
				{
					Dealt_Cards[i]=Table_Deck.Draw_Card();
					j=0;
				}
			}
		}	
		return Dealt_Cards;
	}
	
	
	// Retard but should work
	public boolean End_Bets(int[] active_players)
	{
		int[] active_bet=new int[5];
		int total=0;
		for(int k=0; k<5;k++)
		{
			active_bet[k]=6;
		}
		int k=0;
		for(i=0;i<5;i++)
		{
			if( active_players[i]==1)
			{
				active_bet[k]=i;
				k++;
			}
		}
		for(i=0;i<k;i++)
		{
			total=total+Players[active_bet[i]].bet;
		}
		total=total/k;
		if (total==Players[active_bet[0]].bet)
		{
			return true;
		}
		return false;
	}
	//Retarded but should work
	
	
	
	//// FINDS the active better and returns them
	public int Find_Active_Better(int dealer_chip, int[] active_players)
	{
		for (i=dealer_chip+1; i<5; i++)
		{
			if(i>=4)
			{
				i=0;
			}
			if(active_players[i]==1)
			{
				return i;
			}
		}
		return i;
	}
	
	
	/// table start

	public void run()
	{
		for ( i=0; i<5;i++)
		{
			Players[i]=new User("dummy",  null, -1, -1);
		}
		
		try{
			
			DatagramSocket serverSocket= new DatagramSocket(5555); // Creates a socket 
			SQL_Helper SQL = new SQL_Helper();
			byte[] receiveData=new byte[100];
			byte[] sendData= new byte[100];

		
		while(true)
		{
			//client packet needs to send user name in data field
			
			System.out.println("Waiting for players");
			DatagramPacket receivePacket= new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String data = new String(receivePacket.getData());
			System.out.println(data);
			
			// first player joins table and adds there data to the user Player
			if(Integer.parseInt(data.substring(0,1))==0)
			{
				// Join Table 				Takes a packet from Client with 0 followed by username
					
					for( int i=0;i<5;i++)
						{
							
							if(Players[i].user_ID.equals("dummy"))
							{
								
								Players[i]= new User(data.substring(1, 21).trim(),  receivePacket.getAddress(), receivePacket.getPort(), SQL.Get_Chip_Count(data.substring(1, 21).trim()));
								System.out.println("Joined Table!!!");
								break;
							}

						}
								
			}
			// gets player count			
			player_count=0;
			for( i=0;i<5;i++)
			{
				if(Players[i].user_ID.equals("dummy"));
				else
				{
					player_count++;
				}
			}
			if(player_count>1)
			{
				table_phase=1;
				dealer_chip=0;
				int j =0;
				while(true)
				{
					
					
					//table update packet sent to each player ( contains 
					// 1 deal initial
					if(table_phase==1)
					{
						pot=0;
						dealt_cards=Deal(dealt_cards);
						if(j!=0)
							j=0;
						
						//TEXAS HOLD EM
						for(i=0; i<5;i++)
						{
							if (!Players[i].user_ID.equals("dummy"))
							{
								
								active_players[i]=1;
								InetAddress IPAddress = Players[i].IP_address;
								int port = Players[i].port;
								String card1 = String.valueOf(dealt_cards[j]);
								for(int  p =card1.length(); p < 4; p++)
								{
									card1=card1.concat(" ");
								}
								
								Players[i].hand[0]=dealt_cards[j];
								j++;
								String card2 = String.valueOf(dealt_cards[j]);
								for(int  p =card2.length(); p < 4; p++)
								{
									card2=card2.concat(" ");
								}
								//System.out.println(card2.length());
								Players[i].hand[1]=dealt_cards[j];
								j++;
								
								String response_data1 = card1;
								//System.out.println(response_data1);
								
								String response_data2 = card2;
								//System.out.println(response_data2);
								//System.out.println(response_data2.length());
								
								sendData=String.valueOf(table_phase).concat(response_data1).concat(response_data2).getBytes();
								//System.out.println(sendData);
								DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								serverSocket.send(sendPacket);
							}
						}
						table_phase++;
						
					}
														
					// 2 betting
					// position
					// receive bet
					// update others
					// receive bet
					
					
					
					// STILL NEED TO HANDLE CALLS AND HOW TO BREAK OUT OF BETTING CYCLE
					if(table_phase==2)
					{
						// initialize player bets
						for(i=0;i<5;i++)
						{
							if(!Players[i].user_ID.equals("dummy"))
							{
								Players[i].bet=-1;
							}
						}
						// initialize player bets
						
						//find first active better
						int better;
						dealer_chip=Find_Active_Better(dealer_chip, active_players);
						better=dealer_chip;
						better=Find_Active_Better(better, active_players);
						//find first active better
						
						// cant be broken so tis reason for errors
						int z=0;
						while(z==0)
						{
							InetAddress IPAddress = Players[better].IP_address;
							int port = Players[better].port;									
							sendData=String.valueOf(table_phase).concat(new String("bet")).getBytes();
							
							DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
							serverSocket.send(sendPacket);
							int y=0;
							while(y==0)
							{
								
							
								receivePacket= new DatagramPacket(receiveData, receiveData.length);							
								serverSocket.receive(receivePacket);
							
							
								
								data=new String(receivePacket.getData());
								
								if(Integer.parseInt(data.substring(0,1))==0)
								{
									// Join Table 				Takes a packet from Client with 0 followed by username
										for( i=0;i<5;i++)
										{
											if(Players[i].user_ID.equals("dummy"))
											{
												Players[i]= new User(data.substring(1, 21).trim(),  receivePacket.getAddress(), receivePacket.getPort(), SQL.Get_Chip_Count(data.substring(1, 21).trim()));
												break;
											}

										}
											
								}
								
								// update active_players, setting this player to 0
								//notify
								// fold
							if(Integer.parseInt(data.substring(0,1))==1)
							{
								active_players[better]=0;
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 
											 
											 //// sends bet update to all users format: 1.username(20 bytes);
											 
											 sendData= new String("D").concat(user).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								// tests if someone won and sends that data to everyone
								int k=0;
								for(i=0; i<5;i++)
								{
									if(active_players[i]==1)
									{
										k++;
									}
									
								}
								if (k==1)
								{
									for(i=0; i<5;i++)
									{
										if (active_players[i]==1)
										{
											Players[i].chips= Players[i].chips+pot;
											winner1=i;
											// possible sidepot shit
										}
									}
									
									for(i=0; i<5;i++)
									{
										if (!Players[i].user_ID.equals("dummy"))
										{
											
											
											 IPAddress = Players[i].IP_address;
											 port = Players[i].port;
											 
											 int len = Players[winner1].user_ID.length();
											 String user=Players[winner1].user_ID;
											 for(int p =len; p <20; p++)
											  {
												 user =user.concat("  ");
												 p++;
											   }
												 //// sends bet update to all users format: 9.username(20 bytes);
												 
											 sendData= new String("W").concat(user).getBytes();
											
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
											 
										}
									}
									z=1;
									y=1;
								table_phase =1;
								}
								better=Find_Active_Better(better, active_players);
								
							}
							
							// bet calculations
							if(Integer.parseInt(data.substring(0,1))==2)
							{
								System.out.println("Received Bet");
								System.out.println(Players[better].bet);
								Players[better].chips= Players[better].chips-Integer.parseInt(data.substring(1,11).trim());
								pot=pot+Integer.parseInt(data.substring(1,11).trim());
								if (Players[better].bet==-1)
								{
									Players[better].bet= Integer.parseInt(data.substring(1,11).trim());
								}
								else {
									Players[better].bet=Players[better].bet+Integer.parseInt(data.substring(1,11).trim());
								}
								System.out.println(Players[better].bet);
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 int potlen = String.valueOf(pot).length();
										 String potsend = String.valueOf(pot);
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 for(int q =potlen; q <10; q++)
											   {
												 potsend =potsend.concat("  ");
												 q++;
											   }
											 
											 //// sends bet update to all users format: 2.username(20 bytes).bet amount (10 bytes). pot amount (10 bytes);
											 
											 sendData= new String("A").concat(user).concat(data.substring(1, 11)).concat(potsend).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								better=Find_Active_Better(better, active_players);
								// check if all bets are = to eachother to break out of this phase
								System.out.println("Attempting to End Bets");
								if(End_Bets(active_players))
								{
									System.out.println("Bets Ended");
									z=1;
								}
								
								
								y=1;
								
							}
							}
						}
						table_phase++;
					}
					
					
					
					// 3 flop
					if (table_phase==3)
					{
						for(i=0; i<5;i++)
						{
							if (!Players[i].user_ID.equals("dummy"))
							{
								

								InetAddress IPAddress = Players[i].IP_address;
								int port = Players[i].port;
								
								String card1 = String.valueOf(dealt_cards[j]);
								for(int  p =card1.length(); p < 4; p++)
								{
									card1=card1.concat(" ");
								}
								Players[i].hand[2]=dealt_cards[j];

								String card2 = String.valueOf(dealt_cards[j+1]);
								for(int  p =card2.length(); p < 4; p++)
								{
									card2=card2.concat(" ");
								}
								Players[i].hand[3]=dealt_cards[j+1];
								
								String card3 = String.valueOf(dealt_cards[j+2]);
								for(int  p =card3.length(); p < 4; p++)
								{
									card3=card3.concat(" ");
								}
								Players[i].hand[4]=dealt_cards[j+2];

								
								String response_data1 = card1;
								System.out.println(response_data1);
								String response_data2 = card2;
								System.out.println(response_data2);
								String response_data3 = card3;
								System.out.println(response_data3);
								
								sendData=String.valueOf(table_phase).concat(response_data1).concat(response_data2).concat(response_data3).getBytes();
								System.out.println(String.valueOf(table_phase).concat(response_data1).concat(response_data2).concat(response_data3));
								DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								serverSocket.send(sendPacket);
							}
						}
						j=j+4;
						table_phase++;

					}
					
					
					// 4 betting
					
					if(table_phase==4)
					{
						// initialize player bets
						for(i=0;i<5;i++)
						{
							if(!Players[i].user_ID.equals("dummy"))
							{
								Players[i].bet=-1;
							}
						}
						// initialize player bets
						
						//find first active better
						int better;
						better=dealer_chip;
						better=Find_Active_Better(better, active_players);
						//find first active better
						
						// cant be broken so tis reason for errors
						int z=0;
						while(z==0)
						{
							InetAddress IPAddress = Players[better].IP_address;
							int port = Players[better].port;									
							sendData=new String( "4bet").getBytes();
							
							DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
							serverSocket.send(sendPacket);
							int y=0;
							while(y==0)
							{
								
							
								receivePacket= new DatagramPacket(receiveData, receiveData.length);							
								serverSocket.receive(receivePacket);
							
							
								
								data=new String(receivePacket.getData());
								
								if(Integer.parseInt(data.substring(0,1))==0)
								{
									// Join Table 				Takes a packet from Client with 0 followed by username
										for( i=0;i<5;i++)
										{
											if(Players[i].user_ID.equals("dummy"))
											{
												Players[i]= new User(data.substring(1, 21).trim(),  receivePacket.getAddress(), receivePacket.getPort(), SQL.Get_Chip_Count(data.substring(1, 21).trim()));
												break;
											}

										}
											
								}
								
								// update active_players, setting this player to 0
								//notify
								// fold
							if(Integer.parseInt(data.substring(0,1))==1)
							{
								active_players[better]=0;
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 
											 
											 //// sends bet update to all users format: 1.username(20 bytes);
											 
											 sendData= new String("D").concat(user).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								// tests if someone won and sends that data to everyone
								int k=0;
								for(i=0; i<5;i++)
								{
									if(active_players[i]==1)
									{
										k++;
									}
									
								}
								if (k==1)
								{
									for(i=0; i<5;i++)
									{
										if (active_players[i]==1)
										{
											Players[i].chips= Players[i].chips+pot;
											winner1=i;
											// possible sidepot shit
										}
									}
									
									for(i=0; i<5;i++)
									{
										if (!Players[i].user_ID.equals("dummy"))
										{
											
											
											 IPAddress = Players[i].IP_address;
											 port = Players[i].port;
											 
											 int len = Players[winner1].user_ID.length();
											 String user=Players[winner1].user_ID;
											 for(int p =len; p <20; p++)
											  {
												 user =user.concat("  ");
												 p++;
											   }
												 //// sends bet update to all users format: 9.username(20 bytes);
												 
											 sendData= new String("W").concat(user).getBytes();
											
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
											 
										}
									}
									z=1;
									y=1;
								table_phase =1;
								}
								better=Find_Active_Better(better, active_players);
								
							}
							
							// bet calculations
							if(Integer.parseInt(data.substring(0,1))==4)
							{
								Players[better].chips= Players[better].chips-Integer.parseInt(data.substring(1,11).trim());
								if(Players[better].chips==0)
								{
									
								}
								pot=pot+Integer.parseInt(data.substring(1,11).trim());
								if (Players[better].bet==-1)
								{
									Players[better].bet= Integer.parseInt(data.substring(1,11).trim());
								}
								else {
									Players[better].bet=Players[better].bet+Integer.parseInt(data.substring(1,11).trim());
								}
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 int potlen = String.valueOf(pot).length();
										 String potsend = String.valueOf(pot);
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 for(int q =potlen; q <10; q++)
											   {
												 potsend =potsend.concat("  ");
												 q++;
											   }
											 
											 //// sends bet update to all users format: 2.username(20 bytes).bet amount (10 bytes). pot amount (10 bytes);
											 
											 sendData= new String("A").concat(user).concat(data.substring(1, 11)).concat(potsend).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								better=Find_Active_Better(better, active_players);
								// check if all bets are = to eachother to break out of this phase
								if(End_Bets(active_players))
								{
									z=1;
								}
								
								
								y=1;
								
							}
							}
						}
						table_phase++;
					}
					
					
					
					
					
					// 5 turn
					if (table_phase==5)
					{
						for(i=0; i<5;i++)
						{
							if (!Players[i].user_ID.equals("dummy"))
							{
								

								InetAddress IPAddress = Players[i].IP_address;
								int port = Players[i].port;
								
								String card1 = String.valueOf(dealt_cards[j]);
								for(int  p =card1.length(); p < 4; p++)
								{
									card1=card1.concat(" ");
								}
								Players[i].hand[5]=dealt_cards[j];


								
								String response_data1 = card1;
								System.out.println(response_data1);

								
								sendData=String.valueOf(table_phase).concat(response_data1).getBytes();
								
								DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								serverSocket.send(sendPacket);
							}
						}
						j++;
						table_phase++;

					}

					// 6 betting
					
					if(table_phase==6)
					{
						// initialize player bets
						for(i=0;i<5;i++)
						{
							if(!Players[i].user_ID.equals("dummy"))
							{
								Players[i].bet=-1;
							}
						}
						// initialize player bets
						
						//find first active better
						int better;
						better=dealer_chip;
						better=Find_Active_Better(better, active_players);
						//find first active better
						
						// cant be broken so tis reason for errors
						int z=0;
						while(z==0)
						{
							InetAddress IPAddress = Players[better].IP_address;
							int port = Players[better].port;									
							sendData=new String("6bet").getBytes();
							
							DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
							serverSocket.send(sendPacket);
							int y=0;
							while(y==0)
							{
								
							
								receivePacket= new DatagramPacket(receiveData, receiveData.length);							
								serverSocket.receive(receivePacket);
							
							
								
								data=new String(receivePacket.getData());
								
								if(Integer.parseInt(data.substring(0,1))==0)
								{
									// Join Table 				Takes a packet from Client with 0 followed by username

										for( i=0;i<5;i++)
										{
											if(Players[i].user_ID.equals("dummy"))
											{
												Players[i]= new User(data.substring(1, 21).trim(),  receivePacket.getAddress(), receivePacket.getPort(), SQL.Get_Chip_Count(data.substring(1, 21).trim()));
												break;

											}

										}
											
								}
								
								// update active_players, setting this player to 0
								//notify
								// fold
							if(Integer.parseInt(data.substring(0,1))==1)
							{
								active_players[better]=0;
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 
											 
											 //// sends bet update to all users format: 1.username(20 bytes);
											 
											 sendData= new String("D").concat(user).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								// tests if someone won and sends that data to everyone
								int k=0;
								for(i=0; i<5;i++)
								{
									if(active_players[i]==1)
									{
										k++;
									}
									
								}
								if (k==1)
								{
									for(i=0; i<5;i++)
									{
										if (active_players[i]==1)
										{
											Players[i].chips= Players[i].chips+pot;
											winner1=i;
											// possible sidepot shit
										}
									}
									
									for(i=0; i<5;i++)
									{
										if (!Players[i].user_ID.equals("dummy"))
										{
											
											
											 IPAddress = Players[i].IP_address;
											 port = Players[i].port;
											 
											 int len = Players[winner1].user_ID.length();
											 String user=Players[winner1].user_ID;
											 for(int p =len; p <20; p++)
											  {
												 user =user.concat("  ");
												 p++;
											   }
												 //// sends bet update to all users format: 9.username(20 bytes);
												 
											 sendData= new String("W").concat(user).getBytes();
											
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
											 
										}
									}
									z=1;
									y=1;
								table_phase =1;
								}
								better=Find_Active_Better(better, active_players);
								
							}
							
							// bet calculations
							if(Integer.parseInt(data.substring(0,1))==6)
							{
								Players[better].chips= Players[better].chips-Integer.parseInt(data.substring(1,11).trim());
								if(Players[better].chips==0)
								{
									
								}
								pot=pot+Integer.parseInt(data.substring(1,11).trim());
								if (Players[better].bet==-1)
								{
									Players[better].bet= Integer.parseInt(data.substring(1,11).trim());
								}
								else {
									Players[better].bet=Players[better].bet+Integer.parseInt(data.substring(1,11).trim());
								}
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 int potlen = String.valueOf(pot).length();
										 String potsend = String.valueOf(pot);
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 for(int q =potlen; q <10; q++)
											   {
												 potsend =potsend.concat("  ");
												 q++;
											   }
											 
											 //// sends bet update to all users format: 2.username(20 bytes).bet amount (10 bytes). pot amount (10 bytes);
											 
											 sendData= new String("A").concat(user).concat(data.substring(1, 11)).concat(potsend).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								better=Find_Active_Better(better, active_players);
								// check if all bets are = to eachother to break out of this phase
								if(End_Bets(active_players))
								{
									z=1;
								}
								
								
								y=1;
								
							}
							}
						}
						table_phase++;
					}
					
					
					
					
					
					
					// 7 river
					if (table_phase==7)
					{
						for(i=0; i<5;i++)
						{
							if (!Players[i].user_ID.equals("dummy"))
							{
								

								InetAddress IPAddress = Players[i].IP_address;
								int port = Players[i].port;
								
								String card1 = String.valueOf(dealt_cards[j]);
								for(int  p =card1.length(); p < 4; p++)
								{
									card1=card1.concat(" ");
								}
								Players[i].hand[6]=dealt_cards[j];


								
								String response_data1 = card1;
								System.out.println(response_data1);

								
								sendData=String.valueOf(table_phase).concat(response_data1).getBytes();
								
								DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								serverSocket.send(sendPacket);
							}
						}
						
						table_phase++;

					}
					
					
					
					
					
					// 8 betting
					
					if(table_phase==8)
					{
						// initialize player bets
						for(i=0;i<5;i++)
						{
							if(!Players[i].user_ID.equals("dummy"))
							{
								Players[i].bet=-1;
							}
						}
						// initialize player bets
						
						//find first active better
						int better;
						better=dealer_chip;
						better=Find_Active_Better(better, active_players);
						//find first active better
						
						// cant be broken so tis reason for errors
						int z=0;
						while(z==0)
						{
							InetAddress IPAddress = Players[better].IP_address;
							int port = Players[better].port;									
							sendData=new String("8bet").getBytes();
							
							DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
							serverSocket.send(sendPacket);
							int y=0;
							while(y==0)
							{
								
							
								receivePacket= new DatagramPacket(receiveData, receiveData.length);							
								serverSocket.receive(receivePacket);
							
							
								
								data=new String(receivePacket.getData());
								
								if(Integer.parseInt(data.substring(0,1))==0)
								{
									// Join Table 				Takes a packet from Client with 0 followed by username
										for( i=0;i<5;i++)
										{
											if(Players[i].user_ID.equals("dummy"))
											{
												Players[i]= new User(data.substring(1, 21).trim(),  receivePacket.getAddress(), receivePacket.getPort(), SQL.Get_Chip_Count(data.substring(1, 21).trim()));
												break;
											}

										}
											
								}
								
								// update active_players, setting this player to 0
								//notify
								// fold
							if(Integer.parseInt(data.substring(0,1))==1)
							{
								active_players[better]=0;
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 
											 
											 //// sends bet update to all users format: 1.username(20 bytes);
											 
											 sendData= new String("D").concat(user).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								// tests if someone won and sends that data to everyone
								int k=0;
								for(i=0; i<5;i++)
								{
									if(active_players[i]==1)
									{
										k++;
									}
									
								}
								if (k==1)
								{
									for(i=0; i<5;i++)
									{
										if (active_players[i]==1)
										{
											Players[i].chips= Players[i].chips+pot;
											winner1=i;
											// possible sidepot shit
										}
									}
									
									for(i=0; i<5;i++)
									{
										if (!Players[i].user_ID.equals("dummy"))
										{
											
											
											 IPAddress = Players[i].IP_address;
											 port = Players[i].port;
											 
											 int len = Players[winner1].user_ID.length();
											 String user=Players[winner1].user_ID;
											 for(int p =len; p <20; p++)
											  {
												 user =user.concat("  ");
												 p++;
											   }
												 //// sends bet update to all users format: 9.username(20 bytes);
												 
											 sendData= new String("W").concat(user).getBytes();
											
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
											 
										}
									}
									z=1;
									y=1;
								table_phase =1;
								}
								better=Find_Active_Better(better, active_players);
								
							}
							
							// bet calculations
							if(Integer.parseInt(data.substring(0,1))==8)
							{
								Players[better].chips= Players[better].chips-Integer.parseInt(data.substring(1,11).trim());
								if(Players[better].chips==0)
								{
									
								}
								pot=pot+Integer.parseInt(data.substring(1,11).trim());
								if (Players[better].bet==-1)
								{
									Players[better].bet= Integer.parseInt(data.substring(1,11).trim());
								}
								else {
									Players[better].bet=Players[better].bet+Integer.parseInt(data.substring(1,11).trim());
								}
								for(i=0; i<5;i++)
								{
									if (!Players[i].user_ID.equals("dummy"))
									{
										
										
										 IPAddress = Players[i].IP_address;
										 port = Players[i].port;
										 
										 int len = Players[better].user_ID.length();
										 String user=Players[better].user_ID;
										 
										 int potlen = String.valueOf(pot).length();
										 String potsend = String.valueOf(pot);
										 
										 if(IPAddress != Players[better].IP_address)
										 {									 
											 
											 for(int p =len; p <20; p++)
											   {
												 user =user.concat("  ");
												 p++;
											   }
											 
											 for(int q =potlen; q <10; q++)
											   {
												 potsend =potsend.concat("  ");
												 q++;
											   }
											 
											 //// sends bet update to all users format: 2.username(20 bytes).bet amount (10 bytes). pot amount (10 bytes);
											 
											 sendData= new String("A").concat(user).concat(data.substring(1, 11)).concat(potsend).getBytes();
										
											 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
											 serverSocket.send(sendPacket);
										 }
									}
								}
								
								better=Find_Active_Better(better, active_players);
								// check if all bets are = to eachother to break out of this phase
								if(End_Bets(active_players))
								{
									z=1;
								}
								
								
								y=1;
								
							}
							}
						}
						table_phase++;
					}
					
					
					
					
					
					
					// 9 evaluate
					
					
					if(table_phase == 9)
					{
						
						for(int i=0; i<5;i++)
						{
							if (active_players[i]==1)
							HR=evaluator.lookupHand7(Players[i].hand, 0);
							Players[i].hand_rank= HR>>12;
							Players[i].rank_within_hand= HR&0x00000FFF;
						
						}
						winner1= -1;
						winner2=-1;
						winning_hand=0;
						for(int i=0; i<5;i++)
						{
							if (active_players[i]==1)
							{
								if (Players[i].hand_rank>winning_hand)
								{
									winning_hand=Players[i].hand_rank;
									winner1= i;
								}
								else if(Players[i].hand_rank==winning_hand)
								{
									if (Players[i].rank_within_hand>Players[winner1].rank_within_hand)
									{
										winner1= i;
									}
									if (Players[i].rank_within_hand==Players[winner1].rank_within_hand)
									{
										winner2=i;
									}
								}
							}
						}
						if (winner2!=-1)
						{
							Players[winner1].chips=Players[winner1].chips+ pot/2;
							Players[winner2].chips=Players[winner2].chips+ pot/2;
							for(i=0; i<5;i++)
							{
								if (!Players[i].user_ID.equals("dummy"))
								{
									
									
									InetAddress IPAddress = Players[i].IP_address;
									int port = Players[i].port;
									 
									 int len = Players[winner1].user_ID.length();
									 String user1=Players[winner1].user_ID;
									 String user2=Players[winner2].user_ID;
									 
									 int potlen = String.valueOf(pot).length();
									 String potsend = String.valueOf(pot);
									 
									 									 
										 
									 for(int p =len; p <20; p++)
									 {
										user1 =user1.concat("  ");
										p++;
									}
										 
									for(int p =len; p <20; p++)
									{
										user2 =user2.concat("  ");
										p++;
									}
										 
									for(int q =potlen; q <10; q++)
									{
										potsend =potsend.concat("  ");
										q++;
									}
										 
									//// sends bet update to all users format: 2.username1(20 bytes).username2(20 bytes). pot amount (10 bytes);
										 
									sendData= new String("S").concat(user1).concat(user2).concat(potsend).getBytes();
									
									DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
									serverSocket.send(sendPacket);	
									 
								}
							}
						}
						
						else
						{
							Players[winner1].chips=Players[winner1].chips+ pot;
							
							for(i=0; i<5;i++)
							{
								if (!Players[i].user_ID.equals("dummy"))
								{
									
									
									InetAddress IPAddress = Players[i].IP_address;
									int port = Players[i].port;
									 
									 int len = Players[winner1].user_ID.length();
									 String user1=Players[winner1].user_ID;
									 									 
									 int potlen = String.valueOf(pot).length();
									 String potsend = String.valueOf(pot);
									 
									 									 
										 
									 for(int p =len; p <20; p++)
									 {
										user1 =user1.concat("  ");
										p++;
									}
										 
																			 
									for(int q =potlen; q <10; q++)
									{
										potsend =potsend.concat("  ");
										q++;
									}
										 
									//// sends bet update to all users format: 2.username1(20 bytes).username2(20 bytes). pot amount (10 bytes);
										 
									sendData= new String("9").concat(user1).concat(potsend).getBytes();
									
									DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
									serverSocket.send(sendPacket);	
									 
								}
							}
							
						}
						
						
						
						
						
						table_phase =1;
						
					}
					
					
					
					
					
				
					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
			
			
			
			
		
		}
		} catch (SocketException ex){} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{}
	}
 
}
