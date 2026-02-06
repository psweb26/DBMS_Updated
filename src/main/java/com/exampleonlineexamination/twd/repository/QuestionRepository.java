package com.exampleonlineexamination.twd.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exampleonlineexamination.twd.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByExamId(Integer examId);
    
    List<Question> findByQuestionType(String questionType);
}
