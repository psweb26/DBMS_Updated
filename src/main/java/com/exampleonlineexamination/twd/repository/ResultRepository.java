package com.exampleonlineexamination.twd.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exampleonlineexamination.twd.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByAttemptId(Integer attemptId);
    
    List<Result> findByGrade(String grade);
    
    List<Result> findByPercentageGreaterThanEqual(Float percentage);
    
    boolean existsByAttemptId(Integer attemptId);
}
