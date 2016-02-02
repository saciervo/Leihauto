package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import core.AppSettings;
import core.domain.Car;
import core.domain.Member;
import core.domain.Reservation;

import javax.swing.*;
import java.awt.*;
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
        $$$setupUI$$$();
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

    private void loadReservations() {
        loadReservations(Reservation.findAll());
    }

    private void loadReservations(List<Reservation> reservations) {
        reservationsList.setListData(reservations.toArray(new Reservation[reservations.size()]));
        reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationsList.updateUI();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), -1, -1));
        scrollPane = new JScrollPane();
        panel.add(scrollPane, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane.setViewportView(reservationsList);
        final JLabel label1 = new JLabel();
        label1.setText("Member");
        panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Car");
        panel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterButton = new JButton();
        filterButton.setText("Filter");
        panel.add(filterButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberComboBox.setEnabled(true);
        panel.add(memberComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel.add(carComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        panel.add(editButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}