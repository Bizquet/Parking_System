module com.project.parking_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires webcam.capture;
    requires spring.web;
    requires com.google.gson;
    requires javafx.swing;
    requires java.desktop;
    requires java.sql;

    opens com.project.parking_system to javafx.fxml;
    exports com.project.parking_system;
    exports com.project.parking_system.controllers;
    exports com.project.parking_system.controllers.teller;
    exports com.project.parking_system.controllers.admin;
    opens com.project.parking_system.controllers to javafx.fxml;
    opens com.project.parking_system.controllers.teller to javafx.fxml;
    opens com.project.parking_system.controllers.admin to javafx.fxml;
    opens com.project.parking_system.datamodel;

}