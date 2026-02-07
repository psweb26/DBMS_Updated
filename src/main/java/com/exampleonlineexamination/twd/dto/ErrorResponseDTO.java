package com.exampleonlineexamination.twd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {

    private String message;
    private int statusCode;
    private String timestamp;
    private String path;
    private Map<String, List<String>> errors;
    private String errorType;

    // Constructors
    public ErrorResponseDTO() {}

    public ErrorResponseDTO(String message, int statusCode, String errorType) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorType = errorType;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public ErrorResponseDTO(String message, int statusCode, String path, String errorType) {
        this.message = message;
        this.statusCode = statusCode;
        this.path = path;
        this.errorType = errorType;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        return "ErrorResponseDTO{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", errorType='" + errorType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}