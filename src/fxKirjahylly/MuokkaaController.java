package fxKirjahylly;

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
import kirjahylly.Kirjailijat;

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


    /**
     * Alustetaan dialogin tiedot
     */
    public void alusta() {
        Kirja kirja = KirjahyllyGUIController.kirjaKohdalla;
        mNimi.setText(kirja.getNimi());
        mKuvaus.setText(kirja.getKuvaus());
        mLuettu.setText(kirja.getLuettu());
        mArvio.setText("" + kirja.getArvio() + "/5");
        mLisa.setText(kirja.getLisatietoja());
        error.setText("");

        Kirjahylly hylly = KirjahyllyGUIController.hylly;

        Kirjailijat kir = hylly.getKirjailijat();
        Kustantajat kus = hylly.getKustantajat();

        mKirjailija.setRivit("");
        mKustantaja.setRivit("");
    }


    /**
     * Käsittelee kirjailijan lisäyksen
     */
    public void handleLisaaKirjailija() {
        Dialogs.showMessageDialog("Vielä ei osata kirjailla");
    }


    /**
     * Käsittelee kirjailijan lisäyksen
     */
    public void handleLisaaKustantaja() {
        Dialogs.showMessageDialog("Vielä ei osata kustantaa");
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