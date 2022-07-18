package com.project.parking_system.controllers;

import com.project.parking_system.Main;
import com.project.parking_system.Repositories.AuthenticationRepository;
import com.project.parking_system.controllers.admin.MainAdminController;
import com.project.parking_system.controllers.teller.MainTellerController;
import com.project.parking_system.datamodel.LoginDTO;
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

    public static String token;

    private AuthenticationRepository auth = new AuthenticationRepository();
    private static LoginDTO currentLogin;

    @FXML
    public void login() throws IOException {
        // add authentication/API call
        currentLogin = auth.login(txtName.getText(), passField.getText());

        if(currentLogin == null){
            // Add alert dialog
            System.out.println("Wrong credentials");
            ;
        }else {
            token = currentLogin.getLogin_token();
            switch (currentLogin.getRole()){
                case "ADMIN_USER":
                    switchToAdmin(currentLogin);
                    break;
                case "TELLER_USER":
                    switchToTeller(currentLogin);
                    break;
                default:
                    currentLogin = null;
                    break;
            }
        }
    }

    /**
     * Switches to the teller part of the app (might need to pass in account object or just api calls to store stuff)
     */
    private void switchToTeller(LoginDTO currentLogin) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.TELLER_TAB.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Switches to the admin part of the app (might need to pass in account object or just api calls to store stuff)
     */
    private void switchToAdmin(LoginDTO currentLogin) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.ADMIN_TAB.getFilename()));
        Parent root = loader.load();

        MainAdminController controller = loader.getController();
        controller.initData();

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

