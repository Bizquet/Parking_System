package com.project.parking_system.controllers.teller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.project.parking_system.Main;
import com.project.parking_system.Repositories.TellerOperationsRepository;
import com.project.parking_system.controllers.LoginController;
import com.project.parking_system.controllers.View;
import com.project.parking_system.datamodel.UserDTO;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class QRScanController implements Initializable {
    @FXML
    private BorderPane qrMainPane;

    @FXML
    BorderPane bpWebCamPaneHolder;

    @FXML
    ImageView imgWebCamCapturedImage;

    private StackPane contentArea;

    private Webcam webcam;
    private boolean stopCamera = false;
    BufferedImage grabbedImage;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    public void initData(StackPane pane){
        this.contentArea = pane;
    }

    // not sure if need to pass in actionEvent parameter
    private void switchToNParked(UserDTO currentCustomer) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_SCAN_NPARKED.getFilename()));
        Parent root = loader.load();

        QRnParkedController controller = loader.getController();
        controller.initData(currentCustomer);

        Stage stage = (Stage) qrMainPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        contentArea.getChildren().removeAll();
//        contentArea.getChildren().setAll(root);

    }

    private void switchToParked(UserDTO currentCustomer) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.QR_SCAN_PARKED.getFilename()));
        Parent root = loader.load();

        QRParkedController controller = loader.getController();
        controller.initData(currentCustomer);
        controller.populateTextField();

        Stage stage = (Stage) qrMainPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        contentArea.getChildren().removeAll();
//        contentArea.getChildren().setAll(root);

    }

//    private void backToMainTeller() throws IOException {
//        FXMLLoader loader = new FXMLLoader(Main.class.getResource(View.TELLER_TAB.getFilename()));
//        Parent root = loader.load();
//
//        Stage stage = (Stage) qrMainPane.getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWebcam();
    }

    protected void initWebcam(){
        Task<Void> webCamInitialize = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                webcam = Webcam.getWebcams().get(0);
                startCamera();
                setImageViewSize();
                streamCamera();
                return null;
            }
        };

        new Thread(webCamInitialize).start();

    }

    protected void setImageViewSize() {

        double height = bpWebCamPaneHolder.getHeight();
        double width = bpWebCamPaneHolder.getWidth();
        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);
    }

    protected void streamCamera(){
        stopCamera = false;
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try{
                        if ((grabbedImage = webcam.getImage()) != null) {
                            Platform.runLater(() -> {
                                final Image mainImage = SwingFXUtils
                                        .toFXImage(grabbedImage, null);
                                imageProperty.set(mainImage);

                                try {
                                    readQR();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            grabbedImage.flush();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    protected void readQR() throws IOException {

        Result result = null;

        LuminanceSource source = new BufferedImageLuminanceSource(grabbedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            result = new MultiFormatReader().decode(bitmap);

        } catch (NotFoundException ignored) {

        }

        if(result != null){

            System.out.println("Read String: " + result.toString());

            ObjectMapper mapper = new ObjectMapper();
            UserDTO qrInfo = mapper.readValue(result.toString(), UserDTO.class);
            stopCamera();

            UserDTO currentCustomer = getUserInfo(qrInfo.getUid());

            if(currentCustomer.getParking_slot() == null || Objects.equals(currentCustomer.getParking_slot(), "")){
                switchToNParked(currentCustomer);
//                backToMainTeller();
            } else{
                switchToParked(currentCustomer);
//                backToMainTeller();
            }

        }
    }

    private UserDTO getUserInfo(String uid){

        TellerOperationsRepository tellerOperations = new TellerOperationsRepository();
        return (UserDTO) tellerOperations.getUserInfo(uid, LoginController.tokenString).getPayload();

    }

    private void startCamera(){
        stopCamera = false;
        webcam.open();
    }

    @FXML
    private void stopCamera(){
        stopCamera = true;
        webcam.close();
    }
}
