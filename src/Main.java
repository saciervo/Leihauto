import infrastructure.sqlite.init.DatabaseInitializer;
import ui.MainMenu;

import java.util.logging.*;

public class Main {

    public static void main(String[] args) {
        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
        DatabaseInitializer.init();

        MainMenu.show();
    }
}
