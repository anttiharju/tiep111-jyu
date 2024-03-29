package kirjahylly.test;
// Generated by ComTest BEGIN
import kirjahylly.*;
// Generated by ComTest END
import java.io.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

import kanta.SailoException;

import java.util.Collection;
import java.util.Iterator;

/**
 * Test class made by ComTest
 * @version 2020.04.11 20:54:51 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KirjahyllyTest {


  // Generated by ComTest BEGIN  // Kirjahylly: 22
    private Kirjahylly hylly; 
    private Kirja k1; 
    private Kirja k2; 
    private int kid1; 
    private int kid2; 
    private Kirjailija kir1; 
    private Kirjailija kir2; 
    private Kustantaja kus1; 
    private Kustantaja kus2; 

 // @SuppressWarnings("javadoc")
    public void alustaHylly() {
        hylly = new Kirjahylly(); 

        k1 = new Kirja(); 
        k2 = new Kirja(); 
        k1.tayta(1, 2); 
        k2.tayta(2, 2); 

        kir1 = new Kirjailija(); 
        kir2 = new Kirjailija(); 
        kir1.tayta(1); 
        kir2.tayta(2); 

        kus1 = new Kustantaja(); 
        kus2 = new Kustantaja(); 
        kus1.tayta(1); 
        kus2.tayta(2); 

        try {
            hylly.lisaa(k1); 
            hylly.lisaa(k2); 
            hylly.lisaa(kir1); 
            hylly.lisaa(kir2); 
            hylly.lisaa(kus1); 
            hylly.lisaa(kus2); 
        } catch (Exception e) {
            System.err.println("Kirjahylly: alustaHylly() " + e.getMessage()); 
        }
    }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa77 */
  @Test
  public void testLisaa77() {    // Kirjahylly: 77
    alustaHylly(); 
    try {
    hylly.lisaa(k1); 
    hylly.lisaa(k2); 
    hylly.lisaa(k1); 
    Iterator<Kirja> kirjat = hylly.annaKirjat().iterator(); 
    assertEquals("From: Kirjahylly line: 87", k1, kirjat.next()); 
    assertEquals("From: Kirjahylly line: 88", k2, kirjat.next()); 
    assertEquals("From: Kirjahylly line: 89", k1, kirjat.next()); 
    } catch (SailoException e) {
    hylly.toString(); 
    }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa105 */
  @Test
  public void testLisaa105() {    // Kirjahylly: 105
    alustaHylly(); 
    try {
    hylly.lisaa(kir1); 
    hylly.lisaa(kir2); 
    hylly.lisaa(kir1); 
    Iterator<Kirjailija> kirjailijat = hylly.annaKirjailijat().iterator(); 
    assertEquals("From: Kirjahylly line: 115", kir1, kirjailijat.next()); 
    assertEquals("From: Kirjahylly line: 116", kir2, kirjailijat.next()); 
    assertEquals("From: Kirjahylly line: 117", kir1, kirjailijat.next()); 
    } catch (SailoException e) {
    hylly.toString(); 
    }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa133 */
  @Test
  public void testLisaa133() {    // Kirjahylly: 133
    alustaHylly(); 
    try {
    hylly.lisaa(kus1); 
    hylly.lisaa(kus2); 
    hylly.lisaa(kus1); 
    Iterator<Kustantaja> kustantajat = hylly.annaKustantajat().iterator(); 
    assertEquals("From: Kirjahylly line: 143", kus1, kustantajat.next()); 
    assertEquals("From: Kirjahylly line: 144", kus2, kustantajat.next()); 
    assertEquals("From: Kirjahylly line: 145", kus1, kustantajat.next()); 
    } catch (SailoException e) {
    hylly.toString(); 
    }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaa161 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaa161() throws SailoException {    // Kirjahylly: 161
    alustaHylly(); 
    assertEquals("From: Kirjahylly line: 164", 2, hylly.annaKirjat().getLkm()); 
    hylly.korvaa(1, k2); 
    assertEquals("From: Kirjahylly line: 166", 2, hylly.annaKirjat().getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista178 
   * @throws Exception when error
   */
  @Test
  public void testPoista178() throws Exception {    // Kirjahylly: 178
    alustaHylly(); 
    assertEquals("From: Kirjahylly line: 181", 2, hylly.annaKirjat().getLkm()); 
    hylly.poista(k1); 
    assertEquals("From: Kirjahylly line: 183", 1, hylly.annaKirjat().getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi198 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsi198() throws CloneNotSupportedException, SailoException {    // Kirjahylly: 198
    alustaHylly(); 
    Kirja k3 = new Kirja(); k3.tayta(3, "Mullan salaisuudet"); hylly.lisaa(k3); 
    Collection<Kirja> loytyneet = hylly.etsi("*sala*", 0, 0); 
    assertEquals("From: Kirjahylly line: 203", 1, loytyneet.size()); 
    Iterator<Kirja> it = loytyneet.iterator(); 
    assertEquals("From: Kirjahylly line: 205", true, it.next() == k3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKirjailijanKirjat228 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testKirjailijanKirjat228() throws CloneNotSupportedException, SailoException {    // Kirjahylly: 228
    alustaHylly(); 
    Collection<Kirja> loytyneet = hylly.kirjailijanKirjat(2, 1); 
    assertEquals("From: Kirjahylly line: 232", 1, loytyneet.size()); 
    Iterator<Kirja> it = loytyneet.iterator(); 
    assertEquals("From: Kirjahylly line: 234", true, it.next() == k2); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLukuehto254 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testLukuehto254() throws CloneNotSupportedException, SailoException {    // Kirjahylly: 254
    alustaHylly(); 
    Kirja k4 = new Kirja(); k4.tayta(4, "Pähkinä"); hylly.lisaa(k4); 
    assertEquals("From: Kirjahylly line: 258", "Pähkinä0", hylly.lukuehto(0, 1, k4)); 
    assertEquals("From: Kirjahylly line: 259", "Pähkinä1", hylly.lukuehto(0, 2, k4)); 
    assertEquals("From: Kirjahylly line: 260", "Pähkinä1", hylly.lukuehto(0, 0, k4)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta333 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta333() throws SailoException {    // Kirjahylly: 333
    alustaHylly(); 
    String hakemisto = "testihylly"; 
    File dir = new File(hakemisto); 
    File kdat  = new File(hakemisto+"/kirjat.dat"); 
    File kirdat = new File(hakemisto+"/kirjailijat.dat"); 
    File kusdat = new File(hakemisto+"/kustantajat.dat"); 
    File kbackup  = new File(hakemisto+"/kirjat.backup"); 
    File kirbackup = new File(hakemisto+"/kirjailijat.backup"); 
    File kusbackup = new File(hakemisto+"/kustantajat.backup"); 
    kdat.delete(); 
    kirdat.delete(); 
    kusdat.delete(); 
    try {
    hylly.lueTiedostosta(hakemisto); 
    fail("Kirjahylly: 350 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    hylly.lisaa(k1); 
    hylly.lisaa(k2); 
    hylly.lisaa(kir1); 
    hylly.lisaa(kir2); 
    hylly.lisaa(kus1); 
    hylly.lisaa(kus2); 
    hylly.tallenna(); 
    hylly = new Kirjahylly(); 
    hylly.lueTiedostosta(hakemisto); 
    Iterator<Kirja> kirjat = hylly.annaKirjat().iterator(); 
    assertEquals("From: Kirjahylly line: 362", k1, kirjat.next()); 
    assertEquals("From: Kirjahylly line: 363", k2, kirjat.next()); 
    assertEquals("From: Kirjahylly line: 364", false, kirjat.hasNext()); 
    Iterator<Kirjailija> kirjailijat = hylly.annaKirjailijat().iterator(); 
    assertEquals("From: Kirjahylly line: 367", kir1, kirjailijat.next()); 
    assertEquals("From: Kirjahylly line: 368", kir2, kirjailijat.next()); 
    assertEquals("From: Kirjahylly line: 369", false, kirjailijat.hasNext()); 
    Iterator<Kustantaja> kustantajat = hylly.annaKustantajat().iterator(); 
    assertEquals("From: Kirjahylly line: 372", kus1, kustantajat.next()); 
    assertEquals("From: Kirjahylly line: 373", kus2, kustantajat.next()); 
    assertEquals("From: Kirjahylly line: 374", false, kustantajat.hasNext()); 
    hylly.lisaa(k1); 
    hylly.lisaa(kir1); 
    hylly.lisaa(kus1); 
    hylly.tallenna(); 
    assertEquals("From: Kirjahylly line: 381", true, kdat.delete()); 
    assertEquals("From: Kirjahylly line: 382", true, kirdat.delete()); 
    assertEquals("From: Kirjahylly line: 383", true, kusdat.delete()); 
    assertEquals("From: Kirjahylly line: 384", true, kbackup.delete()); 
    assertEquals("From: Kirjahylly line: 385", true, kirbackup.delete()); 
    assertEquals("From: Kirjahylly line: 386", true, kusbackup.delete()); 
    assertEquals("From: Kirjahylly line: 387", true, dir.delete()); 
  } // Generated by ComTest END
}