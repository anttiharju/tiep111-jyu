package fxKirjahylly;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author antti
 * @version 20.2.2020
 *
 */
public class MuokkaaController implements ModalControllerInterface<String> {

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
        //
    }


    /**
     * Käsittelee peruutuksen
     */
    public void handlePeruuta() {
        Platform.exit();
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