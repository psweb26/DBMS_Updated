package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.Question;
import com.exampleonlineexamination.twd.service.QuestionService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.QuestionRequestDTO;
import com.exampleonlineexamination.twd.dto.QuestionResponseDTO;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Get all questions
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getAllQuestions() {
        logger.info("Fetching all questions");
        try {
            List<Question> questions = questionService.getAllQuestions();
            List<QuestionResponseDTO> response = questions.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching questions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching questions", null, 500));
        }
    }

    /**
     * Get question by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable Integer id) {
        logger.info("Fetching question with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid question ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            Question question = questionService.getQuestionById(id);
            if (question != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(question), 200));
            } else {
                logger.warn("Question not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Question not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching question with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching question", null, 500));
        }
    }

    /**
     * Get all questions for an exam
     */
    @GetMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getQuestionsByExamId(@PathVariable Integer examId) {
        logger.info("Fetching questions for exam ID: {}", examId);

        if (examId == null || examId <= 0) {
            logger.warn("Invalid exam ID: {}", examId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid exam ID", null, 400));
        }

        try {
            List<Question> questions = questionService.getQuestionsByExamId(examId);
            List<QuestionResponseDTO> response = questions.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching questions for exam: {}", examId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching questions", null, 500));
        }
    }

    /**
     * Create new question
     */
    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(@Valid @RequestBody QuestionRequestDTO questionRequest) {
        logger.info("Creating new question for exam ID: {}", questionRequest.getExamId());

        try {
            Question question = convertToEntity(questionRequest);
            Question created = questionService.addQuestion(question);
            logger.info("Question created successfully with ID: {}", created.getQuestionId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Question created successfully", convertToResponseDTO(created), 201));
        } catch (Exception e) {
            logger.error("Error creating question", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating question", null, 500));
        }
    }

    /**
     * Update question
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(
            @PathVariable Integer id,
            @Valid @RequestBody QuestionRequestDTO questionRequest) {
        logger.info("Updating question with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid question ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            Question existingQuestion = questionService.getQuestionById(id);
            if (existingQuestion == null) {
                logger.warn("Question not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Question not found", null, 404));
            }

            Question question = convertToEntity(questionRequest);
            question.setQuestionId(id);
            Question updated = questionService.updateQuestion(id, question);
            logger.info("Question updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Question updated successfully", convertToResponseDTO(updated), 200));
        } catch (Exception e) {
            logger.error("Error updating question with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating question", null, 500));
        }
    }

    /**
     * Delete question
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Integer id) {
        logger.info("Deleting question with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid question ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            Question question = questionService.getQuestionById(id);
            if (question == null) {
                logger.warn("Question not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Question not found", null, 404));
            }

            questionService.deleteQuestion(id);
            logger.info("Question deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("Question deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting question with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting question", null, 500));
        }
    }

    /**
     * Convert Question entity to QuestionResponseDTO
     */
    private QuestionResponseDTO convertToResponseDTO(Question question) {
        if (question == null) return null;
        return new QuestionResponseDTO(
                question.getQuestionId(),
                question.getExamId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getMarks()
        );
    }

    /**
     * Convert QuestionRequestDTO to Question entity
     */
    private Question convertToEntity(QuestionRequestDTO dto) {
        if (dto == null) return null;
        Question question = new Question();
        question.setExamId(dto.getExamId());
        question.setQuestionText(dto.getQuestionText());
        question.setQuestionType(dto.getQuestionType());
        question.setMarks(dto.getMarks());
        return question;
    }
}