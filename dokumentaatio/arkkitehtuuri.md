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

Käyttöliittymä käyttää [InvestmentSimulatorService](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/src/main/java/investmentsimulator/domain/InvestmentSimulatorService.java)-luokan metodeja seuraavasti:


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

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/datamalli.png" width="200">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/pakkauskaavio.png" width="500">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationLuominenSatunnaisillaHinnoilla.png" width="750">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioSimulaationTallentaminen.png" width="750">
<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavioTallennetunSimulaationGenerointiJaNayttaminen.png" width="750">



