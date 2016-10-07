package com.amarjot8.assignment8;

/**
 * Created by Joseph on 10/6/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * class for can object. Contains all relevent methods and variables
 */
public class Can{
    private static final int width=100, height=300;
    private int x, y;
    private boolean visible=true;
    private Random rand=new Random();

    /**
     * picks y coordinate
     * @param x_start left edge of can
     * @param c
     */
    Can(int x_start, Canvas c){
        x=x_start;
        //added extra brackets in case BEDMAS is not followed
        y=rand.nextInt((c.getHeight()/3)-height);
    }

    /**
     * draws the can
     * @param c
     * @param p
     */
    public void draw(Canvas c, Paint p){
        if(visible) {
            p.setColor(Color.BLACK);
            c.drawRect(x, y, x + width, y + height, p);
        }
    }

    /**
     * calculates if the can is touching a circle with given coordinates and radius
     * @param circ_x
     * @param circ_y
     * @param radius
     * @return
     */
    public boolean isTouching(int circ_x, int circ_y, int radius){
        if(visible) {
            Point[] corners = new Point[]{new Point(x, y), new Point(x + width, y),
                    new Point(x, y + width), new Point(x + width, y + height)};
            for (Point p : corners) {
                //PLEASE CHECK THIS MATH! I think this should calculate the distance
                //between the circle and point p, but I'm pretty bad at math :/
                if (Math.sqrt(Math.pow(circ_x - p.x, 2) + Math.pow(circ_y - p.y, 2)) <= radius)
                    return true;
            }
        }
        return false;
    }

    /**
     * moves can left by given distance
     * @param dx
     */
    public void move(int dx, Canvas c){
        x=x-dx;
        if(offCanvas(c)){
            x = c.getWidth();
            y=rand.nextInt((c.getHeight()/3)-height);
            visible = true;
        }
    }

    /**
     * returns true if can is completely off canvas
     * @param c
     * @return
     */
    private boolean offCanvas(Canvas c){
        if(x+width<0||x>c.getWidth()||y+height<0||y>c.getHeight())
            return true;
        return false;
    }

    public void hide(){ visible = false; }
}
