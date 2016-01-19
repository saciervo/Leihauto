import data.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.init();

        System.out.println("Willkommen bei Leihauto!");
        System.out.println("------------------------\n");

        ConsoleUI.mainMenu();

        System.out.println("\nDanke dass Sie diese Software genutzt haben. Auf Wiedersehen!");
    }
}
