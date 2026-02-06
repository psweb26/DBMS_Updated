package com.exampleonlineexamination.twd.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.exampleonlineexamination.twd.model.Question;
import com.exampleonlineexamination.twd.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> getQuestionsByExamId(Integer examId) {
        return questionRepository.findByExamId(examId);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Integer id, Question questionDetails) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question != null) {
            question.setExamId(questionDetails.getExamId());
            question.setQuestionText(questionDetails.getQuestionText());
            question.setQuestionType(questionDetails.getQuestionType());
            question.setMarks(questionDetails.getMarks());
            return questionRepository.save(question);
        }
        return null;
    }

    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
