package infrastructure.sqlite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import core.AppSettings;
import infrastructure.logging.Log;

public class DatabaseContext implements AutoCloseable {
    private final Log log = Log.getInstance();
    private SQLiteConnection db;
    private String delimiter = "[,]";

    public DatabaseContext() {
        try {
            db = new SQLiteConnection(new File(AppSettings.DB_PATH));
            db.open(true);
        } catch (SQLiteException ex) {
            log.error(ex, "Error opening database: " + AppSettings.DB_PATH);
        }
    }

    @Override
    public void close() throws Exception {
        if (db != null && !db.isDisposed()) {
            db.dispose();
        }
    }

    public void execute(String query) {
        try {
            db.exec(query);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void execute(String query, String... args) {
        SQLiteStatement stmt = null;
        try {
            stmt = db.prepare(query);
            String[] parameters;
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    stmt.bind(i + 1, args[i]);
                }
            }
            db.exec("BEGIN TRANSACTION;");
            stmt.step();
            db.exec("COMMIT;");
        } catch (Exception ex) {
            System.out.println("Fetch SQLiteException: " + ex.getMessage());
        } finally {
            if (stmt != null) {
                stmt.dispose();
            }
        }
    }

    public List<Object[]> fetch(String query, String ... args) {
        SQLiteStatement stmt = null;
        List<Object[]> result = new ArrayList<>();
        try {
            stmt = db.prepare(query);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    stmt.bind(i + 1, args[i]);
                }
            }
            while (stmt.step()) {
                if (stmt.hasRow()) {
                    int count = stmt.columnCount();
                    ArrayList columns = new ArrayList<>();
                    for (int column = 0; column < count; column++) {
                        columns.add(stmt.columnValue(column));
                    }
                    result.add(columns.toArray());
                }
            }
        } catch (Exception ex) {
            System.out.println("Fetch SQLiteException: " + ex.getMessage());
        } finally {
            if (stmt != null) {
                stmt.dispose();
            }
        }
        return result;
    }

    public Object[] fetchFirst(String query, String ... args) {
        List<Object[]> obj = fetch(query + " LIMIT 1", args);
        if (obj.size() > 0) {
            return obj.get(0);
        }
        return null;
    }
}
