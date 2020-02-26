package kirjahylly;

/**
 * @author anvemaha
 * @version 20.2.2020
 *
 */
public class Kirjahylly {
    private final Kirjat kirjat = new Kirjat();

    /**
     * Palauttaa kirjahyllyn kirjamäärän
     * @return kirjamäärän
     */
    public int getKirjat() {
        return kirjat.getLkm();
    }


    /**
     * Poistaa hyllystä ne kirjat joilla nro
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako kirjaa poistettiin
     * TODO: tee valmiiksi
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Lisää hyllyyn uuden kirjan
     * @param kirja lisättävä kirja
     * @throws SailoException jos ei voida lisätä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kirjahylly hylly = new Kirjahylly();
     * Kirja m1 = new Kirja(), m2 = new Kirja();
     * m1.rekisteroi();
     * m2.rekisteroi();
     * hylly.getKirjat() === 0;
     * hylly.lisaa(m1); hylly.getKirjat() === 1;
     * hylly.lisaa(m2); hylly.getKirjat() === 2;
     * hylly.lisaa(m1); hylly.getKirjat() === 3;
     * hylly.getKirjat() === 3;
     * hylly.annaKirja(0) === m1;
     * hylly.annaKirja(1) === m2;
     * hylly.annaKirja(2) === m1;
     * hylly.annaKirja(3) === m1; #THROWS IndexOutOfBoundsException
     * hylly.lisaa(m1); hylly.getKirjat() === 4;
     * hylly.lisaa(m1); hylly.getKirjat() === 5;
     * hylly.lisaa(m1); hylly.getKirjat() === 6;
     * hylly.lisaa(m1); hylly.getKirjat() === 7;
     * hylly.lisaa(m1); hylly.getKirjat() === 8;
     * hylly.lisaa(m1); hylly.getKirjat() === 9;
     * hylly.lisaa(m1); hylly.getKirjat() === 10;
     * hylly.lisaa(m1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }


    /**
     * Palauttaa i:n kirjan
     * @param i monesko kirja palautetaan
     * @return viite i:teen kirjaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kirja annaKirja(int i) throws IndexOutOfBoundsException {
        return kirjat.anna(i);
    }


    /**
     * Lukee hyllyn tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        kirjat.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa hyllyn tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        kirjat.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }


    /**
     * Testiohjelma hyllystä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjahylly hylly = new Kirjahylly();

        try {
            // hylly.lueTiedostosta("antti");

            Kirja m1 = new Kirja(), m2 = new Kirja();
            m1.rekisteroi();
            m1.tayta_metro();
            m2.rekisteroi();
            m2.tayta_metro();

            hylly.lisaa(m1);
            hylly.lisaa(m2);

            System.out.println("+ Kirjahylly testi");
            for (int i = 0; i < hylly.getKirjat(); i++) {
                Kirja kirja = hylly.annaKirja(i);
                System.out.println("Kirja paikassa: " + i);
                kirja.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
