# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sijoittamisessa, varsinkin rahastosijoittamisessa käytetään usein kahta erilaista sijoitusstrategiaa, jotka ovat englanniksi cost averaging ja value averaging.

Cost averaging strategiassa, vapaasti suomen kielelle käännettynä kustannuksen keskiarvoistamistrategiassa, sijoitetaan tietyn periodin välein, esimerkiksi kuukausi tai viikko, aina samansuuruinen summa. Tällä tavalla sijoittaessa tuotto on kohteen arvonvaihtelun mukainen.

Value averaging strategiassa, vapaasti suomen kielelle käännettynä arvon keskiarvoistamisstrategiassa, ero aikaisempaan näkyy periodeittain sijoitettavan summan koossa. Sijoitettava summa kasvaa jos kohteen arvo laskee ja pienenee jos kohteen arvo nousee. Mikäli kohteen arvo kasvaa tarpeeksi myydään osuuksia. Tällä tavalla pyritään ostamaan kohdetta enemmän kun se on halvempaa ja ostamaan vähemmän kun se on kalliimpaa.

Sovelluksen tarkoitus on tuoda graafinen tapa näiden sijoitustrategioiden tulosten esittämiselle erilaisissa tapauksissa.

Tällä hetkellä Internetistä löytyy vain kömpelöitä laskentataulukkoja näihin tarkoituksiin.

## Käyttöliittymäluonnos

Sovelluksen perusversiossa on kolme näkymää

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/paavalikko.jpg" width="750">

Päävalikko, josta voidaan luoda simulaatio tai ladata aiemmin tallennettu simulaatio

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/muokkausnakyma.jpg" width="750">

Muokkausnäkymä, josta syötetään uuden manuaalisesti luotavan simulaation kohteen hinnat

<img src="https://raw.githubusercontent.com/JoakimJoensuu/ot-harjoitustyo/master/dokumentaatio/kuvat/simulaationakyma.jpg" width="750">

Simulaationäkymä, josta voidaan tarkastella eri sijoitustrategioiden kehitystä simulaatiossa

## Perusversion tarjoama toiminnallisuus

### Simulaation luominen

- käyttäjä voi luoda simulaation manuaalisesti tai generoimalla sen satunnaisesti
  - valitsemalla simulaation manuaalisen luonnin käyttäjä ohjataan näkymään johon syötetään eri ajankohtien kurssit
  - valitsemalla simulaation satunnaisen generoinnin käyttäjä ohjataan suoraa simulaationäkymään
  - ennen valintaa käyttäjältä vaaditaan esitiedot simulaation luomiselle
- käyttäjä voi ladata aikaisemmin luodun simulaation tietokantaan josta käyttäjä ohjataan simulaationäkymään

### Simulaation tarkastelu

- käyttäjä voi valita näytetäänkö viivakaaviossa simulaationäkymän graafissa kurssikehitys, eri strategioiden tuotto tai tuottoprosentti
  - vaihtamalla näytettävää arvoa viivakaavio päivittyy sen mukaisesti
  
- viivakaavion alla olevasta näkymästä näkyy sijoitustrategioiden tuotto, arvo ja muita tietoja tietyltä päivältä
  - päivä valitaan liikuttamalla hiirtä viivakaavion päällä
- alimpana napit, joista pääsee takaisin päävalikkoon ja tallentamaan simulaation tietokantaan

## Jatkokehitysideoita

- tallennetun simulaation poistaminen

- simulaationäkymän viivakaavioon kohteen hinnan lisäksi strategioiden ostojen keskikurssit

- mahdollisuus muokata jo luodun simulaation kursseja ilman uuden luomista

- Simulaationäkymässä tarkasteltavan aikaväli on valittavissa

- kirjanpitoväline näillä strategioilla toteutettaville sijoituksille 

- historiallisen datan tuominen simulaatioon tiedostosta tai suoraan Internetistä
