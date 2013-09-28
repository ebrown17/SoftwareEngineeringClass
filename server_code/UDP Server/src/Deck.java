import java.io.*;
import java.util.*;
import java.security.SecureRandom;

class Deck 
{
	public int Draw_Card()
	{
		int i;
		Random Deck_Random = new SecureRandom();
		i=Deck_Random.nextInt(52)+1;
		return i;
	}
}
