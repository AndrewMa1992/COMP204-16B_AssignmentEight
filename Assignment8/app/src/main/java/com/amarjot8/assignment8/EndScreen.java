package com.amarjot8.assignment8;

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

        // Score not yet implemented, set to 4 as test value
        score = 4;
        // Display the score
        ((TextView)findViewById(R.id.textView_score)).setText(score + "");



        Button submitButton = (Button)findViewById(R.id.submitScoreButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Open Game
                SubmitHighScore();
            }
        });
    }

    // Method to be called when the user presses the score submit button
    public void SubmitHighScore() {
        // For now, just show a toast with the entered name
        Toast toast = Toast.makeText(getApplicationContext(), ((EditText)findViewById(R.id.nameInput)).getText(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
