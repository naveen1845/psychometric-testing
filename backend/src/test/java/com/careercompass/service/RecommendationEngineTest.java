package com.careercompass.service;

import com.careercompass.model.CareerProfile;
import com.careercompass.model.CareerRecommendation;
import com.careercompass.model.Dimension;
import com.careercompass.model.TraitScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationEngineTest {

    private RecommendationEngine recommendationEngine;
    private List<CareerProfile> sampleProfiles;

    @BeforeEach
    void setUp() {
        recommendationEngine = new RecommendationEngine();

        sampleProfiles = new ArrayList<>();
        // 1. Software Developer: Analytical=5, Creativity=4, Social=2, Leadership=2, Practical=5 (Total=18)
        sampleProfiles.add(new CareerProfile(1L, "Software Developer", "Desc", "Java, SQL",
                "Explanation", "Activities", 5, 4, 2, 2, 5));

        // 2. Data Analyst: Analytical=5, Creativity=3, Social=3, Leadership=2, Practical=4 (Total=17)
        sampleProfiles.add(new CareerProfile(2L, "Data Analyst", "Desc", "SQL, Python",
                "Explanation", "Activities", 5, 3, 3, 2, 4));

        // 3. UX/UI Designer: Analytical=3, Creativity=5, Social=4, Leadership=2, Practical=3 (Total=17)
        sampleProfiles.add(new CareerProfile(3L, "UX/UI Designer", "Desc", "Figma",
                "Explanation", "Activities", 3, 5, 4, 2, 3));

        // 4. Teacher or Trainer: Analytical=3, Creativity=3, Social=5, Leadership=4, Practical=2 (Total=17)
        sampleProfiles.add(new CareerProfile(4L, "Teacher or Trainer", "Desc", "Public Speaking",
                "Explanation", "Activities", 3, 3, 5, 4, 2));

        // 5. Project Manager: Analytical=3, Creativity=3, Social=4, Leadership=5, Practical=3 (Total=18)
        sampleProfiles.add(new CareerProfile(5L, "Project Manager", "Desc", "Agile, Jira",
                "Explanation", "Activities", 3, 3, 4, 5, 3));
    }

    @Test
    @DisplayName("Verify ranking: High analytical and practical scores favor Software Developer")
    void testSoftwareDeveloperRanksFirstForTechnicalProfile() {
        List<TraitScore> scores = List.of(
                new TraitScore(Dimension.ANALYTICAL_THINKING, 15, 100),
                new TraitScore(Dimension.CREATIVITY, 12, 75),
                new TraitScore(Dimension.SOCIAL_ORIENTATION, 6, 25),
                new TraitScore(Dimension.LEADERSHIP_AND_INITIATIVE, 6, 25),
                new TraitScore(Dimension.PRACTICAL_HANDS_ON, 15, 100)
        );

        List<CareerRecommendation> recommendations = recommendationEngine.recommendCareers(scores, sampleProfiles);

        assertEquals(3, recommendations.size(), "Must recommend exactly top 3 careers");
        assertEquals("Software Developer", recommendations.get(0).getCareerName(),
                "Software Developer should rank #1 for high analytical & practical traits");
        assertEquals(1, recommendations.get(0).getRankOrder());
        assertTrue(recommendations.get(0).getMatchScore() > recommendations.get(1).getMatchScore(),
                "Rank #1 match score should be greater than or equal to rank #2");
    }

    @Test
    @DisplayName("Verify ranking: High social and leadership scores favor Teacher and Project Manager")
    void testSocialLeadershipProfileRanksHumanOrientedCareers() {
        List<TraitScore> scores = List.of(
                new TraitScore(Dimension.ANALYTICAL_THINKING, 6, 25),
                new TraitScore(Dimension.CREATIVITY, 9, 50),
                new TraitScore(Dimension.SOCIAL_ORIENTATION, 15, 100),
                new TraitScore(Dimension.LEADERSHIP_AND_INITIATIVE, 15, 100),
                new TraitScore(Dimension.PRACTICAL_HANDS_ON, 6, 25)
        );

        List<CareerRecommendation> recommendations = recommendationEngine.recommendCareers(scores, sampleProfiles);

        assertEquals(3, recommendations.size());
        String topCareer = recommendations.get(0).getCareerName();
        assertTrue(topCareer.equals("Teacher or Trainer") || topCareer.equals("Project Manager"),
                "Top recommendation should be human-centric career (Teacher or Project Manager)");
    }

    @Test
    @DisplayName("Verify weighted match score calculation formula")
    void testWeightedMatchScoreCalculation() {
        // Analytical=100, Creativity=50, Social=0, Leadership=0, Practical=100
        // Software Dev weights: 5, 4, 2, 2, 5 (Total=18)
        // Weighted sum = (100*5) + (50*4) + (0*2) + (0*2) + (100*5) = 500 + 200 + 0 + 0 + 500 = 1200
        // Expected average = 1200 / 18 = 66.67 -> rounds to 67
        List<TraitScore> scores = List.of(
                new TraitScore(Dimension.ANALYTICAL_THINKING, 15, 100),
                new TraitScore(Dimension.CREATIVITY, 9, 50),
                new TraitScore(Dimension.SOCIAL_ORIENTATION, 3, 0),
                new TraitScore(Dimension.LEADERSHIP_AND_INITIATIVE, 3, 0),
                new TraitScore(Dimension.PRACTICAL_HANDS_ON, 15, 100)
        );

        CareerProfile softDev = sampleProfiles.get(0);
        int matchScore = recommendationEngine.calculateCareerMatchScore(scores, softDev);

        assertEquals(67, matchScore, "Weighted match score for Software Developer must round to 67%");
    }
}
