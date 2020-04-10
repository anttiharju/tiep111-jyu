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

import static kanta.apu.*;

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
    private void handleTallenna() {
        tallenna();
    }


    @FXML
    private void handleSulje() {
        sulje();
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


    @FXML
    private void handleTarkistaLuettu() {
        tarkista();
    }

    // ========================================================

    private Nippu nippu;
    private Kirja apuKirja;
    private Kirjahylly hylly;
    private Kirjailijat tmpKirjailijat;
    private Kustantajat tmpKustantajat;
    private int tmpkir, tmpkus;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        tyhjenna(); // turha? olkoot nyt kuitenkin virheiden varalta
    }


    @Override
    public void setDefault(Nippu oletus) {
        nippu = oletus;
        apuKirja = oletus.getKirja();
        hylly = oletus.getHylly();
        tmpKirjailijat = hylly.annaKirjailijat();
        tmpKustantajat = hylly.annaKustantajat();
        tmpkir = oletus.getKirja().getKirjailijaId();
        tmpkus = oletus.getKirja().getKustantajaId();
        naytaKirja(apuKirja);
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
        if (!tarkista())
            return;

        mNimi.setStyle(null);
        mVuosi.setStyle(null);
        mLuettu.setStyle(null);
        mArvio.setStyle(null);

        apuKirja.setNimi(mNimi.getText());
        apuKirja.setKirjailija(
                tmpKirjailijat.getId(mKirjailija.getSelectedText()));
        apuKirja.setKustantaja(
                tmpKustantajat.getId(mKustantaja.getSelectedText()));
        apuKirja.setVuosi(Integer.parseInt(mVuosi.getText()));
        apuKirja.setKuvaus(mKuvaus.getText());
        apuKirja.setLuettu(mLuettu.getText());
        apuKirja.setArvio(Integer.parseInt(mArvio.getText()));
        apuKirja.setLisatietoja(mLisatietoja.getText());

        nippu.set(apuKirja);
        hylly.set(tmpKirjailijat);
        hylly.set(tmpKustantajat);

        viesti.setTextFill(Color.GREEN);
        viesti.setText("Tallennettu!");
    }


    private void sulje() {
        ModalController.closeStage(viesti);
    }


    private void lisaaKirjailija() {
        String nimi = Dialogs.showInputDialog("Anna kirjailijan nimi", "");
        if (nimi == null)
            return;
        Kirjailija tmp = new Kirjailija(nimi);
        tmp.rekisteroi();
        tmpkir = tmpKirjailijat.lisaa(tmp);
        setComboBox(mKirjailija, annaKirjailijat());
    }


    private void lisaaKustantaja() {
        String nimi = Dialogs.showInputDialog("Anna kustantajan nimi", "");
        if (nimi == null)
            return;
        Kustantaja tmp = new Kustantaja(nimi);
        tmp.rekisteroi();
        tmpkus = tmpKustantajat.lisaa(tmp);
        setComboBox(mKustantaja, annaKustantajat());
    }


    private void poistaKirjailija() {
        tmpkir = 0;
        tmpKirjailijat.poista(mKirjailija.getSelectedText());
        setComboBox(mKirjailija, annaKirjailijat());
    }


    private void poistaKustantaja() {
        tmpkus = 0;
        tmpKustantajat.poista(mKustantaja.getSelectedText());
        setComboBox(mKustantaja, annaKustantajat());
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
        setComboBox(mKirjailija, annaKirjailijat());
        setComboBox(mKustantaja, annaKustantajat());
        mVuosi.setText("" + kirja.getVuosi());
        mKuvaus.setText(kirja.getKuvaus());
        mLuettu.setText(kirja.getLuettu());
        mArvio.setText("" + kirja.getArvio());
        mLisatietoja.setText(kirja.getLisatietoja());
        viesti.setText("");
    }


    /**
     * Päivittää ComboBoxChooserin niin että valittu on ekana
     * @param lista merkkijono kohteet eroteltu rivinvaihdoin ja valittu ekana
     * @param kentta päivitettävä comboboxchooser
     */
    public void setComboBox(ComboBoxChooser<String> kentta, String lista) {
        kentta.setRivit(lista);
        if (kentta.getSelectedText().equals("null"))
            kentta.setRivit(kentta.getRivit().replace("null", "Ei valittu"));
    }


    /**
     * ComboBoxChooseria varten tehty
     * @return kaikki kirjailijat, kirjan kirjailija ensimmäisenä
     */
    public String annaKirjailijat() {
        String kirjailija = tmpKirjailijat.annaKirjailija(tmpkir).getNimi();
        StringBuilder sb = new StringBuilder(kirjailija);
        // Pakollinen, muokkaus ei toimi jos kirjailijoita ei ole
        if (kirjailija.equals(""))
            sb.append("null"); // nimenomaan "null" eikä null
        sb.append("\n");

        var iterator = tmpKirjailijat.iterator();
        for (int i = 0; i < tmpKirjailijat.getLkm(); i++) {
            String nyk = iterator.next().getNimi();
            if (!nyk.equals(kirjailija))
                sb.append(nyk).append("\n");
        }
        return sb.toString();
    }


    /**
     * ComboBoxChooseria varten tehty
     * @return kaikki kustantajat, kirjan kustantaja ensimmäisenä
     */
    public String annaKustantajat() {
        String kustantaja = tmpKustantajat.annaKustantaja(tmpkus).getNimi();
        StringBuilder sb = new StringBuilder(kustantaja);
        // Pakollinen, muokkaus ei toimi jos kustantajia ei ole
        if (kustantaja.equals(""))
            sb.append("null"); // nimenomaan "null" eikä null
        sb.append("\n");

        var iterator = tmpKustantajat.iterator();
        for (int i = 0; i < tmpKustantajat.getLkm(); i++) {
            String nyk = iterator.next().getNimi();
            if (!nyk.equals(kustantaja))
                sb.append(nyk).append("\n");
        }
        return sb.toString();
    }


    /**
     * Tekee tarkistukset
     * @return saako tallentaa vai ei
     */
    private boolean tarkista() {
        int totuus = 0;
        totuus += tarkistaArvio();
        totuus += tarkistaLuettu();
        totuus += tarkistaJulkaisuvuosi();
        totuus += tarkistaNimi();
        if (totuus == 0)
            return true;
        return false;
    }


    /**
     * Tarkistaa ettei nimi ole tyhjä
     * @return saako tallentaa vai ei
     */
    private int tarkistaNimi() {
        if (tarkistaEtteiTyhja(mNimi.getText()))
            return 0;
        virheKentta("\"Nimi\" ei voi olla tyhjä!", mNimi);
        return 1;
    }


    /**
     * Tarkistaa että julkaisuvuosi on muotoa vvvv
     * @return saako tallentaa vai ei
     */
    private int tarkistaJulkaisuvuosi() {
        if (tarkistaVuosi(mVuosi.getText()))
            return 0;
        virheKentta("Anna \"Vuosi\" muodossa vvvv!", mVuosi);
        return 1;
    }


    /**
     * Tarkistaa että luettu pvm on muotoa pp.kk.vvvv
     * @return saako tallentaa vai ei
     */
    private int tarkistaLuettu() {
        if (tarkistaPvm(mLuettu.getText()))
            return 0;
        virheKentta("Anna \"Luettu\" muodossa pp.kk.vvvv!", mLuettu);
        return 1;
    }


    /**
     * Tarkistaa että arvio on numeerinen
     * @return saako tallentaa vai ei
     */
    private int tarkistaArvio() {
        if (tarkistaNumeerisuus(mVuosi.getText()))
            return 0;
        virheKentta("Anna \"Arvio\" numeerisena!", mArvio);
        return 1;
    }


    /**
     * Näyttää virheen
     * @param virhe virheilmoitus
     * @param kentta kenttä jossa on virhe
     */
    private void virheKentta(String virhe, TextField kentta) {
        viesti.setTextFill(Color.RED);
        viesti.setText(virhe);
        kentta.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
    }
}
