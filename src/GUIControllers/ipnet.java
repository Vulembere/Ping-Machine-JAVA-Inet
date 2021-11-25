/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import java.net.InetAddress;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.control.TextArea;

/**
 *
 * @author MC
 */
public class ipnet {

    public static String motif = null;
    static int is = 0;
    static int tour;
    static String ipAdresse = null;
    static String nameAdresse = null;

    public static void synchro(TextArea display) {

        final Service<Void> SetService = new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String inet = InetAddress.getLocalHost().getHostAddress();
                        inet = inet.replace(".", "/");
                        String[] tab = inet.split("/");

                        inet = tab[0] + "." + tab[1] + "." + tab[2];

                        for (int xx = 0; xx < 254; xx++) {
                            String ip_ = inet + "." + xx;
                            if (connexion.getValue("select code from host where ip='" + ip_ + "' ") == null) {
                                connexion.execute("INSERT INTO host(ip,name) VALUES (?,?);", ip_, null);
                            }
                        }

                        return null;
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
    } //    try {
    //                            InetAddress localhost = InetAddress.getLocalHost();
    //
    //                            byte[] ip = localhost.getAddress();
    //
    //                            for (int i = 1; i <= 254; i++) {
    //                                tour = i;
    //                                try {
    //                                    ip[3] = (byte) i;
    //                                    InetAddress address = InetAddress.getByAddress(ip);
    //                                    ipAdresse = address.getHostAddress();
    //                                    nameAdresse = address.getHostName();
    //                                    if (address.isReachable(1000)) {
    //                                        is = 1;
    //                                        motif = address.getCanonicalHostName() + " // La machine est allumée et peut être envoyée et récevoir des données.";
    //                                    } else if (!address.getHostAddress().equals(address.getHostName())) {
    //                                        motif = address.getCanonicalHostName() + "// La machine est connue dans une recherche DNS";
    //                                    } else {
    //                                        motif = address.getCanonicalHostName() + "// Adresse non disponible !.";
    //                                    }
    //                                } catch (UnknownHostException ex) {
    //                                    Logger.getLogger(ipnet.class.getName()).log(Level.SEVERE, null, ex);
    //                                } catch (IOException ex) {
    //                                    Logger.getLogger(ipnet.class.getName()).log(Level.SEVERE, null, ex);
    //                                }
    //                                // Enregistrement dans la base des donnees
    //                                if (is == 1) {
    //                                    if (connexion.getValue("select code from host where ip='" + ipAdresse + "' and name='" + nameAdresse + "'") == null) {
    //                                        connexion.execute("INSERT INTO host(ip,name) VALUES (?,?);", ipAdresse, nameAdresse);
    //                                    }
    //                                    Platform.runLater(() -> {
    //
    //                                        display.appendText(tour + " </> " + ipAdresse + " == [ " + motif + "] / " + LocalTime.now().getHour() + "h " + LocalTime.now().getMinute() + "min\n");
    //                                        display.appendText("-------------------------------------------------------------------------------------------------------------\n");
    //
    //                                    });
    //                                } else {
    //                                    Platform.runLater(() -> {
    //                                        display.appendText("\n" + tour + " [ " + motif + " ] " + ipAdresse + " == " + nameAdresse + " / " + LocalTime.now() + "\n");
    //                                        display.appendText("-------------------------------------------------------------------------------------------------------------\n");
    //                                    });
    //                                }
    //                                is = 0;
    //                                ipAdresse = null;
    //                                nameAdresse = null;
    //                                motif = null;
    //                                MainController.isLoad = true;
    //                            }

}
