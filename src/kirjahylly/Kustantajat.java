package kirjahylly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Kirjahyllyn kustantajat, joka osaa mm. lisätä uuden kustantajan
 * @author anvemaha
 * @version 9.3.2020 pohjaa
 * @version 27.3.2020 mallin mukaiseksi koska sooloilu kostautui
 */
public class Kustantajat implements Iterable<Kustantaja>, Cloneable {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    // Taulukko kustantajista
    private final Collection<Kustantaja> alkiot = new ArrayList<Kustantaja>();

    /**
     * Kustantajien alustaminen
     */
    public Kustantajat() {
        // ei tarvita mitään (:
    }


    /**
     * Kustantajien alustaminen (kloonaus)
     * @param muutettu onko muutettu vai ei
     * @param tiedostonPerusNimi tiedoston perusnimi
     */
    public Kustantajat(boolean muutettu, String tiedostonPerusNimi) {
        this.muutettu = muutettu;
        this.tiedostonPerusNimi = tiedostonPerusNimi;
    }


    /**
     * @return onko muutettu
     */
    public boolean getMuutettu() {
        return muutettu;
    }


    /**
     * Lisää uuden kustantajan tietorakenteeseen. Ottaa kustantajan omistukseensa.
     * @param kustantaja lisättävä kustantaja. Huom tietorakenne muuttuu omistajaksi
     * @param kloonaus lisätäänkö kloonatessa vai ei
     * @return lisätyn kustantajan id:n
     */
    public int lisaa(Kustantaja kustantaja, boolean kloonaus) {
        alkiot.add(kustantaja);
        muutettu = !kloonaus;
        return kustantaja.getId();
    }


    /**
     * Lisää uuden kustantajan tietorakenteeseen. Ottaa kustantajan omistukseensa.
     * @param kustantaja lisättävä kustantaja. Huom tietorakenne muuttuu omistajaksi
     * @return lisätyn kustantajan id:n
     */
    public int lisaa(Kustantaja kustantaja) {
        return lisaa(kustantaja, false);
    }


    /**
     * Poistaa kustantajan tietorakenteesta.
     * @param kustantaja poistettava kustantaja.
     */
    public void poista(Kustantaja kustantaja) {
        alkiot.remove(kustantaja);
        muutettu = true;
    }


    /**
     * Poistaa kustantajan tietorakenteesta.
     * @param nimi poistettavan kustantajan nimi
     */
    public void poista(String nimi) {
        for (Kustantaja kustantaja : alkiot)
            if (kustantaja.getNimi().equals(nimi))
                poista(kustantaja);
    }


    @Override
    public Kustantajat clone() {
        Kustantajat klooni = new Kustantajat(muutettu, tiedostonPerusNimi);
        for (Kustantaja kustantaja : alkiot)
            klooni.lisaa(kustantaja, true);
        return klooni;
    }


