package com.careercompass.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Demonstrates the OOP concept of INHERITANCE:
 * - Serves as an abstract base class containing common question attributes
 *   (id, questionText, dimension, displayOrder).
 * - Extended by concrete subclasses: LikertQuestion and ScenarioQuestion.
 * - Single-table inheritance strategy is used for clean relational persistence.
 */
@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "questionType",
        defaultImpl = LikertQuestion.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LikertQuestion.class, name = "LIKERT"),
        @JsonSubTypes.Type(value = ScenarioQuestion.class, name = "SCENARIO")
})
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Dimension dimension;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    /**
     * Default constructor for JPA.
     */
    protected Question() {
    }

    /**
     * Protected constructor for subclasses.
     */
    protected Question(Long id, String questionText, Dimension dimension, int displayOrder) {
        this.id = id;
        setQuestionText(questionText);
        setDimension(dimension);
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty.");
        }
        this.questionText = questionText.trim();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        if (dimension == null) {
            throw new IllegalArgumentException("Question dimension cannot be null.");
        }
        this.dimension = dimension;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Abstract method demonstrating polymorphic behavior across Question subclasses.
     *
     * @return the string descriptor of the question type
     */
    public abstract String getQuestionType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
