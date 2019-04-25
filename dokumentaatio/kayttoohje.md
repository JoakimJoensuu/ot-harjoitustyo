# Käyttöohje

## Sovelluksen lataaminen ja käynnistäminen

Lataa _InvestmentSimulator.jar_-tiedosto viimeisimmästä [julkaisusta](https://github.com/JoakimJoensuu/ot-harjoitustyo/releases).

Avaa terminaali, siirry kansioon johon latasit tiedoston ja käynnistä sovellus terminaalin avulla komennolla

`java -jar InvestmentSimulator.jar`

## Näkymä simulaation luominselle tai lataamiselle

Sovellus käynnistyy päävalikkonäkymään, jossa on lomake uuden simulaation luomiselle ja listaus tallennetuista simulaatioista.

<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/paaValikko.png?raw=true" width="750">

### Uuden simulaation luominen

Uuden simulaation luominen aloitetaan täyttämällä vasemmalla laidassa oleva lomake. Lomakkeen kohdat ovat seuraavat:

"Periodeittain sijoitettava summa" määrittelee summan joka Cost Averaging -strategiassa sijoitetaan jokaisen periodin välein. Value Averaging strategiassa kyseinen summa muuttuu riippuen kohteen hinnan kehityksestä. Kts. vaatimusmäärittelyn kohta [Sovelluksen tarkoitus](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md#sovelluksen-tarkoitus)

"Alkupäivä" määrittelee päivämäärän, josta simulaatio aloitetaan.

"Periodin tyyppi" määrittelee periodin pituuden. Valittavissa ovat päivä, viikko, kuukausi tai vuosi.

"Periodeja" määrittelee sen kuinka monen periodin pituinen simulaatio tulee olemaan.

"Vaihtelutaso" määrittelee sen kuinka paljon kohteen hinta voi vaihdella periodin välein. Suurempi vaihtelutaso aiheuttaa todennäköisimmin suuremman volatiliteetin.

Lomakkeen täytön jälkeen painetaan "Luo manuaalisesti" tai "Generoi" riippuen käyttäjän valinnasta.

### Luo manuaalisesti

Kun lomake on täytetty painamalla "Luo manuaalisesti" -nappia käyttäjä ohjataan näkymään jossa kohteen hintojen syöttäminen eri periodeille on mahdollista manuaalisesti.

### Generoi

Painamalla "Generoi" -nappia käyttäjä ohjataan simulaationäkymään. Samalla sovellus generoi kohteen satunnaiset hinnat, joiden vaihtelutaso määriteltiin lomakkeessa ja laskee strategioiden eri arvot.

### Simulaation lataaminen

Oikealla laidassa näkyy listaus tietokantaan tallennetuista simulaatioista. Painamalla halutun simulaation oikealla olevaa "Lataa" -nappia sovellus lataa kyseisen simulaation tiedot tietokannasta ja ohjaa käyttäjän simulaationäkymään, jossa simulaatio näytetään.

## Näkymä hintojen manuaaliselle syöttämiselle

Hinnat syötetään jokaisen päivämäärän kohdalle erikseen. Kun lista on täytetty "Luo"-nappia painamalla sovellus laskee strategioiden eri arvot ja ohjaa käyttäjän simulaationäkymään.

<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/manuaalisetHinnatNakyma.png?raw=true" width="750">

## Simulaationäkymä

Simulaationäkymä koostuu datan näyttävästä viivakaaviosta, napeista joista voidaan vaihtaa viivakaaviossa näytettävät tiedot, taulukosta, jossa näkyy hiiren osoittaman periodin arvot ja tallennuslomakkeesta jolla näytöllä oleva simulaatio voidaan tallentaa tietokantaan.

<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/simulaatioNakyma.png?raw=true" width="750">

### Viivakaavion näyttämän tiedon vaihtaminen

Viivakaavion alla olevista viidestä napista voidaan valita mitä viivakaaviossa näytetään. Nappia painettaessa sovellus piirtää viivakaavion uudelleen.

### Periodikohtaisen tietojen tarkastelu

Liikuttamalla hiirtä viivakaavion päällä, näkymän alalaidassa olevat tiedot muuttuvat hiiren osoittaman päivämääriä sisältävän vaaka-akselin mukaisesti.

### Simulaation tallentaminen

Oikeassa alakulmassa on tekstilaatikko simulaation nimeämiselle ja "Tallenna" -nappi simulattion tallentamiselle. Kun simulaatiolle on annettu nimi ja "Tallenna" -nappia painetaan sovellus tallentaa simulaation tietokantaan ja jatkaa simulaationäkymässä.

### Takaisin pääsy

Painamalla "Takaisin"-nappia käyttäjä ohjataan takaisin päävalikkoon, missä käyttäjä voi ladata toisen simulaation tai muuttaa simulaation luomiseen tarvittavia tietoja.