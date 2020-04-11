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
public class Kirja implements Cloneable {

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

    @Override
    public Kirja clone() {
        Kirja klooni = new Kirja();
        klooni.setId(id);
        klooni.setNimi(nimi);
        klooni.setKirjailija(kirjailija);
        klooni.setKustantaja(kustantaja);
        klooni.setVuosi(vuosi);
        klooni.setKuvaus(kuvaus);
        klooni.setLuettu(luettu);
        klooni.setArvio(arvio);
        klooni.setLisatietoja(lisatietoja);
        return klooni;
    }


    /**
     * @param toinen verrattava kirja
     * @return onko sama kirja vai ei
     */
    public boolean onkoSama(Kirja toinen) {
        if (id != toinen.getId())
            return false;
        if (!nimi.equals(toinen.getNimi()))
            return false;
        if (kirjailija != toinen.getKirjailijaId())
            return false;
        if (kustantaja != toinen.getKustantajaId())
            return false;
        if (vuosi != toinen.getVuosi())
            return false;
        if (!kuvaus.equals(toinen.getKuvaus()))
            return false;
        if (!luettu.equals(toinen.getLuettu()))
            return false;
        if (arvio != toinen.getArvio())
            return false;
        if (!lisatietoja.equals(toinen.getLisatietoja()))
            return false;
        return true;
    }


    /**
     * @return kirjan nimen
     * @example
     * <pre name="test">
     *  Kirja metro = new Kirja();
     *  metro.tayta();
     *  metro.getNimi() =R= "Kirja .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Apumetodi kirjojen lisäykseen
     */
    public void tayta() {
        nimi = "";
        kirjailija = 0;
        kustantaja = 0;
        vuosi = 0;
        kuvaus = "";
        luettu = "";
        arvio = 0;
        lisatietoja = "";
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     */
    public void tayta_test() {
        nimi = "Kirja " + rand(1, 9999);
        kirjailija = 0;
        kustantaja = 0;
        vuosi = 2020;
        kuvaus = "Asioita tapahtuu";
        luettu = "28.3.2020";
        arvio = 5;
        lisatietoja = "Jänniä juttuja";
    }


    /**
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Kirja\t\t<t/>" + nimi);
        out.println("Kirjailija\t<t/>" + "<kirjailija/>");
        out.println("Kustantaja\t" + "<kustantaja/>");
        out.println("Julkaisuvuosi\t" + vuosi);
        out.println("Lyhyt kuvaus\t" + kuvaus);
        out.println("Luettu\t\t" + luettu);
        out.println("Arvio\t\t" + arvio);
        out.println("Lisätietoja\t" + lisatietoja);
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
     *  Kirja k = new Kirja();
     *  k.parse("2  |Metro 2033      |2         |2         |2005 |Artjom seikkailee metrossa");
     *  k.toString().startsWith("2|Metro 2033|2|2|2005|Artjom seikkailee metrossa") === true;
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
     *  Kirja k = new Kirja();
     *  k.parse("3  |What if?        |3         |3         |2014 |Absurdeja hypoteettisiä kysymyksiä");
     *  k.getId() === 3;
     *  k.toString().startsWith("3|What if?|3|3|2014|Absurdeja hypoteettisiä kysymyksiä") === true;
     *  
     *  k.rekisteroi();
     *  int n = k.getId();
     *  // otetaan pelkkä id
     *  k.parse(""+(n+20));
     *  // tarkistetaan, että seuraavalla on isompi
     *  k.rekisteroi();
     *  k.getId() === n+20+1;
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
        metro2033.tayta_test();
        metro2033.tulosta(System.out);

        metro2035.tayta_test();
        metro2035.tulosta(System.out);

        metro2035.tayta_test();
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


    /**
     * @return kirjan julkaisuvuoden
     */
    public int getVuosi() {
        return vuosi;
    }


    /**
     * @return kirjan kuvauksen
     */
    public String getKuvaus() {
        return kuvaus;
    }


    /**
     * @return kirjan lukupvm
     */
    public String getLuettu() {
        return luettu;
    }


    /**
     * @return kirjan saaman arvion
     */
    public int getArvio() {
        return arvio;
    }


    /**
     * @return kirjan lisätiedot
     */
    public String getLisatietoja() {
        return lisatietoja;
    }


    /**
     * @param nimi asetettava nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;

    }


    /**
     * @param kirjailija asetettava kirjailija
     */
    public void setKirjailija(int kirjailija) {
        this.kirjailija = kirjailija;

    }


    /**
     * @param kustantaja asetettava kustantaja
     */
    public void setKustantaja(int kustantaja) {
        this.kustantaja = kustantaja;
    }


    /**
     * @param vuosi asetettava vuosi
     */
    public void setVuosi(int vuosi) {
        this.vuosi = vuosi;
    }


    /**
     * @param kuvaus asetettava kuvaus
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }


    /**
     * @param luettu asetettava lukupvm
     */
    public void setLuettu(String luettu) {
        this.luettu = luettu;
    }


    /**
     * @param arvio asettava arvio
     */
    public void setArvio(int arvio) {
        this.arvio = arvio;
    }


    /**
     * @param lisatietoja asettavat lisätiedot
     */
    public void setLisatietoja(String lisatietoja) {
        this.lisatietoja = lisatietoja;
    }
}
