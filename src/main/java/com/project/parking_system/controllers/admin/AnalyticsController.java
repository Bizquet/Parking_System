package com.project.parking_system.controllers.admin;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AnalyticsController {

    @FXML
    private AnchorPane analyticsPane;

    public void initialize(){
        // initialize analytics
    }

    @FXML
    public void backToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.ADMIN_TAB.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) analyticsPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
