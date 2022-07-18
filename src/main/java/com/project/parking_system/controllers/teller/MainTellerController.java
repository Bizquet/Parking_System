package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainTellerController implements Initializable {

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane mainTabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.PARKING_MAP.getFilename()));
            Parent homePane = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(homePane);


        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void toParkingMap() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.PARKING_MAP.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }

    @FXML
    public void toClientList() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.CLIENT_LIST_TAB.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }

    @FXML
    public void toGenerateQR() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_GENERATE.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }

    @FXML
    public void toScanQR(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_SCAN.getFilename()));
        Parent root = loader.load();

        // load controller and init data if needed

        QRScanController controller = loader.getController();
        controller.initData(contentArea);

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);

    }

    @FXML
    public void logout() throws IOException{

        // REST API HERE

        // Code to switch to login
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.LOGIN.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) mainTabPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



}
