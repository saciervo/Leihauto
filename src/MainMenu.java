import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class MainMenu {

    public static void show() {
        //         Ermitteln der Verfügbarkeit (Suche für Reservation), K2
        //         Durchführen der Reservation (Update Verfügbarkeit), K2
        //         Buchung der Reservation (Buchung Mitglied), K2

        while (true) {
            System.out.println("Willkommen bei Leihauto!");
            System.out.println("------------------------");
            System.out.println("");
            System.out.println("Wählen Sie eine der folgenden Aktionen:");
            System.out.println("");
            System.out.println("[1] Reservation suchen");
            System.out.println("[2] Reservation bearbeiten");
            System.out.println("[3] Reservation buchen");
            System.out.println("");
            System.out.println("[0] Programm beenden");

            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            try {
                int userChoice = Integer.parseInt(userInput);

                if (userChoice == 0)
                    break;  // Exit program

            } catch (Exception ex) {
                System.out.println("Keine Aktion für Auswahl : '" + userInput + "' gefunden.");
            }
        }
    }
}
