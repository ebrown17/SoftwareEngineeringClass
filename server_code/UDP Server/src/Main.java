import java.io.*;
import java.net.*;
import java.util.Arrays;

class UDPServer{
	public static void main(String args[]) throws Exception
	{
		InetAddress local = InetAddress.getLocalHost();

		// Print address
		System.out.println ("Local IP : " + local.getHostAddress());
		DatagramSocket serverSocket= new DatagramSocket(5554); // Creates a socket at port 5554
		SQL_Helper SQL = new SQL_Helper();
		byte[] receiveData=new byte[100];
		byte[] sendData= new byte[100];
		String converted= null;
		
		Runnable table = new Table_Texas();
		new Thread(table).start();
		
		
		
		
		
		
		
		while(true)
		{
			System.out.println("Waiting for packet");
			receiveData= new byte[100];
			DatagramPacket receivePacket= new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);	
			String data = new String(receivePacket.getData());
			System.out.println(data);
			System.out.println("packet recieved ");
			
			switch(Integer.parseInt(data.substring(0,1)))
			
			{
			case 0:	if(SQL.Create_Account(data.substring(1, 21).trim(), data.substring(21, 41).trim()))
					{
						// send confirmation packet
						
						String response_data = new String("1");
						InetAddress IPAddress = receivePacket.getAddress();
						int port = receivePacket.getPort();
						sendData= response_data.getBytes();
						System.out.println(response_data);
						DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
						serverSocket.send(sendPacket);
					}
					else
					{
						String response_data = new String("0");
						InetAddress IPAddress = receivePacket.getAddress();
						int port = receivePacket.getPort();
						sendData= response_data.getBytes();
						System.out.println(response_data);
						DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
						serverSocket.send(sendPacket);
					}
					break;
					
			case 1:if(SQL.Login(data.substring(1, 21).trim(), data.substring(21, 41).trim()))
			{
				// send confirmation packet
				
				String response_data = new String("1");
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendData= response_data.getBytes();
				System.out.println(response_data);
				DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
			else
			{
				String response_data = new String("0");
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendData= response_data.getBytes();
				System.out.println(response_data);
				DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
					break;
					
					
			//CASE 2 USER SENDS TYPE OF POKER AND RECEIVES LIST OF OPEN TABLES	
					// creates thread if table doesn't exist
			case 2: switch(Integer.parseInt(data.substring(1,2)))
					{
						case 0: String response_data=Arrays.toString(SQL.Table_Lookup(0)).replace("["," ").replace("]", " ");
								
								 String table_data = new String(response_data);
								 InetAddress IPAddress = receivePacket.getAddress();
								 int port = receivePacket.getPort();
								 System.out.println(response_data);
								 sendData= table_data.getBytes();
										
								 DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								 serverSocket.send(sendPacket);	
							
							break;
							
						case 1: String response_data1=Arrays.toString(SQL.Table_Lookup(1)).replace("["," ").replace("]", " ");
								 System.out.println(response_data1);
								 table_data = new String(response_data1);
								 IPAddress = receivePacket.getAddress();
								 port = receivePacket.getPort();
								 System.out.println(response_data1);
								 sendData= table_data.getBytes();
								
								 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								 serverSocket.send(sendPacket);
						
							break;
							
						case 2: String response_data2=Arrays.toString(SQL.Table_Lookup(2)).replace("["," ").replace("]", " ");
						         System.out.println(response_data2);
								 table_data = new String(response_data2);
								 IPAddress = receivePacket.getAddress();
								 port = receivePacket.getPort();
								 System.out.println(response_data2);
								 sendData= table_data.getBytes();
								
								 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								 serverSocket.send(sendPacket);					
						
							break;
							
						case 3: String response_data3=Arrays.toString(SQL.Table_Lookup(3)).replace("["," ").replace("]", " ");
						
								 table_data = new String(response_data3);
								 IPAddress = receivePacket.getAddress();
								 port = receivePacket.getPort();
								 System.out.println(response_data3);
								 sendData= table_data.getBytes();
								
								 sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
								 serverSocket.send(sendPacket);
						
						
							break;
					}	
					break;
			
			
			//CASE 3 USER SELECTS TABLE # AND JOINS
			case 3: //System.out.println(data);
					if(SQL.Table_Join(Integer.parseInt(data.substring(1,2).trim()))) // deal with double digit tables later
					{
					System.out.println("Table Joined!");	
					String table_data = "5555";
					InetAddress IPAddress = receivePacket.getAddress();
					int port = receivePacket.getPort();
					sendData= table_data.getBytes();
					System.out.println(table_data);
					DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);
			
			
					}
			
					else 
					{
						System.out.println("Table Join Failed");
						String table_data = new String("0");
						InetAddress IPAddress = receivePacket.getAddress();
						int port = receivePacket.getPort();
						sendData= table_data.getBytes();
				
						DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
						serverSocket.send(sendPacket);
					}
				
					break;
			
			
			

		}
	}
}	
}