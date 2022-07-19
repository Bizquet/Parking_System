package com.project.parking_system.controllers.teller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class MapDialogController implements Initializable {

    public static final String PATH = "target/sample.jpg";

    @FXML
    private AnchorPane parkingMapDialogPane; // use this to close window
    private UserDTO currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    public void initData(UserDTO currentUser){
        this.currentUser = currentUser;
    }

    @FXML
    public void getSelectedSlot(javafx.event.ActionEvent event) throws IOException, WriterException {
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
                generateQR(source);
            }
        }

    }

    private void generateQR(String slot) throws IOException, WriterException {
        ObjectMapper mapper = new ObjectMapper();
        currentUser.setParking_slot(slot);

        String jsonString = mapper.writeValueAsString(currentUser);

        System.out.println(jsonString);

        BitMatrix matrix = new MultiFormatWriter().encode(jsonString, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(PATH));

        sendToRESTNewUser(currentUser);
    }

    private void initializeButtons(){
        ArrayList<String> buttonStringList = new ArrayList<>();
        ArrayList<Button> buttonList = new ArrayList<>();
        for (Node node : parkingMapDialogPane.getChildren()) {
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

    private void closeWindow(){
        parkingMapDialogPane.getScene().getWindow().hide();
    }

    private void sendToRESTNewUser(UserDTO currentUser){
        SigninDTO signinDTO = new SigninDTO(currentUser.getTimeStamp(), currentUser.getUid(),
                currentUser.getParking_slot());

        TellerOperationsRepository tellerOperation = new TellerOperationsRepository();
        tellerOperation.addUser(currentUser, LoginController.tokenString).getMessage(); // can cast to something to get error message and alert
        tellerOperation.signInUser(signinDTO, LoginController.tokenString).getMessage();

        // close current window after sending to rest
        closeWindow();
    }

}
