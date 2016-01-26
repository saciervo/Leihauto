package data;

import infrastructure.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {

    private final static Log log = Log.getInstance();

    public static void init() {
        try (Database db = new Database()) {
            // Initialize database schema
            String query = getScript("schema.sql");
            db.execute(query);

            // Populate database with data
            query = getScript("data.sql");
            db.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getScript(String scriptName) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get("scripts/" + scriptName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.defaultCharset());
    }
}
