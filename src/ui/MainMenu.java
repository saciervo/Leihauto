package ui;

import infrastructure.sqlite.init.DatabaseInitializer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenu {
    private JPanel panel;
    private JButton newReservationButton;
    private JButton findReservationButton;
    private JButton resetDatabaseButton;

    public MainMenu() {
        newReservationButton.addActionListener(e -> CreateReservation.show());
        findReservationButton.addActionListener(e -> FindReservation.show());
        resetDatabaseButton.addActionListener(e -> DatabaseInitializer.init());
    }

    public static void main(String[] args) {
        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);

        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
