package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.apu.*;

/**
 * Kustantaja joka osaa mm. itse huolehtia id:stään
 * @author anvemaha
 * @version 9.3.2020 pohjaa
 * @version 28.3.2020 mallin mukaiseksi
 */
public class Kustantaja {
    private int id;
    private String nimi;

    private static int seuraavaId = 1;

    /**
     * Alustetaan kustantaja. Toistaiseksi ei tarvita mitään :-)
     */
    public Kustantaja() {
        // ei tarvita mitään :-)
    }


    /**
     * Alustetaan kustantaja.
     * @param nimi haluttu kustantajan nimi
     */
    public Kustantaja(String nimi) {
        this.nimi = nimi;
    }


    /**
     * Alustetaan kustantaja tietyllä id:llä.
     * @param n haluttu kustantajan nimi
     */
    public Kustantaja(int n) {
        this.id = n;
    }


    /**
     * Apumetodi, jolla saadaan täytetyä testiarvot kustantajalle.
     * Kustantajan nimen perään arvotaan numero, ettei kahdella kustantajalla
     * olisi samaa nimeä.
     * @param n kustantajan id
     */
    public void tayta(int n) {
        this.id = n;
        this.nimi = "Kustantaja " + rand(1000, 9999);
    }


    /**
     * Tulostetaan kustantajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(id + " " + nimi);
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
     *  Kustantaja kus1 = new Kustantaja();
     *  kus1.getId() === 0;
     *  kus1.rekisteroi();
     *  Kustantaja kus2 = new Kustantaja();
     *  kus2.rekisteroi();
     *  int n1 = kus1.getId();
     *  int n2 = kus2.getId();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }


    /**
     * @return Palauttaa kustantajan nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Palautetaan kustantajan id
     * @return kustantajan id
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
     * Palauttaa kustantajan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kustantaja tollpaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Kustantaja kus = new Kustantaja();
     *  kus.parse("1  |Random House");
     *  kus.toString() === "1|Random House";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getId() + "|" + getNimi();
    }


    /**
     * Selvittää kustantajan tiedot | erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaId on suurempi kuin tuleva id
     * @param rivi josta kustantajan tiedot otetaan
     * @example
     * <pre name="test">
     *  Kustantaja kus = new Kustantaja();
     *  kus.parse("5  |Orion Publishing Group");
     *  kus.getId() === 5;
     *  kus.toString() === "5|Orion Publishing Group";
     *  
     *  kus.rekisteroi();
     *  int n = kus.getId();
     *  kus.parse("" + (n+20));
     *  kus.rekisteroi();
     *  kus.getId() === n+20+1;
     *  kus.toString() === "" + (n+20+1) + "|Orion Publishing Group";
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
     * Testiohjelma kustantajalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kustantaja kus = new Kustantaja();
        kus.tayta(2);
        kus.tulosta(System.out);
    }
}
