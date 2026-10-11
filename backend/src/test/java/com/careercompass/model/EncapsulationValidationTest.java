package com.careercompass.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncapsulationValidationTest {

    @Test
    @DisplayName("OOP Encapsulation: Student rejects blank or null names")
    void testStudentValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Student(null, "MCA"),
                "Student constructor should reject null name");
        assertThrows(IllegalArgumentException.class, () -> new Student("   ", "MCA"),
                "Student constructor should reject whitespace-only name");

        Student validStudent = new Student("Alice Johnson", null);
        assertEquals("Alice Johnson", validStudent.getName());
        assertEquals("Not Specified", validStudent.getEducationLevel(),
                "Null education level should be safely assigned a default value");
    }

    @Test
    @DisplayName("OOP Encapsulation: Response rejects rating out of 1..5 range")
    void testResponseRatingValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Response(1L, -1));
        assertThrows(IllegalArgumentException.class, () -> new Response(1L, 0));
        assertThrows(IllegalArgumentException.class, () -> new Response(1L, 6));

        Response validResponse = new Response(1L, 4);
        assertEquals(4, validResponse.getRating());
    }

    @Test
    @DisplayName("OOP Encapsulation: TraitScore rejects out-of-bound values")
    void testTraitScoreValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                new TraitScore(Dimension.ANALYTICAL_THINKING, 2, 0),
                "Raw score below 3 must be rejected");
        assertThrows(IllegalArgumentException.class, () ->
                new TraitScore(Dimension.ANALYTICAL_THINKING, 16, 100),
                "Raw score above 15 must be rejected");
        assertThrows(IllegalArgumentException.class, () ->
                new TraitScore(Dimension.ANALYTICAL_THINKING, 12, 105),
                "Normalized score above 100 must be rejected");
    }

    @Test
    @DisplayName("OOP Inheritance: LikertQuestion and ScenarioQuestion exhibit polymorphic type identity")
    void testQuestionInheritance() {
        Question likert = new LikertQuestion(1L, "Logic question", Dimension.ANALYTICAL_THINKING, 1);
        Question scenario = new ScenarioQuestion(2L, "Team conflict", Dimension.LEADERSHIP_AND_INITIATIVE, 2, "Context", 4);

        assertEquals("LIKERT", likert.getQuestionType());
        assertEquals("SCENARIO", scenario.getQuestionType());
        assertTrue(likert instanceof Question);
        assertTrue(scenario instanceof Question);
    }
}
