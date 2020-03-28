package fxKirjahylly;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kirjahylly.Kirja;

/**
 * Muokataan kirjaa erillisessä dialogissa
 * @author anvemaha
 * @version 28.3.2020
 */
public class MuokkaaController implements ModalControllerInterface<Kirja> {

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
    private Label error;

    @FXML
    private void handleTallenna() {
        ModalController.closeStage(error);
    }


    @FXML
    private void handlePeruuta() {
        ModalController.closeStage(error);
    }


    @FXML
    private void handleLisaaKirjailija() {
        //
    }


    @FXML
    private void handleLisaaKustantaja() {
        //
    }


    @FXML
    private void handlePoistaKirjailija() {
        //
    }


    @FXML
    private void handlePoistaKustantaja() {
        //
    }


    @Override
    public void setDefault(Kirja oletus) {
        kirjaKohdalla = oletus;
        naytaKirja(kirjaKohdalla);
    }


    @Override
    public Kirja getResult() {
        return kirjaKohdalla;
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        mNimi.requestFocus();
    }

    // ========================================================
    private Kirja kirjaKohdalla;

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
    }


    /**
     * Näytetään kirjan tiedot
     * @param kirja näytettävä kirja
     */
    public void naytaKirja(Kirja kirja) {
        if (kirja == null)
            return;
        mNimi.setText(kirja.getNimi());
        mKirjailija.setPromptText("");
        mKustantaja.setPromptText("");
        mVuosi.setText("" + kirja.getVuosi());
        mKuvaus.setText(kirja.getKuvaus());
        mLuettu.setText(kirja.getLuettu());
        mArvio.setText("" + kirja.getArvio());
        mLisatietoja.setText(kirja.getLisatietoja());
        error.setText("");
    }


    /**
     * Luodaan kirjan muokkausdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kirja kysyKirja(Stage modalityStage, Kirja oletus) {
        return ModalController.showModal(
                MuokkaaController.class.getResource("MuokkaaView.fxml"),
                "Muokkaa", modalityStage, oletus, null);
    }
}
