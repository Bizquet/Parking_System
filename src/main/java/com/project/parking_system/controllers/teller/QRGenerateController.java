package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QRGenerateController implements Initializable {

    private LoginDTO currentLogin;

    public void initData(LoginDTO currentLogin){
        this.currentLogin = currentLogin;
    }

    // Refresh Page after successful generation of QR
    @FXML
    private TextField txtContactField;

    @FXML
    private TextField txtPlateField;

    @FXML
    private AnchorPane qrPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtContactField.setText("Testing 1");
        txtPlateField.setText("Testing 2");
    }

    @FXML
    public void showMap() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.MAP_DIALOG.getFilename()));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initOwner(qrPane.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Parking Map");
        stage.setScene(new Scene(root));
        MapDialogController dialogController = loader.getController();
        dialogController.initData(stage);
        stage.showAndWait();

    }

    public void generateQr(){

    }


}
