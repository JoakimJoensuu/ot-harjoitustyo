# Arkkitehtuurikuvaus

## Rakenne

Ohjelma on rakennettu kolmitasoiseksi JavaFX:llä toteutetun käyttöliittymän toteuttavan _investmentsimulator.ui_, sovelluslogiikan toteuttavan _investmentsimulator.domain_ ja datan tietokantaan tallennuksesta vastaavan _investmentsimulator.dao_ -pakkauksien avulla.

Sovelluksen pakkausrakenne kuvana:

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/pakkausrakenne.png" width="250">

## Käyttöliittymä

Käyttöliittymän ohjelmallisesta toteutuksesta vastaa luokka [InvestmentSimulatorUi](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/ui/InvestmentSimulatorUi.java).

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

Pakkauksen investmentsimulator.domain luokka [investmentsimulator.domain.InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) toimii kaiken keskiössä yhdistäen [investmentsimulator.dao.SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java), [investmentsimulator.domain.Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java) ja [investmentsimulator.ui.InvestmentSimulatorUi](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/ui/InvestmentSimulatorUi.java) -luokat toisiinsa. Käyttöliittymän näkökulmasta se hoitaa kaiken sovelluslogiikan ja tiedon tallennuksen ja [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java) ja [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java) -luokkien näkökulmasta se taas ohjaa kyseisiä luokkia käyttöliittymältä tulevien käskyjen mukaan.

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

Sovelluksen muutama toiminnallisuus kuvattuna sekvenssikaaviona.

### Simulaation luominen satunnaisilla hinnoilla

Käydään läpi mitä tapahtuu, kun käyttäjä on syöttänyt tiedot simulaatiolomakkeeseen ja painaa "Generoi"-nappia joka saa sovelluksen luomaan uuden simulaation satunnaisilla hinnoilla.

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationLuominenSatunnaisillaHinnoilla.png" width="750">

Kun nappia painetaan sen painamiseen reagoiva tapahtumankäsittelijä kutsuu  [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) -luokan metodia _generateSimulation_ ja antaa sille simulaatiolomakkeesta simulaation luomiseen tarvittavat tiedot.

_generateSimulation_-metodi luo uuden [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java)-olion, joka konstruktorissaan luo uuden [Generator](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Generator.java)-olion. Tämän jälkeen _generateSimulation_-metodi kutsuu [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java)-luokan metodia _initializeSimulation_ parametreilla, jotka saatiin napin painamiseen reagoivalta tapahtumankäsittelijältä. Näillä tiedoilla [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java)-olio generoi satunnaiset hinnat ja muut simulaatioon liittyvät arvot [Generator](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Generator.java)-olion avulla.

Lopuksi _generateSimulation_-metodi palauttaa tapahtumankäsittelijälle boolean arvon _true_ ja tapatumankäsittelijä kutsuu luokkansa metodia _showSimulation_, joka rakentaa ja asettaa näkyviin simulaation näyttämisen käytetyn _Scene_-tyyppisen _simulationMenu_-olion.

### Simulaation tallentaminen

Käydään läpi mitä tapahtuu, kun käyttäjä on syöttänyt halutun nimen jolla simulaatio tallennetaan ja painaa "Tallenna"-nappia joka saa sovelluksen tallentamaan simulaation tarpeelliset tiedot tietokantaan.

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationTallentaminen.png" width="750">

Kun nappia painetaan sen painamiseen reagoiva tapahtumankäsittelijä kutsuu [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) -luokan metodia _saveSimulation_ antaen sille samalla parametrina käyttäjän tekstikenttään syöttämän nimen.

_saveSimulation_-metodi kutsuu valitun eli tällä hetkellä näytettävissä olevan [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java)-olion _setName_-metodia, joka asettaa parametrina saamansa merkkijonon simulaation nimeksi.

_saveSimulation_-metodi kutsuu tämän jälkeen [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java)-olion metodia _saveSimulation_ ja antaa sille parametrina tietokantaan tallennettavat tiedot. Olio tallentaa tiedot tietokantaan kutsuen muutamaa omaa metodiaan.

Tietojen tallennuksen jälkeen tapatumankäsittelijä kutsuu oman luokkansa metodia _redrawSimulationslist_, joka hakee kaikki tietokantaan tallennetut simulaatiot tietokannasta [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java) ja sitä kautta [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java)-olioiden avulla ja päivittää päävalikossa näytettävän tallennettujen Simulaatioiden listauksen.

### Tallennetun simulaation lataaminen ja sen näyttäminen

Käydään läpi mitä tapahtuu, kun käyttäjä painaa tallennettujen simulaatioden listauksesta "Lataa"-nappia joka saa sovelluksen lataamaan tietokannasta napin kohdalla olevan simulaation tiedot tietokannasta ja näyttämään ne simulaationäkymässä.

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioTallennetunSimulaationGenerointiJaNayttaminen.png" width="750">

Kun nappia painetaan sen painamiseen reagoiva tapahtumankäsittelijä kutsuu [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java)-luokan metodia _setLoadedSimulationSelected_, jolle annetaan parametrina simulaatio joka näytetään simulaationäkymässä. _setLoadedSimulationSelected_-metodi hakee simulaation hintatiedot tietokannasta [SimulationDao](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/dao/SimulationDao.java)-olion metodin _getSimulationPrices_ avulla jolle annetaan parametrina simulaation id-numero. _getSimulationPrices_-metodi palauttaa halutun simulaation hintatiedot listaja, jotka _setLoadedSimulationSelected_-metodi antaa valitulle Simulation-oliolle _setPrices_-metodin avulla. Tämän jälkeen _setLoadedSimulationSelected_-metodi kutsuu Simulation-olion metodia _initializeArrays_ joka saa [Simulation](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Simulation.java)-olion laskee simulaatioon liittyvät arvot [Generator](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/Generator.java)-olion avulla.

Lopuksi tapahtumanäsittelijä kutsuu luokkansa metodia _showSimulation_, joka rakentaa ja asettaa näkyviin simulaation näyttämisen käytetyn _Scene_-tyyppisen _simulationMenu_-olion.