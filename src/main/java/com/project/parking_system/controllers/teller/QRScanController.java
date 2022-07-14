package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class QRScanController {
    @FXML
    private AnchorPane qrMainPane;

    private StackPane contentArea;

    public void initData(StackPane pane){
        this.contentArea = pane;
    }

    @FXML
    public void changeScene() throws IOException {
        //comment out each part
        switchToNParked();
//        switchToParked();
    }

    // not sure if need to pass in actionEvent parameter
    private void switchToNParked() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_SCAN_NPARKED.getFilename()));
        Parent root = loader.load();

        // controller if needed

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }

    private void switchToParked() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_SCAN_PARKED.getFilename()));
        Parent root = loader.load();

        // controller if needed

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }
}
