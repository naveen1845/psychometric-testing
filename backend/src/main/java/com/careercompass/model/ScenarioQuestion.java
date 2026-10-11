package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Demonstrates the OOP concept of INHERITANCE and EXTENSIBILITY:
 * Represents situational scenario-based questions that present a realistic workplace
 * dilemma. Demonstrates that new question modalities can be integrated smoothly
 * without altering existing core assessment interfaces.
 */
@Entity
@DiscriminatorValue("SCENARIO")
public class ScenarioQuestion extends Question {

    @Column(name = "scenario_context", length = 1000)
    private String scenarioContext;

    @Column(name = "option_count")
    private int optionCount;

    public ScenarioQuestion() {
        super();
    }

    public ScenarioQuestion(Long id, String questionText, Dimension dimension, int displayOrder,
                            String scenarioContext, int optionCount) {
        super(id, questionText, dimension, displayOrder);
        this.scenarioContext = scenarioContext;
        this.optionCount = optionCount;
    }

    public String getScenarioContext() {
        return scenarioContext;
    }

    public void setScenarioContext(String scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public int getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount = optionCount;
    }

    @Override
    public String getQuestionType() {
        return "SCENARIO";
    }

    @Override
    public String toString() {
        return "ScenarioQuestion{" +
                "id=" + getId() +
                ", text='" + getQuestionText() + '\'' +
                ", dimension=" + getDimension() +
                ", scenarioContext='" + scenarioContext + '\'' +
                '}';
    }
}
