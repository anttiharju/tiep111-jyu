package fxKirjahylly;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Kysytään hyllyn nimi ja luodaan tätä varten dialogi.
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 27.3.2020
 */
public class AvaaController implements ModalControllerInterface<String> {

    @FXML
    private TextField textVastaus;
    private String vastaus = null;

    @FXML
    private void handleSeuraava() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }


    @FXML
    private void handlePeruuta() {
        ModalController.closeStage(textVastaus);
    }


    /**
     * Nopeuttaa ohjelman käyttöä kun voi painaa Enter kirjoittaessa ja haluttu tiedosto aukeaa
     * @param keyEvent painettu näppäin
     */
    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            handleSeuraava();
    }


    @Override
    public String getResult() {
        return vastaus;
    }


    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }

    // =============================


    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Peruuta, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AvaaController.class.getResource("AvaaView.fxml"), "Kerho",
                modalityStage, oletus);
    }
}
