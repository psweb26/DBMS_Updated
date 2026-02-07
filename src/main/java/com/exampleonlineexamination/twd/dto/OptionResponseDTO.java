package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionResponseDTO {

    private Integer optionId;
    private Integer questionId;
    private String optionText;
    private Boolean isCorrect;
    private Long createdAt;
    private Long updatedAt;

    // Constructors
    public OptionResponseDTO() {}

    public OptionResponseDTO(Integer optionId, Integer questionId, String optionText, Boolean isCorrect) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters
    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OptionResponseDTO{" +
                "optionId=" + optionId +
                ", questionId=" + questionId +
                ", optionText='" + optionText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}