package com.exampleonlineexamination.twd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exampleonlineexamination.twd.model.Exam;
import com.exampleonlineexamination.twd.service.ExamService;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Integer id) {
        Exam exam = examService.getExamById(id);
        return exam != null ? ResponseEntity.ok(exam) : ResponseEntity.notFound().build();
    }

    @GetMapping("/creator/{createdBy}")
    public ResponseEntity<List<Exam>> getExamsByCreator(@PathVariable Integer createdBy) {
        return ResponseEntity.ok(examService.getExamsByCreator(createdBy));
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Exam>> getExamsBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(examService.getExamsBySubject(subject));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Exam>> getActiveExams() {
        return ResponseEntity.ok(examService.getActiveExams());
    }

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        Exam created = examService.createExam(exam);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Integer id, @RequestBody Exam exam) {
        Exam updated = examService.updateExam(id, exam);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Integer id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
