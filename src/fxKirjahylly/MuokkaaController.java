package fxKirjahylly;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.Kirjailijat;
import kirjahylly.Kustantaja;
import kirjahylly.Kustantajat;
import kirjahylly.Nippu;
import kirjahylly.SailoException;

/**
 * Muokataan kirjaa erillisessä dialogissa
 * @author anvemaha
 * @version 28.3.2020
 */
public class MuokkaaController
        implements ModalControllerInterface<Nippu>, Initializable {

    @FXML
    private TextField mNimi;
    @FXML
    private ComboBoxChooser<String> mKirjailija;
    @FXML
    private ComboBoxChooser<String> mKustantaja;
    @FXML
    private TextField mVuosi;
    @FXML
    private TextField mKuvaus;
    @FXML
    private TextField mLuettu;
    @FXML
    private TextField mArvio;
    @FXML
    private TextField mLisatietoja;
    @FXML
    private Label viesti;

    @FXML
    private void handleTallenna() throws SailoException {
        tallenna();
    }


    @FXML
    private void handlePeruuta() {
        peruuta();
    }


    @FXML
    private void handleLisaaKirjailija() {
        lisaaKirjailija();
    }


    @FXML
    private void handleLisaaKustantaja() {
        lisaaKustantaja();
    }


    @FXML
    private void handlePoistaKirjailija() {
        poistaKirjailija();
    }


    @FXML
    private void handlePoistaKustantaja() {
        poistaKustantaja();
    }

    // ========================================================

    private Nippu nippu;
    private Kirja kirjaKohdalla;
    private Kirjahylly hylly;
    private Kirjailijat kirLisaaBuffer = new Kirjailijat();
    private Kustantajat kusLisaaBuffer = new Kustantajat();
    private Kirjailijat kirPoistaBuffer = new Kirjailijat();
    private Kustantajat kusPoistaBuffer = new Kustantajat();

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        tyhjenna(); // turha? olkoot nyt kuitenkin virheiden varalta
    }


    @Override
    public void setDefault(Nippu oletus) {
        nippu = oletus;
        kirjaKohdalla = oletus.getKirja();
        hylly = oletus.getHylly();
        naytaKirja(kirjaKohdalla);
    }


    @Override
    public Nippu getResult() {
        return nippu;
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        mNimi.requestFocus();
    }


    private void tallenna() throws SailoException {
        kirjaKohdalla.setNimi(mNimi.getText());
        kirjaKohdalla.setKirjailija(
                hylly.getKirjailijanId(mKirjailija.getSelectedText(),
                        kirjaKohdalla.getKirjailijaId()));
        kirjaKohdalla.setKustantaja(
                hylly.getKustantajanId(mKustantaja.getSelectedText(),
                        kirjaKohdalla.getKustantajaId()));
        kirjaKohdalla.setVuosi(Integer.parseInt(mVuosi.getText()));
        kirjaKohdalla.setKuvaus(mKuvaus.getText());
        kirjaKohdalla.setLuettu(mLuettu.getText());
        kirjaKohdalla.setArvio(Integer.parseInt(mArvio.getText()));
        kirjaKohdalla.setLisatietoja(mLisatietoja.getText());

        for (Kirjailija kirjailija : kirPoistaBuffer)
            hylly.poistaKirjailija(kirjailija.getNimi());
        for (Kirjailija kirjailija : kirLisaaBuffer) {
            kirjailija.rekisteroi();
            hylly.lisaa(kirjailija);
        }
        for (Kustantaja kustantaja : kusPoistaBuffer)
            hylly.poistaKustantaja(kustantaja.getNimi());
        for (Kustantaja kustantaja : kusLisaaBuffer) {
            kustantaja.rekisteroi();
            hylly.lisaa(kustantaja);
        }
        // nippu.set(hylly, kirjaKohdalla); // tarpeeton?

        viesti.setTextFill(Color.GREEN);
        viesti.setText("Tallennettu!");
    }


    private void peruuta() {
        ModalController.closeStage(viesti);
    }


    private void lisaaKirjailija() {
        String nimi = Dialogs.showInputDialog("Anna kirjailijan nimi", "");
        if (nimi == null)
            return;
        Kirjailija tmp = new Kirjailija(nimi);
        kirLisaaBuffer.lisaa(tmp);
        setKirjailijat();
    }


    private void lisaaKustantaja() {
        String nimi = Dialogs.showInputDialog("Anna kustantajan nimi", "");
        if (nimi == null)
            return;
        Kustantaja tmp = new Kustantaja(nimi);
        tmp.rekisteroi();
        kusLisaaBuffer.lisaa(tmp); // SailoException
        setKustantajat();
    }


    private void poistaKirjailija() {
        kirPoistaBuffer
                .lisaa(hylly.annaKirjailija(mKirjailija.getSelectedText()));
    }


    private void poistaKustantaja() {
        kusPoistaBuffer
                .lisaa(hylly.annaKustantaja(mKustantaja.getSelectedText()));
    }


    /**
     * Tyhjentään tekstikentät 
     */
    public void tyhjenna() {
        mNimi.setText("");
        mKirjailija.setPromptText("");
        mKustantaja.setPromptText("");
        mVuosi.setText("");
        mKuvaus.setText("");
        mLuettu.setText("");
        mArvio.setText("");
        mLisatietoja.setText("");
        viesti.setText("");
    }


    /**
     * Näytetään kirjan tiedot
     * @param kirja näytettävä kirja
     */
    public void naytaKirja(Kirja kirja) {
        if (kirja == null)
            return;
        mNimi.setText(kirja.getNimi());
        setKirjailijat();
        setKustantajat();
        mVuosi.setText("" + kirja.getVuosi());
        mKuvaus.setText(kirja.getKuvaus());
        mLuettu.setText(kirja.getLuettu());
        mArvio.setText("" + kirja.getArvio());
        mLisatietoja.setText(kirja.getLisatietoja());
        viesti.setText("");
    }


    /**
     * Päivittää comboboxchooserin niin, että kirjan kirjailija
     * on ensimmäisenä (ts. valittuna) ja kaikki muut mahdolliset kirjailijat ovat valittavissa
     */
    public void setKirjailijat() {
        mKirjailija.setRivit(hylly.annaKirjailijat(kirjaKohdalla));

        if (mKirjailija.getSelectedText().equals("null"))
            mKirjailija.setRivit(
                    mKirjailija.getRivit().replace("null", "Ei valittu"));

    }


    /**
     * Päivittää comboboxchooserin niin, että kirjan kustantaja
     * on ensimmäisenä (ts. valittuna) ja kaikki muut mahdolliset kustantajat ovat valittavissa
     */
    public void setKustantajat() {
        mKustantaja.setRivit(hylly.annaKustantajat(kirjaKohdalla));
        if (mKustantaja.getSelectedText().equals("null"))
            mKustantaja.setRivit(
                    mKustantaja.getRivit().replace("null", "Ei valittu"));
    }

}
