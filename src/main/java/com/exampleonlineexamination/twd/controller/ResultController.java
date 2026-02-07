package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.Result;
import com.exampleonlineexamination.twd.service.ResultService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.ResultRequestDTO;
import com.exampleonlineexamination.twd.dto.ResultResponseDTO;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * Get all results
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getAllResults() {
        logger.info("Fetching all results");
        try {
            List<Result> results = resultService.getAllResults();
            List<ResultResponseDTO> response = results.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching results", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching results", null, 500));
        }
    }

    /**
     * Get result by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> getResultById(@PathVariable Long id) {
        logger.info("Fetching result with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid result ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid result ID", null, 400));
        }

        try {
            Result result = resultService.getResultById(id);
            if (result != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(result), 200));
            } else {
                logger.warn("Result not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Result not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching result with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching result", null, 500));
        }
    }

    /**
     * Get result by attempt ID
     */
    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> getResultByAttemptId(@PathVariable Integer attemptId) {
        logger.info("Fetching result for attempt ID: {}", attemptId);

        if (attemptId == null || attemptId <= 0) {
            logger.warn("Invalid attempt ID: {}", attemptId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid attempt ID", null, 400));
        }

        try {
            Result result = resultService.getResultByAttemptId(attemptId);
            if (result != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(result), 200));
            } else {
                logger.warn("Result not found for attempt ID: {}", attemptId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Result not found for this attempt", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching result for attempt ID: {}", attemptId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching result", null, 500));
        }
    }

    /**
     * Get results by grade
     */
    @GetMapping("/grade/{grade}")
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getResultsByGrade(@PathVariable String grade) {
        logger.info("Fetching results with grade: {}", grade);

        if (grade == null || grade.trim().isEmpty()) {
            logger.warn("Empty grade provided");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Grade cannot be empty", null, 400));
        }

        if (!isValidGrade(grade)) {
            logger.warn("Invalid grade: {}", grade);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid grade. Valid grades are: A+, A, B, C, D, F", null, 400));
        }

        try {
            List<Result> results = resultService.getResultsByGrade(grade.toUpperCase());
            List<ResultResponseDTO> response = results.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching results by grade: {}", grade, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("