package ui;

import core.AppSettings;
import core.domain.Car;
import core.domain.Member;
import core.domain.Reservation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FindReservation {
    private JPanel panel;
    private JScrollPane scrollPane;
    private JList reservationsList;
    private JButton filterButton;
    private JComboBox memberComboBox;
    private JComboBox carComboBox;
    private JButton editButton;

    private List<Member> members;
    private List<Car> cars;

    public FindReservation() {
        filterButton.addActionListener(e -> {
            List<Reservation> reservations = Reservation.findFiltered(getSelectedCarId(), getSelectedMemberId());
            loadReservations(reservations);
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditReservation.show((Reservation) reservationsList.getSelectedValue());
                List<Reservation> reservations = Reservation.findFiltered(getSelectedCarId(), getSelectedMemberId());
                loadReservations(reservations);
            }
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

        reservationsList = new JList(new DefaultListModel<Reservation>());
        loadReservations();
    }

    private void loadReservations()
    {
        loadReservations(Reservation.findAll());
    }

    private void loadReservations(List<Reservation> reservations) {
        reservationsList.setListData(reservations.toArray(new Reservation[reservations.size()]));
        reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationsList.updateUI();
    }
}