package com.amarjot8.assignment8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Storing Play button
        Button b_Play = (Button) findViewById(R.id.playbutton);
        //When Play Button is pressed
        b_Play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Open Game
                launchGameactivity();
            }
        });

        //Storing Exit button
        Button b_Exit = (Button) findViewById(R.id.exitbutton);
        b_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitapp();
            }
        });
    }

    //Launchs Game Activity
    private void launchGameactivity()
    {
        //Launching Gameplay Activity
        Intent intent = new Intent(this, Gameplay.class);
        startActivity(intent);
    }
    //Exits App and returns user to device Home Screen
    private void exitapp()
    {
        //Going to Home Screen
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
