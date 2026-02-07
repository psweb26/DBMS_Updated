package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamResponseDTO {

    private Integer examId;
    private String title;
    private String subject;
    private Integer duration;
    private Integer totalMarks;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ExamResponseDTO() {}

    public ExamResponseDTO(Integer examId, String title, String subject, Integer duration,
                           Integer totalMarks, LocalDateTime startTime, LocalDateTime endTime, Integer createdBy) {
        this.examId = examId;
        this.title = title;
        this.subject = subject;
        this.duration = duration;
        this.totalMarks = totalMarks;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
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
        return "ExamResponseDTO{" +
                "examId=" + examId +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", duration=" + duration +
                ", totalMarks=" + totalMarks +
                '}';
    }
}