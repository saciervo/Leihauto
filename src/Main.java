import infrastructure.logging.Log;
import infrastructure.sqlite.init.DatabaseInitializer;
import ui.ConsoleUI;

import java.util.logging.*;

public class Main {

    public static void main(String[] args) {

        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);

        DatabaseInitializer.init();

        System.out.println("Willkommen bei Leihauto!");
        System.out.println("------------------------\n");

        ConsoleUI.mainMenu();

        System.out.println("\nDanke dass Sie diese Software genutzt haben. Auf Wiedersehen!");
    }
}
