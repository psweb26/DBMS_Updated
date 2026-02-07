package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.Attempt;
import com.exampleonlineexamination.twd.service.AttemptService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.AttemptRequestDTO;
import com.exampleonlineexamination.twd.dto.AttemptResponseDTO;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class AttemptController {

    private static final Logger logger = LoggerFactory.getLogger(AttemptController.class);
    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    /**
     * Get all attempts
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttemptResponseDTO>>> getAllAttempts() {
        logger.info("Fetching all attempts");
        try {
            List<Attempt> attempts = attemptService.getAllAttempts();
            List<AttemptResponseDTO> response = attempts.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching attempts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching attempts", null, 500));
        }
    }

    /**
     * Get attempt by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttemptResponseDTO>> getAttemptById(@PathVariable Integer id) {
        logger.info("Fetching attempt with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid attempt ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid attempt ID", null, 400));
        }

        try {
            Attempt attempt = attemptService.getAttemptById(id);
            if (attempt != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(attempt), 200));
            } else {
                logger.warn("Attempt not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Attempt not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching attempt with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching attempt", null, 500));
        }
    }

    /**
     * Get all attempts by user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AttemptResponseDTO>>> getAttemptsByUserId(@PathVariable Integer userId) {
        logger.info("Fetching attempts for user ID: {}", userId);

        if (userId == null || userId <= 0) {
            logger.warn("Invalid user ID: {}", userId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid user ID", null, 400));
        }

        try {
            List<Attempt> attempts = attemptService.getAttemptsByUserId(userId);
            List<AttemptResponseDTO> response = attempts.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching attempts for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching attempts", null, 500));
        }
    }

    /**
     * Get all attempts for an exam
     */
    @GetMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse<List<AttemptResponseDTO>>> getAttemptsByExamId(@PathVariable Integer examId) {
        logger.info("Fetching attempts for exam ID: {}", examId);

        if (examId == null || examId <= 0) {
            logger.warn("Invalid exam ID: {}", examId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid exam ID", null, 400));
        }

        try {
            List<Attempt> attempts = attemptService.getAttemptsByExamId(examId);
            List<AttemptResponseDTO> response = attempts.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching attempts for exam: {}", examId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching attempts", null, 500));
        }
    }

    /**
     * Start a new exam attempt
     */
    @PostMapping("/start")
    public ResponseEntity<ApiResponse<AttemptResponseDTO>> startAttempt(
            @Valid @RequestBody AttemptRequestDTO attemptRequest) {
        logger.info("Starting exam attempt for user ID: {} and exam ID: {}",
                attemptRequest.getUserId(), attemptRequest.getExamId());

        try {
            Attempt attempt = attemptService.startAttempt(attemptRequest.getUserId(), attemptRequest.getExamId());
            if (attempt != null) {
                logger.info("Exam attempt started successfully with ID: {}", attempt.getAttemptId());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse<>("Exam attempt started successfully", convertToResponseDTO(attempt), 201));
            } else {
                logger.warn("Failed to start attempt - possible duplicate or invalid data");
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("Failed to start attempt. User may have already attempted this exam.", null, 400));
            }
        } catch (Exception e) {
            logger.error("Error starting exam attempt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error starting exam attempt", null, 500));
        }
    }

    /**
     * Submit exam attempt
     */
    @PutMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<AttemptResponseDTO>> submitAttempt(
            @PathVariable Integer id,
            @RequestParam Integer score) {
        logger.info("Submitting exam attempt with ID: {}, score: {}", id, score);

        if (id == null || id <= 0) {
            logger.warn("Invalid attempt ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid attempt ID", null, 400));
        }

        if (score == null || score < 0) {
            logger.warn("Invalid score: {}", score);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Score cannot be negative", null, 400));
        }

        try {
            Attempt existingAttempt = attemptService.getAttemptById(id);
            if (existingAttempt == null) {
                logger.warn("Attempt not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Attempt not found", null, 404));
            }

            Attempt attempt = attemptService.submitAttempt(id, score);
            logger.info("Exam attempt submitted successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Exam attempt submitted successfully", convertToResponseDTO(attempt), 200));
        } catch (Exception e) {
            logger.error("Error submitting exam attempt with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error submitting exam attempt", null, 500));
        }
    }

    /**
     * Update attempt
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AttemptResponseDTO>> updateAttempt(
            @PathVariable Integer id,
            @Valid @RequestBody AttemptRequestDTO attemptRequest) {
        logger.info("Updating attempt with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid attempt ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid attempt ID", null, 400));
        }

        try {
            Attempt existingAttempt = attemptService.getAttemptById(id);
            if (existingAttempt == null) {
                logger.warn("Attempt not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Attempt not found", null, 404));
            }

            Attempt attempt = convertToEntity(attemptRequest);
            attempt.setAttemptId(id);
            Attempt updated = attemptService.updateAttempt(id, attempt);
            logger.info("Attempt updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Attempt updated successfully", convertToResponseDTO(updated), 200));
        } catch (Exception e) {
            logger.error("Error updating attempt with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating attempt", null, 500));
        }
    }

    /**
     * Delete attempt
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttempt(@PathVariable Integer id) {
        logger.info("Deleting attempt with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid attempt ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid attempt ID", null, 400));
        }

        try {
            Attempt attempt = attemptService.getAttemptById(id);
            if (attempt == null) {
                logger.warn("Attempt not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Attempt not found", null, 404));
            }

            attemptService.deleteAttempt(id);
            logger.info("Attempt deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("Attempt deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting attempt with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting attempt", null, 500));
        }
    }

    /**
     * Convert Attempt entity to AttemptResponseDTO
     */
    private AttemptResponseDTO convertToResponseDTO(Attempt attempt) {
        if (attempt == null) return null;
        return new AttemptResponseDTO(
                attempt.getAttemptId(),
                attempt.getUserId(),
                attempt.getExamId(),
                attempt.getStartTime(),
                attempt.getEndTime(),
                attempt.getScore(),
                attempt.getStatus()
        );
    }

    /**
     * Convert AttemptRequestDTO to Attempt entity
     */
    private Attempt convertToEntity(AttemptRequestDTO dto) {
        if (dto == null) return null;
        Attempt attempt = new Attempt();
        attempt.setUserId(dto.getUserId());
        attempt.setExamId(dto.getExamId());
        return attempt;
    }
}