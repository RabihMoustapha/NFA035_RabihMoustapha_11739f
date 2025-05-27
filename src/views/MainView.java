package views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public JButton contactsButton = new JButton("Contacts");
    public JButton groupsButton = new JButton("Groups");

    public MainView() {
        setTitle("Contact Management System");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(contactsButton);
        panel.add(groupsButton);

        add(panel);
    }
}
