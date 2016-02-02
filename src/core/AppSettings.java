package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppSettings {
    public static final String DB_PATH = "data\\Leihauto.sqlite";
    public static final String LOG_PATH = "data\\log";

    public static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
}
