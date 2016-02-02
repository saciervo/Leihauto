package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Holds settings and constants of the application.
 */
public class AppSettings {

    /**
     * The file path to the SQLite database.
     */
    public static final String PhysicalDatabaseFile = "data\\Leihauto.sqlite";

    /**
     * The directory of the log file.
     */
    public static final String LogFilePath = "data\\log";

    /**
     * The format used to save dates in a database.
     */
    public static final DateFormat DatabaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * The format used to display dates on screen.
     */
    public static final DateFormat DisplayDataFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
}
