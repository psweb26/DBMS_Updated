package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponseDTO {

    private Long resultId;
    private Integer attemptId;
    private Integer totalMarks;
    private Float percentage;
    private String grade;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ResultResponseDTO() {}

    public ResultResponseDTO(Long resultId, Integer attemptId, Integer totalMarks,
                             Float percentage, String grade, LocalDateTime publishedAt) {
        this.resultId = resultId;
        this.attemptId = attemptId;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.grade = grade;
        this.publishedAt = publishedAt;
    }

    // Getters and Setters
    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

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

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ResultResponseDTO{" +
                "resultId=" + resultId +
                ", attemptId=" + attemptId +
                ", totalMarks=" + totalMarks +
                ", percentage=" + percentage +
                ", grade='" + grade + '\'' +
                '}';
    }
}