package com.amarjot8.assignment8;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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
        protected int Ball_x, Ball_y;
        protected int Ballradius = 100;

        protected long start_time, last_tick=-1,
                timer=10000, tick_period=1000;

        protected final static int numCans=5;
        protected List<Can> Cans=new ArrayList<>();

        public DrawingView(final Context context)
        {
            super(context);
            setBallxy();
            start_time=System.currentTimeMillis();
        }

        @Override
        protected void onDraw(Canvas c)
        {
            //multiple methods will need a Paint object; might as well put it here.
            final Paint p = new Paint();
            if(last_tick==-1||System.currentTimeMillis()-last_tick>=tick_period){
                last_tick=System.currentTimeMillis();
                if (timer==0)
                    endGame();
                else
                    timer-=tick_period;
            }

            controlBallxy(c);
            DrawBall(c,Ball_x,Ball_y,Ballradius);

            DrawBall(c,Ball_x,Ball_y,Ballradius);

            //populate the list and draw all cans
            if (Cans.isEmpty()){
                for (int i=0; i<numCans; i++)
                    Cans.add(new Can(c));
            }
            for (Can can:Cans){
                can.Draw(c,p);
            }

            /*
            //can't place in constructor, as canvas is needed. Nested in an if statement instead.
            if (timer==null){
                //starts at 10,000ms, ticks every 1000ms
                timer=new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        c.drawCircle(500, 500, 1000, p);
                    }

                    @Override
                    public void onFinish() {
                        endGame();
                    }
                }.start();
            }
            */

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




    private void printToLog(String tag, String msg)
    {
        Log.e(tag,msg);
    }
}
