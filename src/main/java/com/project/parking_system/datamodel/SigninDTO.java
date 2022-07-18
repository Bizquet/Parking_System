package com.project.parking_system.datamodel;

public class SigninDTO {

    private String timestamp;
    private String uid;
    private String parkingslot;

    public SigninDTO(String timestamp, String uid, String parkingslot) {
        this.timestamp = timestamp;
        this.uid = uid;
        this.parkingslot = parkingslot;
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

    public String getParkingslot() {
        return parkingslot;
    }

    public void setParkingslot(String parkingslot) {
        this.parkingslot = parkingslot;
    }
}
