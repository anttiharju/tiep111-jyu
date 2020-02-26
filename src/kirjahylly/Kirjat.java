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
        // ei tarvi mitään
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
     * kirjat.lisaa(k1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length)
            throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja; // selitettiin luennolla
        lkm++;
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
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * TODO: tee valmiiksi
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "\\nimet.dat";
        throw new SailoException(
                "Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kirjan tiedostoon
     * @throws SailoException jos talletus epäonnistuu
     * TODO: tee valmiiksi
     */
    public void talleta() throws SailoException {
        throw new SailoException(
                "Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kirjahyllyn kirjojen lukumäärän
     * @return kirjojen lukumäärän
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * Testiohjelma kirjat luokalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjat kirjat = new Kirjat();
        Kirja m1 = new Kirja(), m2 = new Kirja();
        m1.rekisteroi();
        m1.tayta_metro();
        m2.rekisteroi();
        m2.tayta_metro();

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
