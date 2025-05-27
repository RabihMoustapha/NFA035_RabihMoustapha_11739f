package views;

import javax.swing.*;
import java.awt.*;

public class ViewContactView extends JFrame {
    public JLabel nameLabel = new JLabel();
    public JLabel cityLabel = new JLabel();
    public DefaultListModel<String> phoneNumbersModel = new DefaultListModel<>();
    public JList<String> phoneNumbersList = new JList<>(phoneNumbersModel);
    public DefaultListModel<String> groupsModel = new DefaultListModel<>();
    public JList<String> groupsList = new JList<>(groupsModel);

    public ViewContactView() {
        setTitle("View Contact");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameLabel);
        panel.add(new JLabel("City:"));
        panel.add(cityLabel);
        panel.add(new JLabel("Phone Numbers:"));
        panel.add(new JScrollPane(phoneNumbersList));
        panel.add(new JLabel("Groups:"));
        panel.add(new JScrollPane(groupsList));

        add(panel);
    }
}
