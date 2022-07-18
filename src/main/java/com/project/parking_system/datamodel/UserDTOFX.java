package com.project.parking_system.datamodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserDTOFX {

    private SimpleStringProperty parking_slot = new SimpleStringProperty("");
    private SimpleDoubleProperty contactNum = new SimpleDoubleProperty();
    private SimpleStringProperty plateNum = new SimpleStringProperty("");

    public UserDTOFX(String parking_slot, double contactNum, String plateNum) {
        this.parking_slot.set(parking_slot);
        this.contactNum.set(contactNum);
        this.plateNum.set(plateNum);
    }

    public UserDTOFX() {
    }

    public String getParking_slot() {
        return parking_slot.get();
    }

    public SimpleStringProperty parking_slotProperty() {
        return parking_slot;
    }

    public void setParking_slot(String parking_slot) {
        this.parking_slot.set(parking_slot);
    }

    public double getContactNum() {
        return contactNum.get();
    }

    public SimpleDoubleProperty contactNumProperty() {
        return contactNum;
    }

    public void setContactNum(double contactNum) {
        this.contactNum.set(contactNum);
    }

    public String getPlateNum() {
        return plateNum.get();
    }

    public SimpleStringProperty plateNumProperty() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum.set(plateNum);
    }

}
