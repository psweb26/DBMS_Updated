package com.exampleonlineexamination.twd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exampleonlineexamination.twd.model.Result;
import com.exampleonlineexamination.twd.service.ResultService;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable Long id) {
        Result result = resultService.getResultById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<Result> getResultByAttemptId(@PathVariable Integer attemptId) {
        Result result = resultService.getResultByAttemptId(attemptId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Result>> getResultsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(resultService.getResultsByGrade(grade));
    }

    @GetMapping("/passed/{percentage}")
    public ResponseEntity<List<Result>> getPassedResults(@PathVariable Float percentage) {
        return ResponseEntity.ok(resultService.getPassedResults(percentage));
    }

    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody Result result) {
        Result created = resultService.addResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/calculate")
    public ResponseEntity<Result> calculateResult(@RequestParam Integer attemptId, 
                                              @RequestParam Integer totalMarks,
                                              @RequestParam Integer maxMarks) {
        Result result = resultService.calculateAndSaveResult(attemptId, totalMarks, maxMarks);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(@PathVariable Long id, @RequestBody Result result) {
        Result updated = resultService.updateResult(id, result);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }
}
