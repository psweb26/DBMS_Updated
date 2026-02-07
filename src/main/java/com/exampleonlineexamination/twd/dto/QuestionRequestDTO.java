package com.exampleonlineexamination.twd.dto;

import jakarta.validation.constraints.*;

public class QuestionRequestDTO {

    @NotNull(message = "Exam ID is required")
    @Positive(message = "Exam ID must be greater than 0")
    private Integer examId;

    @NotBlank(message = "Question text is required")
    @Size(min = 5, max = 1000, message = "Question text must be between 5 and 1000 characters")
    private String questionText;

    @NotBlank(message = "Question type is required")
    @Pattern(regexp = "^(MCQ|SHORT_ANSWER|ESSAY|TRUE_FALSE|MATCH_COLUMN)$",
            message = "Question type must be MCQ, SHORT_ANSWER, ESSAY, TRUE_FALSE, or MATCH_COLUMN")
    private String questionType;

    @NotNull(message = "Marks is required")
    @Positive(message = "Marks must be greater than 0")
    private Integer marks;

    // Constructors
    public QuestionRequestDTO() {}

    public QuestionRequestDTO(Integer examId, String questionText, String questionType, Integer marks) {
        this.examId = examId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.marks = marks;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "QuestionRequestDTO{" +
                "examId=" + examId +
                ", questionText='" + questionText.substring(0, Math.min(20, questionText.length())) + "...'" +
                ", questionType='" + questionType + '\'' +
                ", marks=" + marks +
                '}';
    }
}