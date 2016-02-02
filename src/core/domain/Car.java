package core.domain;

import core.AppSettings;
import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Car {
    private final static Log log = Log.getInstance();

    private int carId;
    private Location parkingSpot;
    private CarCategory category;
    private String name;
    private String plateNumber;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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
        car.setCarId((int) obj[0]);
        car.setParkingSpot(Location.find((int) obj[1]));
        car.setCategory(CarCategory.find((int) obj[2]));
        car.setName(obj[3].toString());
        car.setPlateNumber(obj[4].toString());
        return car;
    }

    public static Car find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, parkingSpotLocationId, carCategoryId, name, plateNumber FROM cars WHERE id = ?", Integer.toString(id));
            return ConvertToCar(obj);
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }

    public static List<Car> findAll() {
        try (DatabaseContext db = new DatabaseContext()) {
            List<Car> result = new ArrayList<>();
            List<Object[]> fetchResult = db.fetch("SELECT id, parkingSpotLocationId, carCategoryId, name, plateNumber FROM cars", null);
            for (Object[] obj : fetchResult) {
                result.add(ConvertToCar(obj));
            }

            return result;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;

    }

    public boolean isAvailable(Date startDate, Date endDate) {
        String startDateString = AppSettings.DB_DATE_FORMAT.format(startDate);
        String endDateString = AppSettings.DB_DATE_FORMAT.format(endDate);
        try (DatabaseContext db = new DatabaseContext()) {
            Object obj = db.fetchFirst("SELECT id FROM reservations WHERE (startDate < ? AND endDate > ?) OR (startDate < ? AND endDate > ?)", startDateString, startDateString,endDateString,endDateString);
            return obj == null;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return false;
    }
}

