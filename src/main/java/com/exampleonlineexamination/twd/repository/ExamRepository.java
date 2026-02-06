package com.exampleonlineexamination.twd.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exampleonlineexamination.twd.model.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    List<Exam> findByCreatedBy(Integer createdBy);
    
    List<Exam> findBySubject(String subject);
    
    List<Exam> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    List<Exam> findByEndTimeAfter(LocalDateTime currentTime);
}
