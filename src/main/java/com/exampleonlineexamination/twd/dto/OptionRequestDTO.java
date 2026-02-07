package com.exampleonlineexamination.twd.dto;

import jakarta.validation.constraints.*;

public class OptionRequestDTO {

    @NotNull(message = "Question ID is required")
    @Positive(message = "Question ID must be greater than 0")
    private Integer questionId;

    @NotBlank(message = "Option text is required")
    @Size(min = 2, max = 500, message = "Option text must be between 2 and 500 characters")
    private String optionText;

    @NotNull(message = "isCorrect flag is required")
    private Boolean isCorrect;

    // Constructors
    public OptionRequestDTO() {}

    public OptionRequestDTO(Integer questionId, String optionText, Boolean isCorrect) {
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "OptionRequestDTO{" +
                "questionId=" + questionId +
                ", optionText='" + optionText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}