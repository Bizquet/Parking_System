package com.project.parking_system.datamodel;

public class SignOutDTO {
    private String timestamp;
    private String uid;
    public SignOutDTO(String timestamp, String uid) {
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
