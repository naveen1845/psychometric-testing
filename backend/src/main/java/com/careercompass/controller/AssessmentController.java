package com.careercompass.controller;

import com.careercompass.dto.ApiResponse;
import com.careercompass.dto.AssessmentResultResponse;
import com.careercompass.dto.AssessmentSubmissionRequest;
import com.careercompass.dto.CareerProfileDto;
import com.careercompass.model.Question;
import com.careercompass.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API Controller for Career Compass.
 *
 * Exposes:
 * 1. GET  /api/assessment/questions    - Fetches the 15 predefined assessment questions.
 * 2. POST /api/assessment/submit       - Submits answers, evaluates scores, ranks careers, and saves result.
 * 3. GET  /api/careers                 - Fetches predefined career profiles and dimension weights.
 * 4. GET  /api/assessment/results/{id} - Fetches a saved assessment result by ID.
 */
@RestController
@RequestMapping("/api")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * Endpoint 1: GET /api/assessment/questions
     * Returns all 15 predefined questions across the 5 assessment dimensions.
     */
    @GetMapping("/assessment/questions")
    public ResponseEntity<ApiResponse<List<Question>>> getQuestions() {
        List<Question> questions = assessmentService.getAllQuestions();
        return ResponseEntity.ok(ApiResponse.success("Assessment questions retrieved successfully.", questions));
    }

    /**
     * Endpoint 2: POST /api/assessment/submit
     * Accepts student details and 15 answers; computes trait scores, ranks careers,
     * persists record to MySQL database, and returns calculated results.
     */
    @PostMapping("/assessment/submit")
    public ResponseEntity<ApiResponse<AssessmentResultResponse>> submitAssessment(
            @Valid @RequestBody AssessmentSubmissionRequest request) {
        AssessmentResultResponse response = assessmentService.submitAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Assessment evaluated and recorded successfully.", response));
    }

    /**
     * Endpoint 3: GET /api/careers
     * Returns all predefined career profiles, skills, matching logic, and dimension weights.
     */
    @GetMapping("/careers")
    public ResponseEntity<ApiResponse<List<CareerProfileDto>>> getCareers() {
        List<CareerProfileDto> careers = assessmentService.getAllCareerProfiles();
        return ResponseEntity.ok(ApiResponse.success("Career profiles retrieved successfully.", careers));
    }

    /**
     * Endpoint 4: GET /api/assessment/results/{id}
     * Retrieves a previously saved assessment evaluation by its primary key ID.
     */
    @GetMapping("/assessment/results/{id}")
    public ResponseEntity<ApiResponse<AssessmentResultResponse>> getAssessmentResult(@PathVariable Long id) {
        AssessmentResultResponse response = assessmentService.getAssessmentResultById(id);
        return ResponseEntity.ok(ApiResponse.success("Assessment result retrieved successfully.", response));
    }
}
