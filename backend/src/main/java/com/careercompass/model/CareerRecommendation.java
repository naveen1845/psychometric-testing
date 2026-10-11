package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Represents a ranked career recommendation generated for a student's assessment.
 * Stored as an embeddable element within AssessmentResult.
 */
@Embeddable
public class CareerRecommendation {

    @Column(name = "career_id")
    private Long careerId;

    @Column(name = "career_name", nullable = false)
    private String careerName;

    @Column(name = "match_score", nullable = false)
    private int matchScore;

    @Column(name = "rank_order", nullable = false)
    private int rankOrder;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "relevant_skills", length = 1000)
    private String relevantSkills;

    @Column(name = "matching_explanation", length = 1000)
    private String matchingExplanation;

    @Column(name = "learning_activities", length = 1000)
    private String learningActivities;

    public CareerRecommendation() {
    }

    public CareerRecommendation(Long careerId, String careerName, int matchScore, int rankOrder,
                                String description, String relevantSkills,
                                String matchingExplanation, String learningActivities) {
        this.careerId = careerId;
        this.careerName = careerName;
        this.matchScore = matchScore;
        this.rankOrder = rankOrder;
        this.description = description;
        this.relevantSkills = relevantSkills;
        this.matchingExplanation = matchingExplanation;
        this.learningActivities = learningActivities;
    }

    public Long getCareerId() {
        return careerId;
    }

    public void setCareerId(Long careerId) {
        this.careerId = careerId;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public int getRankOrder() {
        return rankOrder;
    }

    public void setRankOrder(int rankOrder) {
        this.rankOrder = rankOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelevantSkills() {
        return relevantSkills;
    }

    public void setRelevantSkills(String relevantSkills) {
        this.relevantSkills = relevantSkills;
    }

    public String getMatchingExplanation() {
        return matchingExplanation;
    }

    public void setMatchingExplanation(String matchingExplanation) {
        this.matchingExplanation = matchingExplanation;
    }

    public String getLearningActivities() {
        return learningActivities;
    }

    public void setLearningActivities(String learningActivities) {
        this.learningActivities = learningActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareerRecommendation that = (CareerRecommendation) o;
        return matchScore == that.matchScore &&
               rankOrder == that.rankOrder &&
               Objects.equals(careerName, that.careerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(careerName, matchScore, rankOrder);
    }

    @Override
    public String toString() {
        return "CareerRecommendation{" +
                "rank=" + rankOrder +
                ", career='" + careerName + '\'' +
                ", score=" + matchScore + "%" +
                '}';
    }
}
