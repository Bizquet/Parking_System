package com.project.parking_system.controllers.teller;

import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.UserDTO;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ParkingController implements Initializable {
    @FXML
    private AnchorPane parkingMapPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    // on initialize refresh buttons with api calls on which are taken
    @FXML
    public void showFreeAlert(ActionEvent e){
        // can be used in getting source of button to put into dialog
        String source = ((Button)e.getSource()).getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Parking Slot Status");
        alert.setHeaderText(null);
        alert.setContentText("Slot: " + source + " is currently free");
        alert.showAndWait();

    }

    // Get all occupied slots and disables respective button (should color on parking controller
    private void initializeButtons(){
        ArrayList<String> buttonStringList = new ArrayList<>();
        ArrayList<Button> buttonList = new ArrayList<>();

        for(Node node: parkingMapPane.getChildren()){
            Button currentButton = (Button) node;
            String buttonText = currentButton.getText();
            buttonList.add(currentButton);
            buttonStringList.add(buttonText);
        }

        Task<ArrayList<UserDTO>> task = new GetAllSlots(LoginController.tokenString);
        ArrayList<UserDTO> userList = task.getValue();
        new Thread(task).start();

        ArrayList<String> takenSlotList = new ArrayList<>(); // make custom list with not null stuff to test

//         Getting all taken slots and put into ArrayList
        if(!(userList == null)){

            for(UserDTO userDTO: userList){
                if(userDTO.getParking_slot() != null){
                    takenSlotList.add(userDTO.getParking_slot());
                }
            }
            // uncomment to test taken slots
//            takenSlotList.add("A1");
//            takenSlotList.add("B1");
//            takenSlotList.add("A2");
//            takenSlotList.add("A4");

            for(String slot: buttonStringList){
                if(takenSlotList.contains(slot)){
                    for(Button button: buttonList){
                        // setButtonDisable
                        if(Objects.equals(button.getText(), slot)){
                            button.setDisable(true);
                        }
                    }
                }
            }

        }
    }


}

// create task to get all parked users
class GetAllSlots extends Task{
    protected final TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
    protected String token;

    GetAllSlots(String token){
        this.token = token;
    }

    @Override
    protected ArrayList<UserDTO> call() throws Exception {
        List<UserDTO> userDTOList = new ArrayList<>((List<UserDTO>) tellerOperation
                .getAllParkedUsers(token).getPayload());

        return new ArrayList<>(userDTOList);
    }
}