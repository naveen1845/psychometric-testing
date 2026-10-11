package com.careercompass.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Request payload for submitting an assessment.
 */
public class AssessmentSubmissionRequest {

    @NotBlank(message = "Student name is required and cannot be blank.")
    @Size(max = 100, message = "Student name cannot exceed 100 characters.")
    private String studentName;

    @Size(max = 100, message = "Education level cannot exceed 100 characters.")
    private String educationLevel;

    @NotEmpty(message = "Answers list cannot be empty.")
    @Size(min = 15, max = 15, message = "All 15 assessment questions must be answered.")
    @Valid
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
