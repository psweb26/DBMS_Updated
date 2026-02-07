package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponseDTO {

    private Integer questionId;
    private Integer examId;
    private String questionText;
    private String questionType;
    private Integer marks;
    private Long createdAt;
    private Long updatedAt;

    // Constructors
    public QuestionResponseDTO() {}

    public QuestionResponseDTO(Integer questionId, Integer examId, String questionText,
                               String questionType, Integer marks) {
        this.questionId = questionId;
        this.examId = examId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.marks = marks;
    }

    // Getters and Setters
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
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
        return "QuestionResponseDTO{" +
                "questionId=" + questionId +
                ", examId=" + examId +
                ", questionType='" + questionType + '\'' +
                ", marks=" + marks +
                '}';
    }
}