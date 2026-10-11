package com.careercompass.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Complete assessment result response DTO returned after submission
 * or when retrieving historical results by ID.
 */
public class AssessmentResultResponse {

    private Long id;
    private String studentName;
    private String educationLevel;
    private LocalDateTime createdAt;
    private List<TraitScoreDto> traitScores;
    private List<CareerRecommendationDto> recommendations;
    private String algorithmNotice;

    public AssessmentResultResponse() {
        this.algorithmNotice = "Match scores and trait evaluations are calculated using Career Compass rule-based educational heuristics, not clinically or scientifically validated psychometric measures.";
    }

    public AssessmentResultResponse(Long id, String studentName, String educationLevel,
                                    LocalDateTime createdAt, List<TraitScoreDto> traitScores,
                                    List<CareerRecommendationDto> recommendations) {
        this();
        this.id = id;
        this.studentName = studentName;
        this.educationLevel = educationLevel;
        this.createdAt = createdAt;
        this.traitScores = traitScores;
        this.recommendations = recommendations;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
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
