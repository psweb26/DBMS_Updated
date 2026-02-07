package com.exampleonlineexamination.twd.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.exampleonlineexamination.twd.model.Option;
import com.exampleonlineexamination.twd.service.OptionService;
import com.exampleonlineexamination.twd.dto.ApiResponse;
import com.exampleonlineexamination.twd.dto.OptionRequestDTO;
import com.exampleonlineexamination.twd.dto.OptionResponseDTO;

@RestController
@RequestMapping("/api/options")
@CrossOrigin(origins = "*")
public class OptionController {

    private static final Logger logger = LoggerFactory.getLogger(OptionController.class);
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * Get all options
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OptionResponseDTO>>> getAllOptions() {
        logger.info("Fetching all options");
        try {
            List<Option> options = optionService.getAllOptions();
            List<OptionResponseDTO> response = options.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching options", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching options", null, 500));
        }
    }

    /**
     * Get option by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OptionResponseDTO>> getOptionById(@PathVariable Integer id) {
        logger.info("Fetching option with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid option ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid option ID", null, 400));
        }

        try {
            Option option = optionService.getOptionById(id);
            if (option != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", convertToResponseDTO(option), 200));
            } else {
                logger.warn("Option not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Option not found", null, 404));
            }
        } catch (Exception e) {
            logger.error("Error fetching option with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching option", null, 500));
        }
    }

    /**
     * Get all options for a question
     */
    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<List<OptionResponseDTO>>> getOptionsByQuestionId(@PathVariable Integer questionId) {
        logger.info("Fetching options for question ID: {}", questionId);

        if (questionId == null || questionId <= 0) {
            logger.warn("Invalid question ID: {}", questionId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            List<Option> options = optionService.getOptionsByQuestionId(questionId);
            List<OptionResponseDTO> response = options.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching options for question: {}", questionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching options", null, 500));
        }
    }

    /**
     * Get correct answer(s) for a question
     */
    @GetMapping("/question/{questionId}/correct")
    public ResponseEntity<ApiResponse<List<OptionResponseDTO>>> getCorrectAnswers(@PathVariable Integer questionId) {
        logger.info("Fetching correct answers for question ID: {}", questionId);

        if (questionId == null || questionId <= 0) {
            logger.warn("Invalid question ID: {}", questionId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            List<Option> correctAnswers = optionService.getCorrectAnswers(questionId);
            List<OptionResponseDTO> response = correctAnswers.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>("Success", response, 200));
        } catch (Exception e) {
            logger.error("Error fetching correct answers for question: {}", questionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching correct answers", null, 500));
        }
    }

    /**
     * Create new option
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OptionResponseDTO>> createOption(@Valid @RequestBody OptionRequestDTO optionRequest) {
        logger.info("Creating new option for question ID: {}", optionRequest.getQuestionId());

        try {
            Option option = convertToEntity(optionRequest);
            Option created = optionService.addOption(option);
            logger.info("Option created successfully with ID: {}", created.getOptionId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Option created successfully", convertToResponseDTO(created), 201));
        } catch (Exception e) {
            logger.error("Error creating option", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating option", null, 500));
        }
    }

    /**
     * Update option
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OptionResponseDTO>> updateOption(
            @PathVariable Integer id,
            @Valid @RequestBody OptionRequestDTO optionRequest) {
        logger.info("Updating option with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid option ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid option ID", null, 400));
        }

        try {
            Option existingOption = optionService.getOptionById(id);
            if (existingOption == null) {
                logger.warn("Option not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Option not found", null, 404));
            }

            Option option = convertToEntity(optionRequest);
            option.setOptionId(id);
            Option updated = optionService.updateOption(id, option);
            logger.info("Option updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Option updated successfully", convertToResponseDTO(updated), 200));
        } catch (Exception e) {
            logger.error("Error updating option with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating option", null, 500));
        }
    }

    /**
     * Delete option by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOption(@PathVariable Integer id) {
        logger.info("Deleting option with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid option ID: {}", id);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid option ID", null, 400));
        }

        try {
            Option option = optionService.getOptionById(id);
            if (option == null) {
                logger.warn("Option not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Option not found", null, 404));
            }

            optionService.deleteOption(id);
            logger.info("Option deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("Option deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting option with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting option", null, 500));
        }
    }

    /**
     * Delete all options for a question
     */
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteOptionsByQuestionId(@PathVariable Integer questionId) {
        logger.info("Deleting all options for question ID: {}", questionId);

        if (questionId == null || questionId <= 0) {
            logger.warn("Invalid question ID: {}", questionId);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Invalid question ID", null, 400));
        }

        try {
            List<Option> options = optionService.getOptionsByQuestionId(questionId);
            if (options.isEmpty()) {
                logger.warn("No options found for question ID: {}", questionId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("No options found for this question", null, 404));
            }

            optionService.deleteOptionsByQuestionId(questionId);
            logger.info("All options deleted for question ID: {}", questionId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>("All options deleted successfully", null, 204));
        } catch (Exception e) {
            logger.error("Error deleting options for question ID: {}", questionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error deleting options", null, 500));
        }
    }

    /**
     * Convert Option entity to OptionResponseDTO
     */
    private OptionResponseDTO convertToResponseDTO(Option option) {
        if (option == null) return null;
        return new OptionResponseDTO(
                option.getOptionId(),
                option.getQuestionId(),
                option.getOptionText(),
                option.getIsCorrect()
        );
    }

    /**
     * Convert OptionRequestDTO to Option entity
     */
    private Option convertToEntity(OptionRequestDTO dto) {
        if (dto == null) return null;
        Option option = new Option();
        option.setQuestionId(dto.getQuestionId());
        option.setOptionText(dto.getOptionText());
        option.setIsCorrect(dto.getIsCorrect());
        return option;
    }
}