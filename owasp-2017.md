# Vulnerability Analysis

# A1:2017 Injection

#Description
Rechtstreeks een query uitvoeren met gebruikersinput zonder validatie, filtering of sanitization. 

#Risk
 Het risico voor deze kwetsbaarheid binnen dit project is klein. Er zijn twee plekken in de code waar gebruikersinput rechtstreeks in een query word gezet.
 Het gaat hier om het starten van een ronde en het ophalen van woorden. Mensen zouden dus ronden kunnen starten van andere mensen als ze een id zouden gokken en invoeren van een game die niet van hun is.
 De ronde spelen zou lastiger zijn want dan zou je ook nog de rondeid moeten gokken, maar het kan.
 En wat betreft de woorden, daar word de gebruikersinput alleen gevraagd bij de lengte van de woorden. Je zou dus alle woorden van een bepaalde lengte die je moet
 raden opvragen zodat je vals kan spelen. Maar aangezien de gebruikersinput alleen in de where word gezet kan er niks worden verwijderd of verloren gaan.
 Maar je kan dus wel spelen op een game van iemand anders.

# authenticatie en autorisatie
 Als authenticatie en autorisatie toegevoegd zou worden zou het probleem zich niet meer voordoen.
 Dan zouden deze 2 opties alleen van toepassing kunnen worden gemaakt voor de beheerders van het systeem.
 en dan zou kunnen worden toegepast dat de gebruiker alleen zijn eigen ronden kan inzien.

#Counter-measures
 Hier worden op dit moment geen maatregelen voor getroffen. Authenticatie en authorisatie zou de beste maatregel zijn.

#A9:2017 Using Components with Known Vulnerabilities

#Description
oude dependencies gebruiken die kwetsbaar zijn.

#Risk
In dit project zijn worden veel dependencies gebruikt waarvan enkelen oud zijn, hierbij is de kans groter dat die
dependencies known vulnerabilities hebben dan bij nieuwe dependencies.

# Counter-measures
 In dit project word github dependabot gebruikt. Dependabot helpt om security fixes te vinden en stelt dan een patch voor om de security te verbeteren.
 Nog een andere manier om dit te voorkomen is de versies van je dependencies te verifieren tegen cve lijsten


#A5:2017 Broken acces control
#Description
een account word niet gecheckt of het een gebruiker is en heeft dus zomaar toegang, omweg via URL: je verandert iets in url waardoor je op een andere pagina komt die niet voor jou bedoeld is

#Risk
In mijn project is hier sprake van, je kan zo de link aanpassen en dan op een andere pagina komen zonder dat er checks uitgevoerd worden.
 Hierdoor kunnen gegevens van andere gebruikers makkelijk worden opgehaald.

# authenticatie en autorisatie
Als authenticatie en autorisatie toegevoegd zou worden zou dit probleem verholpen kunnen worden
Dan zal er voor bepaalde pagina's een rol vereist moeten zijn en zonder die rol te hebben word je niet toegelaten op die pagina

# Counter-measures
Je zou dus authenticatie en autorisatie kunnen toevoegen en iedereen vast op unauthorized zetten zodat gebruikers die niet zijn ingelogd nog wel de link aan kunnen passen maar dan niet toegelaten worden tot de pagina
