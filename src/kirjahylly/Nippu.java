package kirjahylly;

/**
 * Apuluokka asioiden kuljettamiseen käyttöliittymän dialogien välillä
 * @author anvemaha
 * @version 30.3.2020
 */
public class Nippu {

    Kirjahylly hylly;
    Kirja kirja;

    /**
     * Muodostaja
     * @param hylly kirjahylly
     * @param kirja kirjaKohdalla
     */
    public Nippu(Kirjahylly hylly, Kirja kirja) {
        set(hylly, kirja);
    }


    /**
     * Ettei aina tarvitse luoda uutta oliota
     * @param hylly kirjahylly
     * @param kirja kirjaKohdalla
     */
    public void set(Kirjahylly hylly, Kirja kirja) {
        set(hylly);
        set(kirja);
    }


    /**
     * Ettei aina tarvitse luoda uutta oliota
     * @param hylly kirjahylly
     */
    public void set(Kirjahylly hylly) {
        this.hylly = hylly;
    }


    /**
    * Ettei aina tarvitse luoda uutta oliota
    * @param kirja kirjaKohdalla
    */
    public void set(Kirja kirja) {
        this.kirja = kirja;
    }


    /**
     * @return kirjahyllyn
     */
    public Kirjahylly getHylly() {
        return hylly;
    }


    /**
     * @return kirjan
     */
    public Kirja getKirja() {
        return kirja;
    }
}
