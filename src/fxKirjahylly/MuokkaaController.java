package fxKirjahylly;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private TextArea mKuvaus;
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
        viesti.setTextFill(Color.GREEN);
        viesti.setText("Tallennettu!");
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
        paivitaKirjailijatComboBox();

    }


    @FXML
    private void handleLisaaKustantaja() throws SailoException {
        String nimi = Dialogs.showInputDialog("Anna kustantajan nimi", "");
        if (nimi == null)
            return;
        Kustantaja tmp = new Kustantaja(nimi);
        tmp.rekisteroi();
        hylly.lisaa(tmp); // sailoexception
        paivitaKustantajatComboBox();
    }


    @FXML
    private void handlePoistaKirjailija() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjailijoita!");
    }


    @FXML
    private void handlePoistaKustantaja() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kustantajia!");
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

    // private String kuvaus;
    // private String luettu;
    // private int arvio;
    // private String lisatietoja;


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
        paivitaKirjailijatComboBox();
        paivitaKustantajatComboBox();
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
     * TODO: yhdistä kustantajaversio ja tämä samaksi
     * TODO: voi ehkä asettaa vain valitun eikä tarvi tehdä tällästä tyhmää custom järjestystä?
     */
    public void paivitaKirjailijatComboBox() {
        StringBuilder sisalto = new StringBuilder("");
        var k = hylly.getKirjailijat();
        var iterator = k.iterator();

        // Lisätään valitun kirjan kirjailija ekana jotta se olisi valittuna
        String kirjailija = hylly.annaKirjailija(kirjaKohdalla).getNimi();
        sisalto.append(kirjailija).append("\n");

        for (int i = 0; i < k.getLkm(); i++) {
            var tmp = iterator.next();
            // ei saa lisätä uudestaan ekana lisättyä (kallista iffitellä?)
            if (tmp.getNimi() != kirjailija)
                sisalto.append(tmp.getNimi()).append("\n");
        }
        mKirjailija.setRivit(sisalto.toString());
        if (mKirjailija.getSelectedText().equals("null"))
            mKirjailija.setRivit(
                    mKirjailija.getRivit().replace("null", "Ei valittu"));
    }


    /**
     * Päivittää comboboxchooserin niin, että kirjan kustantaja
     * on ensimmäisenä (ts. valittuna) ja kaikki muut mahdolliset kustantajat ovat valittavissa
     * TODO: yhdistä kirjailijaversio ja tämä samaksi
     * TODO: voi ehkä asettaa vain valitun eikä tarvi tehdä tällästä tyhmää custom järjestystä?
     */
    public void paivitaKustantajatComboBox() {
        StringBuilder sisalto = new StringBuilder("");
        var k = hylly.getKustantajat();
        var iterator = k.iterator();

        // Lisätään valitun kirjan kirjailija ekana jotta se olisi valittuna
        String kustantaja = hylly.annaKustantaja(kirjaKohdalla).getNimi();
        sisalto.append(kustantaja).append("\n");

        for (int i = 0; i < k.getLkm(); i++) {
            var tmp = iterator.next();
            // ei saa lisätä uudestaan ekana lisättyä (kallista iffitellä?)
            if (tmp.getNimi() != kustantaja)
                sisalto.append(tmp.getNimi()).append("\n");
        }

        mKustantaja.setRivit(sisalto.toString());
        if (mKustantaja.getSelectedText().equals("null"))
            mKustantaja.setRivit(
                    mKustantaja.getRivit().replace("null", "Ei valittu"));
    }

}
