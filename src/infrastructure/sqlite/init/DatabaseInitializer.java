package infrastructure.sqlite.init;

import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseInitializer {

    private final static Log log = Log.getInstance();

    public static void init() {
        try (DatabaseContext db = new DatabaseContext()) {

            // Initialize database schema
            db.execute(getScript("schema.sql"));

            // Populate database with data
            db.execute(getScript("data.sql"));

        } catch (Exception ex) {
            log.error(ex, "Could not init database.");
        }
    }

    private static String getScript(String scriptName) {
        byte[] encoded = new byte[0];
        try {
            Path path = Paths.get("scripts", scriptName);
            encoded = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.forName("UTF-8"));
    }
}
