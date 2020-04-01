package kirjahylly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Hyllyn kirjat joka osaa mm. lisätä uuden kirjan
 * @author anvemaha
 * @version 20.2.2020 pohjaa
 * @version 27.3.2020 mallin mukaiseksi
 */
public class Kirjat implements Iterable<Kirja> {

    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "";
    private Kirja[] alkiot = new Kirja[8];

    /**
     * Oletusmuodostaja
     */
    public Kirjat() {
        // ei tarvi mitään
    }


    /**
     * Asettaa kokoNimen
     * @param kokoNimi hyllyn uusi nimi
     */
    public Kirjat(String kokoNimi) {
        this.kokoNimi = kokoNimi;
    }


    /**
     * Lisää uuden kirjan tietorakenteeseen Ottaa kirjan omistukseensa.
     * @param kirja lisätäävän jäsenen viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kirjat kirjat = new Kirjat();
     * Kirja k1 = new Kirja(), k2 = new Kirja();
     * kirjat.getLkm() === 0;
     * kirjat.lisaa(k1); kirjat.getLkm() === 1;
     * kirjat.lisaa(k2); kirjat.getLkm() === 2;
     * kirjat.lisaa(k1); kirjat.getLkm() === 3;
     * kirjat.anna(0) === k1;
     * kirjat.anna(1) === k2;
     * kirjat.anna(2) === k1;
     * kirjat.anna(1) == k1 === false;
     * kirjat.anna(1) == k2 === true;
     * kirjat.anna(3) === k1; #THROWS IndexOutOfBoundsException
     * kirjat.lisaa(k1); kirjat.getLkm() === 4;
     * kirjat.lisaa(k1); kirjat.getLkm() === 5;
     * kirjat.lisaa(k1); kirjat.getLkm() === 6;
     * kirjat.lisaa(k1); kirjat.getLkm() === 7;
     * kirjat.lisaa(k1); kirjat.getLkm() === 8;
     * kirjat.lisaa(k1); kirjat.getLkm() === 9;
     * kirjat.lisaa(k1); kirjat.getLkm() === 10;
     * kirjat.lisaa(k1); kirjat.getLkm() === 11;
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length)
            alkiot = Arrays.copyOf(alkiot, alkiot.length * 2);
        alkiot[lkm] = kirja;
        lkm++;
        muutettu = true;
    }


    /**
     * Korvaa annetulla id:llä löytyvän kirjan annetulla kirjalla
     * @param vid korvattavan kirjan id
     * @param kir uusi kirja
     * TODO: ilmeisesti lkm ei päivity oikein ja siksi tuo null tarkistus
     */
    public void korvaa(int vid, Kirja kir) {
        for (Kirja k : alkiot) {
            if (k != null && k.getId() == vid) {
                k = kir;
                muutettu = true;
            }
        }
    }


    /**
     * Palauttaa viitteen i:nteen kirjaan
     * @param i monennenko kirjan viite halutaan
     * @return viite kirjaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee kirjan tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjat kirjat = new Kirjat();
     *  Kirja k1 = new Kirja(); k1.tayta();
     *  Kirja k2 = new Kirja(); k2.tayta();
     *  Kirja k3 = new Kirja(); k3.tayta(); 
     *  Kirja k4 = new Kirja(); k4.tayta(); 
     *  Kirja k5 = new Kirja(); k5.tayta(); 
     *  String tiedNimi = "tmp_testihylly_kirjat";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kirjat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjat.lisaa(k1);
     *  kirjat.lisaa(k2);
     *  kirjat.lisaa(k3);
     *  kirjat.lisaa(k4);
     *  kirjat.lisaa(k5);
     *  kirjat.tallenna();
     *  kirjat = new Kirjat();
     *  kirjat.lueTiedostosta(tiedNimi);
     *  Iterator<Kirja> i = kirjat.iterator();
     *  i.next().toString() === k1.toString();
     *  i.next().toString() === k2.toString();
     *  i.next().toString() === k3.toString();
     *  i.next().toString() === k4.toString();
     *  i.next().toString() === k5.toString();
     *  i.hasNext() === false;
     *  kirjat.lisaa(k5);
     *  kirjat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".backup");
     *  fbak.delete() === true;     
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {
            kokoNimi = fi.readLine();
            if (kokoNimi == null)
                throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if (rivi == null)
                throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == '#')
                    continue;
                Kirja k = new Kirja();
                k.parse(rivi); // voisi olla virhekäsittely
                lisaa(k);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
        }

    }


    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Tallentaa kirjat tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     *  antti
     *  #id|kirjan nimi     |kirjailija|kustantaja|vuosi|lyhyt kuvaus                      |luettu    |arvio|lisätietoja
     *  1  |Ready Player One|1         |1         |2011 |Wade Wattsin seikkailut           |26.12.2016|4    |Elokuva pilas tän
     *  2  |Metro 2033      |2         |2         |2005 |Artjom seikkailee metrossa        |31.7.2017 |5    |Peli oli huono
     *  3  |What if?        |3         |3         |2014 |Absurdeja hypoteettisiä kysymyksiä|1.1.2020  |5    |xkcd sarjakuvien tekijältä
     *  4  |Metro 2035      |2         |4         |2015 |Artjom on sekaisin                |14.8.2018 |4    |2034 voi jättää lukematta
     *  5  |Diaspora        |4         |5         |1997 |Ihmiskunta elää ohjelmistona      |5.3.2019  |3    |Englanninkielinen
     *  6  |Permutation City|4         |5         |1994 |Simuloitu yhteiskunta             |6.9.2019  |3    |Painaa 306g
     *  
     * </pre>
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!muutettu)
            return;

        File fbak = new File(getBackupNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) {
            fo.println(getKokoNimi());
            fo.println(
                    "#id|kirjan nimi|kirjailija|kustantaja|vuosi|lyhyt kuvaus|luettu|arvio|lisätietoja");
            for (Kirja k : this)
                fo.println(k.toString());
        } catch (FileNotFoundException ex) {
            throw new SailoException(
                    "Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    /**
     * Palauttaa hyllyn koko nimen
     * @return hyllyn koko nimi merkkijonona
     */
    public String getKokoNimi() {
        return kokoNimi;
    }


