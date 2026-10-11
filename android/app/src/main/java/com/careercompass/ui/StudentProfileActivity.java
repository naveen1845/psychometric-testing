package com.careercompass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.careercompass.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Screen 2: Student Profile Screen
 * Captures student's full name (required) and optional education level.
 * Performs validation before initiating the 15-question assessment.
 */
public class StudentProfileActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT_NAME = "extra_student_name";
    public static final String EXTRA_EDUCATION_LEVEL = "extra_education_level";

    private TextInputLayout tilName;
    private EditText etName;
    private EditText etEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        tilName = findViewById(R.id.tilName);
        etName = findViewById(R.id.etName);
        etEducation = findViewById(R.id.etEducation);
        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> validateAndProceed());
    }

    private void validateAndProceed() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String education = etEducation.getText() != null ? etEducation.getText().toString().trim() : "";

        if (name.isEmpty()) {
            tilName.setError(getString(R.string.error_name_required));
            etName.requestFocus();
            return;
        }

        tilName.setError(null);

        Intent intent = new Intent(StudentProfileActivity.this, AssessmentActivity.class);
        intent.putExtra(EXTRA_STUDENT_NAME, name);
        intent.putExtra(EXTRA_EDUCATION_LEVEL, education.isEmpty() ? "Not Specified" : education);
        startActivity(intent);
    }
}
