package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertTrue(kortti.saldo() == 10);
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(20);
        assertTrue(kortti.saldo() == 30);
        kortti.lataaRahaa(20);
        assertTrue(kortti.saldo() == 50);
        kortti.lataaRahaa(20);
        assertTrue(kortti.saldo() == 70);
    }

    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksiJaPalauttaaTrue() {
        assertTrue(kortti.otaRahaa(5));
        assertTrue(kortti.saldo() == 5);
    }

    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksiJaPalauttaaFalse() {
        kortti.otaRahaa(9);
        assertTrue(!kortti.otaRahaa(5));
        assertTrue(kortti.saldo() == 1);
    }

    @Test
    public void kortinSaldoTulostuuOikein() {
        kortti.lataaRahaa(990);
        assertEquals("saldo: 10.0", kortti.toString());
    }

}
