package com.exampleonlineexamination.twd.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.exampleonlineexamination.twd.model.Result;
import com.exampleonlineexamination.twd.repository.ResultRepository;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public Result getResultByAttemptId(Integer attemptId) {
        return resultRepository.findByAttemptId(attemptId).orElse(null);
    }

    public List<Result> getResultsByGrade(String grade) {
        return resultRepository.findByGrade(grade);
    }

    public List<Result> getPassedResults(Float passingPercentage) {
        return resultRepository.findByPercentageGreaterThanEqual(passingPercentage);
    }

    public Result addResult(Result result) {
        if (result.getPublishedAt() == null) {
            result.setPublishedAt(LocalDateTime.now());
        }
        return resultRepository.save(result);
    }

    public Result calculateAndSaveResult(Integer attemptId, Integer totalMarks, Integer maxMarks) {
        Float percentage = (totalMarks.floatValue() / maxMarks.floatValue()) * 100;
        String grade = calculateGrade(percentage);
        
        Result result = new Result();
        result.setAttemptId(attemptId);
        result.setTotalMarks(totalMarks);
        result.setPercentage(percentage);
        result.setGrade(grade);
        result.setPublishedAt(LocalDateTime.now());
        
        return resultRepository.save(result);
    }

    private String calculateGrade(Float percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    public Result updateResult(Long id, Result resultDetails) {
        Result result = resultRepository.findById(id).orElse(null);
        if (result != null) {
            result.setAttemptId(resultDetails.getAttemptId());
            result.setTotalMarks(resultDetails.getTotalMarks());
            result.setPercentage(resultDetails.getPercentage());
            result.setGrade(resultDetails.getGrade());
            result.setPublishedAt(resultDetails.getPublishedAt());
            return resultRepository.save(result);
        }
        return null;
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    public boolean resultExistsForAttempt(Integer attemptId) {
        return resultRepository.existsByAttemptId(attemptId);
    }
}
