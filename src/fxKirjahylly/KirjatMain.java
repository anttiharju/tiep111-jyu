package fxKirjahylly;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author anvemaha
 * @version 26.1.2020
 *
 */
public class KirjatMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjatGUIView.fxml"));
            final Pane root = ldr.load();
            //final KirjatGUIController kirjatCtrl = (KirjatGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kirjat.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kirjahylly");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}