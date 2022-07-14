package com.project.parking_system.datamodel;

import java.util.ArrayList;

public class UserDTOWithTimestampList {

    private String uid;
    private String parking_slot;
    private ArrayList<String> timeStamp;
    private double contactNum;
    private String plateNum;

    public UserDTOWithTimestampList() {
        this.timeStamp = new ArrayList<>();
    }

    public String getParking_slot() {
        return parking_slot;
    }

    public void setParking_slot(String parking_slot) {
        this.parking_slot = parking_slot;
    }

    public double getContactNum() {
        return contactNum;
    }

    public void setContactNum(double contactNum) {
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

    public ArrayList<String> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ArrayList<String> timeStamp) {
        this.timeStamp = timeStamp;
    }
}
