package core.domain;

import core.AppSettings;
import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void save() {
        try (DatabaseContext db = new DatabaseContext()) {
            db.execute("INSERT INTO reservations (carId, memberId, startDate, endDate) VALUES (?, ?, ?, ?)",
                    Integer.toString(getCar().getCarId()),
                    Integer.toString(getMember().getMemberId()),
                    AppSettings.DB_DATE_FORMAT.format(getStartDate()),
                    AppSettings.DB_DATE_FORMAT.format(getEndDate()));
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
    }

    public static List<Reservation> findAll() {
        try (DatabaseContext db = new DatabaseContext()) {
            List<Reservation> result = new ArrayList<>();
            List<Object[]> fetchResult = db.fetch("SELECT id, carId, memberId, startDate, endDate FROM reservations");
            for (Object[] obj : fetchResult) {
                Reservation reservation = new Reservation();
                reservation.setId((int) obj[0]);
                reservation.setCar(Car.find((int) obj[1]));
                reservation.setMember(Member.find((int) obj[2]));
                reservation.setStartDate(AppSettings.DB_DATE_FORMAT.parse(obj[3].toString()));
                reservation.setEndDate(AppSettings.DB_DATE_FORMAT.parse(obj[4].toString()));
                result.add(reservation);
            }

            return result;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;

    }

    public static List<Reservation> findFiltered(Integer selectedCarId, Integer selectedMemberId) {
        String query = "SELECT id, carId, memberId, startDate, endDate FROM reservations WHERE ";

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
            List<Reservation> result = new ArrayList<>();
            List<Object[]> fetchResult = db.fetch(query, args.toArray(new String[args.size()]));
            for (Object[] obj : fetchResult) {
                Reservation reservation = new Reservation();
                reservation.setId((int) obj[0]);
                reservation.setCar(Car.find((int) obj[1]));
                reservation.setMember(Member.find((int) obj[2]));
                reservation.setStartDate(AppSettings.DB_DATE_FORMAT.parse(obj[3].toString()));
                reservation.setEndDate(AppSettings.DB_DATE_FORMAT.parse(obj[4].toString()));
                result.add(reservation);
            }

            return result;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;
    }
}
