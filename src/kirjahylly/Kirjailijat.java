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
    public void tallenna() throws SailoException {
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
     *  Kirjailijat kirjailijatVar = new Kirjailijat();
     *  Kirjailija pete = new Kirjailija(2); kirjailijatVar.lisaa(pete);
     *  Kirjailija vesa = new Kirjailija(1); kirjailijatVar.lisaa(vesa);
     *  Kirjailija tupu = new Kirjailija(2); kirjailijatVar.lisaa(tupu);
     *  Kirjailija hupu = new Kirjailija(1); kirjailijatVar.lisaa(hupu);
     *  Kirjailija lupu = new Kirjailija(2); kirjailijatVar.lisaa(lupu);
     *  
     *  Iterator<Kirjailija> i2 = kirjailijatVar.iterator();
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
     *  for ( Kirjailija kir : kirjailijatVar ) {
     *      kir.getId() === jnrot[n]; n++;
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
     *  Kirjailija pete = new Kirjailija(0); kirjailijatVar.lisaa(pete);
     *  Kirjailija vesa = new Kirjailija(1); kirjailijatVar.lisaa(vesa);
     *  Kirjailija tupu = new Kirjailija(2); kirjailijatVar.lisaa(tupu);
     *  Kirjailija hupu = new Kirjailija(3); kirjailijatVar.lisaa(hupu);
     *  Kirjailija lupu = new Kirjailija(4); kirjailijatVar.lisaa(lupu);
     *  Kirjailija rupu = new Kirjailija(5); kirjailijatVar.lisaa(rupu);
     *  
     *  Kirjailija etsitty = new Kirjailija();
     *  
     *  etsitty = kirjailijatVar.annaKirjailija(0);
     *  etsitty == pete === true;
     *  etsitty = kirjailijatVar.annaKirjailija(1);
     *  etsitty == vesa === true;
     *  etsitty = kirjailijatVar.annaKirjailija(2);
     *  etsitty == tupu === true;
     *  etsitty = kirjailijatVar.annaKirjailija(3);
     *  etsitty == hupu === true;
     *  etsitty = kirjailijatVar.annaKirjailija(4);
     *  etsitty == lupu === true;
     *  etsitty = kirjailijatVar.annaKirjailija(5);
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
        vesa.tayta_tmp(2);
        Kirjailija tupu = new Kirjailija();
        tupu.tayta_tmp(1);
        Kirjailija hupu = new Kirjailija();
        hupu.tayta_tmp(2);
        Kirjailija lupu = new Kirjailija();
        lupu.tayta_tmp(2);

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
