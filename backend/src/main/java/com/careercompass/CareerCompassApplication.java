package com.careercompass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Career Compass - Spring Boot Backend Application
 * MCA Object-Oriented Programming (OOP) Assignment
 *
 * Demonstrates:
 * 1. Encapsulation (Student, Response, TraitScore)
 * 2. Inheritance (Question -> LikertQuestion, ScenarioQuestion)
 * 3. Abstraction (ScoringStrategy interface)
 * 4. Polymorphism (LikertScoringStrategy polymorphic dynamic dispatch)
 */
@SpringBootApplication
public class CareerCompassApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerCompassApplication.class, args);
    }
}
