package com.project.parking_system.controllers.teller;

import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.datamodel.LoginDTO;

public class ClientListController {

    private LoginDTO currentLogin;

    public void initData(LoginDTO currentLogin){
        this.currentLogin = currentLogin;
    }



}

// create task to populate table