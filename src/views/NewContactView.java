package views;

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Models.PhoneNumber;
import java.io.*;
import Models.Contact;

public class NewContactView extends JFrame {
	private JTextField firstNameField = new JTextField(15);
	private JTextField lastNameField = new JTextField(15);
	private JTextField cityField = new JTextField(15);
//	private JComboBox<String> groupsComboBox = new JComboBox<>();
	private JButton saveButton = new JButton("Save");

	public NewContactView(Contact c) {
		setTitle("New Contact");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
		form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Form fields
		form.add(new JLabel("First Name:"));
		form.add(firstNameField);
		form.add(new JLabel("Last Name:"));
		form.add(lastNameField);
		form.add(new JLabel("City:"));
		form.add(cityField);

		form.add(new JLabel("Phone Numbers:"));
//		form.add(new JScrollPane(phoneNumbersList));
//		form.add(new JLabel("Group:"));
//		form.add(groupsComboBox);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(saveButton);
		form.add(buttonPanel);

		// Initialize controller
		saveButton.addActionListener(e -> saveContact(c));

		add(form);
	}

	private void saveContact(Contact c) {
		c = new Contact();
		c.setNom(firstNameField.getText().trim());
		c.setPrenom(lastNameField.getText().trim());
		c.setVille(cityField.getText().trim());

		try {
			List<Contact> contacts = new ArrayList<>();

			// Load existing contacts
			File file = new File("Contacts.dat");
			if (file.exists() && file.length() > 0) {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
					contacts = (List<Contact>) ois.readObject();
				} catch (EOFException eof) {
					// Ignore: file is empty
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error reading existing contacts: " + e.getMessage());
				}
			}

			// Add new contact
			contacts.add(c);

			// Save entire list
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
				oos.writeObject(contacts);
			}

			JOptionPane.showMessageDialog(this, "Contact saved successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error saving contact: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}