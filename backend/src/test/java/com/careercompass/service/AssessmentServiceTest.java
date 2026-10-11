package com.careercompass.service;

import com.careercompass.dto.AssessmentResultResponse;
import com.careercompass.dto.AssessmentSubmissionRequest;
import com.careercompass.dto.QuestionAnswerDto;
import com.careercompass.model.AssessmentResult;
import com.careercompass.model.CareerProfile;
import com.careercompass.model.Dimension;
import com.careercompass.model.LikertQuestion;
import com.careercompass.model.Question;
import com.careercompass.repository.AssessmentResultRepository;
import com.careercompass.repository.CareerProfileRepository;
import com.careercompass.repository.QuestionRepository;
import com.careercompass.strategy.LikertScoringStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AssessmentServiceTest {

    private AssessmentService assessmentService;
    private QuestionRepository questionRepository;
    private CareerProfileRepository careerProfileRepository;
    private AssessmentResultRepository assessmentResultRepository;

    private List<Question> dummyQuestions;
    private List<CareerProfile> dummyProfiles;

    @BeforeEach
    void setUp() {
        questionRepository = Mockito.mock(QuestionRepository.class);
        careerProfileRepository = Mockito.mock(CareerProfileRepository.class);
        assessmentResultRepository = Mockito.mock(AssessmentResultRepository.class);

        LikertScoringStrategy likertStrategy = new LikertScoringStrategy();
        ScoreCalculator scoreCalculator = new ScoreCalculator(likertStrategy);
        RecommendationEngine recommendationEngine = new RecommendationEngine();

        assessmentService = new AssessmentService(
                questionRepository,
                careerProfileRepository,
                assessmentResultRepository,
                scoreCalculator,
                recommendationEngine
        );

        dummyQuestions = new ArrayList<>();
        long qId = 1;
        for (Dimension d : Dimension.values()) {
            for (int i = 1; i <= 3; i++) {
                dummyQuestions.add(new LikertQuestion(qId, "Dummy Question " + qId, d, (int) qId));
                qId++;
            }
        }

        dummyProfiles = List.of(
                new CareerProfile(1L, "Software Developer", "Desc", "Java", "Expl", "Act", 5, 4, 2, 2, 5),
                new CareerProfile(2L, "Data Analyst", "Desc", "SQL", "Expl", "Act", 5, 3, 3, 2, 4),
                new CareerProfile(3L, "UX/UI Designer", "Desc", "Figma", "Expl", "Act", 3, 5, 4, 2, 3),
                new CareerProfile(4L, "Teacher or Trainer", "Desc", "Teaching", "Expl", "Act", 3, 3, 5, 4, 2),
                new CareerProfile(5L, "Project Manager", "Desc", "Agile", "Expl", "Act", 3, 3, 4, 5, 3)
        );

        when(questionRepository.findAllByOrderByDisplayOrderAsc()).thenReturn(dummyQuestions);
        when(careerProfileRepository.findAll()).thenReturn(dummyProfiles);
    }

    @Test
    @DisplayName("Verify successful assessment submission and evaluation")
    void testSuccessfulAssessmentSubmission() {
        List<QuestionAnswerDto> answers = new ArrayList<>();
        for (Question q : dummyQuestions) {
            answers.add(new QuestionAnswerDto(q.getId(), 4));
        }

        AssessmentSubmissionRequest request = new AssessmentSubmissionRequest(
                "Jane Doe", "MCA 1st Year", answers
        );

        when(assessmentResultRepository.save(any(AssessmentResult.class))).thenAnswer(invocation -> {
            AssessmentResult res = invocation.getArgument(0);
            res.setId(100L);
            return res;
        });

        AssessmentResultResponse response = assessmentService.submitAssessment(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Jane Doe", response.getStudentName());
        assertEquals("MCA 1st Year", response.getEducationLevel());
        assertEquals(5, response.getTraitScores().size(), "Must calculate scores for all 5 dimensions");
        assertEquals(3, response.getRecommendations().size(), "Must produce top 3 career recommendations");
        assertEquals(1, response.getRecommendations().get(0).getRankOrder());
    }

    @Test
    @DisplayName("Verify rejection when required question count is incomplete")
    void testSubmissionFailsWithIncompleteAnswers() {
        List<QuestionAnswerDto> answers = new ArrayList<>();
        // Only 10 answers instead of 15
        for (int i = 0; i < 10; i++) {
            answers.add(new QuestionAnswerDto(dummyQuestions.get(i).getId(), 4));
        }

        AssessmentSubmissionRequest request = new AssessmentSubmissionRequest(
                "Jane Doe", "MCA", answers
        );

        assertThrows(IllegalArgumentException.class, () -> assessmentService.submitAssessment(request));
    }
}
