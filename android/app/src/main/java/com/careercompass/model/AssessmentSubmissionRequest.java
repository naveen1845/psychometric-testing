package com.careercompass.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class AssessmentSubmissionRequest implements Serializable {

    @SerializedName("studentName")
    private String studentName;

    @SerializedName("educationLevel")
    private String educationLevel;

    @SerializedName("answers")
    private List<QuestionAnswerDto> answers;

    public AssessmentSubmissionRequest() {
    }

    public AssessmentSubmissionRequest(String studentName, String educationLevel, List<QuestionAnswerDto> answers) {
        this.studentName = studentName;
        this.educationLevel = educationLevel;
        this.answers = answers;
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

    public List<QuestionAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswerDto> answers) {
        this.answers = answers;
    }
}
