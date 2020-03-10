package fxKirjahylly;

import java.util.Iterator;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.Kirjailijat;
import kirjahylly.Kustantaja;
import kirjahylly.Kustantajat;

/**
 * @author antti
 * @version 20.2.2020
 *
 */
public class MuokkaaController implements ModalControllerInterface<String> {

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
    private TextField mLisa;

    @FXML
    private Text error;

    @Override
    public String getResult() {
        kirja.setKirjailijaId(
                hylly.annaKirjailijaId(mKirjailija.getSelectedText()));
        return "";
    }


    @Override
    public void setDefault(String oletus) {
        //
    }


    @Override
    public void handleShown() {
        alusta();
    }

    // -------------------------------

    private Kirjahylly hylly;
    private Kirja kirja;

    /**
     * Alustetaan dialogin tiedot
     */
    public void alusta() {
        hylly = KirjahyllyGUIController.hylly;
        kirja = KirjahyllyGUIController.kirjaKohdalla;

        mNimi.setText(kirja.getNimi());
        mKuvaus.setText(kirja.getKuvaus());
        mLuettu.setText(kirja.getLuettu());
        mArvio.setText("" + kirja.getArvio() + "/5");
        mLisa.setText(kirja.getLisatietoja());
        error.setText("");

        paivitaKirjailijatComboBox();
        paivitaKustantajatComboBox();
        mKustantaja.setRivit("");
    }


    /**
     * Päivittää comboboxchooserin niin, että kirjan kirjailija
     * on ensimmäisenä (ts. valittuna) ja kaikki muut mahdolliset kirjailijat ovat valittavissa
     */
    public void paivitaKirjailijatComboBox() {
        StringBuilder sisalto = new StringBuilder("");
        var k = hylly.getKirjailijat();
        var iterator = k.iterator();

        // Lisätään valitun kirjan kirjailija ekana jotta se olisi valittuna
        String valitunKirjanKirjailija = hylly.annaKirjailija(kirja).getNimi();
        sisalto.append(valitunKirjanKirjailija).append("\n");

        for (int i = 0; i < k.getLkm(); i++) {
            var tmp = iterator.next();
            // ei saa lisätä uudestaan ekana lisättyä (kallista ifftellä?)
            if (tmp.getNimi() != valitunKirjanKirjailija)
                sisalto.append(tmp.getNimi()).append("\n");
        }

        mKirjailija.setRivit(sisalto.toString());
    }


    /**
     * Päivittää comboboxchooserin niin, että kirjan kirjailija
     * on ensimmäisenä (ts. valittuna) ja kaikki muut mahdolliset kirjailijat ovat valittavissa
     */
    public void paivitaKustantajatComboBox() {
        StringBuilder sisalto = new StringBuilder("");
        var k = hylly.getKustantajat();
        var iterator = k.iterator();

        // Lisätään valitun kirjan kirjailija ekana jotta se olisi valittuna
        String valitunKirjanKustantaja = hylly.annaKustantaja(kirja).getNimi();
        sisalto.append(valitunKirjanKustantaja).append("\n");

        for (int i = 0; i < k.getLkm(); i++) {
            var tmp = iterator.next();
            // ei saa lisätä uudestaan ekana lisättyä (kallista ifftellä?)
            if (tmp.getNimi() != valitunKirjanKustantaja)
                sisalto.append(tmp.getNimi()).append("\n");
        }

        mKirjailija.setRivit(sisalto.toString());
    }


    /**
     * Käsittelee kirjailijan lisäyksen
     */
    public void handleLisaaKirjailija() {
        String kirjailijanNimi = Dialogs
                .showInputDialog("Anna kirjailijan nimi", "");
        if (kirjailijanNimi == null)
            return;
        Kirjailija tmp = new Kirjailija(kirjailijanNimi);
        tmp.rekisteroi();
        hylly.lisaa(tmp);
        paivitaKirjailijatComboBox();
    }


    /**
     * Käsittelee kirjailijan lisäyksen
     */
    public void handleLisaaKustantaja() {
        String kustantajanNimi = Dialogs
                .showInputDialog("Anna kirjailijan nimi", "");
        if (kustantajanNimi == null)
            return;
        hylly.lisaa(new Kustantaja(kustantajanNimi));
        paivitaKustantajatComboBox();
    }


    /**
     * Käsittelee peruutuksen
     */
    public void handlePeruuta() {
        Dialogs.showMessageDialog("Vielä ei osata peruuttaa");
    }


    /**
     * Käsittelee tallentamisen
     */
    public void handleTallenna() {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa");
    }


    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                MuokkaaController.class.getResource("MuokkaaView.fxml"),
                "Kirjahylly", modalityStage, oletus);
    }
}