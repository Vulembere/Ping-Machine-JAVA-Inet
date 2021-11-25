/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author MC
 */
public class LoadMachinesController implements Initializable {

    public static String name;
    public static String ip;
    public static String count;

    @FXML
    private Label txtName;
    @FXML
    private Label txtIp;
    @FXML
    private Label txtEtat;
    @FXML
    private Label txtCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtIp.setText(ip);
        txtName.setText(name);
        txtCount.setText(count);
        synchro();
    }

    public void synchro() {

        final Service<Void> SetService = new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        while (true) {
                            String etat_;
                            if (Inet.isPing(txtIp.getText(), 500)) {
                                  etat_ = "Connected";
                                txtEtat.setText("Connected");
                                txtEtat.setStyle("-fx-background-color: #227940;-fx-background-radius:20;");
                                String name = Inet.getName(txtIp.getText());
                                if (name == null) {
                                    connexion.execute("update host set etat=1,name='Mobile' where code='" + txtCount.getText() + "'");
                                } else {
                                    connexion.execute("update host set etat=1,name='" + name + "' where code='" + txtCount.getText() + "'");
                                }
                                MainController.isLoad = true;
                                Platform.runLater(() -> {

                                    MainController.area.appendText(txtCount.getText() + " </> " + txtIp.getText() + " == [ " + etat_ + " ] / " + LocalTime.now().getHour() + "h " + LocalTime.now().getMinute() + "min\n");

                                });
                              
                            } else {
                                txtEtat.setText("Disconnected");
                                txtEtat.setStyle("-fx-background-color: #ff0000;-fx-background-radius:20;");
                                etat_ = "Disconnected";
                            }

                        }

                    }
                };
            }

        };
        SetService.stateProperty()
                       .addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
                           switch (newValue) {
                               case FAILED:

                                   break;
                               case CANCELLED:

                                   break;
                               case SUCCEEDED:
                             ;
                                   break;
                           }
                       }
                       );
        SetService.start();

//    void runR() {
//        try {
//            AnimationTimer tim = new AnimationTimer() {
//                @Override
//                public void handle(long now) {
//                    if (Inet.isPing(txtIp.getText(),500)) {
//                        System.err.println(txtIp.getText()+ " Connected !.");
//                    } else {
//                        System.err.println("disconnected !.");
//                    }
//                }
//            };
//            tim.start();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
