package com.careercompass.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.careercompass.R;
import com.careercompass.model.CareerRecommendationDto;

/**
 * Screen 5: Career Details
 * Displays in-depth information about a selected recommended career:
 * - Detailed description
 * - Key industry skills
 * - Predefined rule-based matching rationale
 * - Suggested beginner learning activities
 */
public class CareerDetailActivity extends AppCompatActivity {

    private TextView tvDetailCareerName;
    private TextView tvDetailMatchScore;
    private TextView tvDetailRankOrder;
    private TextView tvDetailDescription;
    private TextView tvDetailMatchingRationale;
    private TextView tvDetailRelevantSkills;
    private TextView tvDetailLearningActivities;
    private Button btnBackToResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_detail);

        bindViews();

        CareerRecommendationDto rec = (CareerRecommendationDto) getIntent()
                .getSerializableExtra(ResultsActivity.EXTRA_CAREER_RECOMMENDATION);

        if (rec != null) {
            displayCareerDetails(rec);
        }

        btnBackToResults.setOnClickListener(v -> finish());
    }

    private void bindViews() {
        tvDetailCareerName = findViewById(R.id.tvDetailCareerName);
        tvDetailMatchScore = findViewById(R.id.tvDetailMatchScore);
        tvDetailRankOrder = findViewById(R.id.tvDetailRankOrder);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailMatchingRationale = findViewById(R.id.tvDetailMatchingRationale);
        tvDetailRelevantSkills = findViewById(R.id.tvDetailRelevantSkills);
        tvDetailLearningActivities = findViewById(R.id.tvDetailLearningActivities);
        btnBackToResults = findViewById(R.id.btnBackToResults);
    }

    private void displayCareerDetails(CareerRecommendationDto rec) {
        tvDetailCareerName.setText(rec.getCareerName());
        tvDetailMatchScore.setText(rec.getMatchScore() + "% Match");
        tvDetailRankOrder.setText("Rank #" + rec.getRankOrder() + " Career Recommendation");
        tvDetailDescription.setText(rec.getDescription());
        tvDetailMatchingRationale.setText(rec.getMatchingExplanation());
        tvDetailRelevantSkills.setText(rec.getRelevantSkills());
        tvDetailLearningActivities.setText(rec.getLearningActivities());
    }
}
