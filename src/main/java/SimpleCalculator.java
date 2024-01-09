
    import java.util.Scanner;

    public class SimpleCalculator {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Voer een bewerking in (+, -, *, /) in of typ 'exit' om te stoppen: ");
                String operator = scanner.nextLine();

                if (operator.equalsIgnoreCase("exit")) {
                    System.out.println("Programma beÃ«indigd.");
                    break;
                }

                System.out.print("Voer het eerste getal in: ");
                double num1 = Double.parseDouble(scanner.nextLine());

                System.out.print("Voer het tweede getal in: ");
                double num2 = Double.parseDouble(scanner.nextLine());

                double resultaat;

                switch (operator) {
                    case "+":
                        resultaat = num1 + num2;
                        break;
                    case "-":
                        resultaat = num1 - num2;
                        break;
                    case "*":
                        resultaat = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            System.out.println("Kan niet delen door nul.");
                            continue;
                        }
                        resultaat = num1 / num2;
                        break;
                    default:
                        System.out.println("Ongeldige bewerking.");
                        continue;
                }

                System.out.println("Resultaat: " + resultaat);
            }
            scanner.close();
        }
    }



