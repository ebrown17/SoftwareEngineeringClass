import java.io.*;
import java.net.*;

class User
{
	public String user_ID, password;
	InetAddress IP_address;
	int port;
	int chips;
	int bet;
	int[] hand = new int [7];
	int hand_rank;
	int rank_within_hand;
	
	public User(String new_user,  InetAddress new_IP_address, int new_port, int new_chips)
	{
		// Test if User_ID is taken
		// Send Error Message if so		
	    user_ID=new_user;
	    port=new_port;
	    IP_address= new_IP_address;
	    chips= new_chips;
	}
	
}