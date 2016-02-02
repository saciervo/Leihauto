package infrastructure.sqlite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import core.AppSettings;
import infrastructure.logging.Log;

/**
 * Database context to manage data access.
 */
public class DatabaseContext implements AutoCloseable {
    private final Log log = Log.getInstance();
    private SQLiteConnection db;

    /**
     * Instantiates a new Database context.
     */
    public DatabaseContext() {
        try {
            db = new SQLiteConnection(new File(AppSettings.DB_PATH));
            db.open(true);
        } catch (SQLiteException ex) {
            log.error(ex, String.format("Error opening database: %s", AppSettings.DB_PATH));
        }
    }

    @Override
    public void close() throws Exception {
        if (db != null && !db.isDisposed()) {
            db.dispose();
        }
    }

    /**
     * Executes the specified query against the database.
     *
     * @param query the query
     */
    public void execute(String query) {
        try {
            db.exec(query);
        } catch (SQLiteException ex) {
            log.error(ex, "Could not execute query on the database");
        }
    }

    /**
     * Executes the specified query against the database.
     *
     * @param query the query
     * @param args  the place holders in the prepared query
     */
    public void execute(String query, String... args) {
        SQLiteStatement stmt = null;
        try {
            stmt = db.prepare(query);

            // Bind place holders to prepared query
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    stmt.bind(i + 1, args[i]);
                }
            }

            // Wrap the statement in a transaction
            db.exec("BEGIN TRANSACTION;");
            stmt.step();
            db.exec("COMMIT TRANSACTION;");

        } catch (Exception ex) {
            log.error(ex, "Could not fetch data from the database");
        } finally {
            if (stmt != null) {
                stmt.dispose();
            }
        }
    }

    /**
     * Executes the specified query against the database.
     *
     * @param query the query
     * @param args  the place holders in the prepared query
     * @return the requested database objects as a list of java objects
     */
    public List<Object[]> fetch(String query, String... args) {
        SQLiteStatement stmt = null;
        List<Object[]> result = new ArrayList<>();
        try {
            stmt = db.prepare(query);

            // Bind place holders to prepared query
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    stmt.bind(i + 1, args[i]);
                }
            }

            while (stmt.step()) {

                // Iterate through all rows returned by the database server
                if (stmt.hasRow()) {
                    int count = stmt.columnCount();
                    ArrayList<Object> columns = new ArrayList<>();
                    for (int column = 0; column < count; column++) {
                        columns.add(stmt.columnValue(column));
                    }
                    result.add(columns.toArray());
                }
            }
        } catch (Exception ex) {
            log.error(ex, "Could not fetch data from the database");
        } finally {
            if (stmt != null) {
                stmt.dispose();
            }
        }
        return result;
    }

    /**
     * Executes the specified query against the database.
     *
     * @param query the query
     * @param args  the place holders in the prepared query
     * @return the requested database object
     */
    public Object[] fetchFirst(String query, String... args) {
        List<Object[]> obj = fetch(query + " LIMIT 1", args);
        if (obj.size() > 0) {
            return obj.get(0);
        }
        return null;
    }
}
