package com.careercompass.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Individual question answer DTO.
 */
public class QuestionAnswerDto {

    @NotNull(message = "Question ID is required.")
    private Long questionId;

    @Min(value = 1, message = "Rating must be at least 1 (Strongly Disagree).")
    @Max(value = 5, message = "Rating must be at most 5 (Strongly Agree).")
    private int rating;

    public QuestionAnswerDto() {
    }

    public QuestionAnswerDto(Long questionId, int rating) {
        this.questionId = questionId;
        this.rating = rating;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
