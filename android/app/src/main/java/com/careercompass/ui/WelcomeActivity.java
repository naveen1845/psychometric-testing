package com.careercompass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.careercompass.R;

/**
 * Screen 1: Welcome Screen
 * Introduces Career Compass, explains the 5 assessment dimensions,
 * presents an educational disclaimer, and navigates to the Student Profile screen.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnStart = findViewById(R.id.btnStartAssessment);
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, StudentProfileActivity.class);
            startActivity(intent);
        });
    }
}
