package com.project.parking_system.controllers.teller;

import com.project.parking_system.datamodel.LoginDTO;

public class QRParkedController {
    // use api call to get details and use string builder to populate info
    private LoginDTO currentLogin;

    public void initData(LoginDTO currentLogin){
        this.currentLogin = currentLogin;
    }
}
