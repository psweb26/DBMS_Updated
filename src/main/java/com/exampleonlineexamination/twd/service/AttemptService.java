package com.exampleonlineexamination.twd.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.exampleonlineexamination.twd.model.Attempt;
import com.exampleonlineexamination.twd.repository.AttemptRepository;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;

    public AttemptService(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    public List<Attempt> getAllAttempts() {
        return attemptRepository.findAll();
    }

    public Attempt getAttemptById(Integer id) {
        return attemptRepository.findById(id).orElse(null);
    }

    public List<Attempt> getAttemptsByUserId(Integer userId) {
        return attemptRepository.findByUserId(userId);
    }

    public List<Attempt> getAttemptsByExamId(Integer examId) {
        return attemptRepository.findByExamId(examId);
    }

    public List<Attempt> getAttemptsByUserAndExam(Integer userId, Integer examId) {
        return attemptRepository.findByUserIdAndExamId(userId, examId);
    }

    public Attempt startAttempt(Integer userId, Integer examId) {
        Attempt attempt = new Attempt();
        attempt.setUserId(userId);
        attempt.setExamId(examId);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus("IN_PROGRESS");
        return attemptRepository.save(attempt);
    }

    public Attempt submitAttempt(Integer attemptId, Integer score) {
        Attempt attempt = attemptRepository.findById(attemptId).orElse(null);
        if (attempt != null) {
            attempt.setEndTime(LocalDateTime.now());
            attempt.setScore(score);
            attempt.setStatus("COMPLETED");
            return attemptRepository.save(attempt);
        }
        return null;
    }

    public Attempt updateAttempt(Integer id, Attempt attemptDetails) {
        Attempt attempt = attemptRepository.findById(id).orElse(null);
        if (attempt != null) {
            attempt.setUserId(attemptDetails.getUserId());
            attempt.setExamId(attemptDetails.getExamId());
            attempt.setStartTime(attemptDetails.getStartTime());
            attempt.setEndTime(attemptDetails.getEndTime());
            attempt.setStatus(attemptDetails.getStatus());
            attempt.setScore(attemptDetails.getScore());
            return attemptRepository.save(attempt);
        }
        return null;
    }

    public void deleteAttempt(Integer id) {
        attemptRepository.deleteById(id);
    }
}
