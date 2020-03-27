package fxKirjahylly;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.Kustantaja;
import kirjahylly.SailoException;

/**
 * @author anvemaha
 * @version 26.1.2020
 *
 */
public class KirjahyllyGUIController implements Initializable {

    @FXML
    private ListChooser<Kirja> chooserKirjat;
    @FXML
    private ScrollPane panelKirja;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML
    void handleMuokkaa() {
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("MuokkaaView.fxml"),
                "Tulosta", null, "");
    }


    @FXML
    void handleLisää() {
        uusiKirja();
    }


    @FXML
    void handleApua() {
        Dialogs.showMessageDialog("Vielä ei osata auttaa");
    }


    @FXML
    void handleAvaa() {
        ModalController.showModal(KirjahyllyGUIController.class.getResource(
                "TervetuloaView.fxml"), "Kirjarekisteri", null, "");
    }


    @FXML
    void handleLopeta() {
        // Dialogs.showMessageDialog("Vielä ei osata edes lopettaa");
        Platform.exit();
    }


    @FXML
    void handlePoista() {
        // Dialogs.showMessageDialog("Vielä ei osata poistaa kirjoja");
        poista();
    }


    @FXML
    void handleTallenna() {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa kirjoja");
    }


    @FXML
    void handleTietoja() {
        // Dialogs.showMessageDialog("Vielä ei tiedetä mitään");
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
    }


    @FXML
    void handleTulosta() {
        // Dialogs.showMessageDialog("Vielä ei osata tulostaa");
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TulostaView.fxml"),
                "Tulosta", null, "");
    }


    @FXML
    void handleKirjailija() {
        ModalController.showModal(KirjahyllyGUIController.class
                .getResource("KirjailijaView.fxml"), "Kirjailijat", null, "");
    }


    @FXML
    void handleKustantaja() {
        ModalController.showModal(KirjahyllyGUIController.class
                .getResource("KustantajaView.fxml"), "Kustantajat", null, "");
    }

    // ----------------------------------------------------

    private Kirjahylly hylly;
    private Kirja kirjaKohdalla;
    private TextArea areaKirja = new TextArea();

    /**
     * @param ihylly Kirjahylly jota käytetään
     */
    public void setHylly(Kirjahylly ihylly) {
        hylly = ihylly;
        naytaKirja();
    }


    /**
     * Näyttää listasta valitun kirjan tiedot, tilapäisesti yhteen isoon edit-kenttään
     * TODO: tiedot johonkin muuhun kuin yhteen textareaan
     */
    protected void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();

        if (kirjaKohdalla == null)
            return;

        areaKirja.setText("");
        try (PrintStream os = TextAreaOutputStream
                .getTextPrintStream(areaKirja)) {
            kirjaKohdalla.tulosta(os);
        }
    }


    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa kirjojen tiedot.
     * Alustetaan myös kirjalistan kuuntelija.
     */
    protected void alusta() {
        panelKirja.setContent(areaKirja);
        areaKirja.setFont(new Font("Courier New", 12));
        panelKirja.setFitToHeight(true);

        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
    }


    /**
     * Luo uuden kirjan jota aletaan editoimaan
     * TODO: rakennusteline (tayta_metro)
     */
    protected void uusiKirja() {
        Kirja uusi = new Kirja();
        uusi.rekisteroi();
        uusi.tayta_metro();
        try {
            hylly.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(
                    "Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getId());
    }


    /**
     * Hakee kirjojen tiedot listaan
     * @param jnro kirjan numero joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        chooserKirjat.clear();
        int index = 0;
        for (int i = 0; i < hylly.getKirjat(); i++) {
            Kirja kirja = hylly.annaKirja(i);
            if (kirja.getId() == jnro)
                index = i;
            chooserKirjat.add(kirja.getNimi(), kirja);
        }
        chooserKirjat.setSelectedIndex(index); // tästä tulee muutosviesti joka
                                               // näyttää kirjan
    }


    /**
     * Poistaa valitun kirjan
     * TODO: oikeasti poista
     */
    protected void poista() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        if (kirjaKohdalla == null)
            return;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Poista");
        alert.setHeaderText(null);
        alert.setContentText("Poista kirja " + kirjaKohdalla.getNimi() + "?");
        alert.showAndWait();
    }
}
