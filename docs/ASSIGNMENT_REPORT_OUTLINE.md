# Career Compass — Assignment Report Outline

**Course:** Master of Computer Applications (MCA)  
**Subject:** Object-Oriented Programming with Java  
**Project Title:** Career Compass — Rule-Based Career Guidance Application (Version 1)  
**Marks Weightage:** 10 Marks Assignment  

---

## 1. Project Title and Abstract
- **Title:** Career Compass: A Rule-Based Career Guidance Application Demonstrating Core Object-Oriented Principles in Java.
- **Abstract:**  
  Career Compass is an educational mobile application and backend service built for students to discover technology career paths aligned with their interests. The system evaluates students across five key behavioral and vocational dimensions: Analytical Thinking, Creativity, Social Orientation, Leadership and Initiative, and Practical Hands-on Orientation. Operating strictly without external AI dependencies, the application computes deterministic trait scores using a normalized Likert model and ranks suitable career options through a weighted average recommendation engine. The project's primary academic objective is the practical demonstration of the four pillars of Object-Oriented Programming (OOP) in Java: Encapsulation, Inheritance, Abstraction, and Polymorphism.

---

## 2. Project Objectives
1. Design and develop an end-to-end client-server career assessment tool for undergraduate and postgraduate students.
2. Formulate a normalized scoring mechanism across five distinct vocational dimensions using a 15-question Likert assessment.
3. Develop a rule-based weighted recommendation algorithm to match student traits with predefined career profiles.
4. Provide tangible, verifiable demonstrations of Encapsulation, Inheritance, Abstraction, and Polymorphism in Java.
5. Deliver a responsive Android mobile frontend communicating seamlessly with a Spring Boot REST backend and MySQL database.

---

## 3. Technology Stack & System Architecture
### 3.1 Frontend (Android)
- **Language:** Java 17
- **Development Tool:** Android Studio
- **UI Architecture:** XML Layouts, Material Design Components (Cards, Buttons, ProgressBars)
- **HTTP Client:** Retrofit 2.9.0 with Gson converter
- **Network Configuration:** Cleartext HTTP enabled for Android emulator loopback (`10.0.2.2:8080`)

### 3.2 Backend (Spring Boot)
- **Framework:** Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Validation)
- **Build System:** Maven 3.9+
- **Architecture:** Layered Architecture (Controller $\rightarrow$ Service $\rightarrow$ Strategy / Engine $\rightarrow$ Repository $\rightarrow$ Database)

### 3.3 Database & ORM
- **Database:** MySQL 8.0+
- **ORM / Persistence:** Hibernate / Jakarta Persistence API (JPA)
- **Automatic Initialization:** Spring Boot `CommandLineRunner` (`DataInitializer`) for idempotent database seeding.

---

## 4. Assessment Dimensions & Methodology
### 4.1 Five Vocational Dimensions
1. **Analytical Thinking:** Evaluates aptitude for logic, pattern detection, and structured breakdown of problems.
2. **Creativity:** Evaluates enthusiasm for novel concepts, visual design, and out-of-the-box thinking.
3. **Social Orientation:** Evaluates interpersonal communication, mentorship, and team collaboration.
4. **Leadership and Initiative:** Evaluates readiness to take responsibility, organize workflows, and guide groups.
5. **Practical and Hands-on Orientation:** Evaluates enthusiasm for building systems, experimenting, and applying theory.

### 4.2 Scoring Formula
- Each dimension contains 3 questions rated on a 5-point scale (1 = Strongly Disagree to 5 = Strongly Agree).
- Raw Score Range: 3 to 15.
- Normalized Score Formula:
  $$\text{Normalized Score} = \left(\frac{\text{Raw Score} - 3}{12}\right) \times 100$$
- Result is rounded to the nearest integer ($0 \le \text{Normalized Score} \le 100$).

### 4.3 Career Match Ranking Formula
- Predefined career profiles: Software Developer, Data Analyst, UX/UI Designer, Teacher/Trainer, Project Manager.
- Weighted Average Match Formula:
  $$\text{Career Match Score} = \frac{\sum_{i=1}^{5} (\text{Dimension Score}_i \times \text{Career Weight}_i)}{\sum_{i=1}^{5} \text{Career Weight}_i}$$
- Top 3 career profiles are sorted descending and displayed to the user.

---

## 5. Detailed Demonstration of OOP Concepts

| Concept | Concrete Implementation | Significance in Code |
| :--- | :--- | :--- |
| **Encapsulation** | `Student`, `Response`, `TraitScore` | Private attributes; defensive validation in setters preventing ratings outside $1..5$ and scores outside $0..100\%$. |
| **Inheritance** | Abstract `Question` $\rightarrow$ `LikertQuestion`, `ScenarioQuestion` | Code reuse of core question attributes (`id`, `questionText`, `dimension`); extensible hierarchy. |
| **Abstraction** | `ScoringStrategy` Interface | Defines the scoring method contract (`calculateScores`) independent of internal calculation mathematics. |
| **Polymorphism** | `ScoreCalculator` referencing `ScoringStrategy` | Dynamic runtime dispatch to `LikertScoringStrategy`, decoupling the service layer from concrete scoring logic. |

---

## 6. Application Workflow & User Interface (Screenshots Section)
- **Screen 1: Welcome Screen**
  - Features Career Compass branding, 5-dimension overview, educational guidance disclaimer, and "Start Assessment" action.
- **Screen 2: Student Profile Screen**
  - Input validation enforcing required non-blank student name and optional education level.
- **Screen 3: Assessment Screen**
  - Displays one question at a time, question index indicator (e.g. "Question 4 of 15"), dimension badge, horizontal progress bar, and 5 selectable response options with backward/forward state preservation.
- **Screen 4: Results Dashboard**
  - Displays student name, 5 dimension scores with horizontal progress bars, top 3 recommended careers with match percentages, and "View Details" buttons.
- **Screen 5: Career Details Screen**
  - Comprehensive view of selected career: Overview description, required skills, dynamic matching rationale, and suggested beginner learning activities.

*(Include application screenshots in this section for final submission report)*

---

## 7. Database Design & Persistence
- `questions` table (Single-Table Inheritance with discriminator `question_type`)
- `career_profiles` table (5 predefined careers with dimension weights)
- `assessment_results` table (Master evaluation records)
- `assessment_answers` table (15 submitted question ratings)
- `assessment_trait_scores` table (Normalized dimension scores)
- `assessment_recommendations` table (Ranked top 3 career outputs)

---

## 8. Limitations and Version 1 Scope
1. **Rule-Based Matching:** Version 1 utilizes predefined weighted averages; it does not employ machine learning or adaptive psychometrics.
2. **Fixed Question Set:** Exactly 15 static questions are evaluated without adaptive branching.
3. **No External AI Services:** Explanations and career profiles are deterministic and preloaded; no LLM APIs or AI SDKs are integrated into Version 1.
4. **Local Network Scope:** Designed for local evaluation on localhost or Android emulator (`10.0.2.2`).

---

## 9. Conclusion & Future Enhancements
- Summary of how Career Compass successfully fulfills all 10-mark assignment criteria.
- Future work: adaptive questionnaire branching, optional AI career coaching insights (Version 2), PDF assessment report export.
