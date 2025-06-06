package views;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;
import Models.PhoneNumber;

public class ContactsView extends JFrame {
	public List<Contact> contacts;
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
		contacts = new ArrayList<>();
		loadContacts(); // Load existing contacts from file
		setTitle("Contacts");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		addNewContact.addActionListener(e -> new NewContactView(new Contact()));

		deleteContact.addActionListener(e -> deleteSelectedContact());

		viewContact.addActionListener(e -> viewContact());

		updateContact.addActionListener(e -> {
			Contact selected = contactsList.getSelectedValue();
			if (selected != null) {
				new ContactUpdateView(selected);
			} else {
				JOptionPane.showMessageDialog(this, "Please select a contact to update");
			}
		});

		sortByFirstName.addActionListener(e -> {
			if (contacts == null || contacts.isEmpty())
				return;
			List<Contact> sorted = new ArrayList<>(contacts);
			sorted.sort(Comparator.comparing(Contact::getPrenom, String.CASE_INSENSITIVE_ORDER));
			updateListModel(sorted);
		});

		sortByLastName.addActionListener(e -> {
			if (contacts == null || contacts.isEmpty())
				return;
			List<Contact> sorted = new ArrayList<>(contacts);
			sorted.sort(Comparator.comparing(Contact::getNom, String.CASE_INSENSITIVE_ORDER));
			updateListModel(sorted);
		});

		sortByCity.addActionListener(e -> {
			if (contacts == null || contacts.isEmpty())
				return;
			List<Contact> sorted = new ArrayList<>(contacts);
			sorted.sort(Comparator.comparing(Contact::getVille, String.CASE_INSENSITIVE_ORDER));
			updateListModel(sorted);
		});

		searchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				searchContacts();
			}

			public void removeUpdate(DocumentEvent e) {
				searchContacts();
			}

			public void insertUpdate(DocumentEvent e) {
				searchContacts();
			}
		});
		setVisible(true);
	}

	private void addPhoneNumberToContact() {

	}

	private void searchContacts() {
		String query = searchField.getText().toLowerCase().trim();
		if (query.isEmpty()) {
			updateListModel(contacts);
			return;
		}

		List<Contact> filtered = new ArrayList<>();
		for (Contact contact : contacts) {
			if (contact.getNom().toLowerCase().contains(query) || contact.getPrenom().toLowerCase().contains(query)
					|| contact.getVille().toLowerCase().contains(query)) {
				filtered.add(contact);
			}
		}

		updateListModel(filtered);
	}

	private void loadContacts() {
		listModel.clear();
		contacts.clear();

		File file = new File("Contacts.dat");
		if (!file.exists() || file.length() == 0) {
			return; // No contacts to load
		}

		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

			while (true) {
				try {
					Contact contact = (Contact) ois.readObject();
					contacts.add(contact);
					listModel.addElement(contact);
				} catch (EOFException eof) {
					break; // Reached end of file
				}
			}

		} catch (IOException | ClassNotFoundException ioe) {
			JOptionPane.showMessageDialog(this, "Error loading contacts: " + ioe.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateListModel(List<Contact> newContacts) {
		listModel.clear();
		if (newContacts != null && !newContacts.isEmpty()) {
			newContacts.forEach(listModel::addElement);
		}
	}

	private void deleteSelectedContact() {
		Contact selected = contactsList.getSelectedValue();
		if (selected != null) {
			int confirm = JOptionPane.showConfirmDialog(this, "Delete " + selected.getNom() + "?", "Confirm Delete",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				contacts.remove(selected);
				updateListModel(contacts);

				// Safely overwrite the file with the updated list
				File file = new File("Contacts.dat");
				try (FileOutputStream fos = new FileOutputStream(file, false);
						ObjectOutputStream oos = new ObjectOutputStream(fos)) {

					for (Contact contact : contacts) {
						oos.writeObject(contact);
					}

				} catch (IOException ex) {
					JOptionPane.showMessageDialog(this, "Error deleting contact: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		} else {
			JOptionPane.showMessageDialog(this, "Please select a contact to delete");
		}
	}

	private void viewContact() {
		Contact selected = contactsList.getSelectedValue();
		if (selected != null) {
			JDialog dialog = new JDialog(this, "Contact Details", true);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(this);
			dialog.setLayout(new BorderLayout());

			// Info at top
			JTextArea infoArea = new JTextArea();
			infoArea.setEditable(false);
			infoArea.setText("First Name: " + selected.getPrenom() + "\n" + "Last Name: " + selected.getNom() + "\n"
					+ "City: " + selected.getVille() + "\n\nPhone Numbers:\n");

			List<PhoneNumber> phones = selected.getNumbers();
			for (PhoneNumber phone : phones) {
				infoArea.append(phone.toString() + "\n");
			}

			dialog.add(new JScrollPane(infoArea), BorderLayout.CENTER);

			// Button panel
			JPanel buttonPanel = new JPanel(new FlowLayout());
			JButton addPhoneNumber = new JButton("Add Phone Number");
			JButton updatePhoneBtn = new JButton("Update Phone");
			JButton removePhoneBtn = new JButton("Remove Phone");

			// -- Add Phone Action --
			addPhoneNumber.addActionListener(e -> {
				if (selected == null) {
					JOptionPane.showMessageDialog(dialog, "No contact selected.");
					return;
				}
				try {
					String regionCodeStr = JOptionPane.showInputDialog(dialog, "Enter Region Code:");
					if (regionCodeStr == null)
						return;
					int regionCode = Integer.parseInt(regionCodeStr.trim());

					String numberStr = JOptionPane.showInputDialog(dialog, "Enter Phone Number:");
					if (numberStr == null)
						return;
					int number = Integer.parseInt(numberStr.trim());

					PhoneNumber newPhone = new PhoneNumber(regionCode, number);
					selected.addPhoneNumber(newPhone);
					saveContactsToFile();

					JOptionPane.showMessageDialog(dialog, "Phone number added.");
					dialog.dispose();
					viewContact(); // Refresh
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(dialog, "Invalid number format.");
				}
			});

			// -- Update Phone Action --
			updatePhoneBtn.addActionListener(e -> {
				if (phones.isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "No phone numbers to update.");
					return;
				}
				PhoneNumber selectedPhone = (PhoneNumber) JOptionPane.showInputDialog(dialog,
						"Select a phone to update:", "Update Phone", JOptionPane.PLAIN_MESSAGE, null, phones.toArray(),
						phones.get(0));
				if (selectedPhone != null) {
					try {
						String newRegion = JOptionPane.showInputDialog(dialog, "Enter new region code:",
								selectedPhone.getRegionCode());
						if (newRegion == null)
							return;
						int regionCode = Integer.parseInt(newRegion.trim());

						String newNumber = JOptionPane.showInputDialog(dialog, "Enter new phone number:",
								selectedPhone.getNumber());
						if (newNumber == null)
							return;
						int number = Integer.parseInt(newNumber.trim());

						selectedPhone.setRegionCode(regionCode);
						selectedPhone.setNumber(number);
						saveContactsToFile();
						JOptionPane.showMessageDialog(dialog, "Phone updated.");
						dialog.dispose();
						viewContact(); // Refresh
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(dialog, "Invalid number format.");
					}
				}
			});

			// -- Remove Phone Action --
			removePhoneBtn.addActionListener(e -> {
				if (phones.isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "No phone numbers to remove.");
					return;
				}
				PhoneNumber selectedPhone = (PhoneNumber) JOptionPane.showInputDialog(dialog,
						"Select a phone to remove:", "Remove Phone", JOptionPane.PLAIN_MESSAGE, null, phones.toArray(),
						phones.get(0));
				if (selectedPhone != null) {
					int confirm = JOptionPane.showConfirmDialog(dialog,
							"Are you sure you want to delete: " + selectedPhone + "?", "Confirm Remove",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						selected.getNumbers().remove(selectedPhone);
						saveContactsToFile();
						JOptionPane.showMessageDialog(dialog, "Phone removed.");
						dialog.dispose();
						viewContact(); // Refresh
					}
				}
			});

			buttonPanel.add(addPhoneNumber);
			buttonPanel.add(updatePhoneBtn);
			buttonPanel.add(removePhoneBtn);

			dialog.add(buttonPanel, BorderLayout.SOUTH);
			dialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "Please select a contact to view");
		}
	}

	private void saveContactsToFile() {
		try (FileOutputStream fos = new FileOutputStream("Contacts.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			for (Contact contact : contacts) {
				oos.writeObject(contact);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error saving contacts: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}