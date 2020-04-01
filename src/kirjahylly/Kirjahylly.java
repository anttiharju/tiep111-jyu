package kirjahylly;

import java.io.File;
import java.util.Collection;

/**
 * @author anvemaha
 * @version 20.2.2020 testit
 * @version 9.3.2020 kirjailijat ja kustantajat
 * @version 27.3.2020 mallin mukaiseksi (ht5 sooloilin)
 */
public class Kirjahylly {
    private Kirjat kirjat = new Kirjat();
    private Kirjailijat kirjailijat = new Kirjailijat();
    private Kustantajat kustantajat = new Kustantajat();

    /**
     * Lisää hyllyyn uuden kirjan
     * @param kirja lisättävä kirja
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     *  #import java.util.Collection;
     *  #import java.util.Iterator;
     *  #THROWS SailoException
     *  Kirjahylly hylly = new Kirjahylly();
     *  Kirja k1 = new Kirja(), k2 = new Kirja();
     *  try {
     *      hylly.lisaa(k1); 
     *      hylly.lisaa(k2); 
     *      hylly.lisaa(k1);
     *      Collection<Kirja> loytyneet = hylly.etsi("",-1); 
     *      Iterator<Kirja> it = loytyneet.iterator();
     *      it.next() === k1;
     *      it.next() === k2;
     *      it.next() === k1;
     *  } catch (SailoException e) {
     *      k1.toString(); // pelkkä // ei riitä koska ComTest ei vie sitä mukanaan
     *  }
     * </pre>
     */

    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }


    /**
     * Lisätään uusi kirjailija kirjahyllyyn
     * @param kirjailija lisättävä kirjailija
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kirjailija kirjailija) throws SailoException {
        kirjailijat.lisaa(kirjailija);
        kirjailijat.toString();
    }


    /**
     * Lisätään uusi kustantaja kirjahyllyyn
     * @param kustantaja lisättävä kustantaja
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kustantaja kustantaja) throws SailoException {
        kustantajat.lisaa(kustantaja);
    }


    /**
     * Korvaa annetulla id:llä löytyvän kirjan annetulla kirjalla
     * @param vid korvattavan kirjan id
     * @param kir uusi kirja
     */
    public void korvaa(int vid, Kirja kir) {
        kirjat.korvaa(vid, kir);
    }


    /**
     * Poistaa annetun kirjan
     * @param kirja poistettava kirja
     */
    public void poista(Kirja kirja) {
        kirjat.poista(kirja);
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kirjoista 
     * @throws SailoException Jos jotakin menee väärin
     */
    public Collection<Kirja> etsi(String hakuehto, int k)
            throws SailoException {
        return kirjat.etsi(hakuehto, k);
    }


    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty())
            hakemistonNimi = nimi;
        kirjat.setTiedostonPerusNimi(hakemistonNimi + "\\kirjat");
        kirjailijat.setTiedostonPerusNimi(hakemistonNimi + "\\kirjailijat");
        kustantajat.setTiedostonPerusNimi(hakemistonNimi + "\\kustantajat");
    }


    /**
     * Lukee hyllyn tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * TODO: testaa
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        kirjat = new Kirjat(nimi);
        kirjailijat = new Kirjailijat();
        kustantajat = new Kustantajat();

        setTiedosto(nimi);
        kirjat.lueTiedostosta();
        kirjailijat.lueTiedostosta();
        kustantajat.lueTiedostosta();
    }


    /**
     * Tallentaa hyllyn tiedot tiedostoon.  
     * Vaikka kirjojen tallettamien epäonistuisi, niin yritetään silti tallettaa
     * kirjailijat ja kustantajat ennen poikkeuksen heittämistä.    
     * @throws SailoException jos tallentamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kirjat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        try {
            kirjailijat.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }

        try {
            kustantajat.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe))
            throw new SailoException(virhe);

    }


    /**
     * Palauttaa annetun kirjan kirjailijan nimen
     * @param kirja kirja jonka kirjailijan nimi halutaan
     * @return kirjan kirjailijan nimi
     */
    public String kirjanKirjailija(Kirja kirja) {
        return kirjailijat.annaKirjailija(kirja.getKirjailijaId()).getNimi();
    }


    /**
     * Palauttaa annetun kirjan kustantajan nimen
     * @param kirja kirja jonka kustantajan nimi halutaan
     * @return kirjan kustantajan nimi
     */
    public String kirjanKustantaja(Kirja kirja) {
        return kustantajat.annaKustantaja(kirja.getKustantajaId()).getNimi();
    }


    /**
     * Palauttaa annetulla nimellä olevan kirjailijan
     * @param nimi kirjailijan nimi
     * @return kirjailija olion
     */
    public Kirjailija annaKirjailija(String nimi) {
        return kirjailijat.annaKirjailija(nimi);
    }


    /**
     * @return kirjailijat kloonin (alkioita ei kloonata)
     */
    public Kirjailijat annaKirjailijat() {
        return kirjailijat.clone();
    }


    /**
     * Palauttaa annetulla nimellä olevan kirjailijan
     * @param nimi kirjailijan nimi
     * @return kirjailija olion
     */
    public Kustantaja annaKustantaja(String nimi) {
        return kustantajat.annaKustantaja(nimi);
    }


    /**
     * @return kustantajat kloonin (alkioita ei kloonata)
     */
    public Kustantajat annaKustantajat() {
        return kustantajat.clone();
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
            m1.tayta_test();
            m2.rekisteroi();
            m2.tayta_test();

            hylly.lisaa(m1);
            hylly.lisaa(m2);
            int id1 = m1.getId();
            int id2 = m2.getId();
            Kirjailija k11 = new Kirjailija(id1);
            k11.tayta(id1);
            hylly.lisaa(k11);
            Kirjailija k21 = new Kirjailija(id1);
            k21.tayta(id1);
            hylly.lisaa(k21);
            Kirjailija k32 = new Kirjailija(id1);
            k32.tayta(id2);
            hylly.lisaa(k32);
            Kirjailija k42 = new Kirjailija(id1);
            k42.tayta(id2);
            hylly.lisaa(k42);
            Kirjailija k52 = new Kirjailija(id1);
            k52.tayta(id2);
            hylly.lisaa(k52);

            System.out.println("+ Kirjahylly testi");
            Collection<Kirja> kirjat = hylly.etsi("", -1);
            int i = 0;
            for (Kirja k : kirjat) {
                System.out.println("Kirja paikassa: " + i);
                k.tulosta(System.out);
                System.out.println(hylly.kirjanKirjailija(k));
                System.out.println(hylly.kirjanKustantaja(k));
                i++;
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

    }


    /**
     * Asettaa kirjailijat uusiksi (tallennus)
     * @param tmpKirjailijat uudet kirjailijat
     */
    public void set(Kirjailijat tmpKirjailijat) {
        kirjailijat = tmpKirjailijat;
    }


    /**
     * Asettaa kustantajat uusiksi (tallennus)
     * @param tmpKustantajat uudet kustantajat
     */
    public void set(Kustantajat tmpKustantajat) {
        kustantajat = tmpKustantajat;

    }


    /**
     * @return kirjojen lkm
     */
    public int getKirjatLkm() {
        return kirjat.getLkm();
    }
}
