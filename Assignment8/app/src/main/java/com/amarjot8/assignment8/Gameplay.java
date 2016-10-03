package com.amarjot8.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

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

        super.onCreate(savedInstanceState);

        DrawingView dv = new DrawingView(this);
        setContentView(dv);
    }


    public class DrawingView extends View
    {
        protected int Ball_x, Ball_y;
        protected int Ballradius = 100;

        public DrawingView(Context context)
        {
            super(context);
            setBallxy();
        }

        @Override
        protected void onDraw(Canvas c)
        {
            controlBallxy(c);
            DrawBall(c,Ball_x,Ball_y,Ballradius);
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
