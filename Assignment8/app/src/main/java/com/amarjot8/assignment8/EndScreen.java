package com.amarjot8.assignment8;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class EndScreen extends AppCompatActivity {
    // Placeholder array for high scores, to be written into a file for the beta
    String[] names = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    int[] highScores = {10, 8, 6, 6, 6, 5, 3, 3, 1, 0};
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_screen);

        //Lock View to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get the score from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            score = extras.getInt("SCORE");

        // Display the score
        ((TextView)findViewById(R.id.textView_score)).setText(score + "");

        // Set the list to display the high scores
        for(int i = 0; i < highScores.length; i++) {
            listItems.add(i, (i+1) + ". " + names[i] + ": " + highScores[i]);
        }

        // Find position on leaderboard and display it
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        ListView listView = (ListView)findViewById(R.id.highScores);
        listView.setAdapter(adapter);
        if (score < highScores[9]) {
            // If the user doesn't make it onto the leaderboard disable submission
            findViewById(R.id.nameInput).setEnabled(false);
            findViewById(R.id.submitScoreButton).setEnabled(false);
        }

        // When the submit button is pressed, submit the name and high score
        Button submitButton = (Button)findViewById(R.id.submitScoreButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SubmitHighScore();
            }
        });

        // When the home button is pressed, return to the home activity
        Button homeButton = (Button)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });
    }

    // Method to be called when the user presses the score submit button
    private void SubmitHighScore() {
        if (score >= highScores[9]) {
            for (int i = 9; i >= 0; i--) {
                if (score >= highScores[i]) {
                    if (i != 9) {
                        highScores[i+1] = highScores[i];
                        names[i+1] = names[i];
                    }
                    highScores[i] = score;
                    names[i] = ((EditText)findViewById(R.id.nameInput)).getText().toString();
                }
                else break;
            }
        }
        // Update the display list
        updateDisplayList();
        // Display the changes
        adapter.notifyDataSetChanged();

        // Disable the TextView and Button
        findViewById(R.id.nameInput).setEnabled(false);
        findViewById(R.id.submitScoreButton).setEnabled(false);
    }

    // Return to the home activity
    private void startHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void updateDisplayList() {
        // Merge the names and scores lists to be displayed in the ListView
        for(int i = 0; i < highScores.length; i++) {
            listItems.set(i, (i+1) + ". " + names[i] + ": " + highScores[i]);
        }
    }
}
