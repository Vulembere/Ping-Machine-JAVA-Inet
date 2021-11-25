/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package GUIControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com Create on
 * 07/10/2018
 */
public class ViewManager {

    public static StackPane stackPane = Main.stage.getContent();
    private static ViewManager instance;
    public static final HashMap<String, Node> SCREENS = new HashMap<>();
    private static String nameView;
    public static JFXDialog dialog;
    public static final PopOver over = new PopOver();
    public static List<String> InterfacesLink = new ArrayList();
    public static Node currentView = null;
    public static String ERROR_PAGE = "/Dialogue/PageVide.fxml";

    private ViewManager() {
        //Dialogue
        InterfacesLink.add("/GUI/Main.fxml");
        InterfacesLink.add("/GUI/loadMachines.fxml"); 
    }

    public String getName(String url) {
        String nom = url.substring(1, url.length());
        return nom.substring(nom.indexOf("/") + 1, nom.indexOf("."));
    }

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public void put(String name, Node node) {
        nameView = name;
        SCREENS.put(name, node);
    }

    String checkName(String file) {
        String returne = null;
        for (String string : InterfacesLink) {
            if (getName(string).equals(file)) {
                returne = string;
            }
        }
        return returne;
    }

    public Node get(String view) {

        if (!SCREENS.containsKey(view)) {
            try {
                String url = checkName(view);
                if (url == null) {
                    SCREENS.put(view, FXMLLoader.load(getClass().getResource(ERROR_PAGE)));
                } else {
                    currentView = FXMLLoader.load(getClass().getResource(url));
                    SCREENS.put(view, currentView);
                }
            } catch (IOException ex) {
                try {
                    SCREENS.put(view, FXMLLoader.load(getClass().getResource(ERROR_PAGE)));
                    System.out.println("Error Page : " + ex.getMessage());
                } catch (IOException ex1) {
                    System.out.println("Error Page : " + ex1.getMessage());
                }
            }
        }

        return SCREENS.get(view);
    }

    public int getSize() {
        return SCREENS.size();
    }

    public Node getCurrentView() {
        return currentView;
    }

    public ObservableList<Node> getAll() {
        return FXCollections.observableArrayList(SCREENS.values());
    }

    public void setContaint(Node Contrainaire, String interfaces) {

        try {
            Node child = ViewManager.getInstance().get(interfaces);
            if (Contrainaire instanceof StackPane) {
                StackPane contain_area = (StackPane) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof VBox) {
                VBox contain_area = (VBox) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof AnchorPane) {
                AnchorPane contain_area = (AnchorPane) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof ScrollPane) {
                ScrollPane contain_area = (ScrollPane) Contrainaire;
                contain_area.setContent(child);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void popOverMenu(Node node, Node interfaces, PopOver.ArrowLocation arrowLocation) {
        if (!over.isShowing()) {
            AnchorPane box = (AnchorPane) interfaces;
            over.setArrowLocation(arrowLocation);
            over.setAutoHide(true);
            over.setContentNode(box);
            over.show(node, 0);
        } else {
            over.hide();
        }
    }

    public void setDialog(String interfaces) {
        JFXDialogLayout dl = new JFXDialogLayout();
        Node node = get(interfaces);
        dl.setBody(node);
        dialog = new JFXDialog(stackPane, dl, JFXDialog.DialogTransition.CENTER, false);
        dialog.show(stackPane);
    }

    public void setDialog(String url, float x, float y) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(url));
            JFXDialogLayout dl = new JFXDialogLayout();
            dl.setPrefSize(x, y);
            dl.setPadding(Insets.EMPTY);
            dl.setBody(node);
            dialog = new JFXDialog(stackPane, dl, JFXDialog.DialogTransition.CENTER, false);
            dialog.show(stackPane);
        } catch (IOException ex) {
            Logger.getLogger(ViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    boolean bool = false;

    public boolean alert() {
        JFXButton b1 = new JFXButton("OK");
        JFXButton b2 = new JFXButton("Annuler");
        JFXDialogLayout Layout = new JFXDialogLayout();
        Layout.setHeading(new Label("Voulez vous annuler l'operation ?"));
        JFXDialog dialog = new JFXDialog(stackPane, Layout, JFXDialog.DialogTransition.CENTER);
        b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            bool = true;
            System.out.println("Clique##### MOUSE_CLICKED VALIDATE "+bool);

            dialog.close();

        });
        b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Clique##### MOUSE_CLICKED EXIT"+bool);
            bool = Boolean.FALSE;
            dialog.close();

        });
        Layout.setActions(b1, b2);
        dialog.show();
        return bool;
    }

    public void createWindow(URL URL, String title, Stage parentStage, Boolean resizable, Boolean returnLogin) {
        try {
            Parent root = FXMLLoader.load(URL);
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
                stage.initModality(Modality.APPLICATION_MODAL);
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setResizable(resizable);
            stage.setTitle(title);
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.getIcons().add(new javafx.scene.image.Image(manifeste.CONFIGURATION_ICON));

            stage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.initStyle(StageStyle.DECORATED);
                alert.setHeaderText("Voulez-vous vous déconnecter de cette interface ?");
                Stage stages = (Stage) alert.getDialogPane().getScene().getWindow();
                stages.getIcons().add(new Image(this.getClass().getResource(manifeste.CONFIGURATION_ICON).toString()));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (returnLogin) {
                        Main.stage.setContent(ViewManager.getInstance().get("Login"));
                    }
                } else {
                    event.consume();
                }
            });
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
