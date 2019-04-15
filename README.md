# Ohjelmistotekniikka -kurssin palautusrepositorio

Repositorio Helsingin Yliopiston Ohjelmistotekniikka-kurssin palautuksille

## Sijoitussimulaattori

Sijoitussimulaattorin tarkoitus tuoda käyttäjälle graafinen esitystapa Cost Averaging ja Value Averaging strategioiden toteutumiselle eri tapauksissa. Käyttäjä voi generoida satunnaisesti kuvitellun kohteen hinnankehityksen tai luoda ne manuaalisesti.

#### Tarkemmat kuvaukset sovelluksesta löytyvät seuraavien linkkien alta:

[Vaatimusmäärittely](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Release](https://github.com/JoakimJoensuu/ot-harjoitustyo/releases/tag/viikko5)

[Releasen .jar-tiedosto](https://github.com/JoakimJoensuu/ot-harjoitustyo/releases/download/viikko5/InvestmentSimulator.jar)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Raportti löytyy tiedostosta _./target/site/jacoco/index.html_


### Ohjelman suorittaminen

Ohjelma voidaan suorittaa juurikansiosta komennolla

```
mvn compile exec:java -Dexec.mainClass=investmentsimulator.ui.InvestmentSimulatorUi
```


### Jar-tiedoston luominen ja suorittaminen

#### Jar tiedosto luodaan ja suoritetaan repostitorion juuresta seuraavilla komennoilla:

Luodaan ohjelmasta jar-tiedosto

```
mvn package
```

Suoritetaan tiedosto

```
java -jar target/InvestmentSimulator-1.0-SNAPSHOT.jar
```

### Checkstyletarkistus

Checkstyletarkistus suoritetaan juurikansiosta komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Tarkastusraportti löytyy tiedostosta _./target/site/checkstyle.html_

