package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luodunKassapaatteenRahamaaraJaMyytyjenLounaidenMaarOnOikea() {
        assertTrue(kassapaate.kassassaRahaa() == 100000);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 0);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 0);
    }

    @Test
    public void kunKateismaksuOnRiittavaKassanRahamaaraMyydytLounaatJaVaihtorahaOvatOikein() {
        int vaihtoraha = kassapaate.syoEdullisesti(1000);
        vaihtoraha += kassapaate.syoMaukkaasti(1000);

        assertTrue(vaihtoraha == 1360);
        assertTrue(kassapaate.kassassaRahaa() == 100640);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 1);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 1);
    }

    @Test
    public void kunKateismaksuEiOleRiittavaKassanRahamaaraMyydytLounaatJaVaihtorahaOvatOikein() {
        int vaihtoraha = kassapaate.syoEdullisesti(10);
        vaihtoraha += kassapaate.syoMaukkaasti(10);

        assertTrue(vaihtoraha == 20);
        assertTrue(kassapaate.kassassaRahaa() == 100000);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 0);
    }

    @Test
    public void josKortillaOnTarpeeksiRahaaVeloitetaanSummaKortiltaPalautetaanTrueJaMyytyjenLounaidenMaaraKasvaa() {

        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertTrue(kassapaate.syoMaukkaasti(kortti));
        assertTrue(kortti.saldo() == 360);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 1);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 1);

    }

    @Test
    public void josKortillaEiOleTarpeeksiRahaaEiVeloitetaKortilaMyytyjenLounaidenMaaraEiMuutuJaPalautetaanFalse() {
        kassapaate.syoMaukkaasti(kortti);
        kassapaate.syoMaukkaasti(kortti);
        assertTrue(!kassapaate.syoEdullisesti(kortti));
        assertTrue(!kassapaate.syoMaukkaasti(kortti));
        assertTrue(kortti.saldo() == 200);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 0);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 2);
        assertTrue(kassapaate.kassassaRahaa() == 100000);

    }

    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessa() {

        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertTrue(kassapaate.syoMaukkaasti(kortti));
        assertTrue(kortti.saldo() == 360);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 1);
        assertTrue(kassapaate.maukkaitaLounaitaMyyty() == 1);
        assertTrue(kassapaate.kassassaRahaa() == 100000);

    }

    @Test
    public void kortilleLadattaessaKortinSaldoMuuttuuJaKassassaOlevaRahamaaraKasvaaLadatullaSummalla() {
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertTrue(kassapaate.kassassaRahaa() == 100100);
        assertTrue(kortti.saldo() == 1100);

    }
    
    @Test
    public void kortilleLadattaessaNegatiivinenMaaraRahaaMikaanEiMuutu() {
        kassapaate.lataaRahaaKortille(kortti, -100);
        assertTrue(kassapaate.kassassaRahaa() == 100000);
        assertTrue(kortti.saldo() == 1000);

    }

}
