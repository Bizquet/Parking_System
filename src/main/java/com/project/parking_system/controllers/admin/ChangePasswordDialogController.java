package com.project.parking_system.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class ChangePasswordDialogController {

    @FXML
    PasswordField passNew;

    @FXML
    PasswordField passNewConfirm;

    public boolean isSomeFieldEmpty(){
        return (passNew.getText().trim().isEmpty() || passNewConfirm.getText().trim().isEmpty());
    }

    public boolean arePassMatching(){
        return passNew.getText().equals(passNewConfirm.getText());
    }

    public String getNewPassword(){
        return passNewConfirm.getText();
    }



}
