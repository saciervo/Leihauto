package ui;

import core.AppSettings;
import core.domain.Car;
import core.domain.Member;
import core.domain.Reservation;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FindReservation {
    private JPanel panel;
    private JList reservationsList;
    private JButton filterButton;
    private JComboBox memberComboBox;
    private JComboBox carComboBox;
    private JScrollPane scrollPane;

    private List<Member> members;
    private List<Car> cars;
    private HashMap<Integer, String> listItems;

    public FindReservation() {
        filterButton.addActionListener(e -> {
            List<Reservation> reservations = Reservation.findFiltered(getSelectedCarId(), getSelectedMemberId());
            loadReservations(reservations);
        });
    }

    public static void show() {
        JFrame frame = new JFrame("Find reservation");
        frame.setContentPane(new FindReservation().panel);
        frame.pack();
        frame.setVisible(true);
    }


    private Integer getSelectedCarId() {
        Optional<Car> car = cars.stream().filter(x -> x.getName() == carComboBox.getSelectedItem()).findFirst();
        if (car.isPresent()) {
            return car.get().getCarId();
        }
        return null;
    }

    private Integer getSelectedMemberId() {
        Optional<Member> member = members.stream().filter(x -> x.getName() == memberComboBox.getSelectedItem()).findFirst();
        if (member.isPresent()) {
            return member.get().getMemberId();
        }
        return null;
    }

    private void createUIComponents() {
        // Members drop down
        members = Member.findAll();
        memberComboBox = new JComboBox();
        memberComboBox.addItem("- None -");
        for (Member member : members) {
            memberComboBox.addItem(member.getName());
        }

        // Cars drop down
        cars = Car.findAll();
        carComboBox = new JComboBox();
        carComboBox.addItem("- None -");
        for (Car car : cars) {
            carComboBox.addItem(car.getName());
        }

        loadReservations(Reservation.findAll());
    }

    private void loadReservations(List<Reservation> reservations) {
        listItems = new HashMap<>();
        for (Reservation reservation : reservations) {
            String item = String.format("%s bis %s - %s (%s)",
                    AppSettings.DISPLAY_DATE_FORMAT.format(reservation.getStartDate()),
                    AppSettings.DISPLAY_DATE_FORMAT.format(reservation.getEndDate()),
                    reservation.getMember().getName(),
                    reservation.getCar().getName());
            listItems.put(reservation.getId(), item);
        }

        reservationsList = new JList(listItems.values().toArray());
        reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}