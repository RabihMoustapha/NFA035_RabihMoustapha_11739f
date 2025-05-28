package views;

import javax.swing.*;
import controllers.ContactsController;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Models.Contact;

public class ContactsView extends JFrame {
	public Set<Contact> contacts = new HashSet<>();
    public JButton sortByFirstName = new JButton("Sort by First Name");
    public JButton sortByLastName = new JButton("Sort by Last Name");
    public JButton sortByCity = new JButton("Sort by City");
    public JButton addNewContact = new JButton("Add New Contact");
    public JButton updateContact = new JButton("Update Contact");
    public JButton deleteContact = new JButton("Delete Contact");
    public JButton viewContact = new JButton("View Contact");
    public JTextField searchField = new JTextField(20);
    public DefaultListModel<Contact> listModel = new DefaultListModel<>();
    public JList<Contact> contactsList = new JList<>(listModel);

    public ContactsView() {
        setTitle("Contacts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

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
        
        ContactsController ctrl = new ContactsController();
		addNewContact.addActionListener(e -> new NewContactView());
		updateContact.addActionListener(e -> new UpdateContactView());
		viewContact.addActionListener(e -> new ViewContactView());
		deleteContact.addActionListener(e -> ctrl.deleteSelectedContact(contactsList.getSelectedIndex()));
		sortByFirstName.addActionListener(e -> ctrl.sortAndDisplay("first"));
		sortByLastName.addActionListener(e -> ctrl.sortAndDisplay("last"));
		sortByCity.addActionListener(e -> ctrl.sortAndDisplay("city"));
		searchField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				ctrl.search(searchField.getText(), listModel);
			}
		});
				
    }
}
