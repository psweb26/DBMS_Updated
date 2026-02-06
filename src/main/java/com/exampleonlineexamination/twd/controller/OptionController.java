package com.exampleonlineexamination.twd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exampleonlineexamination.twd.model.Option;
import com.exampleonlineexamination.twd.service.OptionService;

@RestController
@RequestMapping("/api/options")
@CrossOrigin(origins = "*")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<Option>> getAllOptions() {
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable Integer id) {
        Option option = optionService.getOptionById(id);
        return option != null ? ResponseEntity.ok(option) : ResponseEntity.notFound().build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Option>> getOptionsByQuestionId(@PathVariable Integer questionId) {
        return ResponseEntity.ok(optionService.getOptionsByQuestionId(questionId));
    }

    @GetMapping("/question/{questionId}/correct")
    public ResponseEntity<List<Option>> getCorrectAnswers(@PathVariable Integer questionId) {
        return ResponseEntity.ok(optionService.getCorrectAnswers(questionId));
    }

    @PostMapping
    public ResponseEntity<Option> createOption(@RequestBody Option option) {
        Option created = optionService.addOption(option);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable Integer id, @RequestBody Option option) {
        Option updated = optionService.updateOption(id, option);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Integer id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteOptionsByQuestionId(@PathVariable Integer questionId) {
        optionService.deleteOptionsByQuestionId(questionId);
        return ResponseEntity.noContent().build();
    }
}
