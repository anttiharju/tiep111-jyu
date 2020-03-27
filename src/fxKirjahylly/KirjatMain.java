package fxKirjahylly;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kirjahylly.Kirjahylly;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * @author anvemaha
 * @version 26.1.2020
 */
public class KirjatMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(
                    getClass().getResource("KirjatGUIView.fxml"));
            final Pane root = (Pane) ldr.load();
            final KirjahyllyGUIController hyllyCtrl = (KirjahyllyGUIController) ldr
                    .getController();

            Scene scene = new Scene(root);
            scene.getStylesheets()
                    .add(getClass().getResource("kirjat.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kirjahylly");

            primaryStage.setOnCloseRequest((event) -> {
                if (!hyllyCtrl.voikoSulkea())
                    event.consume();
            });
            Kirjahylly hylly = new Kirjahylly();
            hyllyCtrl.setHylly(hylly);

            primaryStage.show();

            Application.Parameters params = getParameters();
            if (params.getRaw().size() > 0)
                hyllyCtrl.lueTiedosto(params.getRaw().get(0));
            else if (!hyllyCtrl.avaa())
                Platform.exit();

        } catch (Exception e) {
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