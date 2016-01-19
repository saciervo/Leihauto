package data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {
    public static void init() {
        try (Database db = new Database()) {
            db.execute(getScript("schema.sql"));
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
