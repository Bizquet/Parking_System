package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class QRnParkedController {
    // just use the parking dialog class here (basically the same)

    private UserDTO currentCustomer;

    @FXML
    private AnchorPane parkedPane;

    public void initData(UserDTO currentCustomer){
        this.currentCustomer = currentCustomer;
    }

    @FXML
    public void openParkingMap() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.PARKED_MAP.getFilename()));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initOwner(parkedPane.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Parking Map");
        stage.setScene(new Scene(root));
        OldCustomerParkingMap dialogController = loader.getController();
        dialogController.initData(currentCustomer.getUid());
        stage.showAndWait();
    }

}
