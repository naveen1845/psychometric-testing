-- ====================================================================
-- Career Compass — Complete Database Setup & Seed Script
-- Project: MCA Object-Oriented Programming Assignment (Version 1)
-- Database Engine: MySQL 8.0+
-- ====================================================================

-- --------------------------------------------------------------------
-- 1. CREATE DATABASE
-- --------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS career_compass
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE career_compass;

-- --------------------------------------------------------------------
-- 2. CREATE APPLICATION USER & GRANT PRIVILEGES (OPTIONAL)
-- --------------------------------------------------------------------
CREATE USER IF NOT EXISTS 'career_user'@'localhost' IDENTIFIED BY 'career_pass123';
CREATE USER IF NOT EXISTS 'career_user'@'%' IDENTIFIED BY 'career_pass123';

GRANT ALL PRIVILEGES ON career_compass.* TO 'career_user'@'localhost';
GRANT ALL PRIVILEGES ON career_compass.* TO 'career_user'@'%';
FLUSH PRIVILEGES;

-- --------------------------------------------------------------------
-- 3. SCHEMA DDL (TABLE DEFINITIONS)
-- --------------------------------------------------------------------

-- Table 1: Questions (Demonstrating Inheritance via Single-Table Strategy)
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_type VARCHAR(31) NOT NULL,
    question_text VARCHAR(500) NOT NULL,
    dimension VARCHAR(50) NOT NULL,
    display_order INT NOT NULL,
    scale_min INT DEFAULT 1,
    scale_max INT DEFAULT 5,
    min_label VARCHAR(50) DEFAULT 'Strongly Disagree',
    max_label VARCHAR(50) DEFAULT 'Strongly Agree',
    scenario_context VARCHAR(1000) NULL,
    option_count INT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 2: Predefined Career Profiles & Dimension Weights
CREATE TABLE IF NOT EXISTS career_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    career_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL,
    relevant_skills VARCHAR(1000) NOT NULL,
    matching_explanation VARCHAR(1000) NOT NULL,
    learning_activities VARCHAR(1000) NOT NULL,
    weight_analytical INT NOT NULL,
    weight_creativity INT NOT NULL,
    weight_social INT NOT NULL,
    weight_leadership INT NOT NULL,
    weight_practical INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 3: Assessment Results Master Table
CREATE TABLE IF NOT EXISTS assessment_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    education_level VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 4: Submitted Question Answers
CREATE TABLE IF NOT EXISTS assessment_answers (
    assessment_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    rating INT NOT NULL,
    CONSTRAINT fk_answers_assessment
        FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 5: Normalized Dimension Trait Scores (0..100%)
CREATE TABLE IF NOT EXISTS assessment_trait_scores (
    assessment_id BIGINT NOT NULL,
    dimension VARCHAR(50) NOT NULL,
    raw_score INT NOT NULL,
    normalized_score INT NOT NULL,
    CONSTRAINT fk_traits_assessment
        FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 6: Top Ranked Career Recommendations
CREATE TABLE IF NOT EXISTS assessment_recommendations (
    assessment_id BIGINT NOT NULL,
    career_id BIGINT,
    career_name VARCHAR(255) NOT NULL,
    match_score INT NOT NULL,
    rank_order INT NOT NULL,
    description VARCHAR(1000),
    relevant_skills VARCHAR(1000),
    matching_explanation VARCHAR(1000),
    learning_activities VARCHAR(1000),
    CONSTRAINT fk_recs_assessment
        FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------------------
-- 4. PRELOADED ASSESSMENT QUESTIONS (15 QUESTIONS ACROSS 5 DIMENSIONS)
-- --------------------------------------------------------------------

-- Dimension 1: Analytical Thinking (Q1 to Q3)
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy solving logical problems.', 'ANALYTICAL_THINKING', 1, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 1);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like identifying patterns in information.', 'ANALYTICAL_THINKING', 2, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 2);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy breaking complicated problems into smaller parts.', 'ANALYTICAL_THINKING', 3, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 3);

-- Dimension 2: Creativity (Q4 to Q6)
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy developing original ideas.', 'CREATIVITY', 4, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 4);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like designing or creating new things.', 'CREATIVITY', 5, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 5);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy finding alternative solutions to a problem.', 'CREATIVITY', 6, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 6);

-- Dimension 3: Social Orientation (Q7 to Q9)
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy explaining concepts to other people.', 'SOCIAL_ORIENTATION', 7, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 7);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like helping people solve problems.', 'SOCIAL_ORIENTATION', 8, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 8);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy collaborating with others.', 'SOCIAL_ORIENTATION', 9, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 9);

-- Dimension 4: Leadership and Initiative (Q10 to Q12)
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I am comfortable taking responsibility for tasks.', 'LEADERSHIP_AND_INITIATIVE', 10, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 10);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy organizing activities or projects.', 'LEADERSHIP_AND_INITIATIVE', 11, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 11);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like making decisions when a group needs direction.', 'LEADERSHIP_AND_INITIATIVE', 12, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 12);

