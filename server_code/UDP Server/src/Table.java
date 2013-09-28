import java.io.*;

class Table
{
	
	public Deck Table_Deck= new Deck();
	public int[] Texas_Dealt = new int[18];
	public User[] Players = new User[5];
	private static final String Hand_Rank_Data= "C:\\Users\\Mungojerrie\\Desktop\\workspace\\HandRanks.dat";
	private static final int Hand_Rank_Size= 32487834;
	private static int HR[] = new int [Hand_Rank_Size];
	
	public void Deal(int[] Dealt_Cards)
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
	}
	
	public void Table_Join(User User_ID)
	{
		for(int i=0; i<5; i++)
		{
			if(Players[i]==null)
			{
				Players[i]=User_ID;
				break;
			}
			// Send error Packet
		}
	}
	
	public static int Rate_Hand_7(int[] cards, int offset)
	{
		int pCards = offset;
		int p = HR[53+cards[pCards++]];
		p=HR[p+cards[pCards++]];
		p=HR[p+cards[pCards++]];
		p=HR[p+cards[pCards++]];
		p=HR[p+cards[pCards++]];
		p=HR[p+cards[pCards++]];
		return HR[p+cards[pCards]];
	}
	
	public static int Rate_Hand_5(int[] cards)
	{
		int pCards=0;
		int p= HR[53+cards[pCards++]];
		p= HR[p+cards[pCards++]];
		p= HR[p+cards[pCards++]];
		p= HR[p+cards[pCards++]];
		p= HR[p+cards[pCards]];
		p=HR[p];
		return HR[p];
	}
	
		
}
	
