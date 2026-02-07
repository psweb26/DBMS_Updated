package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttemptResponseDTO {

    private Integer attemptId;
    private Integer userId;
    private Integer examId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public AttemptResponseDTO() {}

    public AttemptResponseDTO(Integer attemptId, Integer userId, Integer examId,
                              LocalDateTime startTime, LocalDateTime endTime, Integer score, String status) {
        this.attemptId = attemptId;
        this.userId = userId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.status = status;
    }

    // Getters and Setters
    public Integer getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "AttemptResponseDTO{" +
                "attemptId=" + attemptId +
                ", userId=" + userId +
                ", examId=" + examId +
                ", status='" + status + '\'' +
                ", score=" + score +
                '}';
    }
}
