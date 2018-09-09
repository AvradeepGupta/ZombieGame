package com.example.s_48_animationhandler;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
 
public class MainActivity extends Activity  {
	
	public Bitmap bg;
	public long mLastMove;
	public int touchX, touchY;
	public Zombies[] zombie = new Zombies[10];

	MediaPlayer mp;
	public RefreshHandler mRedrawHandler = new RefreshHandler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
      
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        
        bg = BitmapFactory.decodeResource(getResources(),R.drawable.haunted);
        bg = Bitmap.createScaledBitmap(bg, width, height, false);
        
        int[] pics = {R.drawable.frame_0,R.drawable.frame_1,R.drawable.frame_2,R.drawable.frame_3,R.drawable.frame_4};
       
        int[] pics2 = {R.drawable.frame_zombie2_0,R.drawable.frame_zombie2_1,R.drawable.frame_zombie2_2,R.drawable.frame_zombie2_3,R.drawable.frame_zombie2_4,R.drawable.frame_zombie2_5,R.drawable.frame_zombie2_6,R.drawable.frame_zombie2_7,R.drawable.frame_zombie2_8,R.drawable.frame_zombie2_9,R.drawable.frame_zombie2_10,R.drawable.frame_zombie2_11};
        int[] pics3 = {R.drawable.frame_zombie3_0,R.drawable.frame_zombie3_1,R.drawable.frame_zombie3_2,R.drawable.frame_zombie3_3,R.drawable.frame_zombie3_4,R.drawable.frame_zombie3_5,R.drawable.frame_zombie3_6,R.drawable.frame_zombie3_7,R.drawable.frame_zombie3_8,R.drawable.frame_zombie3_9,R.drawable.frame_zombie3_10,R.drawable.frame_zombie3_11};
        
        for(int i=0; i<zombie.length; i++) {
			double rand = Math.random();

			if (rand < .33)
				zombie[i] = new Zombies(pics, this);
			else if (rand < .66)
				zombie[i] = new Zombies(pics2, this);
			if (rand >= .66)
				zombie[i] = new Zombies(pics3, this);

		}
        setContentView(new GameView(this));
        mp=MediaPlayer.create(this,R.raw.background);
        mp.setLooping(true);
        mp.start();
        
      }
		
	public void update()
    {	 
		  long now = System.currentTimeMillis();
           if (now - mLastMove > 50) {             
           	 setContentView(new GameView(this));   
                mLastMove = now;
            } 
			
      mRedrawHandler.sleep(50); 
    }
	
	class GameView extends View {
 	    public GameView(Context context)
	    {
	    	super(context);	    	
	    }
	    @Override
	    public void onDraw(Canvas canvas) {
 	        canvas.drawBitmap(bg, 0,0, null);
 	       
 	       for(int i=0; i<zombie.length; i++) { 
 	    	   zombie[i].Draw(canvas);
 	    	   zombie[i].Move();
 	       }
 	       
	        update();
	    }   
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mp.isPlaying())
			mp.pause();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mp.isPlaying())
			mp.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(mp.isPlaying())
			mp.pause();
	}


class RefreshHandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
		MainActivity.this.update();
	}

	public void sleep(long delayMillis) {
		this.removeMessages(0);
		sendMessageDelayed(obtainMessage(0), delayMillis);
	}
}

		public boolean onTouchEvent(MotionEvent event)
		{
		
		touchX = (int) event.getX();
		touchY = (int) event.getY();
		
		int action = event.getAction();
		if (action==MotionEvent.ACTION_DOWN)
			{
			for(int i=0; i<zombie.length; i++) { 
				if(touchX > zombie[i].xPos 
							&& touchX < zombie[i].xPos+zombie[i].pics[0].getWidth()
							&& touchY > zombie[i].yPos
							&& touchY < zombie[i].yPos+zombie[i].pics[0].getHeight()						
						) {
				zombie[i].Die();
				}
			}
			}
		
		return false;
		}
}





