
import java.util.Scanner;

    public class Rekenmachine {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Voer een wiskundige expressie in of typ 'exit' om te stoppen: ");
                String expressie = scanner.nextLine();//Gebruikersinvoer lezen

                if (expressie.equalsIgnoreCase("exit")) {//Controle op stoppen
                    System.out.println("Programma beëindigd.");// Als dit waar is, wordt de boodschap "Programma beëindigd." afgedrukt.
                    break;//wordt de lus beëindigd met het gebruik van break.
                }
//Dit gedeelte van de code voert een poging uit om de ingevoerde wiskundige expressie te evalueren en geeft vervolgens het resultaat weer.
// Als er tijdens het evalueren een fout optreedt, wordt een 'catch'-blok gebruikt om de fout af te handelen en een bericht naar de gebruiker te sturen.
                try {
                    double resultaat = evaluate(expressie);
                    System.out.println("Resultaat: " + resultaat);
                } catch (Exception e) {
                    System.out.println("Foutieve invoer, probeer opnieuw.");
                }
            }
            scanner.close();
        }
        //Deze code definieert een methode genaamd evaluate die verantwoordelijk is voor het evalueren van een wiskundige expressie die wordt doorgegeven als een stringparameter expressie.
//numerieke resultaat als een double-waarde terug te geven.
        private static double evaluate(String expressie) {
            return new Object() {//Dit creëert een anoniem object in Java, een instantie van een anonieme inner class, die binnen de evaluate-methode wordt aangemaakt
                int positie = -1, karakter;// variabelen positie en karakter gedefinieerd. (positie wordt geïnitialiseerd op -1 en karakter wordt nog niet geïnitialiseerd.)

                void volgendeKarakter() {//Dit is een methode binnen het anonieme object die verantwoordelijk is voor het verplaatsen naar het volgende karakter in de invoerexpressie.
                    karakter = (++positie < expressie.length()) ? expressie.charAt(positie) : -1;
                }//deze logica verhoogt eerst de positie met 1 en controleert vervolgens of deze nieuwe positie binnen de lengte van de expressie valt

                void overslaanLeegtes() {//dit is een methode die geen waarde retourneert ,(void)
                    while (Character.isWhitespace(karakter)) {//de methode controleert of het  karakter een spatie, tab, newline of een andere vorm van witruimte is.
                        volgendeKarakter();// Deze methode is verantwoordelijk voor het bijwerken van het karakter naar het volgende karakter in de reeks, dat geen witruimte is
                    }
                    //Deze methode is bedoeld om spaties en andere witruimtes in de invoer over te slaan terwijl de parser door de expressie gaat.
                }

                double parseUitdrukking() {//parse gebruik voor het analizeren en evalueren van de wiskunde
                    volgendeKarakter();
                    double resultaat = parseToevoegingEnAftrek();//Dit is een methode binnen de parser die verantwoordelijk is voor het analyseren en evalueren van de gehele wiskundige expressie.
                    if (positie < expressie.length()) {
                        throw new RuntimeException("Ongeldige expressie.");
                    }
                    return resultaat;//Als er geen ongeldige expressie is aangetroffen, wordt het berekende resultaat van de expressie geretourneerd als een double
                }

                double parseToevoegingEnAftrek() {//Deze methode is verantwoordelijk voor het verwerken van optelling en aftrek in een wiskundige expressie
                    double resultaat = parseVermenigvuldigingEnDelen();//hier wordt voor  om de vermenigvuldiging en deling in de expressie te verwerken en het resulterende getal te verkrijgen.
                    while (true) {//Dit geeft een oneindige lus aan waarin de methode blijft werken om de optelling en aftrek uit te voeren, totdat de hele expressie is geëvalueerd.
                        overslaanLeegtes();// Deze methode wordt gebruikt om eventuele witruimtes over te slaan en naar het volgende relevante karakter in de expressie te gaan.
                        if (karakter == '+') {
                            volgendeKarakter();
                            resultaat += parseVermenigvuldigingEnDelen();
                        } else if (karakter == '-') {
                            volgendeKarakter();
                            resultaat -= parseVermenigvuldigingEnDelen();
                        } else {
                            return resultaat;
                        }
                    }
                    // Deze sectie controleert het huidige karakter in de expressie. Als het een '+' is, wordt de methode parseVermenigvuldigingEnDelen() opnieuw aangeroepen om de
                    // volgende waarde te verkrijgen, en deze waarde wordt toegevoegd aan het totaal. Als het een '-' is, wordt de methode opnieuw aangeroepen en de verkregen waarde
                    // wordt afgetrokken van het totaal. Als het geen '+' of '-' is, betekent dit dat we klaar zijn met optellen en aftrekken, en het huidige resultaat wordt geretourneerd
                    // als het eindresultaat van de optelling en aftrek in dit deel van de expressie.
                }

                double parseVermenigvuldigingEnDelen() {
                    double resultaat = parseModulo();
                    while (true) {
                        overslaanLeegtes();
                        if (karakter == '*') {
                            volgendeKarakter();
                            resultaat *= parseModulo();
                        } else if (karakter == '/') {
                            volgendeKarakter();
                            resultaat /= parseModulo();
                        } else {
                            return resultaat;
                        }
                    }
                }

                double parseModulo() {
                    double resultaat = parseWaarde();
                    while (true) {
                        overslaanLeegtes();
                        if (karakter == '%') {
                            volgendeKarakter();
                            resultaat %= parseWaarde();
                        } else {
                            return resultaat;
                        }
                    }
                }
                //deze methode vertegenwoordigt voor het verwerken van waarden binnen een wiskundige expressie om haakjes in de expressie te verwerken en de waarde binnen deze haakjes te evalueren.
                double parseWaarde() {//een methode die double waarde retourneert
                    overslaanLeegtes();//Dit is een methode die wordt aangeroepen om eventuele witruimtes over te slaan en naar het volgende relevante karakter in de expressie te gaan.
                    if (karakter == '(') {//Deze voorwaarde controleert of het huidige karakter gelijk is aan '(' (een openingshaakje). Als dat het geval is, betekent dit dat er een wiskundige expressie tussen haakjes is.
                        volgendeKarakter();// Als er een openingshaakje is , deze methode aangeroepen om naar het volgende karakter te gaan na het openingshaakje.
                        double resultaat = parseToevoegingEnAftrek();// Dit is een methodeaanroep die verantwoordelijk is voor het parseren en evalueren van de wiskundige expressie binnen de haakjes.
                        if (karakter != ')') {//controleert of het huidige karakter niet gelijk is aan ')' (een sluitingshaakje).
                            throw new RuntimeException("Ontbrekende haakjes.");// Als dat het geval is, wordt er een uitzondering (exception) gegenereerd omdat de sluitingshaakjes ontbreken.
                        }
                        volgendeKarakter();//Als er een sluitingshaakje is, wordt de methode aangeroepen om naar het karakter na het sluitingshaakje te gaan.
                        return resultaat;//Het resulterende getal binnen de haakjes wordt geretourneerd als het eindresultaat van de evaluatie van deze wiskundige expressie.
                    }
                    //deze deel wordt gebruikt om numerieke waarden te verwerken die kunnen voorkomen in de expressie, zoals getallen of kommagetallen.
                    if (Character.isDigit(karakter) || karakter == '.') {//controleert of het huidige karakter een cijfer is (0-9) of een punt (.) dat wordt gebruikt in decimale getallen Als het karakter een cijfer is of een punt, betekent dit dat we mogelijk een numerieke waarde aan het verwerken zijn.
                        StringBuilder nummer = new StringBuilder();//Hier wordt een StringBuilder aangemaakt om een numerieke waarde op te bouwen tijdens het verwerken van de opeenvolgende cijfers en punt in de expressie.
                        while (Character.isDigit(karakter) || karakter == '.') {//Deze while-lus wordt uitgevoerd zolang het huidige karakter een cijfer is of een punt.
                            nummer.append((char) karakter);//Het voegt elk karakter toe aan de StringBuilder nummer
                            volgendeKarakter();//en gaat vervolgens naar het volgende karakter totdat er geen cijfers of punt meer zijn.
                        }
                        return Double.parseDouble(nummer.toString());// Nadat alle opeenvolgende cijfers of punten zijn verwerkt, wordt de waarde in de StringBuilder geconverteerd naar een double-waarde met behulp van Double.parseDouble().( retorneert double waarde)
                    }
                    throw new RuntimeException("Ongeldige expressie.");// Als het karakter geen cijfer is en geen punt, wordt een uitzondering gegenereerd omdat de expressie ongeldig is.
                }
            }.parseUitdrukking();
        }
    }
    //korte uitleggen over "parse":
// pars ebetekent dat het analyseren van een reeks tekens (zoals een string)om de structuur of betekenis ervan te begrijpen en om te zetten naar een ander formaat of gegevenstype.
//in de rekening machine applicatie "parsen" verwijzen naar het analyseren van een wiskundige expressie in een string,waarbij elk wiskundig teken
    //en andere relevante elementen worden herkend en omgezet naar een intern formaat dat kan worden gebruikt om de berekening uit te voeren.
//De parser van een rekenmachine kan bijvoorbeeld de invoerstring "10 - 5 + 6 + 8" analyseren en begrijpen dat het gaat om een reeks getallen gescheiden door wiskundige operatoren.
//De parse zal elk getal en elk teken herkennen en de nodige stappen nemen om de berekening correct uit te voeren.


