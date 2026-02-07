package com.exampleonlineexamination.twd.dto;

import jakarta.validation.constraints.*;

public class AttemptRequestDTO {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    @NotNull(message = "Exam ID is required")
    @Positive(message = "Exam ID must be greater than 0")
    private Integer examId;

    // Constructors
    public AttemptRequestDTO() {}

    public AttemptRequestDTO(Integer userId, Integer examId) {
        this.userId = userId;
        this.examId = examId;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    @Override
    public String toString() {
        return "AttemptRequestDTO{" +
                "userId=" + userId +
                ", examId=" + examId +
                '}';
    }
}