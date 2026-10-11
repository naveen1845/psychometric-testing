package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Demonstrates the OOP concept of ENCAPSULATION:
 * - Encapsulates an individual question response.
 * - Private fields prevent arbitrary direct mutation.
 * - Enforces validation constraints: rating must be strictly between 1 and 5 (Likert scale).
 */
@Embeddable
public class Response {

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "rating", nullable = false)
    private int rating;

    public Response() {
    }

    public Response(Long questionId, int rating) {
        setQuestionId(questionId);
        setRating(rating);
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new IllegalArgumentException("Question ID must be a positive non-null number.");
        }
        this.questionId = questionId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 (inclusive). Received: " + rating);
        }
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return rating == response.rating && Objects.equals(questionId, response.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, rating);
    }

    @Override
    public String toString() {
        return "Response{" +
                "questionId=" + questionId +
                ", rating=" + rating +
                '}';
    }
}
