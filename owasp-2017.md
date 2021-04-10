# Vulnerability Analysis

# A3:2017 Sensitive Data Exposure

#Description
# het lekken of makkelijk kunnen verkrijgen van gevoelige data door slechte beveiliging van het netverkeer, slecht opgeslagen ww's, etc.

# Risk
# Je zou binnen dit project makkelijk de data van de trainer te pakken kunnen krijgen omdat je niet hoeft in te loggen of iets dergelijks. 
# Maar gelukkig gaat het hier om een spelletje waar geen sensitive data word gebruikt.
# Als ik authenticatie en autorisatie zou toevoegen zou niet iedereen zomaar een game kunnen ophalen, alleen de mensen met een account zouden daar dan recht op hebben.

# Counter-measures
# hiertegen word niks gedaan aangezien authenticatie en autorisatie niet verplicht was voor dit project


#A9:2017 Using Components with Known Vulnerabilities

#Description
#oude dependencies gebruiken die kwetsbaar zijn.

#Risk
#In dit project zijn aardig wat dependencies gebruikt waarvan enkele een beetje oud zijn dus het zou kunnen dat sommige 
#dependencies bekende vulnerabilities hebben.

# Counter-measures
#Ik gebruik dependabot, dit is een dependency checker die zulke vulnerabilities van oude dependencies helpt beschermen.


#A5:2017
#Description
#een account word niet gecheckt op of het een gebruiker is en dus zomaar toegang heeft, omweg via URL: je verandert iets in url waardoor je op de admin page komt.

#Risk
#In mijn project is hier sprake van, je kan zo de link aanpassen en dan op een andere pagina komen zonder dat er checks uitgevoerd worden.

# Counter-measures
#Je zou dus authenticatie en autorisatie kunnen toevoegen en iedereen vast op unauthorized zetten zodat gebruikers die niet zijn ingelogd nog wel de link aan kunnen passen maar dan niet toegelaten worden tot de pagina
