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

public class EditReservation {
    private final static Log log = Log.getInstance();

    private JPanel panel;
    private JComboBox memberComboBox;
    private JComboBox carComboBox;
    private JButton requestButton;
    private JTextField memberTextField;
    private JTextField carTextField;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private Reservation reservation;
    private boolean readyToBookReservation = false;
    private static JFrame frame;

    public static void show(Reservation reservation) {
        frame = new JFrame("EditReservation");
        frame.setContentPane(new EditReservation(reservation).panel);
        frame.pack();
        frame.setVisible(true);
    }

    public EditReservation(Reservation reservation) {
        this.reservation = reservation;

        memberTextField.setText(reservation.getMember().getName());
        carTextField.setText(reservation.getCar().getName());
        startDateTextField.setText(AppSettings.DISPLAY_DATE_FORMAT.format(reservation.getStartDate()));
        endDateTextField.setText(AppSettings.DISPLAY_DATE_FORMAT.format(reservation.getEndDate()));

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

    private void requestButtonClick() {
        try {
            Date startDate = AppSettings.DISPLAY_DATE_FORMAT.parse(startDateTextField.getText());
            Date endDate = AppSettings.DISPLAY_DATE_FORMAT.parse(endDateTextField.getText());

            Car car = reservation.getCar();
            if (car.isAvailable(startDate, endDate)) {
                if (readyToBookReservation) {
                    reservation.setStartDate(startDate);
                    reservation.setEndDate(endDate);
                    reservation.update();
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

    private void resetRequestButton() {
        readyToBookReservation = false;
        requestButton.setText("Request");
        requestButton.setBackground(null);
    }
}
