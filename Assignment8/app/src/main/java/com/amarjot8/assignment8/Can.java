package com.amarjot8.assignment8;

/**
 * Created by Joseph on 10/6/2016.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

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

        x=c.getWidth()-width;
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

    /**
     * calculates if the can is touching a circle with given coordinates and radius
     * @param circ_x
     * @param circ_y
     * @param radius
     * @return
     */
    public boolean IsTouching(int circ_x, int circ_y, int radius){
        Point[] corners=new Point[]{new Point(x, y), new Point(x+width, y),
                new Point(x, y+width), new Point(x+width, y+height)};
        for(Point p : corners){
            //PLEASE CHECK THIS MATH! I think this should calculate the distance
            //between the circle and point p, but I'm pretty bad at math :/
            if(Math.sqrt(Math.pow(circ_x-p.x, 2)+Math.pow(circ_y-p.y, 2))<=radius)
                return true;
        }
        return false;
    }

    /**
     * moves can left by given distance
     * @param dx
     */
    public void Move(int dx){ x=x-dx; }

    /**
     * returns true if can is completely off canvas
     * @param c
     * @return
     */
    public boolean OffCanvas(Canvas c){
        if(x+width<0||x>c.getWidth()||y+height<0||y>c.getHeight())
            return true;
        return false;
    }
}
