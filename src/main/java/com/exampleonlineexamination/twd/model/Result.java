package com.exampleonlineexamination.twd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @Column(name = "attempt_id", unique = true)
    private Integer attemptId;

    @Column(name = "total_marks")
    private Integer totalMarks;

    @Column(name = "percentage")
    private Float percentage;

    @Column(name = "grade", length = 255)
    private String grade;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    // Default constructor
    public Result() {}

    // Constructor with fields
    public Result(Integer attemptId, Integer totalMarks, Float percentage, 
                  String grade, LocalDateTime publishedAt) {
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

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", attemptId=" + attemptId +
                ", totalMarks=" + totalMarks +
                ", percentage=" + percentage +
                ", grade='" + grade + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
