package com.project.parking_system.controllers.admin;

import com.project.parking_system.datamodel.EmployeeRegistrationDTO;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddAccountDialogController {

    @FXML
    TextField txtUname;

    @FXML
    PasswordField passField;

    @FXML
    PasswordField passFieldConfirm;

    public boolean isSomeFieldEmpty(){
        return (txtUname.getText().trim().isEmpty() || passField.getText().trim().isEmpty() ||
        passFieldConfirm.getText().trim().isEmpty());

    }

    public boolean arePassMatching(){
        return passField.getText().equals(passFieldConfirm.getText());
    }


    public EmployeeRegistrationDTO getNewEmployeeDetails(){
        String username = txtUname.getText();
        String password = passField.getText();

        return new EmployeeRegistrationDTO(username, password, "TELLER_USER");
    }




}
