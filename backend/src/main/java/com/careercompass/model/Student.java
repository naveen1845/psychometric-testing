package com.careercompass.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Demonstrates the OOP concept of ENCAPSULATION:
 * - Internal state is protected by private fields.
 * - Uncontrolled modification is prevented by input validation in constructors and setters.
 * - Access is provided strictly through public getter and setter methods.
 */
@Embeddable
public class Student {

    private String name;
    private String educationLevel;

    /**
     * Default constructor required by JPA.
     */
    public Student() {
    }

    /**
     * Parameterized constructor with input validation.
     *
     * @param name Student's full name (required, non-blank)
     * @param educationLevel Education level (optional, e.g. "MCA 1st Year")
     */
    public Student(String name, String educationLevel) {
        setName(name);
        setEducationLevel(educationLevel);
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the student's name with validation to prevent empty or null values.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required and cannot be blank.");
        }
        this.name = name.trim();
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * Sets the optional education level, trimming whitespace if provided.
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = (educationLevel != null && !educationLevel.trim().isEmpty())
                ? educationLevel.trim()
                : "Not Specified";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
               Objects.equals(educationLevel, student.educationLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, educationLevel);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                '}';
    }
}
