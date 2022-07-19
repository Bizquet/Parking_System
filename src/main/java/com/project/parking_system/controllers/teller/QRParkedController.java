package com.project.parking_system.controllers.teller;

import com.project.parking_system.Main;
import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.SignOutDTO;
import com.project.parking_system.datamodel.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class QRParkedController{
    private UserDTO currentCustomer;
    @FXML
    private TextArea txtDetails;

    private Timestamp ts;

    @FXML
    private AnchorPane parkedPane;

    public void initialize(){
        this.ts = Timestamp.from(Instant.now());
    }

    public void initData(UserDTO currentCustomer){
        this.currentCustomer = currentCustomer;
    }

    public void populateTextField(){

        String slotString = "Parking Slot: " + currentCustomer.getParking_slot();
        String contactString = "Contact Num: " + currentCustomer.getContactNum();
        String timeInString = "Time in: " + currentCustomer.getTimeStamp();
        String timeOutString = "Time out: " + ts;

        String infoString = slotString + "\n" + contactString + "\n" + timeInString + "\n" + timeOutString;

        txtDetails.setText(infoString);

    }

    private void backToMainTeller() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.TELLER_TAB.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) parkedPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void checkout() throws IOException {

        TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
        SignOutDTO signOutDTO = new SignOutDTO(ts.toString(), currentCustomer.getUid());
        tellerOperation.signOutUser(signOutDTO, LoginController.tokenString);
        backToMainTeller();

    }

}
