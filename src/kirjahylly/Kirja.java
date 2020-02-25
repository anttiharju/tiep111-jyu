package kirjahylly;

import java.io.PrintStream;
import static kanta.apu.*;

/**
 * Kirjahyllyn kirja joka osaa huolehtia id:stään.
 * @author anvemaha
 * @version 20.2.2020
 *
 */
public class Kirja {

    private int id;

    private String nimi;
    private int kirjailija;
    private int kustantaja;
    private int vuosi;
    private String kuvaus;
    private String luettu;
    private int arvio;
    private String lisatietoja;

    private static int seuraavaId = 1;

    /**
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(nimi + "|" + kirjailija + "|" + kustantaja + "|" + vuosi
                + "|" + kuvaus + "|" + luettu + "|" + arvio + "|"
                + lisatietoja);
    }


    /**
     * Antaa kirjalle seuraavan id:n.
     * @return kirjan uusi id
     * @example
     * <pre name="test">
     *   Kirja k1 = new Kirja();
     *   k1.getId() === 0;
     *   k1.rekisteroi();
     *   Kirja k2 = new Kirja();
     *   k2.rekisteroi();
     *   int n1 = k1.getId();
     *   int n2 = k2.getId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }


    /**
     * @return kirjan id:n
     */
    public int getId() {
        return id;
    }


    /**
     * @return kirjan nimen
     * @example
     * <pre name="test">
     *  Kirja metro = new Kirja();
     *  metro.tayta_metro();
     *  metro.getNimi() =R= "Metro .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     * TODO: poista 
     */
    public void tayta_metro() {
        nimi = "Metro " + rand(1, 9999);
        kirjailija = 1;
        kustantaja = 1;
        vuosi = 2005;
        kuvaus = "Artjom seikkailee metrossa";
        luettu = "31.7.2017";
        arvio = 5;
        lisatietoja = "Peli oli huono";
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kirja metro2033 = new Kirja(), metro2035 = new Kirja();
        metro2033.rekisteroi();
        metro2035.rekisteroi();
        metro2033.tulosta(System.out);
        metro2033.tayta_metro(); // rakennusteline
        metro2033.tulosta(System.out);

        metro2035.tayta_metro();
        metro2035.tulosta(System.out);

        metro2035.tayta_metro();
        metro2035.tulosta(System.out);
    }
}
