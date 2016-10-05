package com.amarjot8.assignment8;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Gameplay extends AppCompatActivity {

    protected int Ball_x, Ball_y;
    protected int Ballradius = 100;

    protected int BallSpeed_x, BallSpeed_y = 0;
    protected int sensor_x = 0;

    private boolean FingerDown = false;

    @Override
    protected void onStop()
    {
        super.onStop();
        printToLog("onStop : ", "onStop");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        printToLog("LifeCycle : ", "onPause");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        printToLog("LifeCycle : ", "onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        DrawingView dv = new DrawingView(this);
        setContentView(dv);

        //Locking View in portait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    protected void endGame(){
        //Launching EndScreen Activity
        Intent intent = new Intent(this, EndScreen.class);
        startActivity(intent);
    }

    //Contains methods which Draws ball, Cans to screen
    public class DrawingView extends View
    {
        protected long last_tick=-1,
                timer=10000, tick_period=1000;

        protected final static int numCans=5;
        protected List<Can> Cans=new ArrayList<>();

        public DrawingView(final Context context)
        {
            super(context);
            setBallxy();
            last_tick=System.currentTimeMillis();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getActionMasked())
            {
                //When user makes a touch, check if users touch is on ball
                case MotionEvent.ACTION_DOWN:
                    isFingerDownOnBall((int) event.getX(), (int) event.getY());
                    break;
                //when user lifts finger
                case MotionEvent.ACTION_UP:
                    FingerDown = false;
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas c)
        {
            //multiple methods will need a Paint object; might as well put it here.
            final Paint p = new Paint();

            //timer
            if(System.currentTimeMillis()-last_tick>=tick_period){
                if (timer<=0)
                    endGame();
                timer -= tick_period;
                if(timer<0)timer=0;
                last_tick=System.currentTimeMillis();
            }

            controlBallxy(c);
            DrawBall(c,Ball_x,Ball_y,Ballradius);

            //populate the list and draw all cans
            if (Cans.isEmpty()){
                for (int i=0; i<numCans; i++)
                    Cans.add(new Can(c));
            }
            for (Can can:Cans){
                can.Draw(c,p);
            }

            //drawing timer last so its always on top layer
            p.setColor(Color.RED);
            p.setTextSize(200);
            c.drawText(Long.toString(timer/1000), 10, 205, p);

            invalidate();
        }

        //Makes sure ball cannot go out of screen except from top
        protected void controlBallxy(Canvas c)
        {
            //Stop it from left
            if(Ball_x - Ballradius < 0 )
            {
                Ball_x = 0 + Ballradius;
            }
            //Stop it from right
            if(Ball_x + Ballradius > c.getWidth())
            {
                Ball_x = c.getWidth() - Ballradius;
            }
            //Stop it from bottom
            if(Ball_y + Ballradius > c.getHeight())
            {
                Ball_y  = c.getHeight() - Ballradius;
            }
        }
        //Sets the ball on bottom center of phone
        protected void setBallxy()
        {
            Display display = getWindowManager().getDefaultDisplay();
            Point dimensions = new Point();
            display.getSize(dimensions);
            Ball_x = dimensions.x - (dimensions.x /2);
            Ball_y = dimensions.y - (dimensions.x /4);
        }
    }

    /**
     * class for can object. Contains all relevent methods and variables
     */
    public class Can{
        private static final int width=100;
        private static final int height=300;
        private int x;
        private int y;

        /**
         * picks random x and y coordinates
         * @param c
         */
        Can(Canvas c){
            Random rand=new Random();

            x=rand.nextInt(c.getWidth()-width);
            //added extra brackets in case BEDMAS is not followed
            y=rand.nextInt((c.getHeight()/3)-height);
        }

        /**
         * draws the can
         * @param c
         * @param p
         */
        public void Draw(Canvas c, Paint p){
            c.drawRect(x, y, x+width, y+height, p);
        }
    }



    //Simply Draws ball at given location with given radius
    protected void DrawBall(Canvas c, int x, int y, int radius)
    {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawCircle(x,y,radius,p);
    }


    //Checks if cordinates are on Ball 
    private boolean isFingerDownOnBall(int eventX, int eventY)
    {
        if((eventX <= (Ball_x + Ballradius) && (eventX > Ball_x - Ballradius)) && (eventY <= (Ball_y + Ballradius) && (eventY > Ball_y - Ballradius)))
        {
            FingerDown = true;
            printToLog("Touch", "Ball is touched");
            return true;
        }
        printToLog("Touch", "Ball is NOT touched");
        FingerDown = false;
        return false;
    }

    private void printToLog(String tag, String msg)
    {
        Log.e(tag,msg);
    }
}
