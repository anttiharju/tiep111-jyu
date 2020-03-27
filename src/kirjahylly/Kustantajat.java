package kirjahylly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Kirjahyllyn kustantajat, joka osaa mm. lisätä uuden kustantajan
 * 
 * @author anvemaha
 * @version 9.3.2020
 * TODO: kustantaja ja kirjailija hommat voi yhdistää TYPE hommilla?
 */
public class Kustantajat implements Iterable<Kustantaja> {

    private String tiedostonNimi = "";
    private final Collection<Kustantaja> alkiot = new ArrayList<Kustantaja>();

    /**
     * Kustantajien alustaminen
     */
    public Kustantajat() {
        // ei tarvita mitään (:
    }


    /**
     * Lisää uuden kustantajan tietorakenteeseen. Ottaa kustantajan omistukseensa.
     * @param kustantaja lisättävä kustantaja. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kustantaja kustantaja) {
        alkiot.add(kustantaja);
    }


    /**
     * Lukee kustantajat tiedostosta
     * TODO: kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".kus";
        throw new SailoException(
                "Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kustantajat tiedostoon.
     * TODO: kesken
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException(
                "Ei osta vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kirjahyllyn kustantajien lukumäärän
     * @return kustantajien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
     *  Kustantajat publishers = new Kustantajat();
     *  Kustantaja p12 = new Kustantaja(2); publishers.lisaa(p12);
     *  Kustantaja p21 = new Kustantaja(1); publishers.lisaa(p21);
     *  Kustantaja p32 = new Kustantaja(2); publishers.lisaa(p32);
     *  Kustantaja p41 = new Kustantaja(1); publishers.lisaa(p41);
     *  Kustantaja p52 = new Kustantaja(2); publishers.lisaa(p52);
     *  
     *  Iterator<Kirjailija> i2 = publishers.iterator();
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
     *  for ( Kustantaja kus : kustantajaVar ) {
     *      kus.getKirjailijaId() === jnrot[n]; n++;
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
     * TODO: mun tietorakenteella ei voi tehdä näin? kirjat tietää juttunsa mutta jutut ei tiedä kirjaansa
     * TODO: koska kirjalla voi olla vain yksi kustantaja, ei tarvita List, vaihda nimi (-t) ja testit ja muualla missä käytetään
     * Haetaan kirjan kustantaja
     * @param id kirjan id jonka kustantajaa etsitään
     * @return tietorakenne jossa viite löydettyyn kustantajaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kustantajat kustantajatVar = new Kustantajat();
     *  Kustantaja pete = new Kustantaja(0); kustantajatVar.lisaa(pete);
     *  Kustantaja vesa = new Kustantaja(1); kustantajatVar.lisaa(vesa);
     *  Kustantaja tupu = new Kustantaja(2); kustantajatVar.lisaa(tupu);
     *  Kustantaja hupu = new Kustantaja(3); kustantajatVar.lisaa(hupu);
     *  Kustantaja lupu = new Kustantaja(4); kustantajatVar.lisaa(lupu);
     *  Kustantaja rupu = new Kustantaja(5); kustantajatVar.lisaa(rupu);
     *  
     *  Kustantaja etsitty = new Kustantaja();
     *  
     *  kustantajatVar.annaKustantaja(0);
     *  etsitty == pete === true;
     *  kustantajatVar.annaKustantaja(1);
     *  etsitty == vesa === true;
     *  kustantajatVar.annaKustantaja(2);
     *  etsitty == tupu === true;
     *  kustantajatVar.annaKustantaja(3);
     *  etsitty == hupu === true;
     *  kustantajatVar.annaKustantaja(4);
     *  etsitty == lupu === true;
     *  kustantajatVar.annaKustantaja(5);
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
     * Testipääohjelma kustantajille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kustantajat kustantajat = new Kustantajat();
        Kustantaja vesa = new Kustantaja();
        vesa.tayta_tmp(2);
        Kustantaja tupu = new Kustantaja();
        tupu.tayta_tmp(1);
        Kustantaja hupu = new Kustantaja();
        hupu.tayta_tmp(2);
        Kustantaja lupu = new Kustantaja();
        lupu.tayta_tmp(2);

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
