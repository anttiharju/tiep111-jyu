package fxKirjahylly;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
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
import kanta.SailoException;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjat;
import kirjahylly.Nippu;

/**
 * Luokka hyllyn käyttöliittymän tapahtumien hoitamiseksi.
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 27.3.2020 pohjaa
 * @version 10.4.2020 gitin pitäisi hoitaa nämä eikä minun näitä ei ole muistettu pitää ajantasalla
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
    private ComboBoxChooser<String> cbLuettu;
    @FXML
    private ComboBoxChooser<String> cbEhto;
    @FXML
    private Label viesti;
    @FXML
    private ScrollPane panelKirja;
    @FXML
    private ListChooser<Kirja> chooserKirjat;
    @FXML
    private ListChooser<Kirja> chooserMuut;

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
        poista();
    }


    @FXML
    void handleTallenna() {
        tallenna();
    }


    @FXML
    void handleRefresh() {
        lueUudestaan();
    }


    @FXML
    void handleTietoja() {
        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
    }


    @FXML
    void handleTulosta() {
        tulosta();
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
    private String hyllynNimi = "antti"; // tämä nimi näytetään oletuksena

    /**
     * Alustetaan kirjalistan kuuntelija.
     */
    protected void alusta() {
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
        chooserMuut.addSelectionListener(e -> naytaKirja(false));
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


    private void poista() {
        chooserKirjat.getCursor();
        hylly.poista(kirjaKohdalla);
        if (hylly.getKirjatLkm() == 0) {
            tyhjenna();
        }
        int index = chooserKirjat.getSelectedIndex();
        hae(0);
        chooserKirjat.setSelectedIndex(index);

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
        if (hylly.onkoMuutoksia()) {
            if (Dialogs.showQuestionDialog("Varoitus",
                    "Haluatko varmasti poistua?\nSinulla on tallentamattomia muutoksia.",
                    "Kyllä", "Ei"))
                return true;
            return false;
        }
        return true;
    }


    /**
     * Näyttää valitun kirjan
     * @param ohita ohitetaanko kirjailijan muiden kirjojen päivitys
     */
    protected void naytaKirja(boolean ohita) {
        if (!ohita) {
            kirjaKohdalla = chooserMuut.getSelectedObject();
            chooserKirjat.setSelectedIndex(-1); // valinta pois
        } else {
            haeMuut();
            kirjaKohdalla = chooserKirjat.getSelectedObject();
        }

        if (kirjaKohdalla == null) {
            tyhjenna();
            return;
        }
        nNimi.setText(kirjaKohdalla.getNimi());
        nKirjailija.setText(hylly.kirjanKirjailija(kirjaKohdalla));
        nKustantaja.setText(hylly.kirjanKustantaja(kirjaKohdalla));
        nVuosi.setText("" + kirjaKohdalla.getVuosi());
        nKuvaus.setText(kirjaKohdalla.getKuvaus());
        nLuettu.setText(kirjaKohdalla.getLuettu());
        nArvio.setText("" + kirjaKohdalla.getArvio());
        nLisatietoja.setText(kirjaKohdalla.getLisatietoja());
        viesti.setText("");
    }


    /**
     * Näyttää valitun kirjan
     */
    private void naytaKirja() {
        naytaKirja(true);
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
     * @param id kirjan numero joka aktivoidaan haun jälkeen
     */
    protected void hae(int id) {
        int kid = id;
        if (kid <= 0) {
            Kirja kohdalla = kirjaKohdalla;
            if (kohdalla != null)
                kid = kohdalla.getId();
        }

        int k = cbEhto.getSelectionModel().getSelectedIndex();
        int l = cbLuettu.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0)
            ehto = "*" + ehto + "*";

        chooserKirjat.clear();

        int index = 0;
        Collection<Kirja> kirjat = hylly.etsi(ehto, k, l);
        int i = 0;
        for (Kirja kirja : kirjat) {
            if (kirja.getId() == kid)
                index = i;
            chooserKirjat.add(kirja.getNimi(), kirja);
            i++;
        }

        chooserKirjat.setSelectedIndex(index);
    }


    private void haeMuut() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        if (kirjaKohdalla == null)
            return;
        chooserMuut.clear();
        Collection<Kirja> kirjailijanMuutKirjat = hylly.kirjailijanKirjat(
                kirjaKohdalla.getKirjailijaId(), kirjaKohdalla.getId());
        for (Kirja kirja : kirjailijanMuutKirjat)
            chooserMuut.add(kirja.getNimi(), kirja);
    }


    /**
     * Luo uuden kirjan jota aletaan editoimaan
     */
    protected void uusiKirja() {
        Kirja uusi = new Kirja();
        uusi.tayta();
        try {
            hylly.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(
                    "Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        chooserKirjat.clear();
        chooserKirjat.add(uusi.getNimi(), uusi);
        chooserKirjat.setSelectedIndex(0);
        muokkaa();
        if (uusi.getNimi().equals(""))
            hylly.poista(uusi);
        else
            uusi.rekisteroi();
        hae(kirjaKohdalla.getId());
    }


    /**
     * @param uusiHylly Kirjahylly jota käytetään
     */
    public void setHylly(Kirjahylly uusiHylly) {
        this.hylly = uusiHylly;
        naytaKirja();
    }


    private void muokkaa() {
        if (kirjaKohdalla == null)
            return;
        Kirja alkupKirja = kirjaKohdalla.clone();
        nippu.set(hylly, kirjaKohdalla);
        nippu = ModalController.showModal(
                KirjahyllyGUIController.class.getResource("MuokkaaView.fxml"),
                "Muokkaa", null, nippu);
        kirjaKohdalla = nippu.getKirja();
        hylly = nippu.getHylly();

        if (!kirjaKohdalla.toString().equals(alkupKirja.toString()))
            hylly.korvaa(kirjaKohdalla.getId(), kirjaKohdalla);
        hae(kirjaKohdalla.getId());
    }


    /**
     * Lataa tiedoston uudestaan, eli ts. peruu muutokset
     */
    private void lueUudestaan() {
        boolean varmistus = Dialogs.showQuestionDialog("Varmistus",
                "Haluatko varmasti lukea tiedoston uudestaan levyltä?\nKaikki muutokset menetetään.",
                "Kyllä", "Ei");
        if (varmistus)
            lueTiedosto(hyllynNimi);
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


    /**
     * Tulostaa hyllyn kaikki kirjat
     * TODO: page breakit ettei yksittäisen kirjan tiedot hajoa kahdelle eri sivulle
     */
    private void tulosta() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kirjahylly (" + hyllynNimi + ")\n\n");

        Kirjat kirjat = hylly.annaKirjat();
        for (Kirja kirja : kirjat) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            kirja.tulosta(stream);
            String tulostus = stream.toString()
                    .replaceAll("<kirjailija/>", hylly.kirjanKirjailija(kirja))
                    .replaceAll("<kustantaja/>", hylly.kirjanKustantaja(kirja));
            sb.append(tulostus).append("\n\n");
        }

        ModalController.showModal(
                KirjahyllyGUIController.class.getResource("TulostaView.fxml"),
                "Tulosta kirja", null, sb.toString());
    }
}
