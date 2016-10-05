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
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
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
//https://developer.android.com/training/gestures/movement.html
public class Gameplay extends AppCompatActivity {

    protected int Ball_x, Ball_y;
    protected int Ballradius = 100;

        int spx , spy = -5;

    protected int BallSpeed_x, BallSpeed_y = 0;
    protected int BallSpeedMotion_x, BallSpeedMotion_y = 0;
    protected int sensor_x = 0;

    private boolean FingerDown = false;

    private static final String DEBUG_TAG = "Velocity";

    private VelocityTracker mVelocityTracker = null;

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
                timer=60000, tick_period=1000;

        protected final static int numCans=5;
        protected List<Can> Cans=new ArrayList<>();

        public DrawingView(final Context context)
        {
            super(context);
            setBallxy();
            last_tick=System.currentTimeMillis();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            int index = event.getActionIndex();
            //Storing users action
            int action = event.getActionMasked();
            int pointerId = event.getPointerId(index);

            //Checks if user touched the ball
            if(isFingerDownOnBall((int) event.getX(), (int) event.getY())) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (mVelocityTracker == null) {
                            // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                            mVelocityTracker = VelocityTracker.obtain();
                        } else {
                            // Reset the velocity tracker back to its initial state.
                            mVelocityTracker.clear();
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker.addMovement(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker.addMovement(event);
                        // When you want to determine the velocity, call
                        // computeCurrentVelocity(). Then call getXVelocity()
                        // and getYVelocity() to retrieve the velocity for each pointer ID.
                        mVelocityTracker.computeCurrentVelocity(1000);
                        // Log velocity of pixels per second
                        // Best practice to use VelocityTrackerCompat where possible.
                        Log.d("", "X velocity: " +
                                VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                        pointerId));
                        BallSpeedMotion_x =  10;//(int)VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) - ((int)VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) / 2);
                        Log.d("", "Y velocity: " +
                                VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                        pointerId));
                        BallSpeedMotion_y = 10;//(int) VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId) - ((int)VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId) / 2);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        // Return a VelocityTracker object back to be re-used by others.
                        mVelocityTracker.recycle();
                        break;
                }
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
            Ball_x += spx;
            Ball_y += spy;

            if (Ball_x - Ballradius < 0) { Ball_x = 0 + Ballradius; spx = -spx; }
            if (Ball_x + Ballradius > c.getWidth()) { Ball_x = c.getWidth() - Ballradius; spx = -spx; }
            if (Ball_y  - Ballradius< 0) { Ball_y = 0 + Ballradius; spy = -spy; }
            //When ball is going outside the phone, it stops it.
            if (Ball_x - Ballradius < 0) { Ball_x = 0 + Ballradius; spx = -spx; }
            if (Ball_x + Ballradius > c.getWidth()) { Ball_x = c.getWidth() - Ballradius; spx = -spx; }
            if (Ball_y  - Ballradius< 0) { Ball_y = 0 + Ballradius; spy = -spy; }
            if (Ball_y + Ballradius > c.getHeight()) { Ball_y = c.getHeight() - Ballradius; spy = -spy; }
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
        c.drawCircle(Ball_x,Ball_y,radius,p);
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
