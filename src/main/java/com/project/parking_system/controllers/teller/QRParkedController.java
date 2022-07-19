package com.project.parking_system.controllers.teller;

import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.SignOutDTO;
import com.project.parking_system.datamodel.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

public class QRParkedController implements Initializable {
    private UserDTO currentCustomer;
    @FXML
    private TextArea txtDetails;

    private Timestamp ts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get details
        // populate textfield
        this.ts = Timestamp.from(Instant.now());
        populateTextField();

    }

    public void initData(UserDTO currentCustomer){
        this.currentCustomer = currentCustomer;
    }

    private void populateTextField(){

        String slotString = "Parking Slot: " + currentCustomer.getParking_slot();
        String contactString = "Contact Num: " + currentCustomer.getContactNum();
        String timeInString = "Time in: " + currentCustomer.getTimeStamp();
        String timeOutString = "Time out: " + ts;

        String infoString = slotString + "\n" + contactString + "\n" + timeInString + "\n" + timeOutString;

        txtDetails.setText(infoString);

    }

    @FXML
    public void checkout(){

        TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
        SignOutDTO signOutDTO = new SignOutDTO(ts.toString(), currentCustomer.getUid());
        tellerOperation.signOutUser(signOutDTO, LoginController.tokenString);

    }

}
