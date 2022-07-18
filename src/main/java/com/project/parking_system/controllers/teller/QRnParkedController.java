package com.project.parking_system.controllers.teller;

import com.project.parking_system.datamodel.LoginDTO;
import com.project.parking_system.datamodel.UserDTO;

public class QRnParkedController {
    // just use the parking dialog class here (basically the same)

    private UserDTO currentCustomer;

    public void initData(UserDTO currentCustomer){
        this.currentCustomer = currentCustomer;
    }

}
