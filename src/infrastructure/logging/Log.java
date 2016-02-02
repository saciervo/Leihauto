package infrastructure.logging;

import core.AppSettings;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * Logging singleton class
 */
public class Log {
    private static Log instance = new Log();

    /**
     * Gets the instance.
     *
     * @return the (singleton) instance
     */
    public static Log getInstance() {
        return instance;
    }

    private static Logger logger;

    private Log() {
        try {
            // Configure the global logger
            logger = Logger.getLogger("");
            logger.setLevel(Level.FINEST);

            // Get logging directory and create it, if it does not exist
            Path logPath = Paths.get(AppSettings.LogFilePath);
            if (!Files.exists(logPath)) {
                Files.createDirectory(logPath);
            }

            // Configure logfile
            logPath = Paths.get(AppSettings.LogFilePath, "Leihauto.log");
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

            // Create a text formatter
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

    /**
     * Log messages like executed sql queries
     *
     * @param msg the message
     */
    public void debug(String msg) {
        log(Level.FINE, msg);
    }

    /**
     * Log nrmal behavior like mail sent, user updated profile etc.
     *
     * @param message the message
     */
    public void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Log incorrect behavior but the application can continue
     *
     * @param message the message
     */
    public void warning(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Log application crashes / exceptions.
     *
     * @param exception the exception
     */
    public void error(Exception exception) {
        error(exception, null);
    }

    /**
     * Log application crashes / exceptions.
     *
     * @param exception the exception
     * @param message   the message
     */
    public void error(Exception exception, String message) {
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            // Only include message in log if it is available
            sb.append(message);
            sb.append("\n");
        }

        // Write exception to a string
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        sb.append(sw.toString());

        log(Level.SEVERE, sb.toString());
    }
}
