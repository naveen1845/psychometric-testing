package com.careercompass.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;

/**
 * Demonstrates the OOP concept of ENCAPSULATION:
 * - Represents the calculated score for one of the five career dimensions.
 * - Enforces score domain integrity (raw score 3..15, normalized score 0..100).
 * - Restricts modification to verified setters.
 */
@Embeddable
public class TraitScore {

    @Enumerated(EnumType.STRING)
    @Column(name = "dimension", nullable = false)
    private Dimension dimension;

    @Column(name = "raw_score", nullable = false)
    private int rawScore;

    @Column(name = "normalized_score", nullable = false)
    private int normalizedScore;

    public TraitScore() {
    }

    public TraitScore(Dimension dimension, int rawScore, int normalizedScore) {
        setDimension(dimension);
        setRawScore(rawScore);
        setNormalizedScore(normalizedScore);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        if (dimension == null) {
            throw new IllegalArgumentException("Dimension cannot be null.");
        }
        this.dimension = dimension;
    }

    public int getRawScore() {
        return rawScore;
    }

    public void setRawScore(int rawScore) {
        if (rawScore < 3 || rawScore > 15) {
            throw new IllegalArgumentException("Raw score must be between 3 and 15 (inclusive). Received: " + rawScore);
        }
        this.rawScore = rawScore;
    }

    public int getNormalizedScore() {
        return normalizedScore;
    }

    public void setNormalizedScore(int normalizedScore) {
        if (normalizedScore < 0 || normalizedScore > 100) {
            throw new IllegalArgumentException("Normalized score must be between 0 and 100 (inclusive). Received: " + normalizedScore);
        }
        this.normalizedScore = normalizedScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraitScore that = (TraitScore) o;
        return rawScore == that.rawScore &&
               normalizedScore == that.normalizedScore &&
               dimension == that.dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, rawScore, normalizedScore);
    }

    @Override
    public String toString() {
        return "TraitScore{" +
                "dimension=" + dimension +
                ", rawScore=" + rawScore +
                ", normalizedScore=" + normalizedScore + "%" +
                '}';
    }
}
