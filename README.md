# Career Compass — Career Guidance Application (Version 1)

> **MCA Object-Oriented Programming (OOP) Assignment (10 Marks)**  
> **Topic:** Rule-Based Career Guidance Application Demonstrating Encapsulation, Inheritance, Abstraction, and Polymorphism in Java.  
> **Scope:** Version 1 — Zero AI dependencies. Pure deterministic Java scoring and weighted recommendation engine.

---

## Table of Contents
1. [Project Overview](#1-project-overview)
2. [Technology Stack](#2-technology-stack)
3. [Demonstration of Four Major OOP Concepts](#3-demonstration-of-four-major-oop-concepts)
4. [Project Directory Structure](#4-project-directory-structure)
5. [Database Setup & Automatic Seeding](#5-database-setup--automatic-seeding)
6. [Running the Spring Boot Backend](#6-running-the-spring-boot-backend)
7. [Running the Android Application](#7-running-the-android-application)
8. [REST API Endpoints & Testing](#8-rest-api-endpoints--testing)
9. [Running Automated Tests](#9-running-automated-tests)
10. [Viva Questions & Answers](#10-viva-questions--answers)

---

## 1. Project Overview

**Career Compass** is an educational Android mobile application backed by a Java Spring Boot REST API and MySQL database. It guides students through a structured 15-question assessment evaluating five vocational dimensions:

1. **Analytical Thinking** (Logic, patterns, problem decomposition)
2. **Creativity** (Original ideas, visual aesthetics, novel solutions)
3. **Social Orientation** (Teaching, helping others, collaboration)
4. **Leadership and Initiative** (Ownership, organization, group direction)
5. **Practical and Hands-on Orientation** (Building, experimentation, technical mechanics)

### Assessment Workflow
```
[Welcome Screen] 
       ↓
[Student Profile] (Name & Education Level)
       ↓
[15-Question Likert Assessment] (One question per screen, 1 to 5 scale)
       ↓
[Backend Scoring Engine] (Normalized Dimension Scores 0–100%)
       ↓
[Recommendation Engine] (Weighted Average Matching for 5 Career Profiles)
       ↓
[Results Dashboard] (Dimension Scores + Top 3 Recommended Careers)
       ↓
[Career Details Screen] (Description, Skills, Matching Rationale, Beginner Activities)
```

---

## 2. Technology Stack

### Android Mobile App
- **Language:** Java 17
- **UI Architecture:** XML Layouts, Android Material Design Components
- **HTTP Client:** Retrofit 2.9.0 + Gson Converter
- **IDE:** Android Studio (Hedgehog / Iguana / Koala or newer)

### Backend Service
- **Language:** Java 17+
- **Framework:** Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Jakarta Validation)
- **Build System:** Maven 3.9+ (includes Maven Wrapper `./mvnw`)

### Database
- **Database:** MySQL 8.0+
- **ORM / Driver:** Hibernate JPA + MySQL Connector/J
- **Automated Seeding:** Spring Boot `DataInitializer` (`CommandLineRunner`)

---

## 3. Demonstration of Four Major OOP Concepts

This project specifically highlights the four major pillars of Object-Oriented Programming for academic demonstration:

### 1. Encapsulation
- **Where:** `Student.java`, `Response.java`, `TraitScore.java`
- **How:** Internal fields are strictly `private`. Public getters and validated setters prevent illegal state transitions.
- **Example:** In `Response.java`, ratings outside $1$ to $5$ are rejected:
  ```java
  public void setRating(int rating) {
      if (rating < 1 || rating > 5) {
          throw new IllegalArgumentException("Rating must be between 1 and 5. Received: " + rating);
      }
      this.rating = rating;
  }
  ```

### 2. Inheritance
- **Where:** Abstract base class `Question.java` $\rightarrow$ Subclasses `LikertQuestion.java` and `ScenarioQuestion.java`
- **How:** Common question attributes (`id`, `questionText`, `dimension`, `displayOrder`) are declared in the abstract base class and reused by concrete subclasses via `extends`.
- **Extensibility:** `ScenarioQuestion` demonstrates that future question formats (e.g. situational workplace dilemma questions) can be added seamlessly without breaking existing question models.

### 3. Abstraction
- **Where:** `ScoringStrategy.java` interface
- **How:** Defines the contract `calculateScores(List<Response> responses, List<Question> questions)` without exposing calculation mathematics or psychometric algorithms.

### 4. Polymorphism
- **Where:** `ScoreCalculator.java` $\rightarrow$ `LikertScoringStrategy.java`
- **How:** `ScoreCalculator` depends on the `ScoringStrategy` interface reference rather than any concrete class:
  ```java
  private final ScoringStrategy scoringStrategy;
  ...
  // Dynamic polymorphic method dispatch at runtime
  Map<Dimension, TraitScore> scoreMap = scoringStrategy.calculateScores(responses, questions);
  ```

For a comprehensive breakdown, see [docs/OOP_CONCEPTS_EXPLANATION.md](docs/OOP_CONCEPTS_EXPLANATION.md).

---

## 4. Project Directory Structure

```
OOTL_miniproject/
├── backend/                             # Spring Boot REST API
│   ├── pom.xml                          # Maven build dependencies
│   ├── mvnw / mvnw.cmd                  # Maven wrappers
│   ├── .env.example                     # Sample database environment variables
│   ├── api_samples/                     # Sample JSON payloads & curl scripts
│   │   ├── submit_request_software_dev.json
│   │   ├── submit_request_designer.json
│   │   ├── submit_request_invalid_missing_questions.json
│   │   ├── submit_response_software_dev.json
│   │   └── curl_commands.sh
│   └── src/
│       ├── main/
│       │   ├── java/com/careercompass/
│       │   │   ├── CareerCompassApplication.java
│       │   │   ├── config/ (DataInitializer, CorsConfig)
│       │   │   ├── controller/ (AssessmentController, GlobalExceptionHandler)
│       │   │   ├── dto/ (Request & Response DTOs)
│       │   │   ├── model/ (Student, Question, LikertQuestion, TraitScore, etc.)
│       │   │   ├── repository/ (QuestionRepository, CareerProfileRepository, etc.)
│       │   │   ├── service/ (AssessmentService, ScoreCalculator, RecommendationEngine)
│       │   │   └── strategy/ (ScoringStrategy, LikertScoringStrategy)
│       │   └── resources/
│       │       ├── application.properties
│       │       ├── schema.sql
│       │       └── data.sql
│       └── test/                        # Unit & Integration Tests
├── android/                             # Android Studio Mobile Project
│   ├── build.gradle / settings.gradle
│   └── app/
│       ├── build.gradle                 # Retrofit, Material UI dependencies
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/careercompass/
│           │   ├── model/ (Android DTOs)
│           │   ├── network/ (ApiClient, ApiService)
│           │   └── ui/ (Welcome, Profile, Assessment, Results, CareerDetail)
│           └── res/
│               ├── layout/ (5 Activities XML + Item Cards)
│               ├── values/ (colors, strings, themes)
│               └── xml/ (network_security_config.xml)
├── database/
│   └── database_setup.sql               # MySQL Database and User creation
├── docs/
│   ├── UML_CLASS_DIAGRAM.md             # Mermaid UML Architecture Diagram
│   ├── OOP_CONCEPTS_EXPLANATION.md      # Detailed OOP Viva Guide
│   └── ASSIGNMENT_REPORT_OUTLINE.md     # 10-Mark Academic Report Outline
└── README.md
```

---

## 5. Database Setup & Automatic Seeding

### Step 1: Start MySQL
Ensure MySQL server is running on your machine:
```bash
# macOS (Homebrew)
brew services start mysql

# Linux (systemd)
sudo systemctl start mysql

# Windows
# Start MySQL from MySQL Workbench or Services panel
```

### Step 2: Create the Database
Run the provided SQL script to create `career_compass` and configure user privileges:
```bash
mysql -u root -p < database/database_setup.sql
```

*Alternatively, run in MySQL command line:*
```sql
CREATE DATABASE IF NOT EXISTS career_compass;
```

### Step 3: Automatic Seed Data (Zero Manual Insertions Required)
When the Spring Boot application launches, `DataInitializer.java` automatically inspects the database:
- If the `questions` table is empty $\rightarrow$ seeds all **15 predefined questions** across the 5 dimensions.
- If the `career_profiles` table is empty $\rightarrow$ seeds all **5 career profiles** with descriptions, skills, matching rationales, learning activities, and dimension weights.
- If the `assessment_results` table is empty $\rightarrow$ seeds 1 demo assessment record so `GET /api/assessment/results/1` can be verified immediately.

---

## 6. Running the Spring Boot Backend

### Method A: Using Terminal
```bash
cd backend

# Option 1: Using default environment (localhost:3306, user: root, password: "")
./mvnw spring-boot:run

# Option 2: Overriding database credentials via environment variables
DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

### Method B: Using IntelliJ IDEA / Eclipse / VS Code
1. Open the `backend/` folder in your IDE as a Maven project.
2. Verify JDK 17+ is selected.
3. Locate `src/main/java/com/careercompass/CareerCompassApplication.java`.
4. Click **Run**.

Once launched, the backend will be available at:  
👉 **`http://localhost:8080/api`**

---

## 7. Running the Android Application

### Step 1: Open in Android Studio
1. Launch Android Studio.
2. Select **Open** $\rightarrow$ navigate to `OOTL_miniproject/android` $\rightarrow$ click **Open**.
3. Allow Gradle to sync dependencies.

### Step 2: Network Configuration for Android Emulator
When running the Android application in the **Android Studio Emulator**:
- The emulator uses the virtual loopback address **`10.0.2.2`** to communicate with the host machine's `localhost`.
- `ApiClient.java` is already pre-configured to `http://10.0.2.2:8080/api/` out of the box!
- Cleartext HTTP traffic is explicitly permitted via `network_security_config.xml`.

*Testing on a physical phone connected via Wi-Fi:*
Change the base URL in `ApiClient.java` to your machine's Wi-Fi IP address (e.g. `http://192.168.1.105:8080/api/`).

### Step 3: Launch the App
1. Select your target emulator (e.g. Pixel 6 / 7, Android API 30+).
2. Click the green **Run (▶)** button.
3. The app opens to **WelcomeActivity**. Proceed through:
   - Screen 1: Welcome
   - Screen 2: Student Profile (Enter name)
   - Screen 3: Assessment (Answer 15 questions)
   - Screen 4: Results Dashboard (View dimension scores & top 3 careers)
   - Screen 5: Career Details (Click "View Details" to inspect skills & learning activities)

---

## 8. REST API Endpoints & Testing

All endpoints can be tested instantly using the provided script `backend/api_samples/curl_commands.sh` or standard cURL:

### 1. Fetch 15 Questions
```bash
curl -X GET http://localhost:8080/api/assessment/questions
```

### 2. Fetch Career Profiles & Dimension Weights
```bash
curl -X GET http://localhost:8080/api/careers
```

### 3. Fetch Preloaded Demo Assessment Result
```bash
curl -X GET http://localhost:8080/api/assessment/results/1
```

### 4. Submit Assessment Answers
```bash
curl -X POST http://localhost:8080/api/assessment/submit \
     -H "Content-Type: application/json" \
     -d @backend/api_samples/submit_request_software_dev.json
```

---

## 9. Running Automated Tests

### Method 1: Maven Test Suite
```bash
cd backend
./mvnw test
```
The test suite utilizes an in-memory H2 database profile (`src/test/resources/application.properties`), ensuring tests execute cleanly even without a live MySQL server running.

### Method 2: Standalone Verification Suite
To verify the core scoring formula, validation boundaries, and OOP contracts directly:
```bash
# Executable verification test
/Library/Java/JavaVirtualMachines/temurin-26.jdk/Contents/Home/bin/javac -d /tmp/classes \
  backend/src/main/java/com/careercompass/model/*.java \
  backend/src/main/java/com/careercompass/strategy/*.java \
  backend/src/main/java/com/careercompass/service/ScoreCalculator.java \
  backend/src/main/java/com/careercompass/service/RecommendationEngine.java \
  backend/src/test/java/testhelper/StandaloneVerificationRunner.java && \
/Library/Java/JavaVirtualMachines/temurin-26.jdk/Contents/Home/bin/java -ea -cp /tmp/classes testhelper.StandaloneVerificationRunner
```
**Output:**
```
===============================================================
   CAREER COMPASS - CORE LOGIC & OOP VERIFICATION SUITE       
===============================================================
[PASS] Test 1: Likert score normalization formula ((raw-3)/12)*100
[PASS] Test 2: Encapsulation - Student validation prevents invalid states
[PASS] Test 3: Encapsulation - Response rating validation (strictly 1..5)
[PASS] Test 4: Inheritance - Question abstract class with LikertQuestion & ScenarioQuestion
[PASS] Test 5: Abstraction & Polymorphism - ScoringStrategy interface dispatched via ScoreCalculator
[PASS] Test 6: Recommendation Engine - Weighted ranking correctly ranks Software Developer #1
===============================================================
SUMMARY: 6 PASSED, 0 FAILED
===============================================================
```

---

## 10. Viva Questions & Answers

### Q1: Why did you not use AI in Version 1?
**Answer:** Version 1 focuses strictly on core Object-Oriented Programming principles and predictable, deterministic evaluation for an academic assignment. Rule-based weighted matching ensures transparent calculation without external latency, costs, or API dependencies.

### Q2: How did you implement Encapsulation in your project?
**Answer:** In `Student`, `Response`, and `TraitScore`, all fields are private. Public methods and constructors enforce validation rules (e.g., rejecting ratings outside 1–5 or scores outside 0–100%), ensuring data integrity.

### Q3: Where is Inheritance used?
**Answer:** In the question model. `Question` is an abstract base class containing common fields (`id`, `questionText`, `dimension`). `LikertQuestion` and `ScenarioQuestion` extend `Question`, inheriting core behavior while adding subtype-specific attributes.

### Q4: How are Abstraction and Polymorphism demonstrated together?
**Answer:** We created the `ScoringStrategy` interface (**Abstraction**). The `ScoreCalculator` class holds a reference to `ScoringStrategy` rather than any concrete class. At runtime, the JVM dynamically invokes `LikertScoringStrategy.calculateScores(...)` (**Polymorphism**), decoupling assessment orchestration from calculation mechanics.

### Q5: How is the Normalized Score calculated?
**Answer:** Each dimension has 3 questions on a 1–5 scale, giving a raw score from 3 to 15. The formula is:
$$\text{Normalized Score} = \left(\frac{\text{Raw Score} - 3}{12}\right) \times 100$$
For example, a raw score of 12 gives:
$$\left(\frac{12 - 3}{12}\right) \times 100 = 75\%$$

### Q6: How does the Recommendation Engine rank careers?
**Answer:** Using a weighted average formula:
$$\text{Career Match Score} = \frac{\sum(\text{Dimension Score} \times \text{Career Weight})}{\sum \text{Career Weights}}$$
The application evaluates all 5 predefined career profiles against the student's 5 dimension scores, sorts them in descending order, and displays the top 3.
