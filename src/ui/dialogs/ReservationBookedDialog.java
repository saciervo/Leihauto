package ui.dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationBookedDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public ReservationBookedDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> dispose());
    }

    public static void showDialog() {
        ReservationBookedDialog dialog = new ReservationBookedDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
