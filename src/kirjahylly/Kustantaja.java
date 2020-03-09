package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;
import static kanta.apu.*;

/**
 * Kustantaja joka osaa mm. itse huolehtia id:stään
 * @author anvemaha
 * @version 9.3.2020
 * TODO: poiketaan mallista joten joudutaan pyörittelemään asioita erilailla mutta tässä nyt vain pohjaa
 * (ei luultavasti edes toimi nyt järkevästi)
 */
public class Kustantaja {
    private int id;
    private String kustantaja;

    private static int seuraavaId = 1;

    /**
     * Alustetaan kustantaja. Toistaiseksi ei tarvita mitään :-)
     */
    public Kustantaja() {
        // ei tarvita mitään :-)
    }


    /**
     * Alustetaan kustantaja tietyllä id:llä
     * @param id haluttu id
     */
    public Kustantaja(int id) {
        this.id = id;
    }


    /**
     * Apumetodi, jolla saadaan täytetyä testiarvot kustantajalle
     * kustantajan nimen perään arvotaan numero, ettei kahdella kustantajalla
     * olisi samaa nimeä.
     * @param newId kustantajan id
     */
    public void tayta_tmp(int newId) {
        this.id = newId;
        this.kustantaja = "Generic Publisher " + rand(1000, 9999);
    }


    /**
     * Tulostetaan kustantajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(id + " " + kustantaja);
    }


    /**
     * Tulostetaan kustantajan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa kustantajalle seuraavan rekisterinumeron
     * @return kustantajan uusi id
     * @example
     * <pre name="test">
     *  Kustantaja publisher1 = new Kustantaja();
     *  publisher1.getId() === 0;
     *  publisher1.rekisteroi();
     *  Kustantaja publisher2 = new Kustantaja();
     *  publisher2.rekisteroi();
     *  int n1 = publisher1.getId();
     *  int n2 = publisher2.getId();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }


    /**
     * Palautetaan kustantajan id
     * @return kustantajan id
     */
    public int getId() {
        return id;
    }


    /**
     * Testiohjelma Kustanjalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kustantaja kus = new Kustantaja();
        kus.tayta_tmp(2);
        kus.tulosta(System.out);
    }
}
