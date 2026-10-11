package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Predefined Career Profile entity stored in the database.
 * Holds career overview, relevant technical/interpersonal skills,
 * rule-based matching criteria, learning suggestions, and weights
 * for the five dimensions.
 */
@Entity
@Table(name = "career_profiles")
public class CareerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "career_name", nullable = false, unique = true, length = 100)
    private String careerName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "relevant_skills", nullable = false, length = 1000)
    private String relevantSkills;

    @Column(name = "matching_explanation", nullable = false, length = 1000)
    private String matchingExplanation;

    @Column(name = "learning_activities", nullable = false, length = 1000)
    private String learningActivities;

    // Dimension weights (1 to 5)
    @Column(name = "weight_analytical", nullable = false)
    private int weightAnalytical;

    @Column(name = "weight_creativity", nullable = false)
    private int weightCreativity;

    @Column(name = "weight_social", nullable = false)
    private int weightSocial;

    @Column(name = "weight_leadership", nullable = false)
    private int weightLeadership;

    @Column(name = "weight_practical", nullable = false)
    private int weightPractical;

    public CareerProfile() {
    }

    public CareerProfile(Long id, String careerName, String description, String relevantSkills,
                         String matchingExplanation, String learningActivities,
                         int weightAnalytical, int weightCreativity, int weightSocial,
                         int weightLeadership, int weightPractical) {
        this.id = id;
        this.careerName = careerName;
        this.description = description;
        this.relevantSkills = relevantSkills;
        this.matchingExplanation = matchingExplanation;
        this.learningActivities = learningActivities;
        this.weightAnalytical = weightAnalytical;
        this.weightCreativity = weightCreativity;
        this.weightSocial = weightSocial;
        this.weightLeadership = weightLeadership;
        this.weightPractical = weightPractical;
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

    public int getWeightAnalytical() {
        return weightAnalytical;
    }

    public void setWeightAnalytical(int weightAnalytical) {
        this.weightAnalytical = weightAnalytical;
    }

    public int getWeightCreativity() {
        return weightCreativity;
    }

    public void setWeightCreativity(int weightCreativity) {
        this.weightCreativity = weightCreativity;
    }

    public int getWeightSocial() {
        return weightSocial;
    }

    public void setWeightSocial(int weightSocial) {
        this.weightSocial = weightSocial;
    }

    public int getWeightLeadership() {
        return weightLeadership;
    }

    public void setWeightLeadership(int weightLeadership) {
        this.weightLeadership = weightLeadership;
    }

    public int getWeightPractical() {
        return weightPractical;
    }

    public void setWeightPractical(int weightPractical) {
        this.weightPractical = weightPractical;
    }

    /**
     * Retrieves the weight assigned to a specific dimension.
     */
    public int getWeightForDimension(Dimension dimension) {
        if (dimension == null) return 0;
        return switch (dimension) {
            case ANALYTICAL_THINKING -> weightAnalytical;
            case CREATIVITY -> weightCreativity;
            case SOCIAL_ORIENTATION -> weightSocial;
            case LEADERSHIP_AND_INITIATIVE -> weightLeadership;
            case PRACTICAL_HANDS_ON -> weightPractical;
        };
    }

    /**
     * Calculates the sum of all five dimension weights for this profile.
     */
    public int getTotalWeight() {
        return weightAnalytical + weightCreativity + weightSocial + weightLeadership + weightPractical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareerProfile that = (CareerProfile) o;
        return Objects.equals(id, that.id) || Objects.equals(careerName, that.careerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, careerName);
    }

    @Override
    public String toString() {
        return "CareerProfile{" +
                "id=" + id +
                ", careerName='" + careerName + '\'' +
                ", totalWeight=" + getTotalWeight() +
                '}';
    }
}
