package com.project.parking_system.datamodel;

public class ResponseDTO {
    private String message;
    private boolean status;

    public ResponseDTO(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
