module com.project.parking_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.parking_system to javafx.fxml;
//    opens com.project.parking_system.teller_tabs to javafx.fxml;
    exports com.project.parking_system;
    exports com.project.parking_system.controllers;
    exports com.project.parking_system.controllers.teller;
    exports com.project.parking_system.controllers.admin;
    opens com.project.parking_system.controllers to javafx.fxml;
    opens com.project.parking_system.controllers.teller to javafx.fxml;
    opens com.project.parking_system.controllers.admin to javafx.fxml;
    opens com.project.parking_system.datamodel;

}