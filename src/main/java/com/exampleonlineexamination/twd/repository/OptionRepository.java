package com.exampleonlineexamination.twd.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exampleonlineexamination.twd.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

    List<Option> findByQuestionId(Integer questionId);
    
    List<Option> findByQuestionIdAndIsCorrect(Integer questionId, Boolean isCorrect);
}
