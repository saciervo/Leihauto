package infrastructure.logging;

import core.ApplicationConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class Log {
    private static Log instance = new Log();

    public static Log getInstance() {
        return instance;
    }

    // get the global logger to configure it
    private static Logger logger;

    private Log() {
        try {
            logger = Logger.getLogger("");
            logger.setLevel(Level.FINEST);

            // Get logging directory and create it, if it does not exist
            Path logPath = Paths.get(ApplicationConstants.LOG_PATH);
            if (!Files.exists(logPath)) {
                Files.createDirectory(logPath);
            }

            // Configure logfile
            logPath = Paths.get(ApplicationConstants.LOG_PATH, "Leihauto.log");
            FileHandler fileTxt = new FileHandler(logPath.toString());

            /*
            // suppress the logging output to the console
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers.length > 0 && handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }
            */

            logger.addHandler(new ConsoleHandler());

            // create a TXT formatter
            SimpleFormatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void log(Level level, String msg) {
        logger.log(level, msg);
    }

    public void debug(String msg) {
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
