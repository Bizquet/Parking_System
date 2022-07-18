package com.project.parking_system.controllers.teller;

import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MapDialogController {

    private Stage stage;
    private LoginDTO currentLogin;

    // add more stuff like possibly some api calls
    public void initData(Stage stage){
        this.stage = stage;
    }

    // create function when finished call api

    // use when finish
    @FXML
    public void closeDialog(){
        stage.close();
    }
}