    /**
     * Palauttaa kirjahyllyn kirjojen lukumäärän
     * @return kirjojen lukumäärän
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varmuuskopiotiedoston nimen
     * @return varmuuskopiotiedoston nimi
     */
    public String getBackupNimi() {
        return tiedostonPerusNimi + ".backup";
    }

    /**
     * Luokka kirjojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Kirjat kirjat = new Kirjat();
     * Kirja k1 = new Kirja(), k2 = new Kirja();
     * k1.rekisteroi(); k2.rekisteroi();
     *
     * kirjat.lisaa(k1); 
     * kirjat.lisaa(k2); 
     * kirjat.lisaa(k1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Kirja k:kirjat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" " + k.getId());           
     * 
     * String tulos = " " + k1.getId() + " " + k2.getId() + " " + k1.getId();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Kirja>  i=kirjat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Kirja k = i.next();
     *   ids.append(" " + k.getId());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Kirja>  i=kirjat.iterator();
     * i.next() == k1  === true;
     * i.next() == k2  === true;
     * i.next() == k1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class KirjatIterator implements Iterator<Kirja> {
        private int kohdalla = 0;

        /**
         * Onko seuraava kirja olemassa
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä kirjoja
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava kirja
         * @return seuraava kirja
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Kirja next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("Kirjat loppu");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Ei osata vielä poistaa");
        }
    }

    /**
     * Palautetaan iteraattori kirjoista
     * @return kirja iteraattori
     */
    @Override
    public Iterator<Kirja> iterator() {
        return new KirjatIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kirjoista 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Kirjat kirjat = new Kirjat(); 
     *   Kirja k1 = new Kirja(); k1.parse("1|Ready Player One|1|1|"); 
     *   Kirja k2 = new Kirja(); k2.parse("2|Metro 2033|2|2|"); 
     *   Kirja k3 = new Kirja(); k3.parse("3|What if?|3|3|"); 
     *   Kirja k4 = new Kirja(); k4.parse("4|Metro 2035|2|4|"); 
     *   Kirja k5 = new Kirja(); k5.parse("5|Diaspora|4|5|"); 
     *   kirjat.lisaa(k1); kirjat.lisaa(k2); kirjat.lisaa(k3); kirjat.lisaa(k4); kirjat.lisaa(k5);
     *   // TODO: toistaiseksi palauttaa kaikki kirjat
     * </pre> 
     */
    @SuppressWarnings("unused")
    public Collection<Kirja> etsi(String hakuehto, int k) {
        Collection<Kirja> loytyneet = new ArrayList<Kirja>();
        for (Kirja kir : this) {
            loytyneet.add(kir);
        }
        return loytyneet;
    }


    /**
     * Testiohjelma kirjat luokalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjat kirjat = new Kirjat();
        Kirja m1 = new Kirja(), m2 = new Kirja();
        m1.rekisteroi();
        m1.tayta();
        m2.rekisteroi();
        m2.tayta();

        try {
            kirjat.lisaa(m1);
            kirjat.lisaa(m2);

            System.out.println("+ Kirjat testi");
            for (int i = 0; i < kirjat.getLkm(); i++) {
                Kirja m = kirjat.anna(i);
                System.out.println("Kirjan nro: " + i);
                m.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
