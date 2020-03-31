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
     * @param kir lisättävä kirjailija
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kirjailija kir) throws SailoException {
        kirjailijat.lisaa(kir);
    }


    /**
     * Lisätään uusi kustantaja kirjahyllyyn
     * @param kus lisättävä kustantaja
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kustantaja kus) throws SailoException {
        kustantajat.lisaa(kus);
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
     * Etsii tietyn nimisen kirjailijan
     * @param nimi etsittävän kirjailijan nimi
     * @param oletus arvo joka palautetaan, jos ei löydetä kirjailijaa
     * @return kirjailijan id:n
     */
    public int getKirjailijanId(String nimi, int oletus) {
        for (Kirjailija kirjailija : kirjailijat)
            if (kirjailija.getNimi().equals(nimi))
                return kirjailija.getId();
        return oletus;
    }


    /**
     * Etsii tietyn nimisen kustantajan
     * @param nimi etsittävän kirjailijan nimi
     * @param oletus arvo joka palautetaan, jos ei löydetä kustantajaa
     * @return kustantajan id:n
     */
    public int getKustantajanId(String nimi, int oletus) {
        for (Kustantaja kustantaja : kustantajat)
            if (kustantaja.getNimi().equals(nimi))
                return kustantaja.getId();
        return oletus;
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
        kirjat = new Kirjat();
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
     * ComboBoxChooseria varten tehty
     * @param eka kirjailija jonka halutaan olevan ensimmäisenä
     * @return kaikki kirjailijat, tietty kirjailija ensimmäisenä
     */
    public String annaKirjailijat(Kirja eka) {
        String kirjailija = kirjanKirjailija(eka);
        StringBuilder sb = new StringBuilder(kirjailija).append("\n");

        var iterator = kirjailijat.iterator();
        for (int i = 0; i < kirjailijat.getLkm(); i++) {
            String nyk = iterator.next().getNimi();
            if (!nyk.equals(kirjailija))
                sb.append(nyk).append("\n");
        }
        return sb.toString();
    }


    /**
     * ComboBoxChooseria varten tehty
     * @param eka kustantaja jonka halutaan olevan ensimmäisenä
     * @return kaikki kustantajat, tietty kustantaja ensimmäisenä
     */
    public String annaKustantajat(Kirja eka) {
        String kustantaja = kirjanKustantaja(eka);
        StringBuilder sb = new StringBuilder(kustantaja).append("\n");

        var iterator = kustantajat.iterator();
        for (int i = 0; i < kustantajat.getLkm(); i++) {
            String nyk = iterator.next().getNimi();
            if (!nyk.equals(kustantaja))
                sb.append(nyk).append("\n");
        }
        return sb.toString();
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
     * Testiohjelma hyllystä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjahylly hylly = new Kirjahylly();

        try {
            // hylly.lueTiedostosta("antti");

            Kirja m1 = new Kirja(), m2 = new Kirja();
            m1.rekisteroi();
            m1.tayta();
            m2.rekisteroi();
            m2.tayta();

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
}
