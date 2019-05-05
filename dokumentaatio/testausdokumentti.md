# Testausdokumentti

Ohjelman testaaminen on toteutettu automaatisilla yksikkö- ja integraatiotesteillä ja manuaalisella järjestelmätason testaamisella.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Pääosa testauksesta kohdistuu sovelluslogiikasta vastaaviin luokkiin. Kyseiset luokat sijaitsevat pakkauksessa  [investmentsimulator.domain](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/) ja niitä testaavat testiluokat [SimulationTest](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/test/java/InvestmentSimulator/SimulationTest.java), [InvestmentSimulatorServiceTest](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/test/java/InvestmentSimulator/InvestmentSimulatorServiceTest.java) ja [GeneratorTest](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/test/java/InvestmentSimulator/GeneratorTest.java).

[InvestmentSimulatorServiceTest](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/test/java/InvestmentSimulator/InvestmentSimulatorServiceTest.java) sisälsi integraatiotestejä, sillä kyseinen testiluokka testaa luokan [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) metodeja, jotka kutsuvat muiden luokkien metodeja.

### Tiedontallennus

Tiedon pysyväistallennuksesta vastaavaa luokkaa 
[SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java) testattiin kattavasti yksikkötesteillä ja hieman integraatiotesteillä.

Testauksessa luotiin testeille oma tietokantana antamalla [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java)-luokan konstruktorille jokin toinen tietokannan nimi.

### Testikattavuus

Testauksesta on jätetty ulkopuolelle käyttöliittymästä vastaava luokka, jolloin sovelluksen rivikattavuus on 73% ja haarautumakattavuus 79%.

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/jacococReport.png" width="900">

Testauksen ulkopuolelle jäivät moni [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) -luokan toiminnallisuuksista. Muunmuassa käyttöliittymälle tarjottaa palvelua simulaation pysyväistallennukseen - metodi _saveSimulation(String)_ ei testattu ollenkaan integraatiotestinä.

Valtaosa automaattisista testeistä olivat yksikkötestejä, joten integraatiotestausta olisi pitänyt suorittaa enemmän.

## Järjestelmätestaus

Sovellusta testattu kahdella eri tietokoneella, joissa Ubuntu 18.04 käyttöjärjestelmät.

### Asennus

Asennus suoritettiin [käyttöohjeen](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) mukaan ja sovellus käynnistyi kaikilla ohjeistetuilla tavoilla.

### Toiminnallisuudet 

Sovellusta testattiin kokeilemalla sovellusta, tutkimalla mitä toiminnallisuuksia siitä löytyy, testaamalla erilaisia syötteitä ja nappeja. So Tämän jälkeen tarkistettiin määrittelydokumentin toiminnallisuudet, joihin sovellus vastasi täysin.

### Sovelluksen puutteet ja ongelmat

Käyttäjän virheelliset syötteet kyllä validoidaan, mutta virheviesti ei osoita minkä kentän takia validointi epäonnistuu. Suuria numeroita periodin määrän suhteen ei estetä, mikä johtaa ohjelman totaaliseen pysähtymiseen, jos periodien määräksi syötetään esimerkiksi 2 miljardia. Myös periodeittain sijoitettavan summan kokoa olisi syytä rajoittaa johtuen kokonaislukujen rajoituksista.