package fxKirjahylly;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.SailoException;

/**
 * @author anvemaha
 * @version 27.3.2020
 */
public class KirjahyllyGUIController implements Initializable {

    @FXML
    private TextField hakuehto;
    @FXML
    private ComboBoxChooser<String> cbKentat;
    @FXML
    private Label labelVirhe;
    @FXML
    private ScrollPane panelKirja;
    @FXML
    private ListChooser<Kirja> chooserKirjat;

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
        apua();
    }


    @FXML
    void handleAvaa() {
        avaa();
    }


    @FXML
    void handleLopeta() {
        Platform.exit();
    }


    @FXML
    void handlePoista() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjoja");
    }


    @FXML
    void handleTallenna() {
        tallenna();
    }


    @FXML
    void handleTietoja() {
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
    }


    @FXML
    void handleTulosta() {
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TulostaView.fxml"),
                "Tulosta", null, "");
    }


    @FXML
    void handleHakuehto() {
        if (kirjaKohdalla != null) {
            hae(kirjaKohdalla.getId());
        }
    }

    // ----------------------------------------------------

    private Kirjahylly hylly;
    private Kirja kirjaKohdalla;
    private TextArea areaKirja = new TextArea();
    private String hyllynNimi = "antti";

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


    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }


    /**
     * Alustaa hyllyn lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta hyllyn tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        hyllynNimi = nimi;
        setTitle(hyllynNimi);
        try {
            hylly.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null)
                Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }


    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusiNimi = HyllynNimiController.kysyNimi(null, hyllynNimi);
        if (uusiNimi == null)
            return false;
        lueTiedosto(uusiNimi);
        return true;
    }


    private String tallenna() {
        try {
            hylly.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog(
                    "Tallennuksessa ongelmia! " + e.getMessage());
            return e.getMessage();
        }
    }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }


    /**
     * Näyttää listasta valitun kirjan tiedot, tilapäisesti yhteen isoon edit-kenttään
     * TODO: tiedot johonkin muuhun kuin yhteen textareaan
     */
    protected void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();

        if (kirjaKohdalla == null) {
            areaKirja.clear();
            return;
        }
        areaKirja.setText("");
        try (PrintStream os = TextAreaOutputStream
                .getTextPrintStream(areaKirja)) {
            kirjaKohdalla.tulosta(os);
        }
    }


    /**
     * Hakee kirjojen tiedot listaan
     * @param jnro kirjan numero joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea (kenttä %d, ehto %s)", k,
                    ehto));
        else
            naytaVirhe(null);

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
     * @param ihylly Kirjahylly jota käytetään
     */
    public void setHylly(Kirjahylly ihylly) {
        hylly = ihylly;
        naytaKirja();
    }


    /**
     * Avaa ohjelman suunnitelman selaimessa
     */
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI(
                    "https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/anvemaha");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
}
