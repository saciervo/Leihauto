package core.domain;

import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

/**
 * The car category. Used to describe and categorize a car a bit further
 */
public class CarCategory {
    private final static Log log = Log.getInstance();

    private int id;
    private String name;
    private String basicConfiguration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicConfiguration() {
        return basicConfiguration;
    }

    public void setBasicConfiguration(String basicConfiguration) {
        this.basicConfiguration = basicConfiguration;
    }

    /**
     * Finds the  car category by id.
     *
     * @param id the id
     * @return the car category
     */
    public static CarCategory find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, name, basicConfiguration FROM carCategories WHERE id = ?", Integer.toString(id));
            CarCategory carCategory = new CarCategory();
            carCategory.setId((int) obj[0]);
            carCategory.setName(obj[1].toString());
            carCategory.setBasicConfiguration(obj[2].toString());
            return carCategory;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }
}
