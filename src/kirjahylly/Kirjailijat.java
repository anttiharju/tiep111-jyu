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
 * Kirjahyllyn kirjailijat, joka osaa mm. lisätä uuden kirjailijan
 * 
 * @author anvemaha
 * @version 20.2.2020 pohjaa
 * @version 27.3.2020 mallin mukaiseksi koska sooloilu kostautui
 */
public class Kirjailijat implements Iterable<Kirjailija> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    // Taulukko kirjailijoista
    private final Collection<Kirjailija> alkiot = new ArrayList<Kirjailija>();

    /**
     * Kirjailijoiden alustaminen
     */
    public Kirjailijat() {
        // ei tarvita mitään (:
    }


    /**
     * Lisää uuden kirjailijan tietorakenteeseen. Ottaa kirjailijan omistukseensa.
     * @param kirjailija lisättävä kirjailija. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kirjailija kirjailija) {
        alkiot.add(kirjailija);
        muutettu = true;
    }


    /**
     * Lukee kirjailijat tiedostosta
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija k21 = new Kirjailija(); k21.tayta(2);
     *  Kirjailija k11 = new Kirjailija(); k11.tayta(1);
     *  Kirjailija k22 = new Kirjailija(); k22.tayta(2); 
     *  Kirjailija k12 = new Kirjailija(); k12.tayta(1); 
     *  Kirjailija k23 = new Kirjailija(); k23.tayta(2); 
     *  String tiedNimi = "testihylly";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kirjailijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjailijat.lisaa(k21);
     *  kirjailijat.lisaa(k11);
     *  kirjailijat.lisaa(k22);
     *  kirjailijat.lisaa(k12);
     *  kirjailijat.lisaa(k23);
     *  kirjailijat.tallenna();
     *  kirjailijat = new Kirjailijat();
     *  kirjailijat.lueTiedostosta(tiedNimi);
     *  Iterator<Kirjailija> i = kirjailijat.iterator();
     *  i.next().toString() === k21.toString();
     *  i.next().toString() === k11.toString();
     *  i.next().toString() === k22.toString();
     *  i.next().toString() === k12.toString();
     *  i.next().toString() === k23.toString();
     *  i.hasNext() === false;
     *  kirjailijat.lisaa(k23);
     *  kirjailijat.tallenna();
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
                if ("".equals(rivi) || rivi.charAt(0) == ';')
                    continue;
                Kirjailija kir = new Kirjailija();
                kir.parse(rivi); // voisi olla virhekäsittely
                lisaa(kir);
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
     * Tallentaa kirjailijat tiedostoon.
     * @throws SailoException jos tallennus epäonnistuu
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
            for (Kirjailija kir : this) {
                fo.println(kir.toString());
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
     * Palauttaa kirjahyllyn kirjailijoiden lukumäärän
     * @return kirjailijoiden lukumäärä
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
     * Iteraattori kaikkien kirjailijoiden läpikäymiseen
     * @return kirjailijaiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija p12 = new Kirjailija(2); kirjailijat.lisaa(p12);
     *  Kirjailija p21 = new Kirjailija(1); kirjailijat.lisaa(p21);
     *  Kirjailija p32 = new Kirjailija(2); kirjailijat.lisaa(p32);
     *  Kirjailija p41 = new Kirjailija(1); kirjailijat.lisaa(p41);
     *  Kirjailija p52 = new Kirjailija(2); kirjailijat.lisaa(p52);
     *  
     *  Iterator<Kirjailija> i2 = kirjailijat.iterator();
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
     *  for ( Kirjailija kir : kirjailijat ) {
     *      kir.getKirjailijaId() === jnrot[n]; n++;
     *  }
     *  
     *  n === 5;
     * </pre>
     */
    @Override
    public Iterator<Kirjailija> iterator() {
        return alkiot.iterator();
    }


    /**
     * TODO: mun tietorakenteella ei voi tehdä näin? kirjat tietää juttunsa mutta jutut ei tiedä kirjaansa
     * TODO: koska kirjalla voi olla vain yksi kirjailija, ei tarvita List, vaihda nimi (-t) ja testit ja muualla missä käytetään
     * Haetaan kirjan kirjailija
     * @param id kirjan id jonka kirjailijaa etsitään
     * @return tietorakenne jossa viite löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija pete = new Kirjailija(0); kirjailijat.lisaa(pete);
     *  Kirjailija vesa = new Kirjailija(1); kirjailijat.lisaa(vesa);
     *  Kirjailija tupu = new Kirjailija(2); kirjailijat.lisaa(tupu);
     *  Kirjailija hupu = new Kirjailija(3); kirjailijat.lisaa(hupu);
     *  Kirjailija lupu = new Kirjailija(4); kirjailijat.lisaa(lupu);
     *  Kirjailija rupu = new Kirjailija(5); kirjailijat.lisaa(rupu);
     *  
     *  Kirjailija etsitty = new Kirjailija();
     *  
     *  kirjailijat.annaKirjailija(0);
     *  etsitty == pete === true;
     *  kirjailijat.annaKirjailija(1);
     *  etsitty == vesa === true;
     *  kirjailijat.annaKirjailija(2);
     *  etsitty == tupu === true;
     *  kirjailijat.annaKirjailija(3);
     *  etsitty == hupu === true;
     *  kirjailijat.annaKirjailija(4);
     *  etsitty == lupu === true;
     *  kirjailijat.annaKirjailija(5);
     *  etsitty == rupu === true;
     * </pre>
     */
    public Kirjailija annaKirjailija(int id) {
        for (Kirjailija kir : alkiot)
            if (kir.getId() == id)
                return kir;
        return new Kirjailija();
    }


    /**
     * Testipääohjelma kirjailijoille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailijat kirjailijat = new Kirjailijat();
        Kirjailija vesa = new Kirjailija();
        vesa.tayta(2);
        Kirjailija tupu = new Kirjailija();
        tupu.tayta(1);
        Kirjailija hupu = new Kirjailija();
        hupu.tayta(2);
        Kirjailija lupu = new Kirjailija();
        lupu.tayta(2);

        kirjailijat.lisaa(vesa);
        kirjailijat.lisaa(tupu);
        kirjailijat.lisaa(hupu);
        kirjailijat.lisaa(tupu);
        kirjailijat.lisaa(lupu);

        System.out.println("+ Kirjailijat testi:");

        for (Kirjailija kir : kirjailijat) {
            System.out.println(kir.getId() + " ");
            kir.tulosta(System.out);
        }
    }
}
