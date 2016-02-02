package ui;

import core.AppSettings;
import core.domain.Car;
import core.domain.Member;
import core.domain.Reservation;
import infrastructure.logging.Log;
import ui.dialogs.CarNotAvailableDialog;
import ui.dialogs.ReservationBookedDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;
import java.util.List;

public class CreateReservation {
    private final static Log log = Log.getInstance();

    private JPanel panel;
    private JComboBox memberComboBox;
    private JComboBox carComboBox;
    private JButton requestButton;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private List<Member> members;
    private List<Car> cars;

    private boolean readyToBookReservation = false;
    private static JFrame frame;

    public CreateReservation() {
        startDateTextField.setText(AppSettings.DISPLAY_DATE_FORMAT.format(new Date()));
        endDateTextField.setText(AppSettings.DISPLAY_DATE_FORMAT.format(new Date()));

        requestButton.addActionListener(e -> {
            requestButtonClick();
        });

        startDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                resetRequestButton();
                super.focusGained(e);
            }
        });

        endDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                resetRequestButton();
                super.focusGained(e);
            }
        });
    }

    public static void show() {
        frame = new JFrame("CreateReservation");
        frame.setContentPane(new CreateReservation().panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // Members drop down
        members = Member.findAll();
        memberComboBox = new JComboBox();
        for (Member member : members) {
            memberComboBox.addItem(member.getName());
        }

        // Cars drop down
        cars = Car.findAll();
        carComboBox = new JComboBox();
        for (Car car : cars) {
            carComboBox.addItem(car.getName());
        }
    }

    private Car getSelectedCar() {
        return cars.stream().filter(x -> x.getName() == carComboBox.getSelectedItem()).findFirst().get();
    }

    private Member getSelectedMember() {
        return members.stream().filter(x -> x.getName() == memberComboBox.getSelectedItem()).findFirst().get();
    }

    private void requestButtonClick() {
        try {
            Date startDate = AppSettings.DISPLAY_DATE_FORMAT.parse(startDateTextField.getText());
            Date endDate = AppSettings.DISPLAY_DATE_FORMAT.parse(endDateTextField.getText());

            Car car = getSelectedCar();
            if (car.isAvailable(startDate, endDate)) {
                if (readyToBookReservation) {
                    Reservation reservation = new Reservation();
                    reservation.setMember(getSelectedMember());
                    reservation.setCar(car);
                    reservation.setStartDate(startDate);
                    reservation.setEndDate(endDate);
                    reservation.create();
                    ReservationBookedDialog.showDialog();
                    frame.dispose();
                } else {
                    readyToBookReservation = true;
                    requestButton.setText("Book");
                    requestButton.setBackground(Color.GREEN);
                }
            } else {
                CarNotAvailableDialog.showDialog();
            }
        } catch (Exception ex) {
            log.error(ex, "Unexpected error!");
        }
    }

    private void resetRequestButton(){
        readyToBookReservation = false;
        requestButton.setText("Request");
        requestButton.setBackground(null);
    }
}
