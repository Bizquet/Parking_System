package com.project.parking_system.controllers.teller;

import com.project.parking_system.datamodel.LoginDTO;
import com.project.parking_system.datamodel.UserDTO;

public class QRParkedController {
    private UserDTO currentCustomer;

    public void initData(UserDTO currentCustomer){
        this.currentCustomer = currentCustomer;
    }
}
