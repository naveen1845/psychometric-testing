package com.careercompass.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class AssessmentResultResponse implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("studentName")
    private String studentName;

    @SerializedName("educationLevel")
    private String educationLevel;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("traitScores")
    private List<TraitScoreDto> traitScores;

    @SerializedName("recommendations")
    private List<CareerRecommendationDto> recommendations;

    @SerializedName("algorithmNotice")
    private String algorithmNotice;

    public AssessmentResultResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<TraitScoreDto> getTraitScores() {
        return traitScores;
    }

    public void setTraitScores(List<TraitScoreDto> traitScores) {
        this.traitScores = traitScores;
    }

    public List<CareerRecommendationDto> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<CareerRecommendationDto> recommendations) {
        this.recommendations = recommendations;
    }

    public String getAlgorithmNotice() {
        return algorithmNotice;
    }

    public void setAlgorithmNotice(String algorithmNotice) {
        this.algorithmNotice = algorithmNotice;
    }
}
