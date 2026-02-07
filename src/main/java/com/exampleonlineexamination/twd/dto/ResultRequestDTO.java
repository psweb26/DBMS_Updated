package com.exampleonlineexamination.twd.dto;

import jakarta.validation.constraints.*;

public class ResultRequestDTO {

    @NotNull(message = "Attempt ID is required")
    @Positive(message = "Attempt ID must be greater than 0")
    private Integer attemptId;

    @NotNull(message = "Total marks is required")
    @PositiveOrZero(message = "Total marks cannot be negative")
    private Integer totalMarks;

    @NotNull(message = "Percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private Float percentage;

    @NotBlank(message = "Grade is required")
    @Pattern(regexp = "^(A\\+|A|B|C|D|F)$", message = "Grade must be A+, A, B, C, D, or F")
    private String grade;

    // Constructors
    public ResultRequestDTO() {}

    public ResultRequestDTO(Integer attemptId, Integer totalMarks, Float percentage, String grade) {
        this.attemptId = attemptId;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.grade = grade;
    }

    // Getters and Setters
    public Integer getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ResultRequestDTO{" +
                "attemptId=" + attemptId +
                ", totalMarks=" + totalMarks +
                ", percentage=" + percentage +
                ", grade='" + grade + '\'' +
                '}';
    }
}