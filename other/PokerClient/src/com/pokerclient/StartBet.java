package com.pokerclient;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class StartBet 
{
	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;
	private boolean touched;
	
	public StartBet(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}

	}
	
}
