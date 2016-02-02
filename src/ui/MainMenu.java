package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel;
    private JButton newReservationButton;
    private JButton findReservationButton;

    public MainMenu() {
        newReservationButton.addActionListener(e -> CreateReservation.show());
        findReservationButton.addActionListener(e -> FindReservation.show());
    }

    public static void show() {
        JFrame frame = new JFrame("Leihauto");
        frame.setContentPane(new MainMenu().panel);
        frame.pack();
        frame.setVisible(true);
    }
}
