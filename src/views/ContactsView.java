package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;

public class ContactsView extends JFrame {
	public List<Contact> contacts = new ArrayList<>();
	public Contact c;
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
		this.c = c;
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
		
		loadContacts();
		loadData();

		addNewContact.addActionListener(e -> new NewContactView(c));
		deleteContact.addActionListener(e -> deleteSelectedContact());


		sortByFirstName.addActionListener(e -> {
			List<Contact> contacts = new ArrayList<>();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
				contacts.sort(Comparator.comparing(Contact::getPrenom));
				contacts.add((Contact) ois.readObject());
				ois.close();
			}catch(IOException | ClassNotFoundException ioe) {
				ioe.printStackTrace();
			}
			updateListModel(contacts);
		});

		sortByLastName.addActionListener(e -> {
			List<Contact> contacts = new ArrayList<>();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
				contacts.add((Contact) ois.readObject());
				contacts.sort(Comparator.comparing(Contact::getNom));
			}catch(IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			updateListModel(contacts);
		});

		sortByCity.addActionListener(e -> {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
				contacts.add((Contact) ois.readObject());
				contacts.sort(Comparator.comparing(Contact::getVille));
			}catch(IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			updateListModel(contacts);
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
			JOptionPane.showMessageDialog(null,
					"Prenom" + listModel.getElementAt(0) + 
					"\nNom" + listModel.getElementAt(1) +
					"\nVille" + listModel.getElementAt(ABORT));
	}

	private void deleteSelectedContact() {
		Contact selected = contactsList.getSelectedValue();
		if (selected != null) {
			int confirm = JOptionPane.showConfirmDialog(this, "Delete " + selected.getNom() + "?", "Confirm Delete",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				contacts.remove(selected);
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat", true))) {
					oos.writeObject(contacts);
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
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
			contacts.add((Contact) ois.readObject());
			contacts.forEach(listModel::addElement);
			}catch(IOException | ClassNotFoundException ioe) {
				ioe.printStackTrace();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error loading contacts: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateListModel(List<Contact> contacts) {
		listModel.clear();
		contacts.forEach(listModel::addElement);
	}
	
	private void loadData() {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
			listModel.addElement((Contact) ois.readObject());
		}catch(IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
	}
}