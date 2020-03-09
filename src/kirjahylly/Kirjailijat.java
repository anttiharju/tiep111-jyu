package kirjahylly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Kirjahyllyn kirjailijat, joka osaa mm. lisätä uuden kirjailijan
 * 
 * @author anvemaha
 * @version 20.2.2020
 */
public class Kirjailijat implements Iterable<Kirjailija> {

    private String tiedostonNimi = "";
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
    }


    /**
     * Lukee kirjailijat tiedostosta
     * TODO: kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".kir";
        throw new SailoException(
                "Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kirjailijat tiedostoon.
     * TODO: kesken
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException(
                "Ei osta vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kirjahyllyn kirjailijoiden lukumäärän
     * @return kirjailijoiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
     *  Kirjailijat authors = new Kirjailijat();
     *  Kirjailija pete = new Kirjailija(2); authors.lisaa(pete);
     *  Kirjailija vesa = new Kirjailija(1); authors.lisaa(vesa);
     *  Kirjailija tupu = new Kirjailija(2); authors.lisaa(tupu);
     *  Kirjailija hupu = new Kirjailija(1); authors.lisaa(hupu);
     *  Kirjailija lupu = new Kirjailija(2); authors.lisaa(lupu);
     *  
     *  Iterator<Kirjailija> i2 = authors.iterator();
     *  i2.next() === pete;
     *  i2.next() === vesa;
     *  i2.next() === tupu;
     *  i2.next() === hupu;
     *  i2.next() === lupu;
     *  i2.next() === vesa; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Kirjailija kir : kirjailijaVar ) {
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
     * Haetaan kirjan kirjailija
     * @param id kirjan id jonka kirjailijaa etsitään
     * @return tietorakenne jossa viite löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijatVar = new Kirjailijat();
     *  Kirjailija pete = new Kirjailija(2); authors.lisaa(pete);
     *  Kirjailija vesa = new Kirjailija(1); authors.lisaa(vesa);
     *  Kirjailija tupu = new Kirjailija(2); authors.lisaa(tupu);
     *  Kirjailija hupu = new Kirjailija(1); authors.lisaa(hupu);
     *  Kirjailija lupu = new Kirjailija(2); authors.lisaa(lupu);
     *  Kirjailija rupu = new Kirjailija(5); authors.lisaa(rupu);
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjailijatVar.annaKirjailijat(3);
     *  loytyneet.size() === 0;
     *  loytyneet = kirjailijatVar.annaKirjailijat(1);
     *  loytyneet.size() === 2;
     *  loytyneet.get(0) == vesa === true;
     *  loytyneet.get(1) == hupu === true;
     *  loytyneet = kirjailijatVar.annaKirjailijat(5);
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == rupu === true;
     * </pre>
     */
    public List<Kirjailija> annaKirjailijat(int id) {
        // TODO: koska kirjalla voi olla vain yksi kirjailija, ei tarvita List,
        // vaihda nimi (-t) ja testit ja muualla missä käytetään
        List<Kirjailija> loydetyt = new ArrayList<Kirjailija>();
        for (Kirjailija kir : alkiot)
            if (kir.getId() == id)
                loydetyt.add(kir);
        return loydetyt;
    }


    /**
     * Testipääohjelma kirjailijoille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailijat kirjailijat1 = new Kirjailijat();
        Kirjailija vesa = new Kirjailija();
        vesa.tayta_tmp(2);
        Kirjailija tupu = new Kirjailija();
        tupu.tayta_tmp(1);
        Kirjailija hupu = new Kirjailija();
        hupu.tayta_tmp(2);
        Kirjailija lupu = new Kirjailija();
        lupu.tayta_tmp(2);

        kirjailijat1.lisaa(vesa);
        kirjailijat1.lisaa(tupu);
        kirjailijat1.lisaa(hupu);
        kirjailijat1.lisaa(tupu);
        kirjailijat1.lisaa(lupu);

        System.out.println("Kirjailijat testi:");

        List<Kirjailija> kirjailijat2 = kirjailijat1.annaKirjailijat(2);

        for (Kirjailija kir : kirjailijat2) {
            System.out.println(kir.getId() + " ");
            kir.tulosta(System.out);
        }
    }
}
