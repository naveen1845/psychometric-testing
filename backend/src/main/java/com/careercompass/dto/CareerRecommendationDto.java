package com.careercompass.dto;

/**
 * DTO representing a top recommended career with match percentage,
 * description, skills, match rationale, and beginner activities.
 */
public class CareerRecommendationDto {

    private Long careerId;
    private String careerName;
    private int matchScore;
    private int rankOrder;
    private String description;
    private String relevantSkills;
    private String matchingExplanation;
    private String learningActivities;

    public CareerRecommendationDto() {
    }

    public CareerRecommendationDto(Long careerId, String careerName, int matchScore, int rankOrder,
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
}
