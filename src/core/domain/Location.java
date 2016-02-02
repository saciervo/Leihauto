package core.domain;

import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

public class Location {
    private final static Log log = Log.getInstance();

    private int locationId;
    private String street;
    private String zipCode;
    private String city;
    private int parkingSpaces;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public static Location find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, street, zipCode, city, amountParkingSpaces FROM locations WHERE id = ?", Integer.toString(id));
            Location location = new Location();
            location.setLocationId((int) obj[0]);
            location.setStreet(obj[1].toString());
            location.setZipCode(obj[2].toString());
            location.setCity(obj[3].toString());
            location.setParkingSpaces((int) obj[4]);
            return location;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }
}
