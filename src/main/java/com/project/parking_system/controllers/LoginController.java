package com.project.parking_system.controllers;

import com.project.parking_system.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField passField;

    @FXML
    public void login() throws IOException {
        // add authentication/API call


        if(txtName.getText().equals("teller")){
            switchToTeller();
        }else{
            switchToAdmin();
        }
        // go to Teller
        //switchToTeller();
        // go to Admin
        //switchToAdmin();
    }

    /**
     * Switches to the teller part of the app (might need to pass in account object or just api calls to store stuff)
     */
    private void switchToTeller() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.TELLER_TAB.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Switches to the admin part of the app (might need to pass in account object or just api calls to store stuff)
     */
    private void switchToAdmin() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.ADMIN_TAB.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}