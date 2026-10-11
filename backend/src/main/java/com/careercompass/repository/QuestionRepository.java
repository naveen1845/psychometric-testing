package com.careercompass.repository;

import com.careercompass.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Retrieves all assessment questions sorted in display order (1 to 15).
     */
    List<Question> findAllByOrderByDisplayOrderAsc();
}
