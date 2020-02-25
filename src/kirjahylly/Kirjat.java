package kirjahylly;

/**
 * @author anvemaha
 * @version 20.2.2020
 *
 */
public class Kirjat {

    private static final int MAX_JASENIA = 10;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Kirja alkiot[] = new Kirja[MAX_JASENIA];

    /**
     * Oletusmuodostaja
     */
    public Kirjat() {
        //
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
     * kirjat.lisaa(k1); kirjat.getLkm === 1;
     * kirjat.lisaa(k2); kirjat.getLkm === 2;
     * kirjat.lisaa(k1); kirjat.getLkm === 3;
     * kirjat.anna(0) === k1;
     * kirjat.anna(1) === k2;
     * kirjat.anna(2) === k1;
     * kirjat.anna(1) == k1 === false;
     * kirjat.anna(1) == k2 === true;
     * kirjat.anna(3) == k1; #THROWS IndexOutOfBoundsException
     * kirjat.lisaa(k1); kirjat.getLkm() === 4;
     * kirjat.lisaa(k1); kirjat.getLkm() === 5;
     * kirjat.lisaa(k1); kirjat.getLkm() === 6;
     * kirjat.lisaa(k1); kirjat.getLkm() === 7;
     * kirjat.lisaa(k1); kirjat.getLkm() === 8;
     * kirjat.lisaa(k1); kirjat.getLkm() === 9;
     * kirjat.lisaa(k1); kirjat.getLkm() === 10;
     * kirjat.lisaa(k1); #THROWS SailoException;
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length)
            throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja; // asetetaan uudeksi alkioksi kirja? outo lause
        lkm++;
    }
}
