package core.domain;

import core.AppSettings;
import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The car. Represents a car sharing vehicle evey customer can drive.
 */
public class Car {
    private final static Log log = Log.getInstance();

    private int id;
    private Location parkingSpot;
    private CarCategory category;
    private String name;
    private String plateNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(Location parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    private static Car ConvertToCar(Object[] obj) {
        Car car = new Car();
        car.setId((int) obj[0]);
        car.setParkingSpot(Location.find((int) obj[1]));
        car.setCategory(CarCategory.find((int) obj[2]));
        car.setName(obj[3].toString());
        car.setPlateNumber(obj[4].toString());
        return car;
    }

    /**
     * Find car by id.
     *
     * @param id the id
     * @return the car
     */
    public static Car find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, parkingSpotLocationId, carCategoryId, name, plateNumber FROM cars WHERE id = ?", Integer.toString(id));
            return ConvertToCar(obj);
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }

    /**
     * Find all cars.
     *
     * @return a list of cars.
     */
    public static List<Car> findAll() {
        try (DatabaseContext db = new DatabaseContext()) {
            return db.fetch("SELECT id, parkingSpotLocationId, carCategoryId, name, plateNumber FROM cars")
                    .stream()
                    .map(Car::ConvertToCar)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;

    }

    /**
     * Checks if a car is available at the specified .
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return True if the car is available. False if the car ist not ready book.
     */
    public boolean isAvailable(Date startDate, Date endDate) {
        String startDateString = AppSettings.DatabaseDateFormat.format(startDate);
        String endDateString = AppSettings.DatabaseDateFormat.format(endDate);
        try (DatabaseContext db = new DatabaseContext()) {
            Object obj = db.fetchFirst("SELECT id FROM reservations WHERE (startDate < ? AND endDate > ?) OR (startDate < ? AND endDate > ?)", startDateString, startDateString,endDateString,endDateString);
            return obj == null;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return false;
    }
}

