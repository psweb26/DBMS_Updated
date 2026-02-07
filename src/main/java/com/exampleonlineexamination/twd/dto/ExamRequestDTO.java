package com.exampleonlineexamination.twd.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ExamRequestDTO {

    @NotBlank(message = "Exam title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @NotBlank(message = "Subject is required")
    @Size(min = 2, max = 100, message = "Subject must be between 2 and 100 characters")
    private String subject;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Total marks is required")
    @Positive(message = "Total marks must be greater than 0")
    private Integer totalMarks;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Creator ID is required")
    @Positive(message = "Creator ID must be greater than 0")
    private Integer createdBy;

    // Constructors
    public ExamRequestDTO() {}

    public ExamRequestDTO(String title, String subject, Integer duration, Integer totalMarks,
                          LocalDateTime startTime, LocalDateTime endTime, Integer createdBy) {
        this.title = title;
        this.subject = subject;
        this.duration = duration;
        this.totalMarks = totalMarks;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "ExamRequestDTO{" +
                "title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", duration=" + duration +
                ", totalMarks=" + totalMarks +
                '}';
    }
}