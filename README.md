# Ohjelmistotekniikka -kurssin palautusrepositorio

Repositorio Helsingin Yliopiston Ohjelmistotekniikka-kurssin palautuksille

## Sijoitussimulaattori

[Vaatimusmäärittely](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan ohjelman juurikansiosta komennolla

```
mvn test
```

Testikattavuusraportti luodaan ohjelman juurikansiosta komennolla

```
mvn test jacoco:report
```

Raportti löytyy tiedostosta _ot-harjoitustyo/InvestmentSimulator/target/site/jacoco/index.html_

### Ohjelman suorittaminen

Ohjelma voidaan suorittaa ohjelman juurikansiosta komennolla

```
mvn compile exec:java -Dexec.mainClass=investmentsimulator.ui.InvestmentSimulatorUi
```

### Jar-tiedoston luominen ja suorittaminen

Jar tiedosto luodaan ja suoritetaan repostitorion juuresta seuraavilla komennoilla:

TODO




