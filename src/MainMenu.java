import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class MainMenu {
    private static Log log = new Log();

    public static void show() {
        System.out.println("Willkommen bei Leihauto!");
        System.out.println("------------------------");
        System.out.println("");
        System.out.println("Wählen Sie eine der folgenden Aktionen:");
        System.out.println("");
        System.out.println("[1] Reservation suchen");       // Ermitteln der Verfügbarkeit (Suche für Reservation), K2
        System.out.println("[2] Reservation bearbeiten");   // Durchführen der Reservation (Update Verfügbarkeit), K2
        System.out.println("[3] Reservation buchen");       // Buchung der Reservation (Buchung Mitglied), K2
        System.out.println("");
        System.out.println("[0] Programm beenden");

        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;
        while (keepRunning) {
            String userInput = scanner.nextLine();

            int userChoice = -1;
            try {
                userChoice = Integer.parseInt(userInput);
            } catch (Exception ex) {
                log.warning("Could not parse user input: " + userInput);
            }

            switch (userChoice) {
                case 0:
                    keepRunning = false;    // Exit program
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Keine Aktion gefunden für Auswahl '" + userInput + "'");
                    break;
            }
        }
    }
}
