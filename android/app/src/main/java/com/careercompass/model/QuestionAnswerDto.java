package com.careercompass.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class QuestionAnswerDto implements Serializable {

    @SerializedName("questionId")
    private Long questionId;

    @SerializedName("rating")
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
