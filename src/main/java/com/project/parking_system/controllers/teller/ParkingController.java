package com.project.parking_system.controllers.teller;

import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ParkingController {

    // on initialize refresh buttons with api calls on which are taken
    @FXML
    public void showParkingDialog(ActionEvent e){
        // can be used in getting source of button to put into dialog
        String source = ((Button)e.getSource()).getText();
        System.out.println("Button pressed is: " + source);
    }
}
