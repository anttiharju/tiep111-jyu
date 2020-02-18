package fxKirjat;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author anvemaha
 * @version 26.1.2020
 *
 */
public class KirjatGUIController {

    @FXML
    void handleMuokkaa() {
        ModalController.showModal(
                KirjatGUIController.class.getResource("MuokkaaView.fxml"),
                "Muokkaa kirjaa", null, "");
    }


    @FXML
    void handleLisää() {
        Dialogs.showMessageDialog("Vielä ei osata lisätä kirjoja");
        // TODO: Käyttää samaa pohjaa kuin muokkaa
    }


    @FXML
    void handleApua() {
        Dialogs.showMessageDialog("Vielä ei osata auttaa");
    }


    @FXML
    void handleAvaa() {
        ModalController.showModal(
                KirjatGUIController.class.getResource("TervetuloaView.fxml"),
                "Kirjarekisteri", null, "");
    }


    @FXML
    void handleLopeta() {
        // Dialogs.showMessageDialog("Vielä ei osata edes lopettaa");
        Platform.exit();
    }


    @FXML
    void handlePoista() {
        // Dialogs.showMessageDialog("Vielä ei osata poistaa kirjoja");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Poista");
        alert.setHeaderText(null);
        alert.setContentText("Poista kirja Adeno?");
        alert.showAndWait();
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
                "Tietoja", null, "");
    }


    @FXML
    void handleTulosta() {
        // Dialogs.showMessageDialog("Vielä ei osata tulostaa");
        ModalController.showModal(
                KirjatGUIController.class.getResource("TulostaView.fxml"),
                "Tulosta", null, "");
    }


    @FXML
    void handleKirjailija() {
        ModalController.showModal(
                KirjatGUIController.class.getResource("KirjailijaView.fxml"),
                "Kirjailijat", null, "");
    }


    @FXML
    void handleKustantaja() {
        ModalController.showModal(
                KirjatGUIController.class.getResource("KustantajaView.fxml"),
                "Kustantajat", null, "");
    }
}
