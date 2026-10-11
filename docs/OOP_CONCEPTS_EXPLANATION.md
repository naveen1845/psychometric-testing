# Demonstration of Major OOP Concepts in Career Compass

This guide explains how the four foundational Object-Oriented Programming (OOP) concepts are implemented in the **Career Compass** project. It is structured specifically to assist in project demonstration and viva evaluation.

---

## 1. Encapsulation

### Definition
Encapsulation is the mechanism of bundling data (fields) and methods that manipulate that data into a single unit (class), while restricting direct access to the internal state from outside the class.

### Implementation in Career Compass
In Career Compass, encapsulation is rigorously enforced in:
1. `Student.java`:
   - All internal fields (`name`, `educationLevel`) are marked `private`.
   - The constructor and setter validate input:
     ```java
     public void setName(String name) {
         if (name == null || name.trim().isEmpty()) {
             throw new IllegalArgumentException("Student name is required and cannot be blank.");
         }
         this.name = name.trim();
     }
     ```
   - An external caller cannot corrupt the state or set an empty name.
2. `Response.java`:
   - Holds `questionId` and `rating`.
   - Enforces the 5-point Likert boundary:
     ```java
     public void setRating(int rating) {
         if (rating < 1 || rating > 5) {
             throw new IllegalArgumentException("Rating must be between 1 and 5 (inclusive). Received: " + rating);
         }
         this.rating = rating;
     }
     ```
3. `TraitScore.java`:
   - Enforces domain boundaries: `rawScore` must be between $3$ and $15$, and `normalizedScore` must be between $0$ and $100$.

### Viva Explanation
> *"We implemented encapsulation by making all fields private and providing public getters and validated setters. For example, in our `Response` class, we reject any rating below 1 or above 5, preventing invalid assessment data from entering our scoring engine."*

---

## 2. Inheritance

### Definition
Inheritance is the mechanism by which one class acquires the properties and behaviors of another class (base/parent class), promoting code reuse and establishing an "IS-A" relationship.

### Implementation in Career Compass
In Career Compass, inheritance is demonstrated in the Question model hierarchy:
1. `Question.java` (Abstract Base Class):
   - Defines common attributes: `id`, `questionText`, `dimension`, and `displayOrder`.
   - Defines the abstract method `public abstract String getQuestionType()`.
2. `LikertQuestion.java` (Subclass):
   - Extends `Question` using `public class LikertQuestion extends Question`.
   - Inherits all base attributes and adds Likert-specific properties: `scaleMin`, `scaleMax`, `minLabel`, `maxLabel`.
   - Reuses the parent constructor via `super(...)`.
3. `ScenarioQuestion.java` (Subclass):
   - Extends `Question` to demonstrate future extensibility.
   - Adds situational attributes: `scenarioContext`, `optionCount`.

```
        +-------------------------+
        |   Question (Abstract)   |
        +-------------------------+
                     ^
                     | (extends)
         +-----------+-----------+
         |                       |
+------------------+   +-------------------+
|  LikertQuestion  |   | ScenarioQuestion  |
+------------------+   +-------------------+
```

### Viva Explanation
> *"We created an abstract base class `Question` that encapsulates shared attributes like question text and dimension. `LikertQuestion` and `ScenarioQuestion` inherit from this base class, reusing code while allowing different question modalities to exist in the system without duplicating core attributes."*

---

## 3. Abstraction

### Definition
Abstraction is the concept of exposing essential features while hiding complex implementation details. It defines *what* an operation does without specifying *how* it does it.

### Implementation in Career Compass
In Career Compass, abstraction is implemented via the `ScoringStrategy` interface:
```java
public interface ScoringStrategy {
    Map<Dimension, TraitScore> calculateScores(List<Response> responses, List<Question> questions);
    String getStrategyName();
}
```
- The interface specifies the contract for calculating dimension scores.
- The consumer (e.g. `ScoreCalculator`) does not need to know whether scores are calculated using Likert percentage normalization, percentile ranking, or adaptive scoring. It only cares that calling `calculateScores(...)` returns the mapped `TraitScore` objects.

### Viva Explanation
> *"We achieved abstraction by defining the `ScoringStrategy` interface. It defines the contract for score computation without locking the system into a specific algorithm. If tomorrow we introduce a new scoring standard, we only need to provide a new class implementing this interface."*

---

## 4. Polymorphism

### Definition
Polymorphism ("many forms") is the ability of an object reference or method call to behave differently based on the actual concrete runtime type of the object.

### Implementation in Career Compass
Polymorphism is demonstrated in the dynamic method dispatch between `ScoreCalculator` and `ScoringStrategy`:
1. `ScoreCalculator.java` holds an interface reference:
   ```java
   private final ScoringStrategy scoringStrategy;

   @Autowired
   public ScoreCalculator(ScoringStrategy scoringStrategy) {
       this.scoringStrategy = scoringStrategy; // Interface reference
   }
   ```
2. When `ScoreCalculator.computeTraitScores(...)` executes:
   ```java
   // Polymorphic invocation resolved at runtime
   Map<Dimension, TraitScore> scoreMap = scoringStrategy.calculateScores(responses, questions);
   ```
   At runtime, the JVM invokes `LikertScoringStrategy.calculateScores(...)` dynamically via polymorphism.
3. The high-level assessment service depends on the abstraction rather than the concrete class (Dependency Inversion Principle).

### Viva Explanation
> *"In `ScoreCalculator`, we store a reference of type `ScoringStrategy` (the interface). At runtime, when we invoke `scoringStrategy.calculateScores()`, the JVM dynamically dispatches the call to the concrete `LikertScoringStrategy` instance. This decouples our business logic from any single calculation implementation."*

---

## Summary Matrix

| OOP Concept | Target Artifacts | Key Method / Feature | Viva Highlight |
| :--- | :--- | :--- | :--- |
| **Encapsulation** | `Student`, `Response`, `TraitScore` | Private fields + range validation in setters | Data integrity & bounded inputs ($1..5$, $0..100\%$) |
| **Inheritance** | `Question` $\rightarrow$ `LikertQuestion`, `ScenarioQuestion` | `super(...)`, `getQuestionType()` | Code reuse & extensibility for new question formats |
| **Abstraction** | `ScoringStrategy` interface | Contract: `calculateScores(...)` | Separates interface contract from algorithmic execution |
| **Polymorphism** | `ScoreCalculator` $\rightarrow$ `LikertScoringStrategy` | Dynamic dispatch via interface reference | Eliminates tight coupling between calculator and strategy |