    /**
     * Lukee kustantajat tiedostosta
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kustantajat kustantajat = new Kustantajat();
     *  Kustantaja k21 = new Kustantaja(); k21.tayta(2);
     *  Kustantaja k11 = new Kustantaja(); k11.tayta(1);
     *  Kustantaja k22 = new Kustantaja(); k22.tayta(2); 
     *  Kustantaja k12 = new Kustantaja(); k12.tayta(1); 
     *  Kustantaja k23 = new Kustantaja(); k23.tayta(2); 
     *  String tiedNimi = "tmp_testihylly_kustantajat";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kustantajat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kustantajat.lisaa(k21);
     *  kustantajat.lisaa(k11);
     *  kustantajat.lisaa(k22);
     *  kustantajat.lisaa(k12);
     *  kustantajat.lisaa(k23);
     *  kustantajat.tallenna();
     *  kustantajat = new Kustantajat();
     *  kustantajat.lueTiedostosta(tiedNimi);
     *  Iterator<Kustantaja> i = kustantajat.iterator();
     *  i.next().toString() === k21.toString();
     *  i.next().toString() === k11.toString();
     *  i.next().toString() === k22.toString();
     *  i.next().toString() === k12.toString();
     *  i.next().toString() === k23.toString();
     *  i.hasNext() === false;
     *  kustantajat.lisaa(k23);
     *  kustantajat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".backup");
     *  fbak.delete() === true;     
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {

            String rivi;
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == '#')
                    continue;
                Kustantaja kus = new Kustantaja();
                kus.parse(rivi); // voisi olla virhekäsittely
                lisaa(kus);
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
     * Tallentaa kustantajat tiedostoon.
     * @throws SailoException jos tallennus epäonnistuu
     * Tiedoston muoto:
     * <pre>
     *  #id|kustantaja
     *  1|Random House
     *  2|Victor Gollancz
     *  3|John Murray
     *  4|AST
     *  5|Orion Publishing Group
     *  
     * </pre>
     */
    public void tallenna() throws SailoException {
        if (!muutettu)
            return;

        File fbackup = new File(getBackupNimi());
        File ftied = new File(getTiedostonNimi());
        fbackup.delete(); // if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbackup); // if ... System.err.println("Ei voi nimetä");

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) {
            fo.println("#id|kustantaja");
            for (Kustantaja kus : this) {
                fo.println(kus.toString());
            }
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
     * Palauttaa kirjahyllyn kustantajien lukumäärän
     * @return kustantajien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varmuuskopiotiedoston nimen
     * @return varmuuskopiotiedoston nimi
     */
    public String getBackupNimi() {
        return tiedostonPerusNimi + ".backup";
    }


    /**
     * Iteraattori kaikkien kustantajien läpikäymiseen
     * @return kustantajaiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kustantajat kustantajat = new Kustantajat();
     *  Kustantaja p12 = new Kustantaja(2); kustantajat.lisaa(p12);
     *  Kustantaja p21 = new Kustantaja(1); kustantajat.lisaa(p21);
     *  Kustantaja p32 = new Kustantaja(2); kustantajat.lisaa(p32);
     *  Kustantaja p41 = new Kustantaja(1); kustantajat.lisaa(p41);
     *  Kustantaja p52 = new Kustantaja(2); kustantajat.lisaa(p52);
     *  
     *  Iterator<Kustantaja> i2 = kustantajat.iterator();
     *  i2.next() === p12;
     *  i2.next() === p21;
     *  i2.next() === p32;
     *  i2.next() === p41;
     *  i2.next() === p52;
     *  i2.next() === p21; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Kustantaja kus : kustantajat ) {
     *      kus.getId() === jnrot[n]; n++;
     *  }
     *  
     *  n === 5;
     * </pre>
     */
    @Override
    public Iterator<Kustantaja> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kirjan kustantaja
     * @param id kirjan id jonka kustantajaa etsitään
     * @return tietorakenne jossa viite löydettyyn kustantajaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kustantajat kustantajat = new Kustantajat();
     *  Kustantaja pete = new Kustantaja(0); kustantajat.lisaa(pete);
     *  Kustantaja vesa = new Kustantaja(1); kustantajat.lisaa(vesa);
     *  Kustantaja tupu = new Kustantaja(2); kustantajat.lisaa(tupu);
     *  Kustantaja hupu = new Kustantaja(3); kustantajat.lisaa(hupu);
     *  Kustantaja lupu = new Kustantaja(4); kustantajat.lisaa(lupu);
     *  Kustantaja rupu = new Kustantaja(5); kustantajat.lisaa(rupu);
     *  
     *  Kustantaja etsitty = new Kustantaja();
     *  
     *  etsitty = kustantajat.annaKustantaja(0);
     *  etsitty == pete === true;
     *  etsitty = kustantajat.annaKustantaja(1);
     *  etsitty == vesa === true;
     *  etsitty = kustantajat.annaKustantaja(2);
     *  etsitty == tupu === true;
     *  etsitty = kustantajat.annaKustantaja(3);
     *  etsitty == hupu === true;
     *  etsitty = kustantajat.annaKustantaja(4);
     *  etsitty == lupu === true;
     *  etsitty = kustantajat.annaKustantaja(5);
     *  etsitty == rupu === true;
     * </pre>
     */
    public Kustantaja annaKustantaja(int id) {
        for (Kustantaja kus : alkiot)
            if (kus.getId() == id)
                return kus;
        return new Kustantaja();
    }


    /**
     * Palauttaa annetulla nimellä olevan kustantajan
     * @param nimi kustantajan nimi
     * @return kustantajan id:n
     */
    public Kustantaja annaKustantaja(String nimi) {
        for (Kustantaja kus : alkiot)
            if (kus.getNimi().equals(nimi))
                return kus;
        return new Kustantaja();
    }


    /**
     * Palauttaa annetun kustantajan id:n
     * @param kustantaja kustantajan nimi
     * @return kustantajan id:n
     */
    public int getId(Kustantaja kustantaja) {
        String nimi = kustantaja.getNimi();
        for (Kustantaja kus : alkiot)
            if (kus.getNimi().equals(nimi))
                return kus.getId();
        return 0;
    }


    /**
     * Palauttaa tietyn nimisen kustantajan sen id:llä
     * @param nimi etsittävän kustantajan nimi
     * @return kustantajan id:n, 0 jos ei löydy
     */
    public int getId(String nimi) {
        for (Kustantaja kustantaja : alkiot)
            if (kustantaja.getNimi().equals(nimi))
                return kustantaja.getId();
        return 0;
    }


    /**
     * Testipääohjelma kustantajille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kustantajat kustantajat = new Kustantajat();
        Kustantaja vesa = new Kustantaja();
        vesa.tayta(2);
        Kustantaja tupu = new Kustantaja();
        tupu.tayta(1);
        Kustantaja hupu = new Kustantaja();
        hupu.tayta(2);
        Kustantaja lupu = new Kustantaja();
        lupu.tayta(2);

        kustantajat.lisaa(vesa);
        kustantajat.lisaa(tupu);
        kustantajat.lisaa(hupu);
        kustantajat.lisaa(tupu);
        kustantajat.lisaa(lupu);

        System.out.println("+ Kustantajat testi:");

        for (Kustantaja kus : kustantajat) {
            System.out.println(kus.getId() + " ");
            kus.tulosta(System.out);
        }
    }
}