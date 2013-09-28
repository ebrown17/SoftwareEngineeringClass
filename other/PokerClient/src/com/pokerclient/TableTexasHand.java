package com.pokerclient;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class TableTexasHand extends View
{
	
	Bitmap card1, card2;
	int x=0, x2=0;
	int y=0, y2=0;
	private Paint paint = new Paint();
	public TableTexasHand(String car1, String car2){
		super(null);
		
		
		card1 = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier( car1 , "drawable", null));
		card2 = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier( car2 , "drawable", null));

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		drawCard1(x,y,canvas);
		drawCard2(x2,y2,canvas);
		
	}

	private void drawCard2(int x2, int y2, Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(card2, x2, y2, paint );
		invalidate();
	}

	private void drawCard1(int x2, int y2, Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(card1, x2+50, y2+50, paint );
		invalidate();
	}

	

	



	

	

	
	
	

}
