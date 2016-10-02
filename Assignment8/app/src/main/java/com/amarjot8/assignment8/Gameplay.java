package com.amarjot8.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        protected int Ballx = 100;
        protected int Bally = 100;
        protected int Ballradius = 50;

        public DrawingView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas c)
        {
            DrawBall(c,Ballx,Bally,Ballradius);
            invalidate();
        }
    }




    //Simply Draws ball at given location with given radius
    protected void DrawBall(Canvas c, int x, int y, int radius)
    {
        Paint p = new Paint();

        c.drawCircle(x,y,radius,p);
    }




    private void printToLog(String tag, String msg)
    {
        Log.e(tag,msg);

    }
}
