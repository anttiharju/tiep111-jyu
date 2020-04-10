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
    private Kirjailijat tmpKirjailijat;
    private Kustantajat tmpKustantajat;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        tyhjenna(); // turha? olkoot nyt kuitenkin virheiden varalta
    }


    @Override
    public void setDefault(Nippu oletus) {
        nippu = oletus;
        kirjaKohdalla = oletus.getKirja();
        hylly = oletus.getHylly();
        tmpKirjailijat = hylly.annaKirjailijat();
        tmpKustantajat = hylly.annaKustantajat();
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
                tmpKirjailijat.getWithId(mKirjailija.getSelectedText()));
        kirjaKohdalla.setKustantaja(
                tmpKustantajat.getWithId(mKustantaja.getSelectedText()));
        kirjaKohdalla.setVuosi(Integer.parseInt(mVuosi.getText()));
        kirjaKohdalla.setKuvaus(mKuvaus.getText());
        kirjaKohdalla.setLuettu(mLuettu.getText());
        kirjaKohdalla.setArvio(Integer.parseInt(mArvio.getText()));
        kirjaKohdalla.setLisatietoja(mLisatietoja.getText());

        hylly.set(tmpKirjailijat);
        hylly.set(tmpKustantajat);

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
        tmp.rekisteroi();
        tmpKirjailijat.lisaa(tmp);
        setComboBox(mKirjailija, annaKirjailijat(kirjaKohdalla));
    }


    private void lisaaKustantaja() {
        String nimi = Dialogs.showInputDialog("Anna kustantajan nimi", "");
        if (nimi == null)
            return;
        Kustantaja tmp = new Kustantaja(nimi);
        tmp.rekisteroi();
        tmpKustantajat.lisaa(tmp); // SailoException
        setComboBox(mKustantaja, annaKustantajat(kirjaKohdalla));
    }


    private void poistaKirjailija() {
        tmpKirjailijat.poista(mKirjailija.getSelectedText());
    }


    private void poistaKustantaja() {
        tmpKustantajat.poista(mKustantaja.getSelectedText());
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
        setComboBox(mKirjailija, annaKirjailijat(kirjaKohdalla));
        setComboBox(mKustantaja, annaKustantajat(kirjaKohdalla));
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
     * @param eka kirjailija jonka halutaan olevan ensimmäisenä
     * @return kaikki kirjailijat, tietty kirjailija ensimmäisenä
     */
    public String annaKirjailijat(Kirja eka) {
        String kirjailija = hylly.kirjanKirjailija(eka);
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
     * @param eka kustantaja jonka halutaan olevan ensimmäisenä
     * @return kaikki kustantajat, tietty kustantaja ensimmäisenä
     */
    public String annaKustantajat(Kirja eka) {
        String kustantaja = hylly.kirjanKustantaja(eka);
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
}
