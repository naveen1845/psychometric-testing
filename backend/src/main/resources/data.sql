-- ====================================================================
-- Career Compass - Seed Data (data.sql)
-- Preloaded Questions and Career Profiles
-- ====================================================================

-- 15 Predefined Likert Questions across 5 dimensions
INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy solving logical problems.', 'ANALYTICAL_THINKING', 1, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 1);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like identifying patterns in information.', 'ANALYTICAL_THINKING', 2, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 2);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy breaking complicated problems into smaller parts.', 'ANALYTICAL_THINKING', 3, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 3);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy developing original ideas.', 'CREATIVITY', 4, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 4);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like designing or creating new things.', 'CREATIVITY', 5, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 5);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy finding alternative solutions to a problem.', 'CREATIVITY', 6, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 6);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy explaining concepts to other people.', 'SOCIAL_ORIENTATION', 7, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 7);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like helping people solve problems.', 'SOCIAL_ORIENTATION', 8, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 8);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy collaborating with others.', 'SOCIAL_ORIENTATION', 9, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 9);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I am comfortable taking responsibility for tasks.', 'LEADERSHIP_AND_INITIATIVE', 10, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 10);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy organizing activities or projects.', 'LEADERSHIP_AND_INITIATIVE', 11, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 11);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like making decisions when a group needs direction.', 'LEADERSHIP_AND_INITIATIVE', 12, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 12);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy learning by building or experimenting.', 'PRACTICAL_HANDS_ON', 13, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 13);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I like understanding how technical systems work.', 'PRACTICAL_HANDS_ON', 14, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 14);

INSERT INTO questions (question_type, question_text, dimension, display_order, scale_min, scale_max, min_label, max_label)
SELECT 'LIKERT', 'I enjoy turning theoretical ideas into working solutions.', 'PRACTICAL_HANDS_ON', 15, 1, 5, 'Strongly Disagree', 'Strongly Agree'
WHERE NOT EXISTS (SELECT 1 FROM questions WHERE display_order = 15);

-- 5 Predefined Career Profiles
INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Software Developer',
       'Designs, builds, tests, and maintains robust software applications and system architectures to solve complex business and technical challenges.',
       'Java, Spring Boot, Object-Oriented Design, Data Structures & Algorithms, Git, REST APIs, SQL',
       'Your high scores in Analytical Thinking and Practical Hands-on Orientation align strongly with core software engineering tasks: writing clean code, architectural problem-solving, and systematic debugging.',
       '1. Build a mini project in Java or Python applying OOP principles.\n2. Practice algorithmic problems on LeetCode or HackerRank.\n3. Learn version control using Git and GitHub workflows.',
       5, 4, 2, 2, 5
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Software Developer');

INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Data Analyst',
       'Inspects, cleans, transforms, and models data to uncover meaningful patterns, generate visual reports, and guide data-informed decision-making.',
       'SQL, Python/R, Statistical Modeling, Data Visualization (Tableau/Power BI), Excel, Analytical Thinking',
       'Your elevated Analytical Thinking and Hands-on problem-solving reflect the quantitative inquiry required to dissect datasets, detect anomalies, and derive strategic insights.',
       '1. Learn SQL query formulation and database schema design.\n2. Analyze public datasets on Kaggle or government open data portals.\n3. Build an interactive visual dashboard in Power BI or Google Looker Studio.',
       5, 3, 3, 2, 4
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Data Analyst');

INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'UX/UI Designer',
       'Creates intuitive, aesthetically pleasing, and user-centric interfaces for mobile and web products through research, wireframing, and usability testing.',
       'Figma, Wireframing & Prototyping, User Research, Visual Hierarchy, Design Systems, Empathy',
       'Your high Creativity coupled with strong Social Orientation provides the essential empathy to understand user pain points and design engaging, human-centered experiences.',
       '1. Complete a UI design course using Figma.\n2. Redesign an existing mobile app screen to solve an identified usability flaw.\n3. Study Nielsen Norman Group usability heuristics and design principles.',
       3, 5, 4, 2, 3
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'UX/UI Designer');

INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Teacher or Trainer',
       'Educates, mentors, and develops individuals or corporate teams by breaking down intricate subjects into engaging, digestible learning journeys.',
       'Public Speaking, Curriculum Development, Active Listening, Mentoring, Educational Pedagogy, Empathy',
       'Your standout Social Orientation and Leadership skills make you naturally effective at communicating concepts patiently, inspiring students, and facilitating collaborative growth.',
       '1. Conduct peer-tutoring sessions or seminar presentations in your college.\n2. Write a step-by-step explanatory tutorial or record a 5-minute technical explainer video.\n3. Participate in Toastmasters or public speaking workshops.',
       3, 3, 5, 4, 2
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Teacher or Trainer');

INSERT INTO career_profiles (career_name, description, relevant_skills, matching_explanation, learning_activities, weight_analytical, weight_creativity, weight_social, weight_leadership, weight_practical)
SELECT 'Project Manager',
       'Plans, coordinates, and steers cross-functional teams to deliver projects on schedule and within scope while mitigating risks and aligning stakeholders.',
       'Agile & Scrum Methodologies, Team Leadership, Risk Management, Stakeholder Communication, Scheduling & Budgeting',
       'Your strong Leadership and Social Orientation traits reflect the interpersonal diplomacy, organizational vision, and decisiveness needed to coordinate multi-disciplinary teams.',
       '1. Learn Agile and Scrum fundamentals (e.g. Jira or Trello project boards).\n2. Lead a college group project or club event and document the sprint plan.\n3. Study risk identification and mitigation strategies in software engineering.',
       3, 3, 4, 5, 3
WHERE NOT EXISTS (SELECT 1 FROM career_profiles WHERE career_name = 'Project Manager');
