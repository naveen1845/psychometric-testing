package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Demonstrates the OOP concept of INHERITANCE:
 * Concrete subclass of Question representing 5-point Likert scale questions.
 * Inherits id, questionText, dimension, and displayOrder from Question,
 * and adds scale-specific attributes.
 */
@Entity
@DiscriminatorValue("LIKERT")
public class LikertQuestion extends Question {

    @Column(name = "scale_min")
    private int scaleMin = 1;

    @Column(name = "scale_max")
    private int scaleMax = 5;

    @Column(name = "min_label", length = 50)
    private String minLabel = "Strongly Disagree";

    @Column(name = "max_label", length = 50)
    private String maxLabel = "Strongly Agree";

    public LikertQuestion() {
        super();
    }

    public LikertQuestion(Long id, String questionText, Dimension dimension, int displayOrder) {
        super(id, questionText, dimension, displayOrder);
        this.scaleMin = 1;
        this.scaleMax = 5;
        this.minLabel = "Strongly Disagree";
        this.maxLabel = "Strongly Agree";
    }

    public LikertQuestion(Long id, String questionText, Dimension dimension, int displayOrder,
                          int scaleMin, int scaleMax, String minLabel, String maxLabel) {
        super(id, questionText, dimension, displayOrder);
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
        this.minLabel = minLabel;
        this.maxLabel = maxLabel;
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

    @Override
    public String getQuestionType() {
        return "LIKERT";
    }

    @Override
    public String toString() {
        return "LikertQuestion{" +
                "id=" + getId() +
                ", text='" + getQuestionText() + '\'' +
                ", dimension=" + getDimension() +
                ", order=" + getDisplayOrder() +
                ", scale=" + scaleMin + ".." + scaleMax +
                '}';
    }
}
