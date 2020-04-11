package fxKirjahylly;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * Tulostuscontroller
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 11.4.2020
 */
public class TulostusController implements ModalControllerInterface<String> {
    @FXML
    TextArea tulostusAlue;
    String tulostettava;

    @FXML
    private void handleSulje() {
        ModalController.closeStage(tulostusAlue);
    }


    @FXML
    private void handleTulosta() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent(tulostettava);
            webEngine.print(job);
            job.endJob();
        }
    }


    @Override
    public String getResult() {
        return null;
    }


    @Override
    public void setDefault(String oletus) {
        // kallista mutta näyttääpähän kunnolliselta
        tulostettava = "<pre>" + oletus.replaceAll("<t/>", "") + "</pre>";
        tulostusAlue.setText(oletus.replaceAll("<t/>", "\t"));
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        // ei mitään
    }


    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }


    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = ModalController.showModeless(
                TulostusController.class.getResource("TulostusView.fxml"),
                "Tulostus", tulostus);
        return tulostusCtrl;
    }

}