-- Dimension 5: Practical and Hands-on Orientation (Q13 to Q15)
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy learning by building or experimenting.', 'PRACTICAL_HANDS_ON', 13, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 13);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like understanding how technical systems work.', 'PRACTICAL_HANDS_ON', 14, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 14);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy turning theoretical ideas into working solutions.', 'PRACTICAL_HANDS_ON', 15, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 15);

-- --------------------------------------------------------------------
-- 5. PRELOADED CAREER PROFILES & DIMENSION WEIGHTS (5 PROFILES)
-- --------------------------------------------------------------------

-- Profile 1: Software Developer
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Software Developer',
       'Designs, builds, tests, and maintains robust software applications and system architectures to solve complex business and technical challenges.',
       'Java, Spring Boot, Object-Oriented Design, Data Structures & Algorithms, Git, REST APIs, SQL',
       'Your high scores in Analytical Thinking and Practical Hands-on Orientation align strongly with core software engineering tasks: writing clean code, architectural problem-solving, and systematic debugging.',
       '1. Build a mini project in Java or Python applying OOP principles.\n2. Practice algorithmic problems on LeetCode or HackerRank.\n3. Learn version control using Git and GitHub workflows.',
       5, 4, 2, 2, 5
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Software Developer');

-- Profile 2: Data Analyst
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Data Analyst',
       'Inspects, cleans, transforms, and models data to uncover meaningful patterns, generate visual reports, and guide data-informed decision-making.',
       'SQL, Python/R, Statistical Modeling, Data Visualization (Tableau/Power BI), Excel, Analytical Thinking',
       'Your elevated Analytical Thinking and Hands-on problem-solving reflect the quantitative inquiry required to dissect datasets, detect anomalies, and derive strategic insights.',
       '1. Learn SQL query formulation and database schema design.\n2. Analyze public datasets on Kaggle or government open data portals.\n3. Build an interactive visual dashboard in Power BI or Google Looker Studio.',
       5, 3, 3, 2, 4
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Data Analyst');

-- Profile 3: UX/UI Designer
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'UX/UI Designer',
       'Creates intuitive, aesthetically pleasing, and user-centric interfaces for mobile and web products through research, wireframing, and usability testing.',
       'Figma, Wireframing & Prototyping, User Research, Visual Hierarchy, Design Systems, Empathy',
       'Your high Creativity coupled with strong Social Orientation provides the essential empathy to understand user pain points and design engaging, human-centered experiences.',
       '1. Complete a UI design course using Figma.\n2. Redesign an existing mobile app screen to solve an identified usability flaw.\n3. Study Nielsen Norman Group usability heuristics and design principles.',
       3, 5, 4, 2, 3
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'UX/UI Designer');

-- Profile 4: Teacher or Trainer
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Teacher or Trainer',
       'Educates, mentors, and develops individuals or corporate teams by breaking down intricate subjects into engaging, digestible learning journeys.',
       'Public Speaking, Curriculum Development, Active Listening, Mentoring, Educational Pedagogy, Empathy',
       'Your standout Social Orientation and Leadership skills make you naturally effective at communicating concepts patiently, inspiring students, and facilitating collaborative growth.',
       '1. Conduct peer-tutoring sessions or seminar presentations in your college.\n2. Write a step-by-step explanatory tutorial or record a 5-minute technical explainer video.\n3. Participate in Toastmasters or public speaking workshops.',
       3, 3, 5, 4, 2
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Teacher or Trainer');

-- Profile 5: Project Manager
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Project Manager',
       'Plans, coordinates, and steers cross-functional teams to deliver projects on schedule and within scope while mitigating risks and aligning stakeholders.',
       'Agile & Scrum Methodologies, Team Leadership, Risk Management, Stakeholder Communication, Scheduling & Budgeting',
       'Your strong Leadership and Social Orientation traits reflect the interpersonal diplomacy, organizational vision, and decisiveness needed to coordinate multi-disciplinary teams.',
       '1. Learn Agile and Scrum fundamentals (e.g. Jira or Trello project boards).\n2. Lead a college group project or club event and document the sprint plan.\n3. Study risk identification and mitigation strategies in software engineering.',
       3, 3, 4, 5, 3
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Project Manager');

-- --------------------------------------------------------------------
-- 6. PRELOADED DEMO ASSESSMENT RECORD (FOR IMMEDIATE VIVA DEMO)
-- --------------------------------------------------------------------
INSERT INTO assessment_results (id, name, education_level, created_at)
SELECT 1, 'Demo Student (Preloaded Sample)', 'MCA 1st Year', NOW()
WHERE NOT EXISTS (SELECT 1 FROM assessment_results WHERE id = 1);

