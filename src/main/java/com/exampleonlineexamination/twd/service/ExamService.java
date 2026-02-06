package com.exampleonlineexamination.twd.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.exampleonlineexamination.twd.model.Exam;
import com.exampleonlineexamination.twd.repository.ExamRepository;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Integer id) {
        return examRepository.findById(id).orElse(null);
    }

    public List<Exam> getExamsByCreator(Integer createdBy) {
        return examRepository.findByCreatedBy(createdBy);
    }

    public List<Exam> getExamsBySubject(String subject) {
        return examRepository.findBySubject(subject);
    }

    public List<Exam> getActiveExams() {
        return examRepository.findByEndTimeAfter(LocalDateTime.now());
    }

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam updateExam(Integer id, Exam examDetails) {
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam != null) {
            exam.setTitle(examDetails.getTitle());
            exam.setSubject(examDetails.getSubject());
            exam.setDuration(examDetails.getDuration());
            exam.setTotalMarks(examDetails.getTotalMarks());
            exam.setStartTime(examDetails.getStartTime());
            exam.setEndTime(examDetails.getEndTime());
            exam.setCreatedBy(examDetails.getCreatedBy());
            return examRepository.save(exam);
        }
        return null;
    }

    public void deleteExam(Integer id) {
        examRepository.deleteById(id);
    }
}
