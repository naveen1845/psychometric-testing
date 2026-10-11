package testhelper;

import com.careercompass.model.*;
import com.careercompass.service.*;
import com.careercompass.strategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Direct Java verification test runner.
 * Tests ScoreCalculator, RecommendationEngine, and OOP Encapsulation/Inheritance/Abstraction/Polymorphism.
 */
public class StandaloneVerificationRunner {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("   CAREER COMPASS - CORE LOGIC & OOP VERIFICATION SUITE       ");
        System.out.println("===============================================================");

        int passed = 0;
        int failed = 0;

        // Test 1: Likert Score Normalization formula
        try {
            LikertScoringStrategy strategy = new LikertScoringStrategy();
            // Test ((12 - 3) / 12) * 100 = 75
            int norm12 = strategy.normalizeScore(12);
            assert norm12 == 75 : "Expected 75, got " + norm12;

            // Boundaries
            assert strategy.normalizeScore(3) == 0 : "Raw 3 should be 0%";
            assert strategy.normalizeScore(15) == 100 : "Raw 15 should be 100%";

            System.out.println("[PASS] Test 1: Likert score normalization formula ((raw-3)/12)*100");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 1: " + t.getMessage());
            failed++;
        }

        // Test 2: Encapsulation in Student
        try {
            boolean caughtNull = false;
            try {
                new Student(null, "MCA");
            } catch (IllegalArgumentException e) {
                caughtNull = true;
            }
            assert caughtNull : "Student should reject null name";

            boolean caughtEmpty = false;
            try {
                new Student("   ", "MCA");
            } catch (IllegalArgumentException e) {
                caughtEmpty = true;
            }
            assert caughtEmpty : "Student should reject blank name";

            Student student = new Student("Naveen", null);
            assert "Not Specified".equals(student.getEducationLevel()) : "Default education level should be set";

            System.out.println("[PASS] Test 2: Encapsulation - Student validation prevents invalid states");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 2: " + t.getMessage());
            failed++;
        }

        // Test 3: Encapsulation in Response
        try {
            boolean caughtLow = false;
            try {
                new Response(1L, 0);
            } catch (IllegalArgumentException e) {
                caughtLow = true;
            }
            assert caughtLow : "Response should reject rating 0";

            boolean caughtHigh = false;
            try {
                new Response(1L, 6);
            } catch (IllegalArgumentException e) {
                caughtHigh = true;
            }
            assert caughtHigh : "Response should reject rating 6";

            Response r = new Response(1L, 5);
            assert r.getRating() == 5;

            System.out.println("[PASS] Test 3: Encapsulation - Response rating validation (strictly 1..5)");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 3: " + t.getMessage());
            failed++;
        }

        // Test 4: Inheritance - Question hierarchy
        try {
            Question likertQ = new LikertQuestion(1L, "Logic test", Dimension.ANALYTICAL_THINKING, 1);
            Question scenarioQ = new ScenarioQuestion(2L, "Team conflict", Dimension.LEADERSHIP_AND_INITIATIVE, 2, "Context", 4);

            assert "LIKERT".equals(likertQ.getQuestionType());
            assert "SCENARIO".equals(scenarioQ.getQuestionType());
            assert likertQ instanceof Question;
            assert scenarioQ instanceof Question;

            System.out.println("[PASS] Test 4: Inheritance - Question abstract class with LikertQuestion & ScenarioQuestion");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 4: " + t.getMessage());
            failed++;
        }

        // Test 5: Abstraction & Polymorphism in ScoreCalculator
        try {
            ScoringStrategy strategy = new LikertScoringStrategy();
            ScoreCalculator calculator = new ScoreCalculator(strategy);

            // Create 15 questions
            List<Question> questions = new ArrayList<>();
            long qId = 1;
            for (Dimension d : Dimension.values()) {
                for (int i = 0; i < 3; i++) {
                    questions.add(new LikertQuestion(qId++, "Q" + qId, d, (int) qId));
                }
            }

            // Create answers: Analytical gets all 5s, others get 3s
            List<Response> responses = new ArrayList<>();
            for (Question q : questions) {
                if (q.getDimension() == Dimension.ANALYTICAL_THINKING) {
                    responses.add(new Response(q.getId(), 5));
                } else {
                    responses.add(new Response(q.getId(), 3));
                }
            }

            List<TraitScore> scores = calculator.computeTraitScores(responses, questions);
            assert scores.size() == 5 : "Should have 5 trait scores";

            TraitScore analytical = scores.stream().filter(s -> s.getDimension() == Dimension.ANALYTICAL_THINKING).findFirst().get();
            assert analytical.getRawScore() == 15 : "Analytical raw should be 15";
            assert analytical.getNormalizedScore() == 100 : "Analytical normalized should be 100";

            TraitScore creative = scores.stream().filter(s -> s.getDimension() == Dimension.CREATIVITY).findFirst().get();
            assert creative.getRawScore() == 9 : "Creative raw should be 9";
            assert creative.getNormalizedScore() == 50 : "Creative normalized ((9-3)/12)*100 = 50";

            System.out.println("[PASS] Test 5: Abstraction & Polymorphism - ScoringStrategy interface dispatched via ScoreCalculator");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 5: " + t.getMessage());
            failed++;
        }

        // Test 6: Rule-based recommendation engine ranking
        try {
            RecommendationEngine engine = new RecommendationEngine();

            List<TraitScore> scores = List.of(
                    new TraitScore(Dimension.ANALYTICAL_THINKING, 15, 100),
                    new TraitScore(Dimension.CREATIVITY, 11, 67),
                    new TraitScore(Dimension.SOCIAL_ORIENTATION, 8, 42),
                    new TraitScore(Dimension.LEADERSHIP_AND_INITIATIVE, 8, 42),
                    new TraitScore(Dimension.PRACTICAL_HANDS_ON, 15, 100)
            );

            List<CareerProfile> profiles = List.of(
                    new CareerProfile(1L, "Software Developer", "Desc", "Java, SQL", "Expl", "Act", 5, 4, 2, 2, 5),
                    new CareerProfile(2L, "Data Analyst", "Desc", "SQL, Python", "Expl", "Act", 5, 3, 3, 2, 4),
                    new CareerProfile(3L, "UX/UI Designer", "Desc", "Figma", "Expl", "Act", 3, 5, 4, 2, 3),
                    new CareerProfile(4L, "Teacher or Trainer", "Desc", "Public Speaking", "Expl", "Act", 3, 3, 5, 4, 2),
                    new CareerProfile(5L, "Project Manager", "Desc", "Agile, Jira", "Expl", "Act", 3, 3, 4, 5, 3)
            );

            List<CareerRecommendation> recs = engine.recommendCareers(scores, profiles);
            assert recs.size() == 3 : "Must recommend top 3 careers";
            assert "Software Developer".equals(recs.get(0).getCareerName()) : "Software Developer should rank #1";
            assert recs.get(0).getRankOrder() == 1 : "First rank must be 1";
            assert recs.get(1).getRankOrder() == 2 : "Second rank must be 2";
            assert recs.get(2).getRankOrder() == 3 : "Third rank must be 3";
            assert recs.get(0).getMatchScore() >= recs.get(1).getMatchScore() : "Rank 1 score >= Rank 2 score";

            System.out.println("[PASS] Test 6: Recommendation Engine - Weighted ranking correctly ranks Software Developer #1");
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Test 6: " + t.getMessage());
            failed++;
        }

        System.out.println("===============================================================");
        System.out.println("SUMMARY: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("===============================================================");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
