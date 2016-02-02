package core.domain;

import core.AppSettings;
import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Reservation. A car booking by a member is represented by a reservation.
 */
public class Reservation {
    private final static Log log = Log.getInstance();

    private int id;
    private Car car;
    private Member member;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("%s to %s - %s reserved by %s",
                AppSettings.DisplayDataFormat.format(getStartDate()),
                AppSettings.DisplayDataFormat.format(getEndDate()),
                getCar().getName(),
                getMember().getName());
    }

    /**
     * Creates a new reservation in the database.
     */
    public void create() {
        try (DatabaseContext db = new DatabaseContext()) {
            db.execute("INSERT INTO reservations (carId, memberId, startDate, endDate) VALUES (?, ?, ?, ?)",
                    Integer.toString(getCar().getId()),
                    Integer.toString(getMember().getId()),
                    AppSettings.DatabaseDateFormat.format(getStartDate()),
                    AppSettings.DatabaseDateFormat.format(getEndDate()));
        } catch (Exception ex) {
            log.error(ex, "Could not create reservation.");
        }
    }

    /**
     * Updates the reservation in the database.
     */
    public void update() {
        try (DatabaseContext db = new DatabaseContext()) {
            db.execute("UPDATE reservations SET startDate = ?, endDate = ? WHERE id = ?",
                    AppSettings.DatabaseDateFormat.format(getStartDate()),
                    AppSettings.DatabaseDateFormat.format(getEndDate()),
                    Integer.toString(getId()));
        } catch (Exception ex) {
            log.error(ex, "Could not update the reservation.");
        }
    }

    /**
     * Find all reservations.
     *
     * @return the reservations
     */
    public static List<Reservation> findAll() {
        try (DatabaseContext db = new DatabaseContext()) {
            return db.fetch("SELECT id, carId, memberId, startDate, endDate FROM reservations").stream().map(Reservation::ConvertToReservation).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(ex, "Could not get reservations.");
        }
        return null;
    }

    /**
     * Finds all reservations filtered by the specified filters.
     *
     * @param selectedCarId    the selected car id
     * @param selectedMemberId the selected member id
     * @return the reservations
     */
    public static List<Reservation> findFiltered(Integer selectedCarId, Integer selectedMemberId) {
        String query = "SELECT id, carId, memberId, startDate, endDate FROM reservations WHERE ";

        // Concatenate the filtering SQL query
        ArrayList<String> args = new ArrayList<>();
        if (selectedCarId != null) {
            query += "carId = ?";
            args.add(Integer.toString(selectedCarId));
        } else {
            query += "0=0";
        }
        query += " AND ";
        if (selectedMemberId != null) {
            query += "memberId = ?";
            args.add(Integer.toString(selectedMemberId));
        } else {
            query += "0=0";
        }

        try (DatabaseContext db = new DatabaseContext()) {
            return db.fetch(query, args.toArray(new String[args.size()]))
                    .stream()
                    .map(Reservation::ConvertToReservation)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;
    }

    private static Reservation ConvertToReservation(Object[] obj) {
        Reservation reservation = new Reservation();
        reservation.setId((int) obj[0]);
        reservation.setCar(Car.find((int) obj[1]));
        reservation.setMember(Member.find((int) obj[2]));
        try {
            reservation.setStartDate(AppSettings.DatabaseDateFormat.parse(obj[3].toString()));
            reservation.setEndDate(AppSettings.DatabaseDateFormat.parse(obj[4].toString()));
        } catch (ParseException ex) {
            log.error(ex, "Could not convert StartDate and EndDate.");
        }
        return reservation;
    }
}
