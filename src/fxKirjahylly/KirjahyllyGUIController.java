package fxKirjahylly;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Nippu;
import kirjahylly.SailoException;

/**
 * Luokka hyllyn käyttöliittymän tapahtumien hoitamiseksi.
 * @author anvemaha
 * @version 27.3.2020
 */
public class KirjahyllyGUIController implements Initializable {

    @FXML
    private TextField hakuehto;
    @FXML
    private TextField nNimi;
    @FXML
    private TextField nKirjailija;
    @FXML
    private TextField nKustantaja;
    @FXML
    private TextField nVuosi;
    @FXML
    private TextField nKuvaus;
    @FXML
    private TextField nLuettu;
    @FXML
    private TextField nArvio;
    @FXML
    private TextField nLisatietoja;
    @FXML
    private ComboBoxChooser<String> cbKentat;
    @FXML
    private Label viesti;
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
        muokkaa();
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
    private Nippu nippu = new Nippu(null, null);
    private String hyllynNimi = "antti";

    /**
     * Alustetaan kirjalistan kuuntelija.
     */
    protected void alusta() {
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
    }


    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            viesti.setText("");
            viesti.getStyleClass().removeAll("virhe");
            return;
        }
        viesti.setText(virhe);
        viesti.getStyleClass().add("virhe");
    }


    private void setTitle(String title) {
        ModalController.getStage(hakuehto)
                .setTitle("Kirjahylly (" + title + ")");
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
        String uusiNimi = AvaaController.kysyNimi(null, hyllynNimi);
        if (uusiNimi == null)
            return false;
        lueTiedosto(uusiNimi);
        return true;
    }


    private String tallenna() {
        try {
            hylly.tallenna();
            viesti.setTextFill(Color.GREEN);
            viesti.setText("Tallennettu!");
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
        // tallenna(); // hyi
        return true;
    }


    /**
     * Näyttää listasta valitun kirjan tiedot, tilapäisesti yhteen isoon edit-kenttään
     * TODO: tiedot johonkin muuhun kuin yhteen textareaan
     */
    protected void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();

        if (kirjaKohdalla == null) {
            // tyhjenna();
            return;
        }
        nNimi.setText(kirjaKohdalla.getNimi());
        nKirjailija.setText(hylly.annaKirjailija(kirjaKohdalla).getNimi());
        nKustantaja.setText(hylly.annaKustantaja(kirjaKohdalla).getNimi());
        nVuosi.setText("" + kirjaKohdalla.getVuosi());
        nKuvaus.setText(kirjaKohdalla.getKuvaus());
        nLuettu.setText(kirjaKohdalla.getLuettu());
        nArvio.setText("" + kirjaKohdalla.getArvio());
        nLisatietoja.setText(kirjaKohdalla.getLisatietoja());
        viesti.setText("");

    }


    /**
     * Tyhjentään tekstikentät 
     */
    public void tyhjenna() {
        nNimi.setText("");
        nKirjailija.setPromptText("");
        nKustantaja.setPromptText("");
        nVuosi.setText("");
        nKuvaus.setText("");
        nLuettu.setText("");
        nArvio.setText("");
        nLisatietoja.setText("");
        viesti.setText("");
    }


    /**
     * Hakee kirjojen tiedot listaan
     * @param kid kirjan numero joka aktivoidaan haun jälkeen
     */
    protected void hae(int kid) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k,
                    ehto));
        else
            naytaVirhe(null);

        chooserKirjat.clear();

        int index = 0;
        Collection<Kirja> kirjat;
        try {
            kirjat = hylly.etsi(ehto, k);
            int i = 0;
            for (Kirja kirja : kirjat) {
                if (kirja.getId() == kid)
                    index = i;
                chooserKirjat.add(kirja.getNimi(), kirja);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog(
                    "Kirjan hakemisessa ongelmia! " + ex.getMessage());
        }
        // tästä tulee muutosviesti joka näyttää kirjan
        chooserKirjat.setSelectedIndex(index);
    }


    /**
     * Luo uuden kirjan jota aletaan editoimaan
     */
    protected void uusiKirja() {
        Kirja uusi = new Kirja();
        uusi.rekisteroi();
        uusi.tayta();
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
     * @param uusiHylly Kirjahylly jota käytetään
     */
    public void setHylly(Kirjahylly uusiHylly) {
        this.hylly = uusiHylly;
        naytaKirja();
    }


    private void muokkaa() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        if (kirjaKohdalla == null)
            return;
        nippu.set(hylly, kirjaKohdalla);
        nippu = ModalController.showModal(
                KirjahyllyGUIController.class.getResource("MuokkaaView.fxml"),
                "Muokkaa", null, nippu);
        hylly = nippu.getHylly();
        kirjaKohdalla = nippu.getKirja();
        hylly.korvaa(kirjaKohdalla.getId(), kirjaKohdalla);
        naytaKirja();
        hae(0);
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
