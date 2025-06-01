package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;
import Models.DataClass;

public class ContactsView extends JFrame {
	DataClass dl = new DataClass();
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

		addNewContact.addActionListener(e -> new NewContactView(c));
		deleteContact.addActionListener(e -> deleteSelectedContact());
		loadContacts();

		sortByFirstName.addActionListener(e -> {
			List<Contact> sorted = new ArrayList<>(dl.contacts);
			sorted.sort(Comparator.comparing(Contact::getPrenom));
			updateListModel(sorted);
		});

		sortByLastName.addActionListener(e -> {
			List<Contact> sorted = new ArrayList<>(dl.contacts);
			sorted.sort(Comparator.comparing(Contact::getNom));
			updateListModel(sorted);
		});

		sortByCity.addActionListener(e -> {
			List<Contact> sorted = new ArrayList<>(dl.contacts);
			sorted.sort(Comparator.comparing(Contact::getVille));
			updateListModel(sorted);
		});

		updateContact.addActionListener(e -> {
			Contact selected = contactsList.getSelectedValue();
			if (selected != null) {
				new NewContactView(selected);
			} else {
				JOptionPane.showMessageDialog(this, "Please select a contact to update");
			}
		});
	}

	private void viewContacts() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
			List<Contact> contacts = ((List<Contact>) ois.readObject());
			updateListModel(contacts);
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load contacts: " + ioe.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteSelectedContact() {
		Contact selected = contactsList.getSelectedValue();
		if (selected != null) {
			int confirm = JOptionPane.showConfirmDialog(this, "Delete " + selected.getNom() + "?", "Confirm Delete",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dl.contacts.remove(selected);
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {
					oos.writeObject(dl.contacts);
					loadContacts();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error deleting contact", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select a contact to delete");
		}
	}

	private void loadContacts() {
		try {
			listModel.clear();
			dl.contacts.forEach(listModel::addElement);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error loading contacts: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateListModel(List<Contact> contacts) {
		listModel.clear();
		contacts.forEach(listModel::addElement);
	}
}