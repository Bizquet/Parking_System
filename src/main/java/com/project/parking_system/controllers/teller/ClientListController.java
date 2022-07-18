package com.project.parking_system.controllers.teller;

import com.project.parking_system.Repositories.AdminOperationRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.UserDTO;
import com.project.parking_system.datamodel.UserDTOFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientListController implements Initializable {

    @FXML
    private TableView clientTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Task<ObservableList<UserDTOFX>> task = new GetAllUsersTask(LoginController.token);
        clientTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
}

class GetAllUsersTask extends Task {
    protected final AdminOperationRepository adminOperation = new AdminOperationRepository();
    protected String token;

    GetAllUsersTask(String token){
        this.token = token;
    }

    @Override
    protected ObservableList<UserDTOFX> call() throws Exception {
        List<UserDTO> userDTOList = new ArrayList<>((List<UserDTO>) adminOperation
                .getAllEmployees(token).getPayload());

        List<UserDTOFX> userDTOFXList = new ArrayList<>();

        for(UserDTO user: userDTOList){
            UserDTOFX userDTOFX = new UserDTOFX();
            userDTOFX.setContactNum(user.getContactNum());
            userDTOFX.setParking_slot(user.getParking_slot());
            userDTOFX.setPlateNum(user.getPlateNum());

            userDTOFXList.add(userDTOFX);
        }

        return FXCollections.observableArrayList(userDTOFXList);
    }
}