package kanta;

import java.util.regex.Pattern;

/**
 * Luokka satunnaisille apufunktioille
 * @author Antti Harju, anvemaha@student.jyu.fi
 * @version 20.2.2020 rand
 */
public class apu {

    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala;
        return (int) Math.round(n);
    }


    /**
     * Tarkistetaan ettei ole tyhjä
     * @param jono tarkistettava jono
     * @return onko vai eikö
     * @example
     * <pre name="test">
     *  tarkistaEtteiTyhja("") === false;
     *  tarkistaEtteiTyhja(".") === true;
     *  tarkistaEtteiTyhja("dsiafaöhioawhpä") === true;
     * </pre>
     */
    public static boolean tarkistaEtteiTyhja(String jono) {
        return !Pattern.matches("^$", jono);
    }


    /**
     * Tarkistetaan onko pvm muotoa pp.kk.vvvv tai p.k.vvvv tai tyhjä
     * @param pvm tarkistettava pvm
     * @return onko vai eikö
     * TODO: ota huomioon kuukausien eri pituudet (regex menee vaikeeksi)
     * </pre>
     * @example
     * <pre name="test">
     *  tarkistaPvm("31.12.9999") === true;
     *  tarkistaPvm("32.12.9999") === false;
     *  tarkistaPvm("31.13.9999") === false;
     *  tarkistaPvm("31.12.10000") === false;
     *  tarkistaPvm("1.1.0000") === true;
     *  tarkistaPvm("0.1.0000") === false;
     *  tarkistaPvm("1.0.0000") === false;
     *  tarkistaPvm("1.1.000") === false;
     *  tarkistaPvm("") === true;
     *  tarkistaPvm("2.2.2222") === true;
     *  tarkistaPvm("02.02.2222") === true;
     *  tarkistaPvm("kissa") === false;
     * </pre>
     */
    public static boolean tarkistaPvm(String pvm) {
        return Pattern.matches(
                "^(0[1-9]|[1-9]|[12][0-9]|3[01])[.](0[1-9]|[1-9]|1[0-2])[.]\\d{4}$|^$",
                pvm);
    }


    /**
     * Tarkistetaa onko vuosi muotoa vvvv
     * @param vuosi tarkistettava vuosi
     * @return onko vai eikö
     * @example
     * <pre name="test">
     *  tarkistaVuosi("") === false;
     *  tarkistaVuosi("4242") === true;
     *  tarkistaVuosi("0666") === false;
     *  tarkistaVuosi("69") === false;
     *  tarkistaVuosi("42069") === false;
     *  tarkistaVuosi("kakstuhattakaks") === false;
     * </pre>
     */
    public static boolean tarkistaVuosi(String vuosi) {
        return Pattern.matches("^[1-9]\\d{3}$", vuosi);
    }


    /**
     * Tarkistetaa onko arvio numeerinen
     * @param arvio tarkistettava arvio
     * @return onko vai eikö
     * @example
     * <pre name="test">
     *  tarkistaNumeerisuus("") === false;
     *  tarkistaNumeerisuus("1") === true;
     *  tarkistaNumeerisuus("Hyvä") === false;
     *  tarkistaNumeerisuus("Huono") === false;
     *  tarkistaNumeerisuus("Viisi") === false;
     *  tarkistaNumeerisuus("5/5") === false;
     *  tarkistaNumeerisuus("12") === true;
     * </pre>
     */
    public static boolean tarkistaNumeerisuus(String arvio) {
        return Pattern.matches("^\\d+$", arvio);
    }
}
