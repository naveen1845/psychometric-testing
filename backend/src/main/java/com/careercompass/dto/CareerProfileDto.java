package com.careercompass.dto;

import java.util.Map;

/**
 * DTO representing a career profile returned by GET /api/careers.
 */
public class CareerProfileDto {

    private Long id;
    private String careerName;
    private String description;
    private String relevantSkills;
    private String matchingExplanation;
    private String learningActivities;
    private Map<String, Integer> dimensionWeights;

    public CareerProfileDto() {
    }

    public CareerProfileDto(Long id, String careerName, String description, String relevantSkills,
                            String matchingExplanation, String learningActivities,
                            Map<String, Integer> dimensionWeights) {
        this.id = id;
        this.careerName = careerName;
        this.description = description;
        this.relevantSkills = relevantSkills;
        this.matchingExplanation = matchingExplanation;
        this.learningActivities = learningActivities;
        this.dimensionWeights = dimensionWeights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
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

    public Map<String, Integer> getDimensionWeights() {
        return dimensionWeights;
    }

    public void setDimensionWeights(Map<String, Integer> dimensionWeights) {
        this.dimensionWeights = dimensionWeights;
    }
}
