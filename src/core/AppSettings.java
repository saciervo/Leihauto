package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppSettings {
    public static final String PhysicalDatabaseFile = "data\\Leihauto.sqlite";
    public static final String LogFilePath = "data\\log";
    public static final DateFormat DatabaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DisplayDataFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
}
