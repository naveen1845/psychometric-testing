package com.careercompass.network;

import com.careercompass.model.ApiResponse;
import com.careercompass.model.AssessmentResultResponse;
import com.careercompass.model.AssessmentSubmissionRequest;
import com.careercompass.model.QuestionDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * Retrofit REST API interface for Career Compass backend endpoints.
 */
public interface ApiService {

    /**
     * Fetches the 15 predefined assessment questions from the backend.
     */
    @GET("assessment/questions")
    Call<ApiResponse<List<QuestionDto>>> getQuestions();

    /**
     * Submits student profile and 15 answers for scoring, ranking, and storage.
     */
    @POST("assessment/submit")
    Call<ApiResponse<AssessmentResultResponse>> submitAssessment(
            @Body AssessmentSubmissionRequest request
    );

    /**
     * Retrieves a saved assessment result by ID.
     */
    @GET("assessment/results/{id}")
    Call<ApiResponse<AssessmentResultResponse>> getAssessmentResult(
            @Path("id") Long id
    );
}
