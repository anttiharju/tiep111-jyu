package kanta;

/**
 * @author anvemaha
 * @version 20.2.2020 pohjaa
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

}
