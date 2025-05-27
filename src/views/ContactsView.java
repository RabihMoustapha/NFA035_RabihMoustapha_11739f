package views;

import javax.swing.*;
import java.awt.*;

public class ContactsView extends JFrame {
    public JButton sortByFirstName = new JButton("Sort by First Name");
    public JButton sortByLastName = new JButton("Sort by Last Name");
    public JButton sortByCity = new JButton("Sort by City");
    public JButton addNewContact = new JButton("Add New Contact");
    public JButton updateContact = new JButton("Update Contact");
    public JButton deleteContact = new JButton("Delete Contact");
    public JButton viewContact = new JButton("View Contact");
    public JTextField searchField = new JTextField(20);
    public DefaultListModel<String> listModel = new DefaultListModel<>();
    public JList<String> contactsList = new JList<>(listModel);

    public ContactsView() {
        setTitle("Contacts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(sortByFirstName);
        topPanel.add(sortByLastName);
        topPanel.add(sortByCity);
        topPanel.add(searchField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addNewContact);
        buttonPanel.add(updateContact);
        buttonPanel.add(deleteContact);
        buttonPanel.add(viewContact);

        JScrollPane scrollPane = new JScrollPane(contactsList);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
