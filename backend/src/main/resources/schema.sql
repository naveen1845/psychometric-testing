-- ====================================================================
-- Career Compass - Relational Database Schema (schema.sql)
-- ====================================================================

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
);

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
);

CREATE TABLE IF NOT EXISTS assessment_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    education_level VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS assessment_answers (
    assessment_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    rating INT NOT NULL,
    FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS assessment_trait_scores (
    assessment_id BIGINT NOT NULL,
    dimension VARCHAR(50) NOT NULL,
    raw_score INT NOT NULL,
    normalized_score INT NOT NULL,
    FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
);

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
    FOREIGN KEY (assessment_id) REFERENCES assessment_results(id) ON DELETE CASCADE
);
