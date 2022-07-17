package com.project.parking_system.controllers.admin;

import com.project.parking_system.Main;
import com.project.parking_system.Repositories.AuthenticationRepository;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.EmployeeRegistrationDTO;
import com.project.parking_system.datamodel.LoginDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainAdminController {

    @FXML
    private BorderPane mainAdminPane;

    @FXML
//    private TableView<Teller> tellerTable;

    private LoginDTO currentLogin;
    private AuthenticationRepository auth;

    public void initData(LoginDTO currentLogin){
        this.currentLogin = currentLogin;
        this.auth = new AuthenticationRepository();
    }


    public void initialize(){
        // call task to populate table here
    }

    @FXML
    public void showAddAccountDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminPane.getScene().getWindow());
        dialog.setTitle("Add Account");
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.ADD_ACCOUNT.getFilename()));

        try{
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e){
            System.out.println("Can't load dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        AddAccountDialogController controller = loader.getController();

        if(result.isPresent() && result.get() == ButtonType.OK){
            if(controller.isSomeFieldEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Some Fields are Empty");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the missing fields");
                alert.showAndWait();
                return;
            } else if (!controller.arePassMatching()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Passwords do not match");
                alert.setHeaderText(null);
                alert.setContentText("Please enter matching passwords");
                alert.showAndWait();
                return;
            }

            EmployeeRegistrationDTO newEmployee = controller.getNewEmployeeDetails();
            auth.RegisterEmployee(newEmployee, currentLogin.getLogin_token());

        }
    }

    @FXML
    public void showDeleteAccountDialog(){
//        Example of getting selection from table
//        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
//
//        if(selectedContact == null){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("No Teller Selected");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select teller you want to delete");
//            alert.showAndWait();
//            return;
//        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Teller");
//        alert.setContentText("Are yous sure you want to delete the selected contact? " +
//                selectedContact.getFirstName() + " " + selectedContact.getLastName());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            // perform deletion
        }
    }

    @FXML
    public void showChangePasswordDialog(){
        //        Example of getting selection from table
//        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
//
//        if(selectedContact == null){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("No Teller Selected");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select teller to change the password delete");
//            alert.showAndWait();
//            return;
//        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminPane.getScene().getWindow());
        dialog.setTitle("Add Account");
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.CHANGE_PASSWORD.getFilename()));

        try{
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e){
            System.out.println("Can't load dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            // call controller and change password
        }

    }

    @FXML
    public void changeToAnalytics() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.ANALYTICS.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) mainAdminPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.LOGIN.getFilename()));
        Parent root = loader.load();

        Stage stage = (Stage) mainAdminPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}

// create task to populate table
