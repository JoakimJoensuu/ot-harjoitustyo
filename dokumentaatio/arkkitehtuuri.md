# Arkkitehtuurikuvaus

## Rakenne

Ohjelma on rakennettu kolmitasoiseksi JavaFX:llä toteutetun käyttöliittymän toteuttavan _investmentsimulator.ui_, sovelluslogiikan toteuttavan _investmentsimulator.domain_ ja datan tietokantaan tallennuksesta vastaavan _investmentsimulator.dao_ -pakkauksien avulla.

Sovelluksen pakkausrakenne kuvana:

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/pakkausrakenne.png" width="250">

## Käyttöliittymä

Käyttöliittymän ohjelmallisesta toteutuksesta vastaa luokka [investmentsimulator.ui.InvestmentSimulatorUi](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/ui/InvestmentSimulatorUi.java).

Käyttöliittymä koostuu seuraavista Scene-olioina toteutetuista näkymistä:

- päänäkymä

- simulaationäkymä

- hintojen syöttämisnäkymä

Näytettävä näkymä on vuorollaan asetettu sovelluksen Stage-olioon. 

Luokka [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) toimii liimana sovelluslogiikan ja käyttöliittymän totetuttavan luokan välillä. Se tarjoaa käyttöliittymäluokalle pääsyn sovelluslogiikkaan metodiensa kautta.

Käyttöliittymä käyttää [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) -luokan metodeja seuraavasti:


| metodi                | käyttötapaus                                                              |
|:----------------------|:-------------------------------                                           |
|_generateSimulation_   | kun käyttäjä painaa nappia joka aiheuttaa uuden simulaation luomisen, tällöin simulaatiolomakkeen tiedot annetaan metodin parametreina ja joiden perusteella metodi luo simulaation    |
|_getSavedSimulations_  | kun sovellus käynnistetään tai simulaatio tallennetaan aiheuttaa se tallennettujen simulaatioiden listan päivittämisen ja näin kaikkien simulaatioiden hakemisen tietokannasta, jotka metodi palauttaa käyttöliittymäluokalle |
|_setLoadedSimulationSelected_  | kun käyttäjä valitsee tallennetuiden simulaatioiden listasta yhden simulaation ladattavaksi annetaan se metodille parametrina, joka asettaa kyseisen simulaation luokassaan valituksi simulaatioksi          |
| _chartXAxisToPrice_      |  kun käyttäjä valitsee simulaationäkymästä viivakaavion X-akselilla näytettävät tiedot vaihdettavaksi tai kun viivakaavio piirretään enimmäisen kerran annetaan viivakaavio metodin käsiteltäväksi, jonka jälkeen metodi palauttaa viivakaavion uusilla X-akselin arvoilla         |
|_chartXAxisToROI_|-|
|_chartXAxisToValue_|-|
|_chartXAxisToProfit_|-|
|_chartXAxisToPurchases_|-|
| _saveSimulation_  |  kun käyttäjä painaa nappia joka aiheuttaa simulaation tallentamisen tietokantaan annetaan metodille parametrina tallennettavan simulaation nimi käyttöliittymän tekstikentästä, jolloin metodi tallentaa sillä hetkellä simulaationäkymässä näytettävän eli luokassaan valitun simulaation tietokantaan annetulla nimellä |
|_getSelectedSimulation_|kun simulaationäkymän viivakaavioon osoitetaan hiirellä saa käyttöliittymä metodilta valitun, eli näytettävissä olevan simulaation käyttöönsä, jotta se pystyy näyttämään simulaatiosta halutut arvot viivakaavion alla |
|_setSimulationPrices_| kun käyttäjä on valinnut syöttää simulaation kohteen hinnat manuaalisesti annetaan kyseiset hinta-arvot tekstikentistä lista-oliona metodille parametrina, jotka metodi puolestaan asettaa luodun simulaation kohteen hinta-arvoiksi|
|_getDates_| kun käyttäjä on valinnut syöttää simulaation kohteen hinnat manuaalisesti saadaan metodilta kyseisen simulaation päivämäärät, joille hinnat asetetaan, ja jotka piirretään hinnantäyttö-lomakkeeseen |

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostaa luokka [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java). Luokka pitää sisällään kaiken Simulaatioon liittyvän tiedon ja käyttää apunaan [Generator](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Generator.java) -luokkaa eri arvojen laskemiselle ja hintojen satunnaisgeneroimiselle.

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/datamalli.png" width="200">

Pakkauksen investmentsimulator.domain luokka [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) toimii kaiken keskiössä yhdistäen [investmentsimulator.dao.SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java), [investmentsimulator.domain.Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java) ja [investmentsimulator.ui.InvestmentSimulatorUi](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/ui/InvestmentSimulatorUi.java) -luokat toisiinsa. Käyttöliittymän näkökulmasta se hoitaa kaiken sovelluslogiikan ja tiedon tallennuksen ja [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java) ja [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java) -luokkien näkökulmasta se taas ohjaa kyseisiä luokkia käyttöliittymältä tulevien käskyjen mukaan.

Sovelluksen toimintaa kuvaava pakkauskaavio:

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/pakkauskaavio.png" width="500">

## Tietojen tallennus

Sovelluksen [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java) -luokka vastaa tiedon tallennuksesta tietokantaan. Se sisältää sovelluksen kannalta tarvittavat metodit tiedon tallentamiselle ja hakemiselle sekä tietokannan luomiselle. Luokan konstruktorissa määritellään tietokannan nimi, joka tallentuu juurikansioon. Tietokannan nimi on kovakoodattuna [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java)-luokan luovaan [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) -luokkaan nimellä "simulation.db". Testeissä saadaan näin luotua sovellukselle testausta varten oma tietokantansa.


### Tietokanta

Tietokanta sisältää kaksi taulua, jotka ovat seuraavanalaiset:

|Simulation||||||
|:---|:---|:---|:---|:---|:---|
|(pk) id:integer|name:Varchar(100)|sum:Integer|startingDate:Date|periodType:Varchar(10)|amountOfPeriods:Integer|

|Price|||
|:---|:---|:---|
|(fk) simulation_id:Integer|i:Integer|price:Integer|

Tietokanta pitää sisällään vain ne tiedot, jotka vaaditaan Simulaation arvojen uudelleenlaskemista varten tilanteessa jossa tallennettu simulaatio ladataan tietokannasta. Koska hinnat voivat olla käyttäjän määrittelemiä tai satunnaisesti generoituja niitä on mahdoton laskea uudelleen muiden tallennettavien tietojen perusteella. Hintoja voi myös olla useita jokaisessa simulaatiossa, jonka takia ne tallennetaan omaan tauluunsa.

Taulujen attribuutit ovat Price taulun i-attribuutti poislukien yksiselitteisiä. Price taulun attribuutti i kertoo hintojen kronologisen järjestyksen simulaatiossa.


## Päätoiminnallisuudet 

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationLuominenSatunnaisillaHinnoilla.png" width="750">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationTallentaminen.png" width="750">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioTallennetunSimulaationGenerointiJaNayttaminen.png" width="750">



