package com.careercompass.dto;

import com.careercompass.model.Dimension;

/**
 * TraitScore DTO returned in assessment results.
 */
public class TraitScoreDto {

    private Dimension dimension;
    private String dimensionName;
    private String dimensionDescription;
    private int rawScore;
    private int normalizedScore;

    public TraitScoreDto() {
    }

    public TraitScoreDto(Dimension dimension, String dimensionName, String dimensionDescription,
                         int rawScore, int normalizedScore) {
        this.dimension = dimension;
        this.dimensionName = dimensionName;
        this.dimensionDescription = dimensionDescription;
        this.rawScore = rawScore;
        this.normalizedScore = normalizedScore;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionDescription() {
        return dimensionDescription;
    }

    public void setDimensionDescription(String dimensionDescription) {
        this.dimensionDescription = dimensionDescription;
    }

    public int getRawScore() {
        return rawScore;
    }

    public void setRawScore(int rawScore) {
        this.rawScore = rawScore;
    }

    public int getNormalizedScore() {
        return normalizedScore;
    }

    public void setNormalizedScore(int normalizedScore) {
        this.normalizedScore = normalizedScore;
    }
}
