package kirjahylly;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fi.jyu.mit.ohj2.WildChars;
import kanta.SailoException;

/**
 * Kirjahylly-luokka, joka huolehtii kaikesta muusta.
 * Joutui omimaan metodeja kirjat-luokalta vähän, koska jokaisen kirjan
 * ei tarvitse tietää hyllyään. (esim. KirjaVertailija ihan lopussa)
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 20.2.2020 testit
 * @version 9.3.2020 kirjailijat ja kustantajat
 * @version 27.3.2020 mallin mukaiseksi (ht5 sooloilin)
 * @version 11.4.2020 onnistunutta sooloilua (spaghettia?), viimeistelyä
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 *  #import kanta.SailoException;
 *   private Kirjahylly hylly;
 *   private Kirja k1;
 *   private Kirja k2;
 *   private int kid1;
 *   private int kid2;
 *   private Kirjailija kir1;
 *   private Kirjailija kir2;
 *   private Kustantaja kus1;
 *   private Kustantaja kus2;
 *  
 *   // @SuppressWarnings("javadoc")
 *   public void alustaHylly() {
 *       hylly = new Kirjahylly();
 *   
 *       k1 = new Kirja();
 *       k2 = new Kirja();
 *       k1.tayta(1, 2);
 *       k2.tayta(2, 2);
 *   
 *       kir1 = new Kirjailija();
 *       kir2 = new Kirjailija();
 *       kir1.tayta(1);
 *       kir2.tayta(2);
 *   
 *       kus1 = new Kustantaja();
 *       kus2 = new Kustantaja();
 *       kus1.tayta(1);
 *       kus2.tayta(2);
 *   
 *       try {
 *           hylly.lisaa(k1);
 *           hylly.lisaa(k2);
 *           hylly.lisaa(kir1);
 *           hylly.lisaa(kir2);
 *           hylly.lisaa(kus1);
 *           hylly.lisaa(kus2);
 *       } catch (Exception e) {
 *           System.err.println("Kirjahylly: alustaHylly() " + e.getMessage());
 *       }
 *   }
 * </pre>
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
     *   alustaHylly();
     *   try {
     *       hylly.lisaa(k1); 
     *       hylly.lisaa(k2); 
     *       hylly.lisaa(k1);
     *       Iterator<Kirja> kirjat = hylly.annaKirjat().iterator();
     *       kirjat.next() === k1;
     *       kirjat.next() === k2;
     *       kirjat.next() === k1;
     *   } catch (SailoException e) {
     *       hylly.toString();
     *   }
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }


    /**
     * Lisätään uusi kirjailija kirjahyllyyn
     * @param kirjailija lisättävä kirjailija
     * @throws SailoException jos tulee ongelmia
     * @example
     * <pre name="test">
     *  #import java.util.Collection;
     *  #import java.util.Iterator;
     *  #THROWS SailoException
     *   alustaHylly();
     *   try {
     *       hylly.lisaa(kir1); 
     *       hylly.lisaa(kir2); 
     *       hylly.lisaa(kir1);
     *       Iterator<Kirjailija> kirjailijat = hylly.annaKirjailijat().iterator();
     *       kirjailijat.next() === kir1;
     *       kirjailijat.next() === kir2;
     *       kirjailijat.next() === kir1;
     *   } catch (SailoException e) {
     *       hylly.toString();
     *   }
     * </pre>
     */
    public void lisaa(Kirjailija kirjailija) throws SailoException {
        kirjailijat.lisaa(kirjailija);
    }


    /**
     * Lisätään uusi kustantaja kirjahyllyyn
     * @param kustantaja lisättävä kustantaja
     * @throws SailoException jos tulee ongelmia
     * @example
     * <pre name="test">
     *  #import java.util.Collection;
     *  #import java.util.Iterator;
     *  #THROWS SailoException
     *   alustaHylly();
     *   try {
     *       hylly.lisaa(kus1); 
     *       hylly.lisaa(kus2); 
     *       hylly.lisaa(kus1);
     *       Iterator<Kustantaja> kustantajat = hylly.annaKustantajat().iterator();
     *       kustantajat.next() === kus1;
     *       kustantajat.next() === kus2;
     *       kustantajat.next() === kus1;
     *   } catch (SailoException e) {
     *       hylly.toString();
     *   }
     * </pre>
     */
    public void lisaa(Kustantaja kustantaja) throws SailoException {
        kustantajat.lisaa(kustantaja);
    }


    /**
     * Korvaa annetulla id:llä löytyvän kirjan annetulla kirjalla
     * @param kid korvattavan kirjan id
     * @param kir uusi kirja
     * @example
     * <pre name="test">
     *  #THROWS SailoException  
     *   alustaHylly();
     *   hylly.annaKirjat().getLkm() === 2;
     *   hylly.korvaa(1, k2);
     *   hylly.annaKirjat().getLkm() === 2;
     * </pre>
     */
    public void korvaa(int kid, Kirja kir) {
        kirjat.korvaa(kid, kir);
    }


    /**
     * Poistaa annetun kirjan
     * @param kirja poistettava kirja
     * @example
     * <pre name="test">
     *  #THROWS Exception
     *   alustaHylly();
     *   hylly.annaKirjat().getLkm() === 2;
     *   hylly.poista(k1);
     *   hylly.annaKirjat().getLkm() === 1;
     * </pre>
     */
    public void poista(Kirja kirja) {
        kirjat.poista(kirja);
    }


    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @param l lukuhakuehto, 0 kaikki, 1 luetut, 2 ei luetut
     * @return tietorakenteen löytyneistä kirjoista 
     * @example 
     * <pre name="test">
     *  #THROWS CloneNotSupportedException, SailoException
     *   alustaHylly();
     *   Kirja k3 = new Kirja(); k3.tayta(3, "Mullan salaisuudet"); hylly.lisaa(k3);
     *   Collection<Kirja> loytyneet = hylly.etsi("*sala*", 0, 0);
     *   loytyneet.size() === 1;
     *   Iterator<Kirja> it = loytyneet.iterator();
     *   it.next() == k3 === true;
     * </pre>
     */
    public Collection<Kirja> etsi(String hakuehto, int k, int l) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0)
            ehto = hakuehto + 1; // +1 lukuehto() takia
        List<Kirja> loytyneet = new ArrayList<Kirja>();
        for (Kirja kirja : kirjat) {
            if (WildChars.onkoSamat(lukuehto(k, l, kirja), ehto))
                loytyneet.add(kirja);
        }
        Collections.sort(loytyneet, new KirjaVertailija(k, this));
        return loytyneet;
    }


    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param kirjailijaId sen kirjailijan id, jonka kirjat halutaan
     * @param kirjaId valitun kirjan id, ei haluta näyttää samaa kirjaa uudestaan
     * @return tietorakenteen löytyneistä kirjoista 
     * @example 
     * <pre name="test">
     *  #THROWS CloneNotSupportedException, SailoException
     *   alustaHylly();
     *   Collection<Kirja> loytyneet = hylly.kirjailijanKirjat(2, 1);
     *   loytyneet.size() === 1;
     *   Iterator<Kirja> it = loytyneet.iterator();
     *   it.next() == k2 === true;
     * </pre>
     */
    public Collection<Kirja> kirjailijanKirjat(int kirjailijaId, int kirjaId) {
        List<Kirja> loytyneet = new ArrayList<Kirja>();
        for (Kirja kirja : kirjat)
            if (kirja.getKirjailijaId() == kirjailijaId
                    && kirja.getId() != kirjaId)
                loytyneet.add(kirja);
        return loytyneet;
    }


    /**
     * Tämän avulla saadaan toinen ehto mukaan hakuun
     * @param k monennenko kentän sisältö palautetaan 
     * @param l lukuhakuehto, 0 kaikki, 1 luetut, 2 ei luetut
     * @param kir kirja jonka tiedot palautetaan
     * @return kentän sisältö merkkijonona johon lisätty lukuhakuehto
     * @example 
     * <pre name="test">
     *  #THROWS CloneNotSupportedException, SailoException
     *   alustaHylly();
     *   Kirja k4 = new Kirja(); k4.tayta(4, "Pähkinä"); hylly.lisaa(k4);
     *   hylly.lukuehto(0, 1, k4) === "Pähkinä0";
     *   hylly.lukuehto(0, 2, k4) === "Pähkinä1";
     *   hylly.lukuehto(0, 0, k4) === "Pähkinä1";
     * </pre>
     */
    public String lukuehto(int k, int l, Kirja kir) {
        // Loppuun lisättävä 1 tai 0 tarkoittaa sitä täsmääkö lukuehtoon (l)
        // 1 = kyllä, 0 = ei
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
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     *  alustaHylly();
     *  String hakemisto = "testihylly";
     *  File dir = new File(hakemisto);
     *  File kdat  = new File(hakemisto+"/kirjat.dat");
     *  File kirdat = new File(hakemisto+"/kirjailijat.dat");
     *  File kusdat = new File(hakemisto+"/kustantajat.dat");
     *  File kbackup  = new File(hakemisto+"/kirjat.backup");
     *  File kirbackup = new File(hakemisto+"/kirjailijat.backup");
     *  File kusbackup = new File(hakemisto+"/kustantajat.backup");
     *  
     *  kdat.delete();
     *  kirdat.delete();
     *  kusdat.delete();
     *  hylly.lueTiedostosta(hakemisto); #THROWS SailoException
     *  hylly.lisaa(k1);
     *  hylly.lisaa(k2);
     *  hylly.lisaa(kir1);
     *  hylly.lisaa(kir2);
     *  hylly.lisaa(kus1);
     *  hylly.lisaa(kus2);
     *  hylly.tallenna();
     *  hylly = new Kirjahylly();
     *  hylly.lueTiedostosta(hakemisto);
     *  
     *  Iterator<Kirja> kirjat = hylly.annaKirjat().iterator();
     *  kirjat.next() === k1;
     *  kirjat.next() === k2;
     *  kirjat.hasNext() === false;
     *  
     *  Iterator<Kirjailija> kirjailijat = hylly.annaKirjailijat().iterator();
     *  kirjailijat.next() === kir1;
     *  kirjailijat.next() === kir2;
     *  kirjailijat.hasNext() === false;
     *  
     *  Iterator<Kustantaja> kustantajat = hylly.annaKustantajat().iterator();
     *  kustantajat.next() === kus1;
     *  kustantajat.next() === kus2;
     *  kustantajat.hasNext() === false;
     *  
     *  hylly.lisaa(k1);
     *  hylly.lisaa(kir1);
     *  hylly.lisaa(kus1);
     *  hylly.tallenna();
     *  
     *  kdat.delete() === true;
     *  kirdat.delete() === true;
     *  kusdat.delete() === true;
     *  kbackup.delete() === true;
     *  kirbackup.delete() === true;
     *  kusbackup.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
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
     * @return kirjailijat kloonin (alkiot on alkuperäisiä)
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
     * @return kustantajat kloonin (alkiot on alkuperäisiä)
     */
    public Kustantajat annaKustantajat() {
        return kustantajat.clone();
    }


    /**
     * @return kirjat kloonin (alkiot on alkuperäisiä)
     */
    public Kirjat annaKirjat() {
        return kirjat.clone();
    }


    /**
     * Testiohjelma hyllystä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjahylly hylly = new Kirjahylly();

        try {
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


    /**
     * @return kertoo onko tallentamattomia muutoksia
     */
    public boolean onkoMuutoksia() {
        if (kirjat.getMuutettu())
            return true;
        if (kirjailijat.getMuutettu())
            return true;
        if (kustantajat.getMuutettu())
            return true;
        return false;
    }

    /** 
     * Vertailee kirjoja
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
}
