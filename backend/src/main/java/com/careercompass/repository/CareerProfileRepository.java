package com.careercompass.repository;

import com.careercompass.model.CareerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CareerProfileRepository extends JpaRepository<CareerProfile, Long> {

    /**
     * Find a career profile by its unique career name.
     */
    Optional<CareerProfile> findByCareerName(String careerName);
}
