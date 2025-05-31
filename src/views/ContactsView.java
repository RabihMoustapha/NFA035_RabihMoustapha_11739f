package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
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

	public ContactsView(Contact c) {
		setTitle("Contacts");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		viewContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
				    List<Contact> contactList = (List<Contact>) ois.readObject();
				    listModel.clear(); // Clear old list
				    for (Contact contact : contactList) {
				        listModel.addElement(contact);
				    }
				} catch (IOException | ClassNotFoundException ioe) {
				    ioe.printStackTrace();
				    JOptionPane.showMessageDialog(null, "Failed to load contacts: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
