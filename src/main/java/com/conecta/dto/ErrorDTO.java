package com.conecta.dto;


public class ErrorDTO {
    private String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorDTO fromException(Exception ex) {
        return new ErrorDTO(ex.getMessage());
    }
}
