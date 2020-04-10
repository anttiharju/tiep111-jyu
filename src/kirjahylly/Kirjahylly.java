package kirjahylly;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fi.jyu.mit.ohj2.WildChars;

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
     * Koodi menee spaghetiksi koska en alussa osannut suunnitella näin isoa kokonaisuutta ja
     * oma ohjelmani eroaa malliht:sta liikaa (malliht:ssa ei voi hakea harrastusten perusteella)
     * Se toimii, mutten ole kovinkaan ylpeä tästä koska Kirjahylly ottaa liikaa vastuuta asioista
     * 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @param l lukuhakuehto, 0 kaikki, 1 luetut, 2 ei luetut
     * @return tietorakenteen löytyneistä kirjoista 
     */
    public Collection<Kirja> etsi(String hakuehto, int k, int l) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0)
            ehto = hakuehto + 1; // ks. lukuehto() +1 selitykseen
        List<Kirja> loytyneet = new ArrayList<Kirja>();
        for (Kirja kirja : kirjat) {
            if (WildChars.onkoSamat(lukuehto(k, l, kirja), ehto))
                loytyneet.add(kirja);
        }
        Collections.sort(loytyneet, new KirjaVertailija(k, this));
        return loytyneet;
    }

    /** 
     * Kirjan vertailija 
     */
    public static class KirjaVertailija implements Comparator<Kirja> {
        private int k;
        private Kirjahylly hylly;

        @SuppressWarnings("javadoc")
        public KirjaVertailija(int k, Kirjahylly hylly) {
            this.k = k;
            this.hylly = hylly;
        }


        @Override
        public int compare(Kirja kirja1, Kirja kirja2) {
            return hylly.anna(k, kirja1)
                    .compareToIgnoreCase(hylly.anna(k, kirja2));
        }

    }

    /**
     * Tämän avulla saadaan toinen ehto mukaan hakuun
     * @param k monennenko kentän sisältö palautetaan 
     * @param l lukuhakuehto, 0 kaikki, 1 luetut, 2 ei luetut
     * @param kir kirja jonka tiedot palautetaan
     * @return kentän sisältö merkkijonona johon lisätty lukuhakuehto
     */
    public String lukuehto(int k, int l, Kirja kir) {
        if (l == 1) {
            if (kir.getLuettu().equals(""))
                return anna(k, kir) + 0;
            return anna(k, kir) + 1;
        }
        if (l == 2) {
            if (kir.getLuettu().equals(""))
                return anna(k, kir) + 1;
            return anna(k, kir) + 0;
        }
        return anna(k, kir) + 1;
    }


    /** 
    * Antaa k:n kentän sisällön merkkijonona 
    * @param k monennenko kentän sisältö palautetaan 
    * @param kir kirja jonka tiedot palautetaan
    * @return kentän sisältö merkkijonona 
    */
    public String anna(int k, Kirja kir) {
        switch (k) {
        case 0:
            return "" + kir.getNimi();
        case 1:
            return "" + kirjanKirjailija(kir);
        case 2:
            return "" + kirjanKustantaja(kir);
        case 3:
            return "" + kir.getVuosi();
        case 4:
            return "" + kir.getArvio();
        case 5:
            return "" + kir.getLuettu();
        case 6:
            return "" + kir.getKuvaus();
        case 7:
            return "" + kir.getLisatietoja();
        case 8:
            return "" + kir.getId();
        default:
            return "tollo!";
        }
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
            Collection<Kirja> kirjat = hylly.etsi("", 0, -1);
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
