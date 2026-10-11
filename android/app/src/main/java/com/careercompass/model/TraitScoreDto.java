package com.careercompass.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TraitScoreDto implements Serializable {

    @SerializedName("dimension")
    private String dimension;

    @SerializedName("dimensionName")
    private String dimensionName;

    @SerializedName("dimensionDescription")
    private String dimensionDescription;

    @SerializedName("rawScore")
    private int rawScore;

    @SerializedName("normalizedScore")
    private int normalizedScore;

    public TraitScoreDto() {
    }

    public TraitScoreDto(String dimension, String dimensionName, String dimensionDescription,
                         int rawScore, int normalizedScore) {
        this.dimension = dimension;
        this.dimensionName = dimensionName;
        this.dimensionDescription = dimensionDescription;
        this.rawScore = rawScore;
        this.normalizedScore = normalizedScore;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
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
