package fxKirjahylly;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.Kustantaja;
import kirjahylly.Nippu;
import kirjahylly.SailoException;

/**
 * Muokataan kirjaa erillisessä dialogissa
 * @author anvemaha
 * @version 28.3.2020
 */
public class MuokkaaController implements ModalControllerInterface<Nippu> {

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
    private void handleTallenna() {
        tallenna();
    }


    @FXML
    private void handlePeruuta() {
        ModalController.closeStage(viesti);
    }


    @FXML
    private void handleLisaaKirjailija() throws SailoException {
        String nimi = Dialogs.showInputDialog("Anna kirjailijan nimi", "");
        if (nimi == null)
            return;
        Kirjailija tmp = new Kirjailija(nimi);
        tmp.rekisteroi();
        hylly.lisaa(tmp); // sailoexception
        setKirjailijat();
    }


    @FXML
    private void handleLisaaKustantaja() throws SailoException {
        String nimi = Dialogs.showInputDialog("Anna kustantajan nimi", "");
        if (nimi == null)
            return;
        Kustantaja tmp = new Kustantaja(nimi);
        tmp.rekisteroi();
        hylly.lisaa(tmp); // sailoexception
        setKustantajat();
    }


    @FXML
    private void handlePoistaKirjailija() {
        hylly.poistaKirjailija(mKirjailija.getSelectedText());
        hylly.toString();
    }


    @FXML
    private void handlePoistaKustantaja() {
        hylly.poistaKustantaja(mKustantaja.getSelectedText());
    }

    // ========================================================

    private Nippu nippu;
    private Kirja kirjaKohdalla;
    private Kirjahylly hylly;

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


    private void tallenna() {
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

        nippu.set(hylly, kirjaKohdalla);

        viesti.setTextFill(Color.GREEN);
        viesti.setText("Tallennettu!");
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
