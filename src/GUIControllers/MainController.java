/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MC
 */
public class MainController implements Initializable {

    @FXML
    private ListView<?> listeMachine;
    @FXML
    private TextArea display;
    @FXML
    private Label btnStart;

    public static TextArea area;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        area = display;
        synchro();
        runR();

    }

    void runR() {
        try {
            AnimationTimer tim = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (isLoad) {
                        loadListe();
                        isLoad = false;
                    }
                }
            };
            tim.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void loadListe() {
        try {
            ResultSet rs = connexion.getRs("select * from host  ORDER BY etat DESC");

            while (rs.next()) {
                if (!all.contains((rs.getString("ip") + rs.getString("name")))) {
                    try {
                        LoadMachinesController.ip = (rs.getString("ip"));
                        LoadMachinesController.name = (rs.getString("name"));
                        LoadMachinesController.count = (rs.getString("code"));;
                        listeMachine.getItems().add(FXMLLoader.load(getClass().getResource("/GUI/loadMachines.fxml")));
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    all.add((rs.getString("ip") + rs.getString("name")));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Map map = new HashMap();
    public static Boolean isLoad = false;
    Toolkit tk = Toolkit.getDefaultToolkit();
    int tour = 1;
    int count = 0;

    List<String> all = new ArrayList();

    public void synchro() {

        ipnet.synchro(display);

    }

    @FXML
    private void Start(MouseEvent event) {
        isLoad = true;
        loadListe();
    }
}
