# Käyttöohje

## Sovelluksen lataaminen ja käynnistäminen

Lataa _InvestmentSimulator.jar_-tiedosto viimeisimmästä [julkaisusta](https://github.com/JoakimJoensuu/ot-harjoitustyo/releases).

Avaa terminaali, siirry kansioon johon latasit tiedoston ja käynnistä sovellus terminaalin avulla komennolla

`java -jar InvestmentSimulator.jar`

## Simulaation luominen tai lataaminen

Sovellus käynnistyy päävalikkonäkymään, jossa on lomake uuden simulaation luomiselle ja listaus tallennetuista simulaatioista.

<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/paaValikko.png?raw=true" width="750">

Uuden simulaation luominen onnistuu täyttämällä näkymän vasemmassa laidassa olevat kentät halutuilla arvoilla.

### Lomakkeen kohdat

"Periodeittain sijoitettava summa" määrittelee summan joka Cost Averaging -strategiassa sijoitetaan jokaisen periodin välein. Value Averaging strategiassa kyseinen summa muuttuu riippuen kohteen hinnan kehityksestä. Kts. vaatimusmäärittelyn kohta [Sovelluksen tarkoitus](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md#sovelluksen-tarkoitus)

"Alkupäivä" määrittelee päivämäärän, josta simulaatio aloitetaan.

"Periodin tyyppi" määrittelee periodin pituuden. Valittavissa ovat päivä, viikko, kuukausi tai vuosi.

"Periodeja" määrittelee sen kuinka monen periodin pituinen simulaatio tulee olemaan.

"Vaihtelutaso" määrittelee sen kuinka paljon kohteen hinta voi vaihdella periodin välein. Suurempi vaihtelutaso aiheuttaa todennäköisimmin suuremman volatiliteetin.

### Luo manuaalisesti

Painamalla "Luo manuaalisesti" -nappia käyttäjä ohjataan näkymään jossa kohteen hintojen syöttäminen eri periodeille on mahdollista manuaalisesti.

### Generoi

Painamalla "Generoi" nappia käyttäjä ohjataan simulaationäkymään. Samalla sovellus generoi kohteen satunnaiset hinnat, joiden vaihtelutaso määriteltiin lomakkeessa.

## Näkymä hintojen manuaaliselle syöttämiselle

Hinnat syötetään jokaisen päivämäärän kohdalle erikseen. Kun lista on täytetty "Luo"-nappia painamalla sovellus laskee strategioiden eri arvot ja ohjaa käyttäjän simulaationäkymään.

<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/simulaatioNakyma.png?raw=true" width="750">

## Simulaationäkymä





<img src="https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/manuaalisetHinnatNakyma.png?raw=true" width="750">







