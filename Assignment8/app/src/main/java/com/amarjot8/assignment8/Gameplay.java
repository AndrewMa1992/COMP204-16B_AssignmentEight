package com.amarjot8.assignment8;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class Gameplay extends AppCompatActivity implements SensorEventListener {

    protected int Ball_x, Ball_y, Ballradius = 100,
            BallSpeed_x, BallSpeed_y = 0,
            BallSpeedMotion_x, BallSpeedMotion_y = 0;

    private int score=0;
    protected boolean paused=false;
    private ListView listView=null;
    protected AlertDialog dialog=null;

    private DrawingView dv;

    //sensor's values will be sored
    protected float sensor_x = 0;

    //Used to store Sesnor/ Register listner / Unregister listner
    SensorManager sMang;
    Sensor acc;

    private boolean BallMoved = false;

    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;


    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor s = event.sensor;
        //checking sensor that triggered this method is Accelerometer
        if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Extracting information from sensor
            sensor_x = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

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
        //Un-registering listener
        sMang.unregisterListener(this);
        printToLog("LifeCycle : ", "onPause");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //Registering Listener
        sMang.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        printToLog("LifeCycle : ", "onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);

        //Locking View in portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Used for Accelerometer sensor
        sMang = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sMang.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //this is the code for the pause dialog
        listView=new ListView(this);
        String[] options={"Home","Restart","Quit","Resume"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.pause_menu,R.id.dialog_options,options);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup viewGroup=(ViewGroup) view;
                TextView selected_view=(TextView)viewGroup.findViewById(R.id.dialog_options);
                String selected=selected_view.getText().toString();
                dialog.hide();
                switch (selected) {
                    case "Home": startActivity(new Intent(Gameplay.this,Home.class));
                        break;
                    case "Restart": startActivity(new Intent(Gameplay.this,Gameplay.class));
                        break;
                    case "Quit": endGame();
                        break;
                    case "Resume": paused=false;
                        dv.invalidate();
                        break;
                    default: printToLog("pause","selected:"+selected+" and option wasn't handled.");
                        break;
                }
            }
        });
    }

    protected void addPoint(){ score++; }
    protected int getScore(){ return score; }

    protected void endGame(){
        //Launching EndScreen Activity
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

    //Contains methods which Draws ball, Cans to screen
    public class DrawingView extends View
    {
        protected long last_tick=-1,
                timer=60000, tick_period=1000;

        protected int can_dx=10;
        protected final static int numCans=5;
        protected List<Can> Cans=new ArrayList<>();

        protected final Paint p=new Paint();

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
                BallMoved = true;
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
                        //Divided by offset since velocity is too great for game-play
                        BallSpeedMotion_x =  (int)VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) /40;
                        BallSpeedMotion_y = (int)VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId)/40;
                        controlBallMotionSpeed();
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

            if(Cans.isEmpty()) {
                for(int i=1; i<=numCans; i++){
                    Cans.add(new Can(c.getWidth()/numCans*i, c));
                }
            }
            for (Can can:Cans){
                if(can.isTouching(Ball_x, Ball_y, Ballradius)){
                    addPoint();
                    can.hide();
                }
                can.draw(c,p);
                can.move(can_dx, c);
            }

            //draws score on top right
            p.setColor(Color.GREEN);
            p.setTextSize(200);
            c.drawText(Integer.toString(getScore()), c.getWidth()-330, 205, p);
            //drawing timer last so its always on top layer
            p.setColor(Color.RED);
            c.drawText(Long.toString(timer/1000), 10, 205, p);

            if(!paused)
                invalidate();
        }

        //Makes sure ball cannot go out of screen except from top
        protected void controlBallxy(Canvas c)
        {
            Ball_x += BallSpeedMotion_x;
            Ball_y += BallSpeedMotion_y;

            //When ball is moving applying phones tilt
            if(BallMoved)
            {
                //Increasing affect of tilt and correcting distance
                Ball_x += -(sensor_x+sensor_x);
            }

            //When ball is going outside the phone, it bounces it except if y < 0 then respawn ball.
            if (Ball_x - Ballradius < 0) { Ball_x = Ballradius; BallSpeedMotion_x = -BallSpeedMotion_x; }
            if (Ball_x + Ballradius > c.getWidth()) { Ball_x = c.getWidth() - Ballradius; BallSpeedMotion_x = -BallSpeedMotion_x; }

            //Ball going past y < 0
            if (Ball_y  - Ballradius< 0) {restball();}
            if (Ball_y + Ballradius > c.getHeight()) { Ball_y = c.getHeight() - Ballradius; BallSpeedMotion_y = -BallSpeedMotion_y; }
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
        //Resets Ball back to its initial position
        private void restball()
        {
            BallSpeed_x = 0;
            BallSpeed_y = 0;
            BallSpeedMotion_x = 0;
            BallSpeedMotion_y = 0;
            setBallxy();
            BallMoved = false;
        }
    }

    @Override
    public void onBackPressed() {
        paused=true;
        //if dialog is not yet created, create it.
        if(dialog==null) {
            AlertDialog.Builder b = new AlertDialog.Builder(Gameplay.this);
            b.setCancelable(false);
            b.setView(listView);
            dialog = b.create();
        }
        dialog.show();
    }

    //Simply Draws ball at given location with given radius
    protected void DrawBall(Canvas c, int x, int y, int radius)
    {
        Paint p = new Paint();
        p.setColor(Color.BLUE);

        c.drawCircle(Ball_x,Ball_y,radius,p);
    }

    //Checks if co-ordinates are on Ball
    private boolean isFingerDownOnBall(int eventX, int eventY)
    {
        //Wont let the user hold the ball when ball has travelled a certain y cord
        if(eventY > 1600)
        {
            if ((eventX <= (Ball_x + Ballradius + 10) && (eventX > Ball_x - Ballradius + 10)) &&
                    (eventY <= (Ball_y + Ballradius + 10) && (eventY > Ball_y - Ballradius + 10))){
                printToLog("Touch", "Ball is touched");
                return true;
            }
        }
        printToLog("Touch", "Ball is NOT touched");
        return false;
    }

    //Method called when Ball Speed is being set when flick gesture is used.
    private void controlBallMotionSpeed()
    {
        if(BallSpeedMotion_x > 80)
        {
            BallSpeedMotion_x = 80;
        }
        else if(BallSpeedMotion_x < -80)
        {
            BallSpeedMotion_x = -80;
        }

        if(BallSpeedMotion_y > 80)
        {
            BallSpeedMotion_y = 80;
        }
        else if(BallSpeedMotion_y < -80)
        {
            BallSpeedMotion_y = -80;
        }
        printToLog("Speed", " Ball Speed : " + BallSpeedMotion_x + ", " + BallSpeed_y);
    }
    private void printToLog(String tag, String msg)
    {
        Log.e(tag,msg);
    }
}
