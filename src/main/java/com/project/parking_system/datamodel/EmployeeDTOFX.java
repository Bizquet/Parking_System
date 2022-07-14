package com.project.parking_system.datamodel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeDTOFX {

    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty role = new SimpleStringProperty("");
    private SimpleStringProperty username = new SimpleStringProperty("");

    public EmployeeDTOFX(Long id, String role, String username) {
        this.id.set(id);
        this.role.set(role);
        this.username.set(username);
    }

    public EmployeeDTOFX() {
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }
}
