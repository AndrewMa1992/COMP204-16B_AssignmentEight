package com.amarjot8.assignment8;

import android.content.Context;
import android.graphics.Canvas;
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
        protected int Ballx, Bally;
        protected int Ballradius = 50;

        public DrawingView(Context context)
        {
            super(context);
            setBallxy();
        }

        @Override
        protected void onDraw(Canvas c)
        {
            DrawBall(c,Ballx,Bally,Ballradius);
            invalidate();
        }

        protected void setBallxy()
        {
            Display display = getWindowManager().getDefaultDisplay();
            Point dimensions = new Point();
            display.getSize(dimensions);
            Ballx = dimensions.x - (dimensions.x /2);
            Bally = dimensions.y - (dimensions.x /4);
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
