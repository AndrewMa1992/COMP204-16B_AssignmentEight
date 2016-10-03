package com.amarjot8.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

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

        protected final static int numCans=5;
        protected List<Can> Cans=new ArrayList<>();

        public DrawingView(Context context) { super(context); }

        @Override
        protected void onDraw(Canvas c)
        {
            //multiple methods will need a Paint object; might as well put it here.
            Paint p = new Paint();

            DrawBall(c,Ballx,Bally,Ballradius);

            //populate the list and draw all cans
            if (Cans.isEmpty()){
                for (int i=0; i<numCans; i++)
                    Cans.add(new Can(c));
            }
            for (Can can:Cans){
                can.Draw(c,p);
            }

            invalidate();
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

        c.drawCircle(x,y,radius,p);
    }




    private void printToLog(String tag, String msg)
    {
        Log.e(tag,msg);

    }
}
