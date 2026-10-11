package com.careercompass.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Android DTO for questions received from GET /api/assessment/questions.
 */
public class QuestionDto implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("questionText")
    private String questionText;

    @SerializedName("dimension")
    private String dimension;

    @SerializedName("displayOrder")
    private int displayOrder;

    @SerializedName("scaleMin")
    private int scaleMin = 1;

    @SerializedName("scaleMax")
    private int scaleMax = 5;

    @SerializedName("minLabel")
    private String minLabel;

    @SerializedName("maxLabel")
    private String maxLabel;

    public QuestionDto() {
    }

    public QuestionDto(Long id, String questionText, String dimension, int displayOrder) {
        this.id = id;
        this.questionText = questionText;
        this.dimension = dimension;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getScaleMin() {
        return scaleMin;
    }

    public void setScaleMin(int scaleMin) {
        this.scaleMin = scaleMin;
    }

    public int getScaleMax() {
        return scaleMax;
    }

    public void setScaleMax(int scaleMax) {
        this.scaleMax = scaleMax;
    }

    public String getMinLabel() {
        return minLabel;
    }

    public void setMinLabel(String minLabel) {
        this.minLabel = minLabel;
    }

    public String getMaxLabel() {
        return maxLabel;
    }

    public void setMaxLabel(String maxLabel) {
        this.maxLabel = maxLabel;
    }

    /**
     * Helper to get a clean, human-readable dimension name for UI badges.
     */
    public String getFormattedDimensionName() {
        if (dimension == null) return "Assessment";
        return switch (dimension) {
            case "ANALYTICAL_THINKING" -> "Analytical Thinking";
            case "CREATIVITY" -> "Creativity";
            case "SOCIAL_ORIENTATION" -> "Social Orientation";
            case "LEADERSHIP_AND_INITIATIVE" -> "Leadership & Initiative";
            case "PRACTICAL_HANDS_ON" -> "Practical & Hands-on";
            default -> dimension;
        };
    }
}
