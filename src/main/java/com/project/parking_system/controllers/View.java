package com.project.parking_system.controllers;

public enum View {

    LOGIN("main_login.fxml"),
    TELLER_TAB("main_teller_tab.fxml"),
    ADMIN_TAB("main_admin_panel.fxml"),
    CLIENT_LIST_TAB("client_list.fxml"),
    PARKING_MAP("parking_map.fxml"),
    QR_GENERATE("qr_generate.fxml"),
    MAP_DIALOG("parking_map_dialog.fxml"),
    QR_MAIN_PANE("qr_scan.fxml"),
    QR_SCAN_NPARKED("qr_scan_nparked.fxml"),
    QR_SCAN_PARKED("qr_scan_parked.fxml"),
    ADD_ACCOUNT("add_account_dialog.fxml"),
    CHANGE_PASSWORD("change_password_dialog.fxml"),
    ANALYTICS("analytics.fxml"),
    QR_SCAN("qr_scan.fxml");


    private String filename;

    View(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return filename;
    }


}
