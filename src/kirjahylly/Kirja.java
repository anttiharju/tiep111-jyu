package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.apu.*;

/**
 * Kirjahyllyn kirja joka osaa huolehtia id:stään.
 * @author anvemaha
 * @version 27.3.2020
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
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        /** /
        out.println(nimi + "|" + kirjailija + "|" + kustantaja + "|" + vuosi
                + "|" + kuvaus + "|" + luettu + "|" + arvio + "|"
                + lisatietoja);
        /**/
        // toimii paremmin koska textarea / scrollpane ei suostu olemaan leveä
        // vaikka kuinka pakotan, ylempi tosin testatessa ehkä
        /**/
        out.println(nimi + "\n" + kirjailija + "\n" + kustantaja + "\n" + vuosi
                + "\n" + kuvaus + "\n" + luettu + "\n" + arvio + "\n"
                + lisatietoja);
        /**/
    }


    /**
     * Tulostetaan kirjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
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
     * Asettaa id:n ja samalla varmistaa että
     * seuraava id on aina suurempi kuin tähän mennessä suurin.
     * @param n asetettava id
     */
    private void setId(int n) {
        id = n;
        if (id >= seuraavaId)
            seuraavaId = id + 1;
    }


    /**
     * Palauttaa kirjan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kirja tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kirja k = new Kirja();
     *   k.parse("   3  |  Ankka Aku   | 030201-111C");
     *   k.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getId() + "|" + nimi + "|" + kirjailija + "|" + kustantaja
                + "|" + vuosi + "|" + kuvaus + "|" + luettu + "|" + arvio + "|"
                + lisatietoja;
    }


    /**
     * Selvitää kirjan tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta kirjan tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Kirja k = new Kirja();
     *   k.parse("   3  |  Ankka Aku   | 030201-111C");
     *   k.getTunnusNro() === 3;
     *    // loppuu | koska kenttiä on enemmänkin
     *   k.toString().startsWith("3|Ankka Aku|030201-111C|") === true;
     *
     *   k.rekisteroi();
     *   int n = k.getTunnusNro();
     *   // otetaan pelkkä id
     *   k.parse(""+(n+20));
     *   // tarkistetaan, että seuraavalla on isompi
     *   k.rekisteroi();
     *   k.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        kirjailija = Mjonot.erota(sb, '|', kirjailija);
        kustantaja = Mjonot.erota(sb, '|', kustantaja);
        vuosi = Mjonot.erota(sb, '|', vuosi);
        kuvaus = Mjonot.erota(sb, '|', kuvaus);
        luettu = Mjonot.erota(sb, '|', luettu);
        arvio = Mjonot.erota(sb, '|', arvio);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }


    @Override
    public boolean equals(Object kirja) {
        if (kirja == null)
            return false;
        return this.toString().equals(kirja.toString());
    }


    @Override
    public int hashCode() {
        // alkeellinen
        return id;
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


    /**
     * @return kirjan kirjailijan id:n
     */
    public int getKirjailijaId() {
        return kirjailija;
    }


    /**
     * @return kirjan kustantajan id:n
     */
    public int getKustantajaId() {
        return kustantaja;
    }
}
