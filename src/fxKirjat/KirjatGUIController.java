package fxKirjat;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * @author anvemaha
 * @version 26.1.2020
 *
 */
public class KirjatGUIController {

    @FXML
    void handleApua() {
        Dialogs.showMessageDialog("Vielä ei osata auttaa");
    }


    @FXML
    void handleAvaa() {
        Dialogs.showMessageDialog("Vielä ei osata avata kirjoja");
    }


    @FXML
    void handleLisää() {
        // Dialogs.showMessageDialog("Vielä ei osata lisätä kirjoja");
        ModalController.showModal(
                KirjatGUIController.class.getResource("MuokkaaView.fxml"),
                "Kirjat", null, "");
    }


    @FXML
    void handleLopeta() {
        // Dialogs.showMessageDialog("Vielä ei osata edes lopettaa");
        Platform.exit();
    }


    @FXML
    void handlePoista() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjoja");
    }


    @FXML
    void handleTallenna() {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa kirjoja");
    }


    @FXML
    void handleTietoja() {
        // Dialogs.showMessageDialog("Vielä ei tiedetä mitään");
        ModalController.showModal(
                KirjatGUIController.class.getResource("TietojaView.fxml"),
                "Kirjat", null, "");
    }


    @FXML
    void handleTulosta() {
        // Dialogs.showMessageDialog("Vielä ei osata tulostaa");
        ModalController.showModal(
                KirjatGUIController.class.getResource("TulostaView.fxml"),
                "Kirjat", null, "");
    }

}
