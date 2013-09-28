package com.pokerclient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Rect;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class MainTablePanel extends SurfaceView implements 
SurfaceHolder.Callback{
	
	private static final String TAG = MainTablePanel.class.getSimpleName();
	
	private MainGameThread thread;
	
	private Card card1,card2,card3,card4,card5,card6,card7;
	
	private Button bet, fold,call,check, cancel, allIn,plus25,plus100,plus500;
	
	private StartBet	startBets;
	
	private int width=0,height=0,startBet,okBet,test;
	
	Bitmap 	background, betBm,foldBm,callBm,checkBm, cancelBm,startBetBm, 
			allInBm,plus25Bm,plus100Bm, plus500Bm, card1Bm, card2Bm,card3Bm,
			card4Bm,card5Bm,card6Bm,card7Bm,card8Bm,
			card9Bm,card10Bm,card11Bm,card12Bm,card13Bm,
			card14Bm,card15Bm,card16Bm,card17Bm,card18Bm,
			card19Bm,card20Bm,card21Bm,card22Bm,card23Bm,
			card24Bm,card25Bm,card26Bm,card27Bm,card28Bm,
			card29Bm,card30Bm,card31Bm,card32Bm,card33Bm,
			card34Bm,card35Bm,card36Bm,card37Bm,card38Bm,
			card39Bm,card40Bm,card41Bm,card42Bm,card43Bm,
			card44Bm,card45Bm,card46Bm,card47Bm,card48Bm,
			card49Bm,card50Bm, card51Bm,card52Bm,avatar1Bm, avatar2Bm, avatar3Bm, avatar4Bm;

	
	private Paint paint;
	
	private int curBetAmt; //kara
	private int adding; //kara
	private boolean isAdding = false;
	private boolean folding = false;
	private boolean allinGo = false;
	private boolean betting;
	private boolean calling = false;
	private boolean checking = false;
	private boolean canceling = false;
	private CurrentPlayers curPlayers = new CurrentPlayers();
	private int prevBet = 0;
	private String prevPlayer = "";
	private int mainPot;
	

	private boolean okAdd = false;
	
	String user,port;
	int chip;
	
	String yourBet = "Your turn to bet!";
	String waiting = "Waiting for other bets.";
	
	
	public MainTablePanel(Context context, String username, String portnumber) 
	{
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		curBetAmt = 0;
		user = username;
		port = portnumber;
		
		// create droid and load bitmap
		card1Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
		card2Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
		card3Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
		card4Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c4);
		card5Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c5);
		card6Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c6);
		card7Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c7);
		card8Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c8);
		card9Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c9);
		card10Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c10);
		card11Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c11);
		card12Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c12);
		card13Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c13);
		card14Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c14);
		card15Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c15);
		card16Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c16);
		card17Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c17);
		card18Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c18);
		card19Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c19);
		card20Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c20);
		card21Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c21);
		card22Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c22);
		card23Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c23);
		card24Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c24);
		card25Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c25);
		card26Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c26);
		card27Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c27);
		card28Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c28);
		card29Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c29);
		card30Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c30);
		card31Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c31);
		card32Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c32);
		card33Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c33);
		card34Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c34);
		card35Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c35);
		card36Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c36);
		card37Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c37);
		card38Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c38);
		card39Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c39);
		card40Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c40);
		card41Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c41);
		card42Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c42);
		card43Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c43);
		card44Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c44);
		card45Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c45);
		card46Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c46);
		card47Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c47);
		card48Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c48);
		card49Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c49);
		card50Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c50);
		card51Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c51);
		card52Bm = BitmapFactory.decodeResource(getResources(), R.drawable.c52);
		        		
		background = BitmapFactory.decodeResource(getResources(), R.drawable.table);
		betBm=BitmapFactory.decodeResource(getResources(), R.drawable.bet);
		startBetBm=BitmapFactory.decodeResource(getResources(), R.drawable.startbet);
		foldBm=BitmapFactory.decodeResource(getResources(), R.drawable.fold);
		callBm=BitmapFactory.decodeResource(getResources(), R.drawable.call);
		checkBm=BitmapFactory.decodeResource(getResources(), R.drawable.check);
		cancelBm=BitmapFactory.decodeResource(getResources(), R.drawable.cancel);
		allInBm=BitmapFactory.decodeResource(getResources(), R.drawable.allin);
		plus25Bm=BitmapFactory.decodeResource(getResources(), R.drawable.plus25);
		plus100Bm=BitmapFactory.decodeResource(getResources(), R.drawable.plus100s);
		plus500Bm=BitmapFactory.decodeResource(getResources(), R.drawable.plus500);
		
		avatar1Bm=BitmapFactory.decodeResource(getResources(), R.drawable.female1);
		avatar2Bm=BitmapFactory.decodeResource(getResources(), R.drawable.female2);
		avatar3Bm=BitmapFactory.decodeResource(getResources(), R.drawable.male1);
		avatar4Bm=BitmapFactory.decodeResource(getResources(), R.drawable.male2);
		
		
		
		// create the game loop thread
		thread = new MainGameThread(getHolder(), this,user,port);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	protected void buttonSet()
	{
		bet= new Button(betBm,  width/2+100, (height-(height/10)));
		startBets= new StartBet(startBetBm,  width/2+100, (height-(height/10)));
		plus25 = new Button(plus25Bm, width/2+175, (height-(height/10)));
		plus100 = new Button(plus100Bm, width/2+250, (height-(height/10)));
		plus500 = new Button(plus500Bm, width/2+325, (height-(height/10)));
		allIn = new Button(allInBm, width/2-100, (height-(height/10)));
		cancel = new Button(cancelBm, width/2-175, (height-(height/10)));

		call = new Button(callBm,  width/2+175, (height-(height/10)));
		check = new Button(checkBm,  width/2+250, (height-(height/10)));
		fold = new Button(foldBm,  width/2+325, (height-(height/10))); 
		
		curPlayers.setBitmap(1, avatar1Bm);
		curPlayers.setBitmap(2, avatar2Bm);
		curPlayers.setBitmap(3, avatar3Bm);
		curPlayers.setBitmap(4, avatar4Bm);
	}
	
	protected void card1Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card1 = new Card(card1Bm,width/2-40, (height-(height/10)));
					break;
			case 2: card1 = new Card(card2Bm,width/2-40, (height-(height/10)));
					break;
			case 3: card1 = new Card(card3Bm,width/2-40, (height-(height/10)));
					break;
			case 4: card1 = new Card(card4Bm,width/2-40, (height-(height/10)));
					break;
			case 5: card1 = new Card(card5Bm,width/2-40, (height-(height/10)));
					break;
			case 6: card1 = new Card(card6Bm,width/2-40, (height-(height/10)));
					break;
			case 7: card1 = new Card(card7Bm,width/2-40, (height-(height/10)));
					break;
			case 8: card1 = new Card(card8Bm,width/2-40, (height-(height/10)));
					break;
			case 9: card1 = new Card(card9Bm,width/2-40, (height-(height/10)));
					break;
			case 10: card1 = new Card(card10Bm,width/2-40, (height-(height/10)));
					break;
			case 11: card1 = new Card(card11Bm,width/2-40, (height-(height/10)));
					break;
			case 12: card1 = new Card(card12Bm,width/2-40, (height-(height/10)));
					break;
			case 13: card1 = new Card(card13Bm,width/2-40, (height-(height/10)));
					break;
			case 14: card1 = new Card(card14Bm,width/2-40, (height-(height/10)));
					break;
			case 15: card1 = new Card(card15Bm,width/2-40, (height-(height/10)));
					break;
			case 16: card1 = new Card(card16Bm,width/2-40, (height-(height/10)));
					break;
			case 17: card1 = new Card(card17Bm,width/2-40, (height-(height/10)));
					break;
			case 18: card1 = new Card(card18Bm,width/2-40, (height-(height/10)));
					break;
			case 19: card1 = new Card(card19Bm,width/2-40, (height-(height/10)));
					break;
			case 20: card1 = new Card(card20Bm,width/2-40, (height-(height/10)));
					break;
			case 21: card1 = new Card(card21Bm,width/2-40, (height-(height/10)));
					break;
			case 22: card1 = new Card(card22Bm,width/2-40, (height-(height/10)));
					break;
			case 23: card1 = new Card(card23Bm,width/2-40, (height-(height/10)));
					break;
			case 24: card1 = new Card(card24Bm,width/2-40, (height-(height/10)));
					break;
			case 25: card1 = new Card(card25Bm,width/2-40, (height-(height/10)));
					break;
			case 26: card1 = new Card(card26Bm,width/2-40, (height-(height/10)));
					break;
			case 27: card1 = new Card(card27Bm,width/2-40, (height-(height/10)));
					break;
			case 28: card1 = new Card(card28Bm,width/2-40, (height-(height/10)));
					break;
			case 29: card1 = new Card(card29Bm,width/2-40, (height-(height/10)));
					break;
			case 30: card1 = new Card(card30Bm,width/2-40, (height-(height/10)));
					break;
			case 31: card1 = new Card(card31Bm,width/2-40, (height-(height/10)));
					break;
			case 32: card1 = new Card(card32Bm,width/2-40, (height-(height/10)));
					break;
			case 33: card1 = new Card(card33Bm,width/2-40, (height-(height/10)));
					break;
			case 34: card1 = new Card(card34Bm,width/2-40, (height-(height/10)));
					break;
			case 35: card1 = new Card(card35Bm,width/2-40, (height-(height/10)));
					break;
			case 36: card1 = new Card(card36Bm,width/2-40, (height-(height/10)));
					break;
			case 37: card1 = new Card(card37Bm,width/2-40, (height-(height/10)));
					break;
			case 38: card1 = new Card(card38Bm,width/2-40, (height-(height/10)));
					break;
			case 39: card1 = new Card(card39Bm,width/2-40, (height-(height/10)));
					break;
			case 40: card1 = new Card(card40Bm,width/2-40, (height-(height/10)));
					break;
			case 41: card1 = new Card(card41Bm,width/2-40, (height-(height/10)));
					break;
			case 42: card1 = new Card(card42Bm,width/2-40, (height-(height/10)));
					break;
			case 43: card1 = new Card(card43Bm,width/2-40, (height-(height/10)));
					break;
			case 44: card1 = new Card(card44Bm,width/2-40, (height-(height/10)));
					break;
			case 45: card1 = new Card(card45Bm,width/2-40, (height-(height/10)));
					break;
			case 46: card1 = new Card(card46Bm,width/2-40, (height-(height/10)));
					break;
			case 47: card1 = new Card(card47Bm,width/2-40, (height-(height/10)));
					break;
			case 48: card1 = new Card(card48Bm,width/2-40, (height-(height/10)));
					break;
			case 49: card1 = new Card(card49Bm,width/2-40, (height-(height/10)));
					break;
			case 50: card1 = new Card(card50Bm,width/2-40, (height-(height/10)));
					break;
			case 51: card1 = new Card(card51Bm,width/2-40, (height-(height/10)));
					break;
			case 52: card1 = new Card(card52Bm,width/2-40, (height-(height/10)));
					break;
		
		
		
		
		}
		
			

		
	}
	
	protected void card2Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card2 = new Card(card1Bm,width/2+40, (height-(height/10)));
					break;
			case 2: card2 = new Card(card2Bm,width/2+40, (height-(height/10)));
					break;
			case 3: card2 = new Card(card3Bm,width/2+40, (height-(height/10)));
					break;
			case 4: card2 = new Card(card4Bm,width/2+40, (height-(height/10)));
					break;
			case 5: card2 = new Card(card5Bm,width/2+40, (height-(height/10)));
					break;
			case 6: card2 = new Card(card6Bm,width/2+40, (height-(height/10)));
					break;
			case 7: card2 = new Card(card7Bm,width/2+40, (height-(height/10)));
					break;
			case 8: card2 = new Card(card8Bm,width/2+40, (height-(height/10)));
					break;
			case 9: card2 = new Card(card9Bm,width/2+40, (height-(height/10)));
					break;
			case 10: card2 = new Card(card10Bm,width/2+40, (height-(height/10)));
					break;
			case 11: card2 = new Card(card11Bm,width/2+40, (height-(height/10)));
					break;
			case 12: card2 = new Card(card12Bm,width/2+40, (height-(height/10)));
					break;
			case 13: card2 = new Card(card13Bm,width/2+40, (height-(height/10)));
					break;
			case 14: card2 = new Card(card14Bm,width/2+40, (height-(height/10)));
					break;
			case 15: card2 = new Card(card15Bm,width/2+40, (height-(height/10)));
					break;
			case 16: card2 = new Card(card16Bm,width/2+40, (height-(height/10)));
					break;
			case 17: card2 = new Card(card17Bm,width/2+40, (height-(height/10)));
					break;
			case 18: card2 = new Card(card18Bm,width/2+40, (height-(height/10)));
					break;
			case 19: card2 = new Card(card19Bm,width/2+40, (height-(height/10)));
					break;
			case 20: card2 = new Card(card20Bm,width/2+40, (height-(height/10)));
					break;
			case 21: card2 = new Card(card21Bm,width/2+40, (height-(height/10)));
					break;
			case 22: card2 = new Card(card22Bm,width/2+40, (height-(height/10)));
					break;
			case 23: card2 = new Card(card23Bm,width/2+40, (height-(height/10)));
					break;
			case 24: card2 = new Card(card24Bm,width/2+40, (height-(height/10)));
					break;
			case 25: card2 = new Card(card25Bm,width/2+40, (height-(height/10)));
					break;
			case 26: card2 = new Card(card26Bm,width/2+40, (height-(height/10)));
					break;
			case 27: card2 = new Card(card27Bm,width/2+40, (height-(height/10)));
					break;
			case 28: card2 = new Card(card28Bm,width/2+40, (height-(height/10)));
					break;
			case 29: card2 = new Card(card29Bm,width/2+40, (height-(height/10)));
					break;
			case 30: card2 = new Card(card30Bm,width/2+40, (height-(height/10)));
					break;
			case 31: card2 = new Card(card31Bm,width/2+40, (height-(height/10)));
					break;
			case 32: card2 = new Card(card32Bm,width/2+40, (height-(height/10)));
					break;
			case 33: card2 = new Card(card33Bm,width/2+40, (height-(height/10)));
					break;
			case 34: card2 = new Card(card34Bm,width/2+40, (height-(height/10)));
					break;
			case 35: card2 = new Card(card35Bm,width/2+40, (height-(height/10)));
					break;
			case 36: card2 = new Card(card36Bm,width/2+40, (height-(height/10)));
					break;
			case 37: card2 = new Card(card37Bm,width/2+40, (height-(height/10)));
					break;
			case 38: card2 = new Card(card38Bm,width/2+40, (height-(height/10)));
					break;
			case 39: card2 = new Card(card39Bm,width/2+40, (height-(height/10)));
					break;
			case 40: card2 = new Card(card40Bm,width/2+40, (height-(height/10)));
					break;
			case 41: card2 = new Card(card41Bm,width/2+40, (height-(height/10)));
					break;
			case 42: card2 = new Card(card42Bm,width/2+40, (height-(height/10)));
					break;
			case 43: card2 = new Card(card43Bm,width/2+40, (height-(height/10)));
					break;
			case 44: card2 = new Card(card44Bm,width/2+40, (height-(height/10)));
					break;
			case 45: card2 = new Card(card45Bm,width/2+40, (height-(height/10)));
					break;
			case 46: card2 = new Card(card46Bm,width/2+40, (height-(height/10)));
					break;
			case 47: card2 = new Card(card47Bm,width/2+40, (height-(height/10)));
					break;
			case 48: card2 = new Card(card48Bm,width/2+40, (height-(height/10)));
					break;
			case 49: card2 = new Card(card49Bm,width/2+40, (height-(height/10)));
					break;
			case 50: card2 = new Card(card50Bm,width/2+40, (height-(height/10)));
					break;
			case 51: card2 = new Card(card51Bm,width/2+40, (height-(height/10)));
					break;
			case 52: card2 = new Card(card52Bm,width/2+40, (height-(height/10)));
					break;
		
		
		
		
		}
		
			

		
	}

	protected void card3Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card3 = new Card(card1Bm, width/2-100, height/2);
					break;
			case 2: card3 = new Card(card2Bm, width/2-110, height/2);
					break;
			case 3: card3 = new Card(card3Bm, width/2-110, height/2);
					break;
			case 4: card3 = new Card(card4Bm, width/2-110, height/2);
					break;
			case 5: card3 = new Card(card5Bm, width/2-110, height/2);
					break;
			case 6: card3 = new Card(card6Bm, width/2-110, height/2);
					break;
			case 7: card3 = new Card(card7Bm, width/2-110, height/2);
					break;
			case 8: card3 = new Card(card8Bm, width/2-110, height/2);
					break;
			case 9: card3 = new Card(card9Bm, width/2-110, height/2);
					break;
			case 10: card3 = new Card(card10Bm, width/2-110, height/2);
					break;
			case 11: card3 = new Card(card11Bm, width/2-110, height/2);
					break;
			case 12: card3 = new Card(card12Bm, width/2-110, height/2);
					break;
			case 13: card3 = new Card(card13Bm, width/2-110, height/2);
					break;
			case 14: card3 = new Card(card14Bm, width/2-110, height/2);
					break;
			case 15: card3 = new Card(card15Bm, width/2-110, height/2);
					break;
			case 16: card3 = new Card(card16Bm, width/2-110, height/2);
					break;
			case 17: card3 = new Card(card17Bm, width/2-110, height/2);
					break;
			case 18: card3 = new Card(card18Bm, width/2-110, height/2);
					break;
			case 19: card3 = new Card(card19Bm, width/2-110, height/2);
					break;
			case 20: card3 = new Card(card20Bm, width/2-110, height/2);
					break;
			case 21: card3 = new Card(card21Bm, width/2-110, height/2);
					break;
			case 22: card3 = new Card(card22Bm, width/2-110, height/2);
					break;
			case 23: card3 = new Card(card23Bm, width/2-110, height/2);
					break;
			case 24: card3 = new Card(card24Bm, width/2-110, height/2);
					break;
			case 25: card3 = new Card(card25Bm, width/2-110, height/2);
					break;
			case 26: card3 = new Card(card26Bm, width/2-110, height/2);
					break;
			case 27: card3 = new Card(card27Bm, width/2-110, height/2);
					break;
			case 28: card3 = new Card(card28Bm, width/2-110, height/2);
					break;
			case 29: card3 = new Card(card29Bm, width/2-110, height/2);
					break;
			case 30: card3 = new Card(card30Bm, width/2-110, height/2);
					break;
			case 31: card3 = new Card(card31Bm, width/2-110, height/2);
					break;
			case 32: card3 = new Card(card32Bm, width/2-110, height/2);
					break;
			case 33: card3 = new Card(card33Bm, width/2-110, height/2);
					break;
			case 34: card3 = new Card(card34Bm, width/2-110, height/2);
					break;
			case 35: card3 = new Card(card35Bm, width/2-110, height/2);
					break;
			case 36: card3 = new Card(card36Bm, width/2-110, height/2);
					break;
			case 37: card3 = new Card(card37Bm, width/2-110, height/2);
					break;
			case 38: card3 = new Card(card38Bm, width/2-110, height/2);
					break;
			case 39: card3 = new Card(card39Bm, width/2-110, height/2);
					break;
			case 40: card3 = new Card(card40Bm, width/2-110, height/2);
					break;
			case 41: card3 = new Card(card41Bm, width/2-110, height/2);
					break;
			case 42: card3 = new Card(card42Bm, width/2-110, height/2);
					break;
			case 43: card3 = new Card(card43Bm, width/2-110, height/2);
					break;
			case 44: card3 = new Card(card44Bm, width/2-110, height/2);
					break;
			case 45: card3 = new Card(card45Bm, width/2-110, height/2);
					break;
			case 46: card3 = new Card(card46Bm, width/2-110, height/2);
					break;
			case 47: card3 = new Card(card47Bm, width/2-110, height/2);
					break;
			case 48: card3 = new Card(card48Bm, width/2-110, height/2);
					break;
			case 49: card3 = new Card(card49Bm, width/2-110, height/2);
					break;
			case 50: card3 = new Card(card50Bm, width/2-110, height/2);
					break;
			case 51: card3 = new Card(card51Bm, width/2-110, height/2);
					break;
			case 52: card3 = new Card(card52Bm, width/2-110, height/2);
					break;
		
		
		
		
		}
		
			

		
	}

	protected void card4Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card4 = new Card(card1Bm,  width/2-55, height/2);
					break;
			case 2: card4 = new Card(card2Bm,  width/2-55, height/2);
					break;
			case 3: card4 = new Card(card3Bm,  width/2-55, height/2);
					break;
			case 4: card4 = new Card(card4Bm,  width/2-55, height/2);
					break;
			case 5: card4 = new Card(card5Bm,  width/2-55, height/2);
					break;
			case 6: card4 = new Card(card6Bm,  width/2-55, height/2);
					break;
			case 7: card4 = new Card(card7Bm,  width/2-55, height/2);
					break;
			case 8: card4 = new Card(card8Bm,  width/2-55, height/2);
					break;
			case 9: card4 = new Card(card9Bm,  width/2-55, height/2);
					break;
			case 10: card4 = new Card(card10Bm,  width/2-55, height/2);
					break;
			case 11: card4 = new Card(card11Bm,  width/2-55, height/2);
					break;
			case 12: card4 = new Card(card12Bm,  width/2-55, height/2);
					break;
			case 13: card4 = new Card(card13Bm,  width/2-55, height/2);
					break;
			case 14: card4 = new Card(card14Bm,  width/2-55, height/2);
					break;
			case 15: card4 = new Card(card15Bm,  width/2-55, height/2);
					break;
			case 16: card4 = new Card(card16Bm,  width/2-55, height/2);
					break;
			case 17: card4 = new Card(card17Bm,  width/2-55, height/2);
					break;
			case 18: card4 = new Card(card18Bm,  width/2-55, height/2);
					break;
			case 19: card4 = new Card(card19Bm,  width/2-55, height/2);
					break;
			case 20: card4 = new Card(card20Bm,  width/2-55, height/2);
					break;
			case 21: card4 = new Card(card21Bm,  width/2-55, height/2);
					break;
			case 22: card4 = new Card(card22Bm,  width/2-55, height/2);
					break;
			case 23: card4 = new Card(card23Bm,  width/2-55, height/2);
					break;
			case 24: card4 = new Card(card24Bm,  width/2-55, height/2);
					break;
			case 25: card4 = new Card(card25Bm,  width/2-55, height/2);
					break;
			case 26: card4 = new Card(card26Bm,  width/2-55, height/2);
					break;
			case 27: card4 = new Card(card27Bm,  width/2-55, height/2);
					break;
			case 28: card4 = new Card(card28Bm,  width/2-55, height/2);
					break;
			case 29: card4 = new Card(card29Bm,  width/2-55, height/2);
					break;
			case 30: card4 = new Card(card30Bm,  width/2-55, height/2);
					break;
			case 31: card4 = new Card(card31Bm,  width/2-55, height/2);
					break;
			case 32: card4 = new Card(card32Bm,  width/2-55, height/2);
					break;
			case 33: card4 = new Card(card33Bm,  width/2-55, height/2);
					break;
			case 34: card4 = new Card(card34Bm,  width/2-55, height/2);
					break;
			case 35: card4 = new Card(card35Bm,  width/2-55, height/2);
					break;
			case 36: card4 = new Card(card36Bm,  width/2-55, height/2);
					break;
			case 37: card4 = new Card(card37Bm,  width/2-55, height/2);
					break;
			case 38: card4 = new Card(card38Bm,  width/2-55, height/2);
					break;
			case 39: card4 = new Card(card39Bm,  width/2-55, height/2);
					break;
			case 40: card4 = new Card(card40Bm,  width/2-55, height/2);
					break;
			case 41: card4 = new Card(card41Bm,  width/2-55, height/2);
					break;
			case 42: card4 = new Card(card42Bm,  width/2-55, height/2);
					break;
			case 43: card4 = new Card(card43Bm,  width/2-55, height/2);
					break;
			case 44: card4 = new Card(card44Bm,  width/2-55, height/2);
					break;
			case 45: card4 = new Card(card45Bm,  width/2-55, height/2);
					break;
			case 46: card4 = new Card(card46Bm,  width/2-55, height/2);
					break;
			case 47: card4 = new Card(card47Bm,  width/2-55, height/2);
					break;
			case 48: card4 = new Card(card48Bm,  width/2-55, height/2);
					break;
			case 49: card4 = new Card(card49Bm,  width/2-55, height/2);
					break;
			case 50: card4 = new Card(card50Bm,  width/2-55, height/2);
					break;
			case 51: card4 = new Card(card51Bm,  width/2-55, height/2);
					break;
			case 52: card4 = new Card(card52Bm,  width/2-55, height/2);
					break;
		
		
		
		
		}
		
			

		
	}

	protected void card5Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card5 = new Card(card1Bm,width/2, height/2);
					break;
			case 2: card5 = new Card(card2Bm,width/2, height/2);
					break;
			case 3: card5 = new Card(card3Bm,width/2, height/2);
					break;
			case 4: card5 = new Card(card4Bm,width/2, height/2);
					break;
			case 5: card5 = new Card(card5Bm,width/2, height/2);
					break;
			case 6: card5 = new Card(card6Bm,width/2, height/2);
					break;
			case 7: card5 = new Card(card7Bm,width/2, height/2);
					break;
			case 8: card5 = new Card(card8Bm,width/2, height/2);
					break;
			case 9: card5 = new Card(card9Bm,width/2, height/2);
					break;
			case 10: card5 = new Card(card10Bm,width/2, height/2);
					break;
			case 11: card5 = new Card(card11Bm,width/2, height/2);
					break;
			case 12: card5 = new Card(card12Bm,width/2, height/2);
					break;
			case 13: card5 = new Card(card13Bm,width/2, height/2);
					break;
			case 14: card5 = new Card(card14Bm,width/2, height/2);
					break;
			case 15: card5 = new Card(card15Bm,width/2, height/2);
					break;
			case 16: card5 = new Card(card16Bm,width/2, height/2);
					break;
			case 17: card5 = new Card(card17Bm,width/2, height/2);
					break;
			case 18: card5 = new Card(card18Bm,width/2, height/2);
					break;
			case 19: card5 = new Card(card19Bm,width/2, height/2);
					break;
			case 20: card5 = new Card(card20Bm,width/2, height/2);
					break;
			case 21: card5 = new Card(card21Bm,width/2, height/2);
					break;
			case 22: card5 = new Card(card22Bm,width/2, height/2);
					break;
			case 23: card5 = new Card(card23Bm,width/2, height/2);
					break;
			case 24: card5 = new Card(card24Bm,width/2, height/2);
					break;
			case 25: card5 = new Card(card25Bm,width/2, height/2);
					break;
			case 26: card5 = new Card(card26Bm,width/2, height/2);
					break;
			case 27: card5 = new Card(card27Bm,width/2, height/2);
					break;
			case 28: card5 = new Card(card28Bm,width/2, height/2);
					break;
			case 29: card5 = new Card(card29Bm,width/2, height/2);
					break;
			case 30: card5 = new Card(card30Bm,width/2, height/2);
					break;
			case 31: card5 = new Card(card31Bm,width/2, height/2);
					break;
			case 32: card5 = new Card(card32Bm,width/2, height/2);
					break;
			case 33: card5 = new Card(card33Bm,width/2, height/2);
					break;
			case 34: card5 = new Card(card34Bm,width/2, height/2);
					break;
			case 35: card5 = new Card(card35Bm,width/2, height/2);
					break;
			case 36: card5 = new Card(card36Bm,width/2, height/2);
					break;
			case 37: card5 = new Card(card37Bm,width/2, height/2);
					break;
			case 38: card5 = new Card(card38Bm,width/2, height/2);
					break;
			case 39: card5 = new Card(card39Bm,width/2, height/2);
					break;
			case 40: card5 = new Card(card40Bm,width/2, height/2);
					break;
			case 41: card5 = new Card(card41Bm,width/2, height/2);
					break;
			case 42: card5 = new Card(card42Bm,width/2, height/2);
					break;
			case 43: card5 = new Card(card43Bm,width/2, height/2);
					break;
			case 44: card5 = new Card(card44Bm,width/2, height/2);
					break;
			case 45: card5 = new Card(card45Bm,width/2, height/2);
					break;
			case 46: card5 = new Card(card46Bm,width/2, height/2);
					break;
			case 47: card5 = new Card(card47Bm,width/2, height/2);
					break;
			case 48: card5 = new Card(card48Bm,width/2, height/2);
					break;
			case 49: card5 = new Card(card49Bm,width/2, height/2);
					break;
			case 50: card5 = new Card(card50Bm,width/2, height/2);
					break;
			case 51: card5 = new Card(card51Bm,width/2, height/2);
					break;
			case 52: card5 = new Card(card52Bm,width/2, height/2);
					break;
		
		
		
		
		}
		
			

		
	}

	protected void card6Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card6 = new Card(card1Bm, width/2+55, height/2);
					break;
			case 2: card6 = new Card(card2Bm, width/2+55, height/2);
					break;
			case 3: card6 = new Card(card3Bm, width/2+55, height/2);
					break;
			case 4: card6 = new Card(card4Bm, width/2+55, height/2);
					break;
			case 5: card6 = new Card(card5Bm, width/2+55, height/2);
					break;
			case 6: card6 = new Card(card6Bm, width/2+55, height/2);
					break;
			case 7: card6 = new Card(card7Bm, width/2+55, height/2);
					break;
			case 8: card6 = new Card(card8Bm, width/2+55, height/2);
					break;
			case 9: card6 = new Card(card9Bm, width/2+55, height/2);
					break;
			case 10: card6 = new Card(card10Bm, width/2+55, height/2);
					break;
			case 11: card6 = new Card(card11Bm, width/2+55, height/2);
					break;
			case 12: card6 = new Card(card12Bm, width/2+55, height/2);
					break;
			case 13: card6 = new Card(card13Bm, width/2+55, height/2);
					break;
			case 14: card6 = new Card(card14Bm, width/2+55, height/2);
					break;
			case 15: card6 = new Card(card15Bm, width/2+55, height/2);
					break;
			case 16: card6 = new Card(card16Bm, width/2+55, height/2);
					break;
			case 17: card6 = new Card(card17Bm, width/2+55, height/2);
					break;
			case 18: card6 = new Card(card18Bm, width/2+55, height/2);
					break;
			case 19: card6 = new Card(card19Bm, width/2+55, height/2);
					break;
			case 20: card6 = new Card(card20Bm, width/2+55, height/2);
					break;
			case 21: card6 = new Card(card21Bm, width/2+55, height/2);
					break;
			case 22: card6 = new Card(card22Bm, width/2+55, height/2);
					break;
			case 23: card6 = new Card(card23Bm, width/2+55, height/2);
					break;
			case 24: card6 = new Card(card24Bm, width/2+55, height/2);
					break;
			case 25: card6 = new Card(card25Bm, width/2+55, height/2);
					break;
			case 26: card6 = new Card(card26Bm, width/2+55, height/2);
					break;
			case 27: card6 = new Card(card27Bm, width/2+55, height/2);
					break;
			case 28: card6 = new Card(card28Bm, width/2+55, height/2);
					break;
			case 29: card6 = new Card(card29Bm, width/2+55, height/2);
					break;
			case 30: card6 = new Card(card30Bm, width/2+55, height/2);
					break;
			case 31: card6 = new Card(card31Bm, width/2+55, height/2);
					break;
			case 32: card6 = new Card(card32Bm, width/2+55, height/2);
					break;
			case 33: card6 = new Card(card33Bm, width/2+55, height/2);
					break;
			case 34: card6 = new Card(card34Bm, width/2+55, height/2);
					break;
			case 35: card6 = new Card(card35Bm, width/2+55, height/2);
					break;
			case 36: card6 = new Card(card36Bm, width/2+55, height/2);
					break;
			case 37: card6 = new Card(card37Bm, width/2+55, height/2);
					break;
			case 38: card6 = new Card(card38Bm, width/2+55, height/2);
					break;
			case 39: card6 = new Card(card39Bm, width/2+55, height/2);
					break;
			case 40: card6 = new Card(card40Bm, width/2+55, height/2);
					break;
			case 41: card6 = new Card(card41Bm, width/2+55, height/2);
					break;
			case 42: card6 = new Card(card42Bm, width/2+55, height/2);
					break;
			case 43: card6 = new Card(card43Bm, width/2+55, height/2);
					break;
			case 44: card6 = new Card(card44Bm, width/2+55, height/2);
					break;
			case 45: card6 = new Card(card45Bm, width/2+55, height/2);
					break;
			case 46: card6 = new Card(card46Bm, width/2+55, height/2);
					break;
			case 47: card6 = new Card(card47Bm, width/2+55, height/2);
					break;
			case 48: card6 = new Card(card48Bm, width/2+55, height/2);
					break;
			case 49: card6 = new Card(card49Bm, width/2+55, height/2);
					break;
			case 50: card6 = new Card(card50Bm, width/2+55, height/2);
					break;
			case 51: card6 = new Card(card51Bm, width/2+55, height/2);
					break;
			case 52: card6 = new Card(card52Bm, width/2+55, height/2);
					break;
		
		
		
		
		}
		
			

		
	}
	
	protected void card7Set(String card)
	{	
		switch(Integer.parseInt(card))
		{
			case 1: card7 = new Card(card1Bm,width/2+110, height/2);
					break;
			case 2: card7 = new Card(card2Bm,width/2+110, height/2);
					break;
			case 3: card7 = new Card(card3Bm,width/2+110, height/2);
					break;
			case 4: card7 = new Card(card4Bm,width/2+110, height/2);
					break;
			case 5: card7 = new Card(card5Bm,width/2+110, height/2);
					break;
			case 6: card7 = new Card(card6Bm,width/2+110, height/2);
					break;
			case 7: card7 = new Card(card7Bm,width/2+110, height/2);
					break;
			case 8: card7 = new Card(card8Bm,width/2+110, height/2);
					break;
			case 9: card7 = new Card(card9Bm,width/2+110, height/2);
					break;
			case 10: card7 = new Card(card10Bm,width/2+110, height/2);
					break;
			case 11: card7 = new Card(card11Bm,width/2+110, height/2);
					break;
			case 12: card7 = new Card(card12Bm,width/2+110, height/2);
					break;
			case 13: card7 = new Card(card13Bm,width/2+110, height/2);
					break;
			case 14: card7 = new Card(card14Bm,width/2+110, height/2);
					break;
			case 15: card7 = new Card(card15Bm,width/2+110, height/2);
					break;
			case 16: card7 = new Card(card16Bm,width/2+110, height/2);
					break;
			case 17: card7 = new Card(card17Bm,width/2+110, height/2);
					break;
			case 18: card7 = new Card(card18Bm,width/2+110, height/2);
					break;
			case 19: card7 = new Card(card19Bm,width/2+110, height/2);
					break;
			case 20: card7 = new Card(card20Bm,width/2+110, height/2);
					break;
			case 21: card7 = new Card(card21Bm,width/2+110, height/2);
					break;
			case 22: card7 = new Card(card22Bm,width/2+110, height/2);
					break;
			case 23: card7 = new Card(card23Bm,width/2+110, height/2);
					break;
			case 24: card7 = new Card(card24Bm,width/2+110, height/2);
					break;
			case 25: card7 = new Card(card25Bm,width/2+110, height/2);
					break;
			case 26: card7 = new Card(card26Bm,width/2+110, height/2);
					break;
			case 27: card7 = new Card(card27Bm,width/2+110, height/2);
					break;
			case 28: card7 = new Card(card28Bm,width/2+110, height/2);
					break;
			case 29: card7 = new Card(card29Bm,width/2+110, height/2);
					break;
			case 30: card7 = new Card(card30Bm,width/2+110, height/2);
					break;
			case 31: card7 = new Card(card31Bm,width/2+110, height/2);
					break;
			case 32: card7 = new Card(card32Bm,width/2+110, height/2);
					break;
			case 33: card7 = new Card(card33Bm,width/2+110, height/2);
					break;
			case 34: card7 = new Card(card34Bm,width/2+110, height/2);
					break;
			case 35: card7 = new Card(card35Bm,width/2+110, height/2);
					break;
			case 36: card7 = new Card(card36Bm,width/2+110, height/2);
					break;
			case 37: card7 = new Card(card37Bm,width/2+110, height/2);
					break;
			case 38: card7 = new Card(card38Bm,width/2+110, height/2);
					break;
			case 39: card7 = new Card(card39Bm,width/2+110, height/2);
					break;
			case 40: card7 = new Card(card40Bm,width/2+110, height/2);
					break;
			case 41: card7 = new Card(card41Bm,width/2+110, height/2);
					break;
			case 42: card7 = new Card(card42Bm,width/2+110, height/2);
					break;
			case 43: card7 = new Card(card43Bm,width/2+110, height/2);
					break;
			case 44: card7 = new Card(card44Bm,width/2+110, height/2);
					break;
			case 45: card7 = new Card(card45Bm,width/2+110, height/2);
					break;
			case 46: card7 = new Card(card46Bm,width/2+110, height/2);
					break;
			case 47: card7 = new Card(card47Bm,width/2+110, height/2);
					break;
			case 48: card7 = new Card(card48Bm,width/2+110, height/2);
					break;
			case 49: card7 = new Card(card49Bm,width/2+110, height/2);
					break;
			case 50: card7 = new Card(card50Bm,width/2+110, height/2);
					break;
			case 51: card7 = new Card(card51Bm,width/2+110, height/2);
					break;
			case 52: card7 = new Card(card52Bm,width/2+110, height/2);
					break;
		
		
		
		
		}
		
			

		
	}

	protected void chipSet(String chips)
	{
		
		 chip = Integer.parseInt(chips);
	}
	
	public void surfaceCreated(SurfaceHolder holder) 
	{
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			bet.handleActionDown((int)event.getX(), (int)event.getY());  //kara
			fold.handleActionDown((int)event.getX(), (int)event.getY());  //kara
			plus25.handleActionDown((int)event.getX(), (int)event.getY()); //kara
			plus100.handleActionDown((int)event.getX(), (int)event.getY());
			plus500.handleActionDown((int)event.getX(), (int)event.getY());
			check.handleActionDown((int)event.getX(), (int)event.getY());
			call.handleActionDown((int)event.getX(), (int)event.getY());
			allIn.handleActionDown((int)event.getX(), (int)event.getY());
			cancel.handleActionDown((int)event.getX(), (int)event.getY());
			
		}  if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if(call.isTouched() && (!isAdding))
			{
				call.setTouched(false);
				calling = true;
			}
			if(check.isTouched() && (!isAdding))
			{
				check.setTouched(false);
				checking = true;
			}
			if(fold.isTouched() && (!isAdding))
			{
				fold.setTouched(false);
				folding = true;
			}
			if(allIn.isTouched() && isAdding)
			{
				allIn.setTouched(false);
				allinGo = true;
				curBetAmt = chip;
			}
			if(cancel.isTouched() && isAdding)
			{
				cancel.setTouched(false);
				canceling =true;
				curBetAmt = 0;
				allinGo = false;
				
			}
			if (bet.isTouched()) {
				bet.setTouched(false);
				betting = true;
				
			}
			if(plus25.isTouched()){ //kara
				plus25.setTouched(false);
				if((curBetAmt + 25) <= chip)
				{
					curBetAmt = curBetAmt + 25;
					adding = 25;

				}
			}
			if(plus100.isTouched()){ //kara
				plus100.setTouched(false);
				if((curBetAmt + 100) <= chip){
				curBetAmt = curBetAmt + 100;
				adding = 100;

				}	
			}
			if(plus500.isTouched()){ //kara
				plus25.setTouched(false);
				if((curBetAmt + 500) <= chip)
				{
				curBetAmt = curBetAmt + 500;
				adding = 500;
				
				}

			
			}
			
			
		}
		
		return true ;  
	}
	
	protected void okToBet(){
		okBet=1;
	}
	
	protected void setBet(){
		 
		if(betting== true){
			betting = false;
			okBet=0;
			test=0;
		}
		else
			{betting = true;}		
		
	}
	protected void unsetBet(){
		betting = false;
		okBet = 0;
	}
	
////////////
// ADDING
////////////
	protected int getAdding(){
		return adding;
	}
	protected void clrAdding()
	{
		adding = 0;
		isAdding = false;
	}
	protected void setIsAdding()
	{
		isAdding = true;
	}
	protected boolean isAdding()
	{
		return isAdding;
	}

	protected void clearCurBet(){
		curBetAmt = 0;
	}
	
	protected int getCurBetAmt()
	{
		return curBetAmt;
	}
///////////
// FOLDING
///////////
	
	protected boolean getFolding()
	{
		return folding;
	}
	protected void clrFolding()
	{
		folding = false;
	}
///////////
//  ALLIN
///////////

protected boolean getAllin()
{
	return allinGo;
}
protected void clrAllin()
{
	allinGo = false;
}
protected int getChips()
{
	return chip;
}
	

///////////
// BETTING
///////////	
	protected boolean betAmount()
	{
		if(betting== true) {
			betting = false;
			okBet=0;
			test=0;
			return true;  
			
		}
		return false;
	}
	
	protected boolean okBetAmount()
	{
		if((betting== true) && (okBet ==1)&& (test==1)){
			betting = false;
			okBet=0;
			test=0;
			chip = chip - curBetAmt;
			return true;
			
		}
		return false;
	}
///////////////
// CANCELLING
///////////////
	protected boolean getCancel()
	{
		return canceling;
	}
	protected void clrCancel()
	{
		canceling = false;
	}
	protected void clrCurBet()
	{
		curBetAmt = 0;
	}
	
	
/////////////
// CHECKING
/////////////
	protected boolean getChecking()
	{
		return checking;
	}
	
	protected void clrChecking()
	{
		checking = false;
	}

/////////////
// CALLING
/////////////
protected boolean getCalling()
{
return calling;
}

protected void clrCalling()
{
	calling = false;
}

protected int callingAmt(int chipsToMatch)
{
	int tmpChip = 0;
	if(chip > chipsToMatch)
	{
		chip = chip - chipsToMatch;
		return chipsToMatch;
	}
	else
	{
		tmpChip = chip;
		chip = 0;
		return tmpChip;
	}
}
/////////////////
// PLAYERS
/////////////////
protected void setPlayer(int num, int coins, String user) //(int num, Bitmap btmp, int coins)
{
	curPlayers.setPlayer(num, coins, user); //num, btmp, coins);
}
protected void decrPlayerCoin(String user, int amt)
{
	curPlayers.decrCoin(user, amt);
}
protected void setPlayerCoords(Canvas canvas)
{
	curPlayers.setCoords(canvas, width, height);
}
protected void setPlayerFold(String plr)
{
	curPlayers.playerFold(plr);
}
protected void clrPlayers()
{
	curPlayers.clrPlayers();
}
protected void clrPot()
{
	mainPot = 0;
}

	
/////////////
// DRAWING
/////////////

	protected void setPrevBet(String usr, int bet)
	{
		prevPlayer = usr;
		prevBet = bet;
	}
	protected void setMainPot(int num)
	{
		mainPot = num;
	}

	protected void drawBackground(Canvas canvas)
	{	
		
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		width =canvas.getWidth();
		height=canvas.getHeight();
		buttonSet();
		unsetBet();
		//bet.draw(canvas);
	}
	
	protected void drawHand(Canvas canvas)
	{	Paint paint = new Paint();
		unsetBet();

		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
			
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(30); 
		paint.setColor(Color.RED);
        
		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(waiting, 15, (height-(height/8)), paint);
		card1.draw(canvas);
		card2.draw(canvas);   
	}
	//new kara
	protected void drawPlayerBet(Canvas canvas) //before
	{	Paint paint = new Paint();
		unsetBet();

		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		
		
		String outputStr = prevPlayer + " bet: " + prevBet;
		
		paint.setStyle(Paint.Style.FILL);
		
		paint.setAntiAlias(true);
		paint.setTextSize(30); 
		
		paint.setColor(Color.RED);
		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(outputStr, 15, (height-(height/8)), paint);
		curPlayers.draw(canvas);

	}
	protected void drawPlayerFold(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();

		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		
		
		String outputStr = prevPlayer + " folded!";
		
		paint.setStyle(Paint.Style.FILL);
		
		paint.setAntiAlias(true);
		paint.setTextSize(30); 
		
		paint.setColor(Color.RED);
		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(outputStr, 15, (height-(height/8)), paint);
		curPlayers.draw(canvas);
	}
	
	
	protected void handBet(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK); // clearing canvas?
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		
		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(yourBet, 15, (height-(height/8)), paint);
		card1.draw(canvas);
		card2.draw(canvas);   
		
		
		
	}
	
	protected void handBetStart(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		plus25.draw(canvas);
		plus100.draw(canvas);
		plus500.draw(canvas);
		allIn.draw(canvas);
		cancel.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		
		String curBetString = "You bet: " + curBetAmt; //kara
		canvas.drawText(curBetString, 15, (height-(height/8)), paint); //kara
		
        test=1;

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
        
		card1.draw(canvas);
		card2.draw(canvas);
		
		
		
	}
	
	protected void drawFlop(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		unsetBet();

		paint.setAntiAlias(true);
		paint.setTextSize(30);
		
		
		canvas.drawColor(Color.BLACK);
		paint.setColor(Color.RED);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
		
		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		
		canvas.drawText(waiting, 15, (height-(height/8)), paint);
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
	}
	
	protected void flopBet(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		
		canvas.drawText(yourBet, 15, (height-(height/8)), paint);
		
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		
	}
	
	protected void flopBetStart(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		plus25.draw(canvas);
		plus100.draw(canvas);
		plus500.draw(canvas);
		allIn.draw(canvas);
		cancel.draw(canvas);
		curPlayers.draw(canvas);
		
		test=1;
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		
		String curBetString = "You bet: " + curBetAmt; //kara
		canvas.drawText(curBetString, 15, (height-(height/8)), paint); //kara

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		
	}
	protected void drawTurn(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		unsetBet();
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		
		canvas.drawColor(Color.BLACK);
		paint.setColor(Color.RED);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		
		canvas.drawText(waiting, 15, (height-(height/8)), paint);
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
		
		
	}
	
	protected void turnBet(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(yourBet, 15, (height-(height/8)), paint);
		
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
	}
	
	protected void turnBetStart(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		plus25.draw(canvas);
		plus100.draw(canvas);
		plus500.draw(canvas);
		allIn.draw(canvas);
		cancel.draw(canvas);
		curPlayers.draw(canvas);
		
		test=1;
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		
		String curBetString = "You bet: " + curBetAmt; //kara
		canvas.drawText(curBetString, 15, (height-(height/8)), paint); //kara

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);

		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
	}
	
	protected void drawRiver(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		unsetBet();

		paint.setAntiAlias(true);
		paint.setTextSize(30);
		
		//canvas.drawColor(Color.BLACK);
		paint.setColor(Color.RED);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		
		canvas.drawText(waiting, 15, (height-(height/8)), paint);
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
		card7.draw(canvas);
		
		
	}
	
	protected void riverBet(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		fold.draw(canvas);
		check.draw(canvas);
		call.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
		canvas.drawText(yourBet, 15, (height-(height/8)), paint);
		
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
		card7.draw(canvas);
	}
	
	protected void riverBetStart(Canvas canvas)
	{
		Paint paint = new Paint();
		unsetBet();
		
		//canvas.drawColor(Color.BLACK);
		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		bet.draw(canvas);
		plus25.draw(canvas);
		plus100.draw(canvas);
		plus500.draw(canvas);
		allIn.draw(canvas);
		cancel.draw(canvas);
		curPlayers.draw(canvas);
		
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		
		String curBetString = "You bet: " + curBetAmt; //kara
		canvas.drawText(curBetString, 15, (height-(height/8)), paint); //kara
		
        test=1;

		String chipCt = "Your chips: " + Integer.toString(chip);
		String mainPotCt = "Pot: " + mainPot;
		
		canvas.drawText(mainPotCt, width/2-50, ((height/2)-(height/11)), paint);
		canvas.drawText(chipCt, width/2-75, (height-(height/4)), paint);
        
		card1.draw(canvas);
		card2.draw(canvas);
		card3.draw(canvas);
		card4.draw(canvas);
		card5.draw(canvas);
		card6.draw(canvas);
		card7.draw(canvas); 

	}
	
	//kara
	protected void drawWhoWon(Canvas canvas, String userWon, String chipsWon, String winType) 
	{
		Paint paint = new Paint();
		unsetBet();

		Rect titz = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(background, null, titz, paint);
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.RED);
		curPlayers.draw(canvas);
		
		String winStr = userWon + " won " + chipsWon + " chips";
		
		if(winType.equals("W"))
		{
			winStr = winStr.concat(" , because everyone folded");
			
			
		}
		
		canvas.drawText(winStr, 15, (height-(height/8)), paint); //kara
		canvas.drawText(Integer.toString(chip), width/2-50, (height-(height/4)), paint);
		
	}
	

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		// TODO Auto-generated method stub
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	
	protected void setOkAdd()
	{
		okAdd = true;
	}
	protected boolean getOkAdd()
	{
		return okAdd;
	}
	protected boolean getBetting()
	{
		return betting;
	}
	protected boolean canBet()
	{
		if(betting && ((okBet ==1) || allinGo ))
		{
			chip = chip - curBetAmt;
			return true;
		}
		else return false;
	}

	
	
	
	

}
