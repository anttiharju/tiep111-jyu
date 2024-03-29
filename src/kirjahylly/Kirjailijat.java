package kirjahylly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import kanta.SailoException;

/**
 * Kirjahyllyn kirjailijat, joka osaa mm. lisätä uuden kirjailijan
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 20.2.2020 pohjaa
 * @version 27.3.2020 mallin mukaiseksi koska sooloilu kostautui
 */
public class Kirjailijat implements Iterable<Kirjailija>, Cloneable {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    private final Collection<Kirjailija> alkiot = new ArrayList<Kirjailija>();

    /**
     * Kirjailijoiden alustaminen
     */
    public Kirjailijat() {
        // ei tarvita mitään (:
    }


    /**
     * Kirjailijoiden alustaminen (kloonaus)
     * @param muutettu onko muutettu vai ei
     * @param tiedostonPerusNimi tiedoston perusnimi
     */
    public Kirjailijat(boolean muutettu, String tiedostonPerusNimi) {
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
     * Lisää uuden kirjailijan tietorakenteeseen. Ottaa kirjailijan omistukseensa.
     * @param kirjailija lisättävä kirjailija. Huom tietorakenne muuttuu omistajaksi
     * @param kloonaus lisätäänkö kloonatessa vai ei
     * @return lisätyn kirjailijan id:n
     * @example
     * <pre name="test">
     *  #THROWS SailoException 
     *   Kirjailijat kirjailijat = new Kirjailijat();
     *   Kirjailija kir1 = new Kirjailija(), kir2 = new Kirjailija();
     *   kirjailijat.getLkm() === 0;
     *   kirjailijat.lisaa(kir1); kirjailijat.getLkm() === 1;
     *   kirjailijat.lisaa(kir2); kirjailijat.getLkm() === 2;
     *   kirjailijat.lisaa(kir1); kirjailijat.getLkm() === 3;
     *   Iterator<Kirjailija> it = kirjailijat.iterator(); 
     *   it.next() === kir1;
     *   it.next() === kir2; 
     *   it.next() === kir1;  
     *   kirjailijat.lisaa(kir1); kirjailijat.getLkm() === 4;
     *   kirjailijat.lisaa(kir1); kirjailijat.getLkm() === 5;
     * </pre>
     */
    public int lisaa(Kirjailija kirjailija, boolean kloonaus) {
        alkiot.add(kirjailija);
        if (!kloonaus)
            muutettu = true;
        return kirjailija.getId();
    }


    /**
     * Lisää uuden kirjailijan tietorakenteeseen. Ottaa kirjailijan omistukseensa.
     * @param kirjailija lisättävä kirjailija. Huom tietorakenne muuttuu omistajaksi
     * @return lisätyn kirjailijan id:n
     */
    public int lisaa(Kirjailija kirjailija) {
        return lisaa(kirjailija, false);
    }


    /**
     * Poistaa kirjailijan tietorakenteesta.
     * @param kirjailija poistettava kirjailija.
     * @example 
     * <pre name="test"> 
     *  #THROWS SailoException  
     *   Kirjailijat kirjailijat = new Kirjailijat(); 
     *   Kirjailija kir1 = new Kirjailija(), kir2 = new Kirjailija(), kir3 = new Kirjailija(); 
     *   kir1.rekisteroi(); kir2.rekisteroi(); kir3.rekisteroi(); 
     *   int id1 = kir1.getId(); 
     *   kirjailijat.lisaa(kir1); kirjailijat.lisaa(kir2); kirjailijat.lisaa(kir3); 
     *   kirjailijat.poista(id1+1); 
     *   kirjailijat.poista(id1);   kirjailijat.getLkm() === 1; 
     *   kirjailijat.poista(id1+3); kirjailijat.getLkm() === 1; 
     *   kirjailijat.poista(id1+2); kirjailijat.getLkm() === 0; 
     * </pre> 
     */
    public void poista(Kirjailija kirjailija) {
        alkiot.remove(kirjailija);
        muutettu = true;
    }


    /**
     * Poistaa id:n perusteella
     * @param id poistettavan kirjailijan id
     */
    public void poista(int id) {
        poista(annaKirjailija(id));
    }


    /**
     * Poistaa kirjailijan tietorakenteesta.
     * @param nimi poistettavan kirjailija nimi
     */
    public void poista(String nimi) {
        poista(annaKirjailija(nimi));
    }


    /**
     * Lukee kirjailijat tiedostosta
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     *  #THROWS SailoException 
     *  #import java.io.File;
     *   Kirjailijat kirjailijat = new Kirjailijat();
     *   Kirjailija k21 = new Kirjailija(); k21.tayta(2);
     *   Kirjailija k11 = new Kirjailija(); k11.tayta(1);
     *   Kirjailija k22 = new Kirjailija(); k22.tayta(2); 
     *   Kirjailija k12 = new Kirjailija(); k12.tayta(1); 
     *   Kirjailija k23 = new Kirjailija(); k23.tayta(2); 
     *   String tiedNimi = "tmp_testihylly_kirjailijat";
     *   File ftied = new File(tiedNimi+".dat");
     *   ftied.delete();
     *   kirjailijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *   kirjailijat.lisaa(k21);
     *   kirjailijat.lisaa(k11);
     *   kirjailijat.lisaa(k22);
     *   kirjailijat.lisaa(k12);
     *   kirjailijat.lisaa(k23);
     *   kirjailijat.tallenna();
     *   kirjailijat = new Kirjailijat();
     *   kirjailijat.lueTiedostosta(tiedNimi);
     *   Iterator<Kirjailija> i = kirjailijat.iterator();
     *   i.next().toString() === k21.toString();
     *   i.next().toString() === k11.toString();
     *   i.next().toString() === k22.toString();
     *   i.next().toString() === k12.toString();
     *   i.next().toString() === k23.toString();
     *   i.hasNext() === false;
     *   kirjailijat.lisaa(k23);
     *   kirjailijat.tallenna();
     *   ftied.delete() === true;
     *   File fbak = new File(tiedNimi+".backup");
     *   fbak.delete() === true;         
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi(), StandardCharsets.UTF_8))) {
            String rivi;
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == '#')
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
     * Tiedoston muoto:
     * <pre>
     *  #id|kirjailija
     *  1|Ernest Cline
     *  2|Dmitri Gluhovski
     *  3|Randall Munroe
     *  4|Greg Egan
     * </pre>
     */
    public void tallenna() throws SailoException {
        if (!muutettu)
            return;

        File fbackup = new File(getBackupNimi());
        File ftied = new File(getTiedostonNimi());
        fbackup.delete();
        ftied.renameTo(fbackup);

        try (PrintWriter fo = new PrintWriter(new FileWriter(
                ftied.getCanonicalPath(), StandardCharsets.UTF_8))) {
            fo.println("#id|kirjailija");
            for (Kirjailija kir : this)
                fo.println(kir.toString());
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
     *  #PACKAGEIMPORT
     *  #import java.util.*;
     *  
     *   Kirjailijat kirjailijat = new Kirjailijat();
     *   Kirjailija p12 = new Kirjailija(2); kirjailijat.lisaa(p12);
     *   Kirjailija p21 = new Kirjailija(1); kirjailijat.lisaa(p21);
     *   Kirjailija p32 = new Kirjailija(2); kirjailijat.lisaa(p32);
     *   Kirjailija p41 = new Kirjailija(1); kirjailijat.lisaa(p41);
     *   Kirjailija p52 = new Kirjailija(2); kirjailijat.lisaa(p52);
     *   
     *   Iterator<Kirjailija> i2 = kirjailijat.iterator();
     *   i2.next() === p12;
     *   i2.next() === p21;
     *   i2.next() === p32;
     *   i2.next() === p41;
     *   i2.next() === p52;
     *   i2.next() === p21; #THROWS NoSuchElementException
     *   
     *   int n = 0;
     *   int kidt[] = {2,1,2,1,2};
     *   
     *   for ( Kirjailija kir : kirjailijat ) {
     *       kir.getId() === kidt[n]; n++;
     *   }
     *   
     *   n === 5;
     * </pre>
     */
    @Override
    public Iterator<Kirjailija> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kirjan kirjailija
     * @param id kirjan id jonka kirjailijaa etsitään
     * @return tietorakenne jossa viite löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     *  #import java.util.*;
     *  
     *   Kirjailijat kirjailijat = new Kirjailijat();
     *   Kirjailija pete = new Kirjailija(0); kirjailijat.lisaa(pete);
     *   Kirjailija vesa = new Kirjailija(1); kirjailijat.lisaa(vesa);
     *   Kirjailija tupu = new Kirjailija(2); kirjailijat.lisaa(tupu);
     *   Kirjailija hupu = new Kirjailija(3); kirjailijat.lisaa(hupu);
     *   Kirjailija lupu = new Kirjailija(4); kirjailijat.lisaa(lupu);
     *   Kirjailija rupu = new Kirjailija(5); kirjailijat.lisaa(rupu);
     *   
     *   Kirjailija etsitty = new Kirjailija();
     *   
     *   etsitty = kirjailijat.annaKirjailija(0);
     *   etsitty == pete === true;
     *   etsitty = kirjailijat.annaKirjailija(1);
     *   etsitty == vesa === true;
     *   etsitty = kirjailijat.annaKirjailija(2);
     *   etsitty == tupu === true;
     *   etsitty = kirjailijat.annaKirjailija(3);
     *   etsitty == hupu === true;
     *   etsitty = kirjailijat.annaKirjailija(4);
     *   etsitty == lupu === true;
     *   etsitty = kirjailijat.annaKirjailija(5);
     *   etsitty == rupu === true;
     * </pre>
     */
    public Kirjailija annaKirjailija(int id) {
        for (Kirjailija kir : alkiot)
            if (kir.getId() == id)
                return kir;
        return new Kirjailija();
    }


    /**
     * Palauttaa annetulla nimellä olevan kirjailijan
     * @param nimi kirjailijan nimi
     * @return kirjailija olion
     */
    public Kirjailija annaKirjailija(String nimi) {
        for (Kirjailija kirjailija : alkiot)
            if (kirjailija.getNimi().equals(nimi))
                return kirjailija;
        return new Kirjailija();
    }


    /**
     * Palauttaa tietyn nimisen kirjailijan sen id:llä
     * @param nimi etsittävän kirjailijan nimi
     * @return kirjailijan id:n, 0 jos ei löydy
     */
    public int getId(String nimi) {
        for (Kirjailija kirjailija : alkiot)
            if (kirjailija.getNimi().equals(nimi))
                return kirjailija.getId();
        return 0;
    }


    /**
     * Kloonaa tietorakenteen muttei sen alkioita,
     * jotta voidaan poistaa ja lisätä uusia muokkausdialogissa.
     */
    @Override
    public Kirjailijat clone() {
        Kirjailijat klooni = new Kirjailijat(muutettu, tiedostonPerusNimi);
        for (Kirjailija kirjailija : alkiot)
            klooni.lisaa(kirjailija, true);
        return klooni;
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