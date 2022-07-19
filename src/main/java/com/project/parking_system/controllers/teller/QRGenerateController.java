package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class QRGenerateController implements Initializable {

    // Refresh Page after successful generation of QR
    @FXML
    private TextField txtContactField;

    @FXML
    private TextField txtPlateField;

    @FXML
    private AnchorPane qrPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void openParkingMap() throws IOException {

        if(!txtFieldHasErrors()){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.MAP_DIALOG.getFilename()));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initOwner(qrPane.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Parking Map");
            stage.setScene(new Scene(root));
            MapDialogController dialogController = loader.getController();
            dialogController.initData(packInfo());
            stage.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inputs");
            alert.setContentText("Please input correct text in fields");
            alert.showAndWait();

        }

    }

    private UserDTO packInfo(){

        String contactNum = txtContactField.getText();
        String plateNum = txtPlateField.getText();
        Timestamp ts = Timestamp.from(Instant.now());
        UUID uid = UUID.randomUUID();

        return new UserDTO(ts.toString(), contactNum, plateNum, uid.toString());

    }

    // create functions to check if there are errors in textfield.

    private boolean txtFieldHasErrors(){
        return (txtContactField.getText().trim().isEmpty() || txtPlateField.getText().trim().isEmpty());
    }

}
