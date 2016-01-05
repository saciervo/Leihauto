import java.io.IOException;
import java.util.logging.*;

public class Log {

    // get the global logger to configure it
    private static Logger logger = Logger.getLogger("Leihauto");

    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    public Log() {
        try {
            logger.setLevel(Level.INFO);
            fileTxt = new FileHandler("Leihauto.log");

            // suppress the logging output to the console
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }

            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            error(e.getStackTrace().toString());
        }
    }

    private void log(Level level, String msg) {
        logger.log(level, msg);
    }

    public void debug(String msg){
        log(Level.FINE, msg);
    }

    public void info(String msg) {
        log(Level.INFO, msg);
    }

    public void warning(String msg) {
        log(Level.WARNING, msg);
    }

    public void error(String msg) {
        log(Level.SEVERE, msg);
    }
}