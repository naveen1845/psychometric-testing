package com.careercompass.config;

import com.careercompass.model.AssessmentResult;
import com.careercompass.model.CareerProfile;
import com.careercompass.model.CareerRecommendation;
import com.careercompass.model.Dimension;
import com.careercompass.model.LikertQuestion;
import com.careercompass.model.Response;
import com.careercompass.model.Student;
import com.careercompass.model.TraitScore;
import com.careercompass.repository.AssessmentResultRepository;
import com.careercompass.repository.CareerProfileRepository;
import com.careercompass.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Automatically initializes database seed data upon Spring Boot startup.
 * Idempotent: checks table counts first to avoid duplicating or overwriting data.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final QuestionRepository questionRepository;
    private final CareerProfileRepository careerProfileRepository;
    private final AssessmentResultRepository assessmentResultRepository;

    public DataInitializer(QuestionRepository questionRepository,
                           CareerProfileRepository careerProfileRepository,
                           AssessmentResultRepository assessmentResultRepository) {
        this.questionRepository = questionRepository;
        this.careerProfileRepository = careerProfileRepository;
        this.assessmentResultRepository = assessmentResultRepository;
    }

    @Override
    public void run(String... args) {
        initializeQuestions();
        initializeCareerProfiles();
        initializeDemoAssessmentResult();
    }

    private void initializeQuestions() {
        if (questionRepository.count() > 0) {
            logger.info("Questions already initialized (Count: {}). Skipping seed.", questionRepository.count());
            return;
        }

        logger.info("Seeding 15 predefined assessment questions across 5 dimensions...");
        List<LikertQuestion> questions = new ArrayList<>();

        // Dimension 1: Analytical Thinking (Q1 - Q3)
        questions.add(new LikertQuestion(null, "I enjoy solving logical problems.",
                Dimension.ANALYTICAL_THINKING, 1));
        questions.add(new LikertQuestion(null, "I like identifying patterns in information.",
                Dimension.ANALYTICAL_THINKING, 2));
        questions.add(new LikertQuestion(null, "I enjoy breaking complicated problems into smaller parts.",
                Dimension.ANALYTICAL_THINKING, 3));

        // Dimension 2: Creativity (Q4 - Q6)
        questions.add(new LikertQuestion(null, "I enjoy developing original ideas.",
                Dimension.CREATIVITY, 4));
        questions.add(new LikertQuestion(null, "I like designing or creating new things.",
                Dimension.CREATIVITY, 5));
        questions.add(new LikertQuestion(null, "I enjoy finding alternative solutions to a problem.",
                Dimension.CREATIVITY, 6));

        // Dimension 3: Social Orientation (Q7 - Q9)
        questions.add(new LikertQuestion(null, "I enjoy explaining concepts to other people.",
                Dimension.SOCIAL_ORIENTATION, 7));
        questions.add(new LikertQuestion(null, "I like helping people solve problems.",
                Dimension.SOCIAL_ORIENTATION, 8));
        questions.add(new LikertQuestion(null, "I enjoy collaborating with others.",
                Dimension.SOCIAL_ORIENTATION, 9));

        // Dimension 4: Leadership and Initiative (Q10 - Q12)
        questions.add(new LikertQuestion(null, "I am comfortable taking responsibility for tasks.",
                Dimension.LEADERSHIP_AND_INITIATIVE, 10));
        questions.add(new LikertQuestion(null, "I enjoy organizing activities or projects.",
                Dimension.LEADERSHIP_AND_INITIATIVE, 11));
        questions.add(new LikertQuestion(null, "I like making decisions when a group needs direction.",
                Dimension.LEADERSHIP_AND_INITIATIVE, 12));

        // Dimension 5: Practical and Hands-on Orientation (Q13 - Q15)
        questions.add(new LikertQuestion(null, "I enjoy learning by building or experimenting.",
                Dimension.PRACTICAL_HANDS_ON, 13));
        questions.add(new LikertQuestion(null, "I like understanding how technical systems work.",
                Dimension.PRACTICAL_HANDS_ON, 14));
        questions.add(new LikertQuestion(null, "I enjoy turning theoretical ideas into working solutions.",
                Dimension.PRACTICAL_HANDS_ON, 15));

        questionRepository.saveAll(questions);
        logger.info("Successfully seeded {} assessment questions.", questions.size());
    }

    private void initializeCareerProfiles() {
        if (careerProfileRepository.count() > 0) {
            logger.info("Career profiles already initialized (Count: {}). Skipping seed.", careerProfileRepository.count());
            return;
        }

        logger.info("Seeding 5 predefined career profiles with dimension weights...");
        List<CareerProfile> profiles = new ArrayList<>();

        // 1. Software Developer
        profiles.add(new CareerProfile(
                null,
                "Software Developer",
                "Designs, builds, tests, and maintains robust software applications and system architectures to solve complex business and technical challenges.",
                "Java, Spring Boot, Object-Oriented Design, Data Structures & Algorithms, Git, REST APIs, SQL",
                "Your high scores in Analytical Thinking and Practical Hands-on Orientation align strongly with core software engineering tasks: writing clean code, architectural problem-solving, and systematic debugging.",
                "1. Build a mini project in Java or Python applying OOP principles.\n2. Practice algorithmic problems on LeetCode or HackerRank.\n3. Learn version control using Git and GitHub workflows.",
                5, // Analytical
                4, // Creativity
                2, // Social
                2, // Leadership
                5  // Practical
        ));

        // 2. Data Analyst
        profiles.add(new CareerProfile(
                null,
                "Data Analyst",
                "Inspects, cleans, transforms, and models data to uncover meaningful patterns, generate visual reports, and guide data-informed decision-making.",
                "SQL, Python/R, Statistical Modeling, Data Visualization (Tableau/Power BI), Excel, Analytical Thinking",
                "Your elevated Analytical Thinking and Hands-on problem-solving reflect the quantitative inquiry required to dissect datasets, detect anomalies, and derive strategic insights.",
                "1. Learn SQL query formulation and database schema design.\n2. Analyze public datasets on Kaggle or government open data portals.\n3. Build an interactive visual dashboard in Power BI or Google Looker Studio.",
                5, // Analytical
                3, // Creativity
                3, // Social
                2, // Leadership
                4  // Practical
        ));

        // 3. UX/UI Designer
        profiles.add(new CareerProfile(
                null,
                "UX/UI Designer",
                "Creates intuitive, aesthetically pleasing, and user-centric interfaces for mobile and web products through research, wireframing, and usability testing.",
                "Figma, Wireframing & Prototyping, User Research, Visual Hierarchy, Design Systems, Empathy",
                "Your high Creativity coupled with strong Social Orientation provides the essential empathy to understand user pain points and design engaging, human-centered experiences.",
                "1. Complete a UI design course using Figma.\n2. Redesign an existing mobile app screen to solve an identified usability flaw.\n3. Study Nielsen Norman Group usability heuristics and design principles.",
                3, // Analytical
                5, // Creativity
                4, // Social
                2, // Leadership
                3  // Practical
        ));

        // 4. Teacher or Trainer
        profiles.add(new CareerProfile(
                null,
                "Teacher or Trainer",
                "Educates, mentors, and develops individuals or corporate teams by breaking down intricate subjects into engaging, digestible learning journeys.",
                "Public Speaking, Curriculum Development, Active Listening, Mentoring, Educational Pedagogy, Empathy",
                "Your standout Social Orientation and Leadership skills make you naturally effective at communicating concepts patiently, inspiring students, and facilitating collaborative growth.",
                "1. Conduct peer-tutoring sessions or seminar presentations in your college.\n2. Write a step-by-step explanatory tutorial or record a 5-minute technical explainer video.\n3. Participate in Toastmasters or public speaking workshops.",
                3, // Analytical
                3, // Creativity
                5, // Social
                4, // Leadership
                2  // Practical
        ));

        // 5. Project Manager
        profiles.add(new CareerProfile(
                null,
                "Project Manager",
                "Plans, coordinates, and steers cross-functional teams to deliver projects on schedule and within scope while mitigating risks and aligning stakeholders.",
                "Agile & Scrum Methodologies, Team Leadership, Risk Management, Stakeholder Communication, Scheduling & Budgeting",
                "Your strong Leadership and Social Orientation traits reflect the interpersonal diplomacy, organizational vision, and decisiveness needed to coordinate multi-disciplinary teams.",
                "1. Learn Agile and Scrum fundamentals (e.g. Jira or Trello project boards).\n2. Lead a college group project or club event and document the sprint plan.\n3. Study risk identification and mitigation strategies in software engineering.",
                3, // Analytical
                3, // Creativity
                4, // Social
                5, // Leadership
                3  // Practical
        ));

        careerProfileRepository.saveAll(profiles);
        logger.info("Successfully seeded {} career profiles.", profiles.size());
    }

    private void initializeDemoAssessmentResult() {
        if (assessmentResultRepository.count() > 0) {
            return;
        }

        logger.info("Seeding 1 demonstration assessment record for viva and test evaluation...");
        Student demoStudent = new Student("Demo Student (Preloaded Sample)", "MCA 1st Year");

        // 15 Sample Answers (favoring Analytical & Practical)
        List<Response> sampleAnswers = new ArrayList<>();
        // Analytical (5, 5, 4) -> raw = 14
        sampleAnswers.add(new Response(1L, 5));
        sampleAnswers.add(new Response(2L, 5));
        sampleAnswers.add(new Response(3L, 4));
        // Creativity (4, 4, 3) -> raw = 11
        sampleAnswers.add(new Response(4L, 4));
        sampleAnswers.add(new Response(5L, 4));
        sampleAnswers.add(new Response(6L, 3));
        // Social (3, 3, 2) -> raw = 8
        sampleAnswers.add(new Response(7L, 3));
        sampleAnswers.add(new Response(8L, 3));
        sampleAnswers.add(new Response(9L, 2));
        // Leadership (3, 2, 3) -> raw = 8
        sampleAnswers.add(new Response(10L, 3));
        sampleAnswers.add(new Response(11L, 2));
        sampleAnswers.add(new Response(12L, 3));
        // Practical (5, 5, 5) -> raw = 15
        sampleAnswers.add(new Response(13L, 5));
        sampleAnswers.add(new Response(14L, 5));
        sampleAnswers.add(new Response(15L, 5));

        // Calculated scores: Normalized = ((Raw - 3) / 12) * 100
        List<TraitScore> sampleScores = List.of(
                new TraitScore(Dimension.ANALYTICAL_THINKING, 14, 92),
                new TraitScore(Dimension.CREATIVITY, 11, 67),
                new TraitScore(Dimension.SOCIAL_ORIENTATION, 8, 42),
                new TraitScore(Dimension.LEADERSHIP_AND_INITIATIVE, 8, 42),
                new TraitScore(Dimension.PRACTICAL_HANDS_ON, 15, 100)
        );

        List<CareerRecommendation> sampleRecommendations = List.of(
                new CareerRecommendation(
                        1L, "Software Developer", 78, 1,
                        "Designs, builds, tests, and maintains robust software applications...",
                        "Java, Spring Boot, Object-Oriented Design, Data Structures",
                        "High scores in Analytical Thinking and Practical Hands-on Orientation align strongly with coding and architecture.",
                        "Build a mini project in Java; practice coding on LeetCode."
                ),
                new CareerRecommendation(
                        2L, "Data Analyst", 71, 2,
                        "Inspects, cleans, transforms, and models data to uncover meaningful patterns...",
                        "SQL, Python/R, Statistical Modeling, Data Visualization",
                        "Elevated Analytical Thinking and Hands-on problem-solving reflect quantitative inquiry.",
                        "Learn SQL query formulation; analyze open datasets on Kaggle."
                ),
                new CareerRecommendation(
                        3L, "UX/UI Designer", 57, 3,
                        "Creates intuitive, aesthetically pleasing, and user-centric interfaces...",
                        "Figma, Wireframing & Prototyping, User Research",
                        "Creativity and structured problem solving support interface layout.",
                        "Complete a UI design course using Figma; study UX heuristics."
                )
        );

        AssessmentResult demoResult = new AssessmentResult(demoStudent, sampleAnswers, sampleScores, sampleRecommendations);
        assessmentResultRepository.save(demoResult);
        logger.info("Demo assessment result seeded successfully (ID: {}).", demoResult.getId());
    }
}
