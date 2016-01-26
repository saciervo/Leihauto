package ui;

import infrastructure.logging.Log;

import java.util.Scanner;

public class ConsoleUI {
    private final static Log log = Log.getInstance();

    public static void mainMenu() {
        printMainMenu();

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
                    System.out.println("Programm beenden...");
                    break;
                case 1:
                    System.out.println("Reservation suchen");
                    break;
                case 2:
                    System.out.println("Reservation bearbeiten");
                    break;
                case 3:
                    System.out.println("Reservation buchen");
                    break;
                default:
                    System.out.println("Keine Aktion gefunden für Auswahl '" + userInput + "'");
                    break;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("Wählen Sie eine der folgenden Aktionen:");
        System.out.println("");
        System.out.println("[1] Reservation suchen");       // Ermitteln der Verfügbarkeit (Suche für Reservation), K2
        System.out.println("[2] Reservation bearbeiten");   // Durchführen der Reservation (Update Verfügbarkeit), K2
        System.out.println("[3] Reservation buchen");       // Buchung der Reservation (Buchung Mitglied), K2
        System.out.println("");
        System.out.println("[0] Programm beenden");
    }
}
