/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vulembere.vulembereGUI;

/**
 *
 * @author vulembere
 */
public class Main extends Application {

    public static vulembereGUI stage = null;

    @Override
    public void start(Stage primaryStage) {

        stage = new vulembereGUI();

        stage.setContent(ViewManager.getInstance().get("Main"));

        stage.getStage().getIcons().add(new Image(manifeste.CONFIGURATION_ICON));
        stage.setTitle(manifeste.CONFIGURATION_TITRE);
        stage.setResizable(false);
        stage.setMaximized(false);
//        stage.setBarHeight(5);
        
        //stage.getScene().getStylesheets().add("/style/allStyle.css");
        stage.show();

    }

    public static void Maximise() {
        stage.setMaximized(true);
        stage.setResizable(true);
    }

    public static void Minimise() {
        Main.stage.setMaximized(false);
        Main.stage.setResizable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
