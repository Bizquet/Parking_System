package com.project.parking_system.controllers.admin;

import com.project.parking_system.Main;
import com.project.parking_system.Repositories.AdminOperationRepository;
import com.project.parking_system.Repositories.AuthenticationRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.EmployeeDTO;
import com.project.parking_system.datamodel.EmployeeDTOFX;
import com.project.parking_system.datamodel.EmployeeRegistrationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainAdminController {

    @FXML
    private BorderPane mainAdminPane;

    @FXML
    private TableView tellerTable;
    private AuthenticationRepository auth;
    private AdminOperationRepository adminOp;

    public void initData(){
        this.auth = new AuthenticationRepository();
        this.adminOp = new AdminOperationRepository();

        refreshTable();

    }

    private void refreshTable(){
        Task<ObservableList<EmployeeDTOFX>> task = new GetAllEmployeesTask(LoginController.tokenString);
        tellerTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
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
            auth.RegisterEmployee(newEmployee, LoginController.tokenString);
            refreshTable();

        }
    }

    @FXML
    public void showDeleteAccountDialog(){


        EmployeeDTOFX selectedEmployeeFX = (EmployeeDTOFX) tellerTable.getSelectionModel().getSelectedItem();

        if(selectedEmployeeFX == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Teller Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select teller you want to delete");
            alert.showAndWait();
            return;
        }

        EmployeeDTO selectedEmployee = new EmployeeDTO(selectedEmployeeFX.getId(), selectedEmployeeFX.getRole(),
                selectedEmployeeFX.getUsername());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Teller");
        alert.setContentText("Are yous sure you want to delete the selected contact? " +
                selectedEmployee.getUsername() + " " + selectedEmployee.getRole());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            adminOp.DeleteEmployee(selectedEmployee, LoginController.tokenString);
            refreshTable();
        }
    }

    @FXML
    public void showChangePasswordDialog(){
        EmployeeDTOFX selectedEmployeeFX = (EmployeeDTOFX) tellerTable.getSelectionModel().getSelectedItem();

        if(selectedEmployeeFX == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Teller Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select teller you want to modify");
            alert.showAndWait();
            return;
        }

        EmployeeRegistrationDTO selectedEmployee = new EmployeeRegistrationDTO(selectedEmployeeFX.getUsername(),
                selectedEmployeeFX.getRole());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminPane.getScene().getWindow());
        dialog.setTitle("Change Password");
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

        ChangePasswordDialogController controller = loader.getController();

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
            selectedEmployee.setPassword(controller.getNewPassword());
            adminOp.ChangeEmployeePassword(selectedEmployee, LoginController.tokenString);
            refreshTable();
        }

    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.LOGIN.getFilename()));
        Parent root = loader.load();

        LoginController.tokenString = null;

        Stage stage = (Stage) mainAdminPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}

// create task to populate table
class GetAllEmployeesTask extends Task{
    protected final AdminOperationRepository adminOperation = new AdminOperationRepository();
    protected String token;

    GetAllEmployeesTask(String token){
        this.token = token;
    }

    @Override
    protected ObservableList<EmployeeDTOFX> call() throws Exception {

        List<EmployeeDTO> employeeDTOList = new ArrayList<>((List<EmployeeDTO>)
                adminOperation.getAllEmployees(token).getPayload());

        List<EmployeeDTOFX> employeeDTOFXList = new ArrayList<>();

        for(EmployeeDTO employee: employeeDTOList){
            EmployeeDTOFX employeeDTOFX = new EmployeeDTOFX();
            if(Objects.equals(employee.getRole(), "TELLER_USER")) {
                employeeDTOFX.setId(employee.getId());
                employeeDTOFX.setRole(employee.getRole());
                employeeDTOFX.setUsername(employee.getUsername());
                employeeDTOFXList.add(employeeDTOFX);
            }

        }

        return FXCollections.observableArrayList(employeeDTOFXList);
    }
}
