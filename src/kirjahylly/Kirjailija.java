package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;
import static kanta.apu.*;

/**
 * Kirjailija joka osaa mm. itse huolehtia id:stään
 * @author anvemaha
 * @version 20.2.2020
 * TODO: poiketaan mallista joten joudutaan pyörittelemään asioita erilailla mutta tässä nyt vain pohjaa
 * (ei luultavasti edes toimi nyt järkevästi)
 */
public class Kirjailija {
    private int id;
    private String nimi;

    private static int seuraavaId = 1;

    /**
     * Alustetaan kirjailija. Toistaiseksi ei tarvita mitään :-)
     */
    public Kirjailija() {
        // ei tarvita mitään :-)
    }


    /**
     * Alustetaan kirjailija.
     * @param nimi haluttu kirjailijan nimi
     */
    public Kirjailija(String nimi) {
        this.nimi = nimi;
    }


    /**
     * Alustetaan kirjailija tietyllä id:llä
     * @param id haluttu id
     * TODO: estä saman id:n käyttö jollain omalla exceptionilla?
     */
    public Kirjailija(int id) {
        this.id = id;
    }


    /**
     * Apumetodi, jolla saadaan täytetyä testiarvot Kirjailijalle
     * kirjailijan nimen perään arvotaan numero, ettei kahdella kirjailijalla
     * olisi samaa nimeä.
     * @param newId kirjailijan id
     */
    public void tayta_tmp(int newId) {
        this.id = newId;
        this.nimi = "Dimitrios " + rand(1000, 9999);
    }


    /**
     * @return Palauttaa kirjailijan nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Tulostetaan kirjailijan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(id + " " + nimi);
    }


    /**
     * Tulostetaan kirjailijan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa kirjailijalle seuraavan rekisterinumeron
     * @return kirjailijan uusi id
     * @example
     * <pre name="test">
     *  Kirjailija mortti = new Kirjailija();
     *  mortti.getId() === 0;
     *  mortti.rekisteroi();
     *  Kirjailija vertti = new Kirjailija();
     *  vertti.rekisteroi();
     *  int n1 = mortti.getId();
     *  int n2 = vertti.getId();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }


    /**
     * Palautetaan kirjailijan id
     * @return kirjailijan id
     */
    public int getId() {
        return id;
    }


    /**
     * Testiohjelma Kirjailijalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailija kir = new Kirjailija();
        kir.tayta_tmp(2);
        kir.tulosta(System.out);
    }
}
