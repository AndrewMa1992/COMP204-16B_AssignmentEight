package com.amarjot8.assignment8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    }


    private void launchGameactivity()
    {
        //Launching Gameplay Acitvity
        Intent intent = new Intent(this, Gameplay.class);
        startActivity(intent);
    }
}
