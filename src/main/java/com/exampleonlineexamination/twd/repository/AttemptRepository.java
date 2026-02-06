package com.exampleonlineexamination.twd.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exampleonlineexamination.twd.model.Attempt;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    List<Attempt> findByUserId(Integer userId);
    
    List<Attempt> findByExamId(Integer examId);
    
    List<Attempt> findByUserIdAndExamId(Integer userId, Integer examId);
    
    List<Attempt> findByStatus(String status);
}
