package com.project.parking_system.datamodel;

public class ResponseDTO {
    private String message;
    private boolean status;
    private Object payload;

    public ResponseDTO(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ResponseDTO(String message, boolean status, Object payload) {
        this.message = message;
        this.status = status;
        this.payload = payload;
    }

    public ResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
