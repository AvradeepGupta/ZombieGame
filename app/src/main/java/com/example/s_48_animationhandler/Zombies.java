package com.example.s_48_animationhandler;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
 
public class Zombies
{
	public int xPos, yPos, xSpeed, ySpeed, xDirection, yDirection, frameIndex;
	public boolean isAlive;
	public Canvas canvas;
	public Bitmap[] pics;
	public Bitmap splatter;
	public Context context;
	public int deathWaitCounter;
	private Paint transparentpaint;
	
	public Zombies( int[] images, Context context)
	{
		
		this.pics    = new Bitmap[images.length]; 
		this.context = context;
		
		for(int i=0; i<pics.length; i++) {
		       pics[i] = BitmapFactory.decodeResource(context.getResources(), images[i]);
		}
		
		splatter = BitmapFactory.decodeResource(context.getResources(),R.drawable.splatter);
		transparentpaint = new Paint();
		Born();
	}
	
	public void Draw(Canvas canvas)
	{
		this.canvas  = canvas;
				
		if(isAlive==false) {
			transparentpaint.setAlpha(deathWaitCounter); 
			canvas.drawBitmap(pics[frameIndex], xPos, yPos, transparentpaint);
			canvas.drawBitmap(splatter, xPos, yPos, transparentpaint);
		}
		else
			canvas.drawBitmap(pics[frameIndex], xPos, yPos, null);
	}
	
	public void Move()
	{
		if(deathWaitCounter <0 && !isAlive)
			Born();
			
		if(!isAlive) {
			deathWaitCounter -=10;
			return;
		}
		
		xPos += xDirection*xSpeed;
		if(xPos<0) {
			xDirection = 1;
			reSpeed();
		}
		if(xPos>canvas.getWidth()-pics[0].getWidth()) {
			xDirection = -1;
			reSpeed();
		}
		
		yPos += yDirection*ySpeed;
		
		if(yPos<0) {
			yDirection = 1;
			reSpeed();
		}
		if(yPos>canvas.getWidth()) {
			yDirection = -1;
			reSpeed();
		}
		
		frameIndex++;
		if(frameIndex == pics.length) frameIndex = 0;
	}
	
	public void Born()
	{
		this.isAlive = true;
		this.frameIndex = 0;
		
		this.xPos = 0;
		this.yPos = 0;

		deathWaitCounter = 255;
		
		if(Math.random()<0.5) xDirection = 1; else xDirection = -1;		
		yDirection = ((Math.random()<0.5)) ?  1 : -1;	
	
		reSpeed();
	}
	
	private void reSpeed() {
		xSpeed = 25+(int)(Math.random()*15);
		ySpeed = 25+(int)(Math.random()*15);
			
	}

	public void Die()
	{
		isAlive = false;
	}
	
	
}