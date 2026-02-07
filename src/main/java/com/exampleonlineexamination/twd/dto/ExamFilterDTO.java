package com.exampleonlineexamination.twd.dto;

import java.time.LocalDateTime;

public class ExamFilterDTO {

    private String subject;
    private Integer minMarks;
    private Integer maxMarks;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer createdBy;
    private String sortBy;
    private String sortOrder;
    private int pageNumber;
    private int pageSize;

    // Constructors
    public ExamFilterDTO() {
        this.sortOrder = "ASC";
        this.pageNumber = 0;
        this.pageSize = 10;
    }

    // Getters and Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getMinMarks() {
        return minMarks;
    }

    public void setMinMarks(Integer minMarks) {
        this.minMarks = minMarks;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ExamFilterDTO{" +
                "subject='" + subject + '\'' +
                ", minMarks=" + minMarks +
                ", maxMarks=" + maxMarks +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}