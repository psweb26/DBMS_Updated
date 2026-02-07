package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.Exam;
import com.exampleonlineexamination.twd.service.ExamService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.ExamRequestDTO;
import com.exampleonlineexamination.twd.dto.ExamResponseDTO;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * Get all exams
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExamResponseDTO>>> getAllExams() {
        logger.info("Fetching all exams");
        try {
            List<Exam> exams = examService.getAllExams();
            List<ExamResponseDTO> response = exams.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching exams", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching exams", null, 500));
        }
    }

    /**
     * Get exam by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> getExamById(@PathVariable Integer id) {
        logger.info("Fetching exam with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid exam ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid exam ID", null, 400));
        }

        try {
            Exam exam = examService.getExamById(id);
            if (exam != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(exam), 200));
            } else {
                logger.warn("Exam not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Exam not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching exam with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching exam", null, 500));
        }
    }

    /**
     * Get exams by creator
     */
    @GetMapping("/creator/{createdBy}")
    public ResponseEntity<ApiResponse<List<ExamResponseDTO>>> getExamsByCreator(@PathVariable Integer createdBy) {
        logger.info("Fetching exams created by user ID: {}", createdBy);

        if (createdBy == null || createdBy <= 0) {
            logger.warn("Invalid creator ID: {}", createdBy);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid creator ID", null, 400));
        }

        try {
            List<Exam> exams = examService.getExamsByCreator(createdBy);
            List<ExamResponseDTO> response = exams.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching exams by creator: {}", createdBy, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching exams", null, 500));
        }
    }

    /**
     * Get exams by subject
     */
    @GetMapping("/subject/{subject}")
    public ResponseEntity<ApiResponse<List<ExamResponseDTO>>> getExamsBySubject(@PathVariable String subject) {
        logger.info("Fetching exams with subject: {}", subject);

        if (subject == null || subject.trim().isEmpty()) {
            logger.warn("Empty subject provided");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Subject cannot be empty", null, 400));
        }

        try {
            List<Exam> exams = examService.getExamsBySubject(subject.trim());
            List<ExamResponseDTO> response = exams.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching exams by subject: {}", subject, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching exams", null, 500));
        }
    }

    /**
     * Get active exams (currently running)
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ExamResponseDTO>>> getActiveExams() {
        logger.info("Fetching active exams");
        try {
            List<Exam> exams = examService.getActiveExams();
            List<ExamResponseDTO> response = exams.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching active exams", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching active exams", null, 500));
        }
    }

    /**
     * Create new exam
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ExamResponseDTO>> createExam(@Valid @RequestBody ExamRequestDTO examRequest) {
        logger.info("Creating new exam: {}", examRequest.getTitle());

        try {
            if (examRequest.getStartTime().isAfter(examRequest.getEndTime())) {
                logger.warn("Start time after end time");
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("Start time must be before end time", null, 400));
            }

            Exam exam = convertToEntity(examRequest);
            Exam created = examService.createExam(exam);
            logger.info("Exam created successfully with ID: {}", created.getExamId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Exam created successfully", convertToResponseDTO(created), 201));
        } catch (Exception e) {
            logger.error("Error creating exam", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating exam", null, 500));
        }
    }

    /**
     * Update exam
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> updateExam(
            @PathVariable Integer id,
            @Valid @RequestBody ExamRequestDTO examRequest) {
        logger.info("Updating exam with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid exam ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid exam ID", null, 400));
        }

        try {
            Exam existingExam = examService.getExamById(id);
            if (existingExam == null) {
                logger.warn("Exam not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Exam not found", null, 404));
            }

            if (examRequest.getStartTime().isAfter(examRequest.getEndTime())) {
                logger.warn("Start time after end time");
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("Start time must be before end time", null, 400));
            }

            Exam exam = convertToEntity(examRequest);
            exam.setExamId(id);
            Exam updated = examService.updateExam(id, exam);
            logger.info("Exam updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Exam updated successfully", convertToResponseDTO(updated), 200));
        } catch (Exception e) {
            logger.error("Error updating exam with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating exam", null, 500));
        }
    }

    /**
     * Delete exam
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExam(@PathVariable Integer id) {
        logger.info("Deleting exam with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid exam ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid exam ID", null, 400));
        }

        try {
            Exam exam = examService.getExamById(id);
            if (exam == null) {
                logger.warn("Exam not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Exam not found", null, 404));
            }

            examService.deleteExam(id);
            logger.info("Exam deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("Exam deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting exam with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting exam", null, 500));
        }
    }

    /**
     * Convert Exam entity to ExamResponseDTO
     */
    private ExamResponseDTO convertToResponseDTO(Exam exam) {
        if (exam == null) return null;
        return new ExamResponseDTO(
                exam.getExamId(),
                exam.getTitle(),
                exam.getSubject(),
                exam.getDuration(),
                exam.getTotalMarks(),
                exam.getStartTime(),
                exam.getEndTime(),
                exam.getCreatedBy()
        );
    }

    /**
     * Convert ExamRequestDTO to Exam entity
     */
    private Exam convertToEntity(ExamRequestDTO dto) {
        if (dto == null) return null;
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setSubject(dto.getSubject());
        exam.setDuration(dto.getDuration());
        exam.setTotalMarks(dto.getTotalMarks());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setCreatedBy(dto.getCreatedBy());
        return exam;
    }
}