package com.careercompass.repository;

import com.careercompass.model.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {

    /**
     * Retrieve all historical assessments sorted by newest first.
     */
    List<AssessmentResult> findAllByOrderByCreatedAtDesc();
}
