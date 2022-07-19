package com.project.parking_system.datamodel;

public class UserDTO {

    private String parking_slot;
    private String timeStamp;
    private String contactNum;
    private String plateNum;
    private String uid;

    public UserDTO(String parking_slot, String timeStamp, String contactNum, String plateNum, String uid) {
        this.parking_slot = parking_slot;
        this.timeStamp = timeStamp;
        this.contactNum = contactNum;
        this.plateNum = plateNum;
        this.uid = uid;
    }

    public UserDTO(String timeStamp, String contactNum, String plateNum, String uid) {
        this.timeStamp = timeStamp;
        this.contactNum = contactNum;
        this.plateNum = plateNum;
        this.uid = uid;
    }

    public UserDTO() {
    }

    public String getParking_slot() {
        return parking_slot;
    }

    public void setParking_slot(String parking_slot) {
        this.parking_slot = parking_slot;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
