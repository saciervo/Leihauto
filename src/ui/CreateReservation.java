package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
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

/**
 * The GUI form to create a new reservation
 */
public class CreateReservation {
    private final static Log log = Log.getInstance();

    private JPanel panel;
    private JComboBox<String> memberComboBox;
    private JComboBox<String> carComboBox;
    private JButton requestButton;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private List<Member> members;
    private List<Car> cars;

    private boolean readyToBookReservation = false;
    private static JFrame frame;

    /**
     * Show this GUI form.
     */
    public static void show() {
        frame = new JFrame("CreateReservation");
        frame.setContentPane(new CreateReservation().panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates a new GUI Form to create a new reservation.
     */
    public CreateReservation() {
        $$$setupUI$$$();
        startDateTextField.setText(AppSettings.DisplayDataFormat.format(new Date()));
        endDateTextField.setText(AppSettings.DisplayDataFormat.format(new Date()));

        // Click event for the request button
        requestButton.addActionListener(e -> requestButtonClick());

        // Reset request button when user is about to edit a reservation date
        startDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                resetRequestButton();
                super.focusGained(e);
            }
        });

        // Reset request button when user is about to edit a reservation date
        endDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                resetRequestButton();
                super.focusGained(e);
            }
        });
    }

    private void createUIComponents() {
        // Members drop down
        members = Member.findAll();
        memberComboBox = new JComboBox<>();
        for (Member member : members) {
            memberComboBox.addItem(member.getName());
        }

        // Cars drop down
        cars = Car.findAll();
        carComboBox = new JComboBox<>();
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
            Date startDate = AppSettings.DisplayDataFormat.parse(startDateTextField.getText());
            Date endDate = AppSettings.DisplayDataFormat.parse(endDateTextField.getText());

            Car car = getSelectedCar();
            if (car.isAvailable(startDate, endDate)) {
                if (!readyToBookReservation) {
                    // Restyle the request button to achieve double confirmation before booking a reservation
                    readyToBookReservation = true;
                    requestButton.setText("Book");
                    requestButton.setBackground(Color.GREEN);
                } else {
                    // Book the requested reservation
                    Reservation reservation = new Reservation();
                    reservation.setMember(getSelectedMember());
                    reservation.setCar(car);
                    reservation.setStartDate(startDate);
                    reservation.setEndDate(endDate);
                    reservation.create();
                    ReservationBookedDialog.showDialog();

                    // The job is done. We do not need this form anymore
                    frame.dispose();
                }
            } else {
                CarNotAvailableDialog.showDialog();
            }
        } catch (Exception ex) {
            log.error(ex, "Unexpected exception!");
        }
    }

    private void resetRequestButton() {
        readyToBookReservation = false;
        requestButton.setText("Request");
        requestButton.setBackground(null);
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
        final JLabel label1 = new JLabel();
        label1.setText("Member");
        panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel.add(memberComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Car");
        panel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel.add(carComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Start date");
        panel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("End date");
        panel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        requestButton = new JButton();
        requestButton.setText("Request");
        panel.add(requestButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
