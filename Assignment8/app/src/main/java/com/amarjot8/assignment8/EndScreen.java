package com.amarjot8.assignment8;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EndScreen extends AppCompatActivity {
    // Placeholder array for high scores, to be written into a file for the beta
    int[] highScores = {10, 8, 6, 6, 6, 5, 3, 3, 1, 0};
    int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_screen);

        //Lock View to portait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get the score from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            score = extras.getInt("SCORE");

        // Display the score
        ((TextView)findViewById(R.id.textView_score)).setText(score + "");

        // Find position on leaderboard and display it
        if (score >= highScores[9]) {
            for (int i = 0; i < 10; i++) {
                if (score >= highScores[i]) {
                    ((TextView)findViewById(R.id.textView_position)).setText(i + 1 + "");
                    break;
                }
            }
        } else {
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
        // For now, just show a toast with the entered name
        Toast toast = Toast.makeText(getApplicationContext(), ((EditText)findViewById(R.id.nameInput)).getText(), Toast.LENGTH_SHORT);
        toast.show();
    }

    // Return to the home activity
    private void startHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