-- 15 Demo Answers
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 1, 5 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 1);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 2, 5 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 2);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 3, 4 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 3);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 4, 4 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 4);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 5, 4 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 5);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 6, 3 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 6);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 7, 3 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 7);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 8, 3 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 8);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 9, 2 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 9);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 10, 3 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 10);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 11, 2 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 11);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 12, 3 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 12);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 13, 5 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 13);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 14, 5 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 14);
INSERT INTO assessment_answers (assessment_id, question_id, rating)
SELECT 1, 15, 5 WHERE NOT EXISTS (SELECT 1 FROM assessment_answers WHERE assessment_id = 1 AND question_id = 15);

-- Demo Trait Scores (Normalized)
INSERT INTO assessment_trait_scores (assessment_id, dimension, raw_score, normalized_score)
SELECT 1, 'ANALYTICAL_THINKING', 14, 92 WHERE NOT EXISTS (SELECT 1 FROM assessment_trait_scores WHERE assessment_id = 1 AND dimension = 'ANALYTICAL_THINKING');
INSERT INTO assessment_trait_scores (assessment_id, dimension, raw_score, normalized_score)
SELECT 1, 'CREATIVITY', 11, 67 WHERE NOT EXISTS (SELECT 1 FROM assessment_trait_scores WHERE assessment_id = 1 AND dimension = 'CREATIVITY');
INSERT INTO assessment_trait_scores (assessment_id, dimension, raw_score, normalized_score)
SELECT 1, 'SOCIAL_ORIENTATION', 8, 42 WHERE NOT EXISTS (SELECT 1 FROM assessment_trait_scores WHERE assessment_id = 1 AND dimension = 'SOCIAL_ORIENTATION');
INSERT INTO assessment_trait_scores (assessment_id, dimension, raw_score, normalized_score)
SELECT 1, 'LEADERSHIP_AND_INITIATIVE', 8, 42 WHERE NOT EXISTS (SELECT 1 FROM assessment_trait_scores WHERE assessment_id = 1 AND dimension = 'LEADERSHIP_AND_INITIATIVE');
INSERT INTO assessment_trait_scores (assessment_id, dimension, raw_score, normalized_score)
SELECT 1, 'PRACTICAL_HANDS_ON', 15, 100 WHERE NOT EXISTS (SELECT 1 FROM assessment_trait_scores WHERE assessment_id = 1 AND dimension = 'PRACTICAL_HANDS_ON');

-- Demo Career Recommendations (Top 3)
INSERT INTO assessment_recommendations (assessment_id, career_id, career_name, match_score, rank_order, description, relevant_skills, matching_explanation, learning_activities)
SELECT 1, 1, 'Software Developer', 78, 1,
       'Designs, builds, tests, and maintains robust software applications...',
       'Java, Spring Boot, Object-Oriented Design, Data Structures',
       'High scores in Analytical Thinking and Practical Hands-on Orientation align strongly with coding and architecture.',
       'Build a mini project in Java; practice coding on LeetCode.'
WHERE NOT EXISTS (SELECT 1 FROM assessment_recommendations WHERE assessment_id = 1 AND rank_order = 1);

INSERT INTO assessment_recommendations (assessment_id, career_id, career_name, match_score, rank_order, description, relevant_skills, matching_explanation, learning_activities)
SELECT 1, 2, 'Data Analyst', 71, 2,
       'Inspects, cleans, transforms, and models data to uncover meaningful patterns...',
       'SQL, Python/R, Statistical Modeling, Data Visualization',
       'Elevated Analytical Thinking and Hands-on problem-solving reflect quantitative inquiry.',
       'Learn SQL query formulation; analyze open datasets on Kaggle.'
WHERE NOT EXISTS (SELECT 1 FROM assessment_recommendations WHERE assessment_id = 1 AND rank_order = 2);

INSERT INTO assessment_recommendations (assessment_id, career_id, career_name, match_score, rank_order, description, relevant_skills, matching_explanation, learning_activities)
SELECT 1, 3, 'UX/UI Designer', 57, 3,
       'Creates intuitive, aesthetically pleasing, and user-centric interfaces...',
       'Figma, Wireframing & Prototyping, User Research',
       'Creativity and structured problem solving support interface layout.',
       'Complete a UI design course using Figma; study UX heuristics.'
WHERE NOT EXISTS (SELECT 1 FROM assessment_recommendations WHERE assessment_id = 1 AND rank_order = 3);

-- --------------------------------------------------------------------
-- 7. VERIFICATION OUTPUT
-- --------------------------------------------------------------------
SELECT 'Database setup complete!' AS Status,
       (SELECT COUNT(*) FROM questions) AS QuestionsCount,
       (SELECT COUNT(*) FROM career_profiles) AS CareersCount,
       (SELECT COUNT(*) FROM assessment_results) AS DemoResultsCount;
