package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.apu.*;

/**
 * Kirjailija joka osaa mm. itse huolehtia id:stään
 * @author anvemaha
 * @version 20.2.2020 pohjaa
 * @version 28.3.2020 mallin mukaiseksi
 */
public class Kirjailija {
    private int id = 0;
    private String nimi = "";

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
     * Alustetaan kirjailija.
     * @param n haluttu kirjailijan id
     */
    public Kirjailija(int n) {
        this.id = n;
    }


    /**
     * Apumetodi, jolla saadaan täytetyä testiarvot kirjailijalle.
     * Kirjailijan nimen perään arvotaan numero, ettei kahdella kirjailijalla
     * olisi samaa nimeä.
     * @param n kirjailijan id
     */
    public void tayta(int n) {
        this.id = n;
        this.nimi = "Kirjailija " + rand(1000, 9999);
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
     *  Kirjailija kir1 = new Kirjailija();
     *  kir1.getId() === 0;
     *  kir1.rekisteroi();
     *  Kirjailija kir2 = new Kirjailija();
     *  kir2.rekisteroi();
     *  int n1 = kir1.getId();
     *  int n2 = kir2.getId();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }


    /**
     * @return Palauttaa kirjailijan nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Palautetaan kirjailijan id
     * @return kirjailijan id
     */
    public int getId() {
        return id;
    }


    /**
     * Asettaa id:n ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param n asetettava id
     */
    private void setId(int n) {
        id = n;
        if (id >= seuraavaId)
            seuraavaId = id + 1;
    }


    /**
     * Palauttaa kirjailijan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kirjailija tollpaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Kirjailija kir = new Kirjailija();
     *  kir.parse("2  |Dmitri Gluhovski");
     *  kir.toString() === "2|Dmitri Gluhovski";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getId() + "|" + getNimi();
    }


    /**
     * Selvittää kirjailijan tiedot | erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaId on suurempi kuin tuleva id
     * @param rivi josta kirjailijan tiedot otetaan
     * @example
     * <pre name="test">
     *  Kirjailija kir = new Kirjailija();
     *  kir.parse("3  |Randall Munroe");
     *  kir.getId() === 3;
     *  kir.toString() === "3|Randall Munroe";
     *  kir.rekisteroi();
     *  int n = kir.getId();
     *  kir.parse("" + (n+20));
     *  kir.rekisteroi();
     *  kir.getId() === n+20+1;
     *  kir.toString() === "" + (n+20+1) + "|Randall Munroe";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return this.toString().equals(obj.toString());
    }


    @Override
    public int hashCode() {
        return id;
    }


    /**
     * Testiohjelma kirjailijalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailija kir = new Kirjailija();
        kir.tayta(2);
        kir.tulosta(System.out);
    }
}