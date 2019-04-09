# Ohjelmistotekniikka -kurssin palautusrepositorio

Repositorio Helsingin Yliopiston Ohjelmistotekniikka-kurssin palautuksille

## Sijoitussimulaattori

[Vaatimusmäärittely](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/JoakimJoensuu/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

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

Raportti löytyy tiedostosta _ot-harjoitustyo/target/site/jacoco/index.html_


### Ohjelman suorittaminen

Ohjelma voidaan suorittaa komennolla

```
mvn compile exec:java -Dexec.mainClass=investmentsimulator.ui.InvestmentSimulatorUi
```


### Jar-tiedoston luominen ja suorittaminen

#### Jar tiedosto luodaan ja suoritetaan repostitorion juuresta seuraavilla komennoilla:

Luodaan ohjelmasta jar-tiedosto

```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

Merkitään tiedosto suoritettavaksi tiedostoksi

```
chmod +x target/InvestmentSimulator-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

Suoritetaan tiedosto

```
java -jar target/InvestmentSimulator-1.0-SNAPSHOT-jar-with-dependencies.jar 
```
