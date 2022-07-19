package com.project.parking_system.controllers.teller;

import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.SigninDTO;
import com.project.parking_system.datamodel.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class OldCustomerParkingMap implements Initializable {

    @FXML
    private AnchorPane oldParkingMap;
    private String uid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    public void initData(String uid){
        this.uid = uid;
    }

    private void initializeButtons(){
        ArrayList<String> buttonStringList = new ArrayList<>();
        ArrayList<Button> buttonList = new ArrayList<>();
        for (Node node : oldParkingMap.getChildren()) {
            Button currentButton = (Button) node;
            String buttonText = currentButton.getText();
            buttonList.add(currentButton);
            buttonStringList.add(buttonText);
        }

        ArrayList<UserDTO> userList = getAllParkedUsers();
        ArrayList<String> takenSlotList = new ArrayList<>(); // make custom list with not null stuff to test

        for (UserDTO userDTO : userList) {

            takenSlotList.add(userDTO.getParking_slot());
            for (String slot : buttonStringList) {
                if (takenSlotList.contains(slot)) {
                    for (Button button : buttonList) {
                        // setButtonDisable
                        if (Objects.equals(button.getText(), slot)) {
                            button.setDisable(true);
                        }
                    }
                }
            }

        }
    }

    private ArrayList<UserDTO> getAllParkedUsers(){
        TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
        List<UserDTO> userDTOList = new ArrayList<>((List<UserDTO>) tellerOperation
                .getAllParkedUsers(LoginController.tokenString).getPayload());

        return new ArrayList<>(userDTOList);

    }

    @FXML
    public void getSelectedSlot(javafx.event.ActionEvent event){

        String source = ((Button) event.getSource()).getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecting Slot");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to choose: " + source + " as parking slot?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Slot Taken");
            alert2.setContentText("Successfully Occupied Slot: " + source);
            alert2.setHeaderText(null);

            Optional<ButtonType> result2 = alert2.showAndWait();
            if(result2.isPresent() && result2.get() == ButtonType.OK){
                signInUser(source);
            }
        }
    }

    private void signInUser(String parkingSlot){
        TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
        Timestamp ts = Timestamp.from(Instant.now());

        SigninDTO signInDTO = new SigninDTO(ts.toString(), uid, parkingSlot);
        tellerOperation.signInUser(signInDTO, LoginController.tokenString);

        closeWindow();
    }

    private void closeWindow(){
        oldParkingMap.getScene().getWindow().hide();
    }

}
