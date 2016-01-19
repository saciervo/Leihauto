package data;

import java.io.File;
import java.util.ArrayList;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteConstants;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class Database implements AutoCloseable {
    private SQLiteConnection db;
    private String delimiter = "[,]";

    private static final String DB_PATH = System.getProperty("user.home") + "\\" + "leihauto.db";

    public Database() throws SQLiteException {
        db = new SQLiteConnection(new File(DB_PATH));
        db.open(true);
    }

    @Override
    public void close() throws Exception {
        if (db != null && !db.isDisposed()) {
            db.dispose();
        }
    }

    public boolean execute(String query) throws SQLiteException {
        SQLiteStatement stmt = db.prepare(query);
        boolean result = false;
        try {
            if (stmt.step()) {
                result = true;
            }
        } finally {
            stmt.dispose();
        }
        return result;
    }

    public String firstString(String query) throws SQLiteException {
        return firstString(query, null);
    }

    public String firstString(String query, String args) throws SQLiteException {
        SQLiteStatement stmt = db.prepare(query + " LIMIT 1");
        String value = null;
        try {
            if (args != null) {
                String[] parameters = args.split(delimiter);
                for (int i = 0; i < parameters.length; i++) {
                    stmt.bind(i + 1, parameters[i]);
                }
            }
            if (stmt.step()) {
                value = stmt.columnString(0);
            }
        } finally {
            stmt.dispose();
        }
        return value;
    }

    public String selectSingle(String query, String args) throws SQLiteException {
        SQLiteStatement st = db.prepare(query);
        String[] parameters = args.split(delimiter);
        String result = null;
        try {
            for (int i = 0; i < parameters.length; i++) {
                st.bind(i + 1, parameters[i]);
            }
            int columnsCount = st.columnCount();
            if (columnsCount > 1) {
                System.err.println("Error: More than a single column!");
                return null;
            }

            if (st.step()) {
                int type = st.columnType(0);

                switch (type) {
                    case SQLiteConstants.SQLITE_INTEGER:
                        result = String.valueOf(st.columnInt(0));
                    case SQLiteConstants.SQLITE_TEXT:
                        result = st.columnString(0);
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            st.dispose();
        }
        return result;
    }

    public ArrayList selectMany(String query, String args) {
        ArrayList result = new ArrayList();
        SQLiteStatement st = null;
        try {
            st = db.prepare(query);
            String[] parameters;
            if (!args.isEmpty() && args != null) {
                parameters = args.split(delimiter);
                for (int i = 0; i < parameters.length; i++) {
                    st.bind(i + 1, parameters[i]);
                }
            }

            int columnsCount = st.columnCount();
            if (columnsCount > 1) {
                System.err.println("Error: More than a single column!");
                return null;
            }
            while (st.step()) {
                int type = st.columnType(0);

                switch (type) {
                    case SQLiteConstants.SQLITE_INTEGER:
                        result.add(String.valueOf(st.columnInt(0)));
                        break;

                    case SQLiteConstants.SQLITE_TEXT:
                        result.add(st.columnString(0));
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            st.dispose();
        }
        return result;
    }

    public boolean updateSingleField(String query, String args) {
        boolean success = false;
        SQLiteStatement st = null;
        try {
            db.exec("BEGIN TRANSACTION; ");
            st = db.prepare(query);
            String[] parameters = args.split(delimiter);

            for (int i = 0; i < parameters.length; i++) {
                st.bind(i + 1, parameters[i]);
            }
            st.step();
            db.exec("COMMIT;");
            success = true;
        } catch (SQLiteException e) {
            System.err.println(e.getMessage());
        } finally {
            if (st != null) {
                st.dispose();
            }
        }
        return success;
    }
}
