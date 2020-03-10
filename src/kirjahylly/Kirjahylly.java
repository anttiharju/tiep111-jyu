package kirjahylly;

/**
 * @author anvemaha
 * @version 20.2.2020 testit
 * @version 9.3.2020 kirjailijat ja kustantajat
 *
 */
public class Kirjahylly {
    private final Kirjat kirjat = new Kirjat();
    private final Kirjailijat kirjailijat = new Kirjailijat();
    private final Kustantajat kustantajat = new Kustantajat();

    /**
     * Palauttaa kirjahyllyn kirjamäärän
     * @return kirjamäärän
     */
    public int getKirjat() {
        return kirjat.getLkm();
    }


    /**
     * Lisätään uusi kirjailija kirjahyllyyn
     * @param kir lisättävä kirjailija
     */
    public void lisaa(Kirjailija kir) {
        kirjailijat.lisaa(kir);
    }


    /**
     * Lisätään uusi kustantaja kirjahyllyyn
     * @param kus lisättävä kustantaja
     */
    public void lisaa(Kustantaja kus) {
        kustantajat.lisaa(kus);
    }


    /**
     * @param kirja kirja jonka kirjailija halutaan tietää
     * @return kirjan kirjailijan
     */
    public Kirjailija annaKirjailija(Kirja kirja) {
        return kirjailijat.annaKirjailija(kirja.getKirjailijaId());
    }


    /**
     * @param kirja kirja jonka kustantaja halutaan tietää
     * @return kirjan kustantajan
     */
    public Kustantaja annaKustantaja(Kirja kirja) {
        return kustantajat.annaKustantaja(kirja.getKustantajaId());
    }


    /**
     * ComboBoxChooseria varten palauttaa kirjailijat muuttujan
     * @return kaikki kirjailijat
     */
    public Kirjailijat getKirjailijat() {
        return kirjailijat;
    }


    /**
     * ComboBoxChooseria varten palauttaa kirjailijat muuttujan
     * @return kaikki kustantajat
     */
    public Kustantajat getKustantajat() {
        return kustantajat;
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
        kirjailijat.lueTiedostosta(nimi);
        kustantajat.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa hyllyn tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        kirjat.talleta();
        kustantajat.talleta();
        kirjailijat.talleta();
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
            int id1 = m1.getId();
            int id2 = m2.getId();
            Kirjailija k11 = new Kirjailija(id1);
            k11.tayta_tmp(id1);
            hylly.lisaa(k11);
            Kirjailija k21 = new Kirjailija(id1);
            k21.tayta_tmp(id1);
            hylly.lisaa(k21);
            Kirjailija k32 = new Kirjailija(id1);
            k32.tayta_tmp(id2);
            hylly.lisaa(k32);
            Kirjailija k42 = new Kirjailija(id1);
            k42.tayta_tmp(id2);
            hylly.lisaa(k42);
            Kirjailija k52 = new Kirjailija(id1);
            k52.tayta_tmp(id2);
            hylly.lisaa(k52);

            System.out.println("+ Kirjahylly testi");
            for (int i = 0; i < hylly.getKirjat(); i++) {
                Kirja kirja = hylly.annaKirja(i);
                System.out.println("Kirja paikassa: " + i);
                kirja.tulosta(System.out);
                Kirjailija loytynytKir = hylly.annaKirjailija(kirja);
                Kustantaja loytynytKus = hylly.annaKustantaja(kirja);
                loytynytKir.tulosta(System.out);
                loytynytKus.tulosta(System.out);

            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
