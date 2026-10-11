package com.careercompass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.careercompass.R;
import com.careercompass.model.AssessmentResultResponse;
import com.careercompass.model.CareerRecommendationDto;
import com.careercompass.model.TraitScoreDto;

import java.util.List;

/**
 * Screen 4: Results Dashboard
 * Displays:
 * - Student identification details
 * - Five normalized dimension scores (0..100%) with visual progress bars
 * - Top 3 recommended careers with match score percentages
 * - Navigation to Career Details screen for each recommendation
 * - Rule-based algorithm disclaimer
 */
public class ResultsActivity extends AppCompatActivity {

    public static final String EXTRA_CAREER_RECOMMENDATION = "extra_career_recommendation";

    private AssessmentResultResponse resultData;

    private TextView tvStudentGreeting;
    private TextView tvStudentEducation;
    private LinearLayout layoutDimensionScoresContainer;
    private LinearLayout layoutRecommendationsContainer;
    private TextView tvResultsDisclaimer;
    private Button btnRetakeAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultData = (AssessmentResultResponse) getIntent().getSerializableExtra(AssessmentActivity.EXTRA_RESULT_DATA);

        bindViews();
        displayResults();
    }

    private void bindViews() {
        tvStudentGreeting = findViewById(R.id.tvStudentGreeting);
        tvStudentEducation = findViewById(R.id.tvStudentEducation);
        layoutDimensionScoresContainer = findViewById(R.id.layoutDimensionScoresContainer);
        layoutRecommendationsContainer = findViewById(R.id.layoutRecommendationsContainer);
        tvResultsDisclaimer = findViewById(R.id.tvResultsDisclaimer);
        btnRetakeAssessment = findViewById(R.id.btnRetakeAssessment);

        btnRetakeAssessment.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void displayResults() {
        if (resultData == null) {
            tvStudentGreeting.setText("No assessment results available.");
            return;
        }

        // Student Header
        tvStudentGreeting.setText("Prepared for " + resultData.getStudentName());
        String edu = resultData.getEducationLevel();
        if (edu != null && !edu.isEmpty() && !edu.equalsIgnoreCase("Not Specified")) {
            tvStudentEducation.setText("Education: " + edu);
            tvStudentEducation.setVisibility(View.VISIBLE);
        } else {
            tvStudentEducation.setVisibility(View.GONE);
        }

        // Custom Algorithm Notice
        if (resultData.getAlgorithmNotice() != null) {
            tvResultsDisclaimer.setText(resultData.getAlgorithmNotice());
        }

        // Populate Dimension Scores
        populateDimensionScores(resultData.getTraitScores());

        // Populate Career Recommendations
        populateCareerRecommendations(resultData.getRecommendations());
    }

    private void populateDimensionScores(List<TraitScoreDto> scores) {
        layoutDimensionScoresContainer.removeAllViews();
        if (scores == null || scores.isEmpty()) return;

        LayoutInflater inflater = LayoutInflater.from(this);
        for (TraitScoreDto score : scores) {
            View scoreView = inflater.inflate(R.layout.item_trait_score, layoutDimensionScoresContainer, false);

            TextView tvDimension = scoreView.findViewById(R.id.tvTraitDimensionName);
            TextView tvPercent = scoreView.findViewById(R.id.tvTraitScorePercent);
            ProgressBar pbScore = scoreView.findViewById(R.id.pbTraitScore);

            tvDimension.setText(score.getDimensionName());
            tvPercent.setText(score.getNormalizedScore() + "%");
            pbScore.setProgress(score.getNormalizedScore());

            layoutDimensionScoresContainer.addView(scoreView);
        }
    }

    private void populateCareerRecommendations(List<CareerRecommendationDto> recommendations) {
        layoutRecommendationsContainer.removeAllViews();
        if (recommendations == null || recommendations.isEmpty()) return;

        LayoutInflater inflater = LayoutInflater.from(this);
        for (CareerRecommendationDto rec : recommendations) {
            View cardView = inflater.inflate(R.layout.item_career_recommendation, layoutRecommendationsContainer, false);

            TextView tvRank = cardView.findViewById(R.id.tvRankBadge);
            TextView tvMatchScore = cardView.findViewById(R.id.tvMatchScoreBadge);
            TextView tvCareerName = cardView.findViewById(R.id.tvCareerName);
            TextView tvCareerDesc = cardView.findViewById(R.id.tvCareerDescription);
            TextView tvSkills = cardView.findViewById(R.id.tvRelevantSkillsPreview);
            Button btnViewDetails = cardView.findViewById(R.id.btnViewCareerDetails);

            tvRank.setText("#" + rec.getRankOrder() + " Match");
            tvMatchScore.setText(rec.getMatchScore() + "% Match");
            tvCareerName.setText(rec.getCareerName());
            tvCareerDesc.setText(rec.getDescription());

            if (rec.getRelevantSkills() != null && !rec.getRelevantSkills().isEmpty()) {
                tvSkills.setText("Key Skills: " + rec.getRelevantSkills());
                tvSkills.setVisibility(View.VISIBLE);
            } else {
                tvSkills.setVisibility(View.GONE);
            }

            btnViewDetails.setOnClickListener(v -> {
                Intent intent = new Intent(ResultsActivity.this, CareerDetailActivity.class);
                intent.putExtra(EXTRA_CAREER_RECOMMENDATION, rec);
                startActivity(intent);
            });

            layoutRecommendationsContainer.addView(cardView);
        }
    }
}
