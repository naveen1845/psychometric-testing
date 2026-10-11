package com.careercompass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.careercompass.R;
import com.careercompass.model.ApiResponse;
import com.careercompass.model.AssessmentResultResponse;
import com.careercompass.model.AssessmentSubmissionRequest;
import com.careercompass.model.QuestionAnswerDto;
import com.careercompass.model.QuestionDto;
import com.careercompass.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Screen 3: Assessment Activity
 * Displays the 15 assessment questions one by one, preserving answer state
 * when navigating forward and backward. Submits the completed payload to the
 * backend scoring engine.
 */
public class AssessmentActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT_DATA = "extra_result_data";

    private String studentName;
    private String educationLevel;

    private List<QuestionDto> questions = new ArrayList<>();
    // Maps question index (0..14) to selected rating (1..5)
    private final Map<Integer, Integer> answersMap = new HashMap<>();
    private int currentQuestionIndex = 0;

    // UI Elements
    private TextView tvQuestionCounter;
    private TextView tvDimensionBadge;
    private ProgressBar progressBarAssessment;
    private TextView tvQuestionPrompt;
    private RadioGroup rgLikertOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4, rbOption5;
    private Button btnPrevious, btnNext;
    private FrameLayout layoutLoading;
    private TextView tvLoadingMessage;

    private boolean isSubmitting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        studentName = getIntent().getStringExtra(StudentProfileActivity.EXTRA_STUDENT_NAME);
        educationLevel = getIntent().getStringExtra(StudentProfileActivity.EXTRA_EDUCATION_LEVEL);

        if (studentName == null || studentName.trim().isEmpty()) {
            studentName = "Student";
        }

        bindViews();
        setupListeners();
        fetchQuestionsFromBackend();
    }

    private void bindViews() {
        tvQuestionCounter = findViewById(R.id.tvQuestionCounter);
        tvDimensionBadge = findViewById(R.id.tvDimensionBadge);
        progressBarAssessment = findViewById(R.id.progressBarAssessment);
        tvQuestionPrompt = findViewById(R.id.tvQuestionPrompt);
        rgLikertOptions = findViewById(R.id.rgLikertOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        rbOption5 = findViewById(R.id.rbOption5);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        layoutLoading = findViewById(R.id.layoutLoading);
        tvLoadingMessage = findViewById(R.id.tvLoadingMessage);
    }

    private void setupListeners() {
        btnPrevious.setOnClickListener(v -> navigatePrevious());
        btnNext.setOnClickListener(v -> navigateNextOrSubmit());

        // Save selected rating into answersMap on radio button change
        rgLikertOptions.setOnCheckedChangeListener((group, checkedId) -> {
            int rating = getSelectedRatingFromId(checkedId);
            if (rating > 0) {
                answersMap.put(currentQuestionIndex, rating);
            }
        });
    }

    /**
     * Fetches the 15 predefined questions from the Spring Boot backend REST endpoint.
     */
    private void fetchQuestionsFromBackend() {
        showLoading("Fetching assessment questions from server…");

        ApiClient.getService().getQuestions().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<List<QuestionDto>>> call,
                                   Response<ApiResponse<List<QuestionDto>>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    questions = response.body().getData();
                    if (questions.isEmpty()) {
                        showErrorDialog("Server returned an empty question list. Please ensure the database is seeded.");
                    } else {
                        currentQuestionIndex = 0;
                        renderCurrentQuestion();
                    }
                } else {
                    showErrorDialog("Failed to load questions from backend (HTTP " + response.code() + ").");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<QuestionDto>>> call, Throwable t) {
                // If emulator alias 10.0.2.2 fails, automatically try the Mac's host Wi-Fi IP
                if (ApiClient.getBaseUrl().contains("10.0.2.2")) {
                    ApiClient.setBaseUrl("http://10.173.188.75:8080/api/");
                    fetchQuestionsFromBackend();
                    return;
                }
                hideLoading();
                showErrorDialog("Cannot connect to backend server at " + ApiClient.getBaseUrl() +
                        "\n\nPlease ensure Spring Boot is running and reachable.\n\nError: " + t.getMessage());
            }
        });
    }

    /**
     * Renders the current question and restores any previously saved answer.
     */
    private void renderCurrentQuestion() {
        if (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            return;
        }

        QuestionDto question = questions.get(currentQuestionIndex);

        // Update Counter (e.g. Question 4 of 15)
        tvQuestionCounter.setText(getString(R.string.question_counter_format,
                (currentQuestionIndex + 1), questions.size()));

        // Update Dimension Badge
        tvDimensionBadge.setText(question.getFormattedDimensionName());

        // Update Progress Bar
        progressBarAssessment.setMax(questions.size());
        progressBarAssessment.setProgress(currentQuestionIndex + 1);

        // Update Question Text
        tvQuestionPrompt.setText(question.getQuestionText());

        // Clear radio selection temporarily before restoring
        rgLikertOptions.clearCheck();

        // Restore previously selected answer if present
        if (answersMap.containsKey(currentQuestionIndex)) {
            int savedRating = answersMap.get(currentQuestionIndex);
            setRadioButtonByRating(savedRating);
        }

        // Previous button visibility/state
        btnPrevious.setEnabled(currentQuestionIndex > 0);
        btnPrevious.setAlpha(currentQuestionIndex > 0 ? 1.0f : 0.4f);

        // Next button text: "Next" or "Submit Assessment" on the final question
        if (currentQuestionIndex == questions.size() - 1) {
            btnNext.setText(R.string.submit_assessment_btn);
        } else {
            btnNext.setText(R.string.next_btn);
        }
    }

    private void navigatePrevious() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            renderCurrentQuestion();
        }
    }

    private void navigateNextOrSubmit() {
        // Enforce required selection
        if (!answersMap.containsKey(currentQuestionIndex)) {
            Toast.makeText(this, R.string.error_select_option, Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentQuestionIndex < questions.size() - 1) {
            // Move to next question
            currentQuestionIndex++;
            renderCurrentQuestion();
        } else {
            // Last question: submit assessment to backend
            submitAssessment();
        }
    }

    /**
     * Prepares submission request and posts to POST /api/assessment/submit.
     * Prevents duplicate submissions while a request is in progress.
     */
    private void submitAssessment() {
        if (isSubmitting) return;

        // Verify all 15 questions have an answer
        if (answersMap.size() < questions.size()) {
            Toast.makeText(this, "Please answer all questions before submitting.", Toast.LENGTH_LONG).show();
            return;
        }

        isSubmitting = true;
        btnNext.setEnabled(false);
        showLoading("Evaluating scores and generating career recommendations…");

        List<QuestionAnswerDto> answersList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            QuestionDto q = questions.get(i);
            int rating = answersMap.get(i);
            answersList.add(new QuestionAnswerDto(q.getId(), rating));
        }

        AssessmentSubmissionRequest request = new AssessmentSubmissionRequest(
                studentName, educationLevel, answersList
        );

        ApiClient.getService().submitAssessment(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<AssessmentResultResponse>> call,
                                   Response<ApiResponse<AssessmentResultResponse>> response) {
                isSubmitting = false;
                btnNext.setEnabled(true);
                hideLoading();

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    AssessmentResultResponse resultData = response.body().getData();
                    Intent intent = new Intent(AssessmentActivity.this, ResultsActivity.class);
                    intent.putExtra(EXTRA_RESULT_DATA, resultData);
                    startActivity(intent);
                    finish(); // Close assessment activity
                } else {
                    String errorMsg = "Submission error (HTTP " + response.code() + ")";
                    if (response.body() != null && response.body().getMessage() != null) {
                        errorMsg = response.body().getMessage();
                    }
                    showErrorDialog("Evaluation Failed: " + errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AssessmentResultResponse>> call, Throwable t) {
                isSubmitting = false;
                btnNext.setEnabled(true);
                hideLoading();
                showErrorDialog("Network communication failed:\n" + t.getMessage() +
                        "\n\nPlease ensure the backend server is reachable at " + ApiClient.getBaseUrl());
            }
        });
    }

    private int getSelectedRatingFromId(int checkedId) {
        if (checkedId == R.id.rbOption1) return 1;
        if (checkedId == R.id.rbOption2) return 2;
        if (checkedId == R.id.rbOption3) return 3;
        if (checkedId == R.id.rbOption4) return 4;
        if (checkedId == R.id.rbOption5) return 5;
        return 0;
    }

    private void setRadioButtonByRating(int rating) {
        switch (rating) {
            case 1 -> rbOption1.setChecked(true);
            case 2 -> rbOption2.setChecked(true);
            case 3 -> rbOption3.setChecked(true);
            case 4 -> rbOption4.setChecked(true);
            case 5 -> rbOption5.setChecked(true);
        }
    }

    private void showLoading(String message) {
        if (tvLoadingMessage != null) tvLoadingMessage.setText(message);
        if (layoutLoading != null) layoutLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        if (layoutLoading != null) layoutLoading.setVisibility(View.GONE);
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Connection Notice")
                .setMessage(message)
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (questions.isEmpty()) {
                        fetchQuestionsFromBackend();
                    } else if (currentQuestionIndex == questions.size() - 1) {
                        submitAssessment();
                    }
                })
                .setNeutralButton("Change Server IP", (dialog, which) -> showChangeUrlDialog())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void showChangeUrlDialog() {
        android.widget.EditText input = new android.widget.EditText(this);
        input.setText(ApiClient.getBaseUrl());
        input.setSelection(input.getText().length());
        input.setPadding(40, 30, 40, 30);

        new AlertDialog.Builder(this)
                .setTitle("Configure Server URL")
                .setMessage("Current host address:\n- Emulator: http://10.0.2.2:8080/api/\n- Physical Phone: http://10.173.188.75:8080/api/\n\nEdit URL below:")
                .setView(input)
                .setPositiveButton("Save & Connect", (d, w) -> {
                    String url = input.getText().toString().trim();
                    if (!url.isEmpty()) {
                        ApiClient.setBaseUrl(url);
                        fetchQuestionsFromBackend();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
