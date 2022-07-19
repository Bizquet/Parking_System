package com.project.parking_system.controllers.teller;

import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.UserDTO;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParkingController {

//    @FXML
//    private Button btnA1;
//    @FXML
//    private Button btnA2;
//    @FXML
//    private Button btnA3;
//    @FXML
//    private Button btnA4;
//    @FXML
//    private Button btnB1;
//    @FXML
//    private Button btnB2;
//    @FXML
//    private Button btnB3;
//    @FXML
//    private Button btnB4;
//    @FXML
//    private Button btnC1;
//    @FXML
//    private Button btnC2;
//    @FXML
//    private Button btnC3;
//    @FXML
//    private Button btnC4;
//    @FXML
//    private Button btnD1;
//    @FXML
//    private Button btnD2;
//    @FXML
//    private Button btnD3;
//    @FXML
//    private Button btnD4;

    @FXML
    private AnchorPane parkingMapPane;

    public void initialize(){
        initializeButtons();
    }

    // on initialize refresh buttons with api calls on which are taken
    @FXML
    public void showFreeAlert(ActionEvent e){
        // can be used in getting source of button to put into dialog
        String source = ((Button)e.getSource()).getText();
        System.out.println("Button pressed is: " + source);
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


        // Getting all taken slots and put into ArrayList
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