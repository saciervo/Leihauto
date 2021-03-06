package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import core.AppSettings;
import core.domain.Car;
import core.domain.Reservation;
import infrastructure.logging.Log;
import ui.dialogs.CarNotAvailableDialog;
import ui.dialogs.ReservationBookedDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

/**
 * GUI form to edit a reservation.
 */
public class EditReservation {
    private final static Log log = Log.getInstance();

    private JPanel panel;
    private JButton requestButton;
    private JTextField memberTextField;
    private JTextField carTextField;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private Reservation reservation;
    private boolean readyToBookReservation = false;
    private static JFrame frame;

    /**
     * Show this form.
     *
     * @param reservation the reservation
     */
    public static void show(Reservation reservation) {
        frame = new JFrame("EditReservation");
        frame.setContentPane(new EditReservation(reservation).panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates a new form
     *
     * @param reservation the reservation
     */
    public EditReservation(Reservation reservation) {
        this.reservation = reservation;

        memberTextField.setText(reservation.getMember().getName());
        carTextField.setText(reservation.getCar().getName());
        startDateTextField.setText(AppSettings.DisplayDataFormat.format(reservation.getStartDate()));
        endDateTextField.setText(AppSettings.DisplayDataFormat.format(reservation.getEndDate()));

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
            Date startDate = AppSettings.DisplayDataFormat.parse(startDateTextField.getText());
            Date endDate = AppSettings.DisplayDataFormat.parse(endDateTextField.getText());

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Member");
        panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Car");
        panel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Start date");
        panel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("End date");
        panel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        requestButton = new JButton();
        requestButton.setText("Request");
        panel.add(requestButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberTextField = new JTextField();
        memberTextField.setEditable(false);
        memberTextField.setEnabled(false);
        panel.add(memberTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        carTextField = new JTextField();
        carTextField.setEditable(false);
        carTextField.setEnabled(false);
        panel.add(carTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        startDateTextField = new JTextField();
        panel.add(startDateTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        endDateTextField = new JTextField();
        panel.add(endDateTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
