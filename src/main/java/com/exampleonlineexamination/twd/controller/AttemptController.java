package com.exampleonlineexamination.twd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exampleonlineexamination.twd.model.Attempt;
import com.exampleonlineexamination.twd.service.AttemptService;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping
    public ResponseEntity<List<Attempt>> getAllAttempts() {
        return ResponseEntity.ok(attemptService.getAllAttempts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attempt> getAttemptById(@PathVariable Integer id) {
        Attempt attempt = attemptService.getAttemptById(id);
        return attempt != null ? ResponseEntity.ok(attempt) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attempt>> getAttemptsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(attemptService.getAttemptsByUserId(userId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Attempt>> getAttemptsByExamId(@PathVariable Integer examId) {
        return ResponseEntity.ok(attemptService.getAttemptsByExamId(examId));
    }

    @PostMapping("/start")
    public ResponseEntity<Attempt> startAttempt(@RequestParam Integer userId, @RequestParam Integer examId) {
        Attempt attempt = attemptService.startAttempt(userId, examId);
        return ResponseEntity.status(HttpStatus.CREATED).body(attempt);
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<Attempt> submitAttempt(@PathVariable Integer id, @RequestParam Integer score) {
        Attempt attempt = attemptService.submitAttempt(id, score);
        return attempt != null ? ResponseEntity.ok(attempt) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attempt> updateAttempt(@PathVariable Integer id, @RequestBody Attempt attempt) {
        Attempt updated = attemptService.updateAttempt(id, attempt);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttempt(@PathVariable Integer id) {
        attemptService.deleteAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
