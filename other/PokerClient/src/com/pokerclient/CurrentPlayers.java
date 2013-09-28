package com.pokerclient;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class CurrentPlayers {

	private Bitmap[] curPlayers = new Bitmap[4];

	private int[] xCoords = new int[4];
	
	private int[] yCoords = new int[4];
	
	private int[] playerCoins = new int[4];
	
	private String[] name = new String[4];
	
	private boolean[] folded = new boolean[4];

	public CurrentPlayers()
	{
		for(int i=0; i < 4; i++)
		{
				playerCoins[i] = 0;
				curPlayers[i] = null;
				name[i] = null;
				folded[i] = false;
		}
	}
	public void clrPlayers()
	{
		for(int i=0; i < 4; i++)
		{
				playerCoins[i] = 0;
				name[i] = null;
				folded[i] = false;
		}
	}
	
	public void setCoords(Canvas canvas, int x, int y)
	{
		//xCoords[0] = 0;
		//xCoords[1] = x/4;
		//xCoords[2] = x-(x/4)-(curPlayers[2].getWidth());	// caution, MUST SET BITMAPS FIRST
		//xCoords[3] = x-curPlayers[3].getWidth(); 			// caution, MUST SET BITMAPS FIRST
		
		xCoords[0] = Math.round(x/8);
		xCoords[1] = Math.round(x*3/8);
		xCoords[2] = x-(Math.round(x*5/8));//curPlayers[2].getWidth());
		xCoords[3] = (Math.round(x*7/8))-(curPlayers[3].getWidth());
		
		
		yCoords[0] = Math.round(y/10);
		yCoords[1] = Math.round(y/12);
		yCoords[2] = Math.round(y/12);
		yCoords[3] = Math.round(y/10);

	}
	
	public void setPlayer( int player_num,int coins, String user) //int player_num, Bitmap bitmap, int coins)
	{
		//curPlayers[player_num-1] = bitmap;
		playerCoins[player_num-1] = coins;
		name[player_num-1] = user;
		
	}
	public void setBitmap(int player_num, Bitmap btmp)
	{
		curPlayers[player_num-1] = btmp;
	}
	
	public void clearPlayer(int player_num)
	{
		curPlayers[player_num-1] = null;
		name[player_num-1] = null;
		playerCoins[player_num-1] = 0;
	}
	
	public void decrCoin(String user, int amt)
	{
		for(int i=0; i < 4; i++)
		{
			if(user.equals(name[i]))
			{
				playerCoins[i] = playerCoins[i] - amt;
			}
		}
	}
	
	public void playerFold(String user)
	{
		for(int i=0; i < 4; i++)
		{
			if(user.equals(name[i]))
			{
				folded[i] = true;
			}
		}
	}
	
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		
		paint.setAntiAlias(true);
		paint.setTextSize(25); 
		
		paint.setColor(Color.RED);
			
		for(int i=0; i < 4; i++)
		{
			if(name[i] != null)
			{
			canvas.drawBitmap(curPlayers[i], xCoords[i], yCoords[i], null);
			//canvas.drawBitmap(curPlayers[i], xCoords[1] - (curPlayers[i].getWidth() / 2), yCoords[i] - (curPlayers[i].getHeight() / 2), null);
			
			canvas.drawText(name[i], xCoords[i], yCoords[i]+curPlayers[i].getHeight()+20, paint);
			canvas.drawText(Integer.toString(playerCoins[i]), xCoords[i], yCoords[i]+curPlayers[i].getHeight()+20+25, paint);
			}
			if(folded[i])
			{
				canvas.drawText("Folded!", xCoords[i], yCoords[i]+(curPlayers[i].getHeight()/2), paint);
			}
	
		}

	 }
}

