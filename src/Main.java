import com.almworks.sqlite4java.SQLiteException;

public class Main {

    public static void main(String[] args) {

        try (Database db = new Database()) {
            db.execute("DROP TABLE IF EXISTS books");
            db.execute("CREATE TABLE books (author, title, publication, pages, price)");
            db.execute("INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')");
            String hallo = db.firstString("SELECT author FROM books");
            System.out.println(hallo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Willkommen bei Leihauto!");
        System.out.println("------------------------\n");

        ConsoleUI.mainMenu();

        System.out.println("\nDanke dass Sie diese Software genutzt haben. Auf Wiedersehen!");
    }
}
