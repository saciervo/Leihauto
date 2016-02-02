package ui.dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarNotAvailableDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public CarNotAvailableDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> dispose());
    }

    public static void showDialog() {
        CarNotAvailableDialog dialog = new CarNotAvailableDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
