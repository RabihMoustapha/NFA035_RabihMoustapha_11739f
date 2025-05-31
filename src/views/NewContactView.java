package views;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Models.PhoneNumber;
import java.io.*;
import Models.Contact;

public class NewContactView extends JFrame {
	private Contact c;
	private JTextField firstNameField = new JTextField(15);
	private JTextField lastNameField = new JTextField(15);
	private JTextField cityField = new JTextField(15);
	private DefaultListModel<PhoneNumber> phoneNumbersModel = new DefaultListModel<>();
	private JList<PhoneNumber> phoneNumbersList = new JList<>();
	private JTextField phoneInput = new JTextField(15);
	private JTextField regionCodeInput = new JTextField(15);
	private JButton addPhoneButton = new JButton("Add Phone");
	private JComboBox<String> groupsComboBox = new JComboBox<>();
	private JButton saveButton = new JButton("Save");

	public NewContactView(Contact c) {
		this.c = c;
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
		form.add(new JLabel("Region Code:"));
		form.add(regionCodeInput);
		form.add(new JLabel("Phone Number:"));
		form.add(phoneInput);
		form.add(addPhoneButton);
		form.add(new JLabel("Phone Numbers:"));
		form.add(new JScrollPane(phoneNumbersList));
		form.add(new JLabel("Group:"));
		form.add(groupsComboBox);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(saveButton);
		form.add(buttonPanel);

		// Initialize controller
		addPhoneButton.addActionListener(e -> addPhoneNumber());
		saveButton.addActionListener(e -> saveContact(c));

		add(form);
	}

	private void addPhoneNumber() {
		try {
			String phone = phoneInput.getText().trim();
			String regionCode = regionCodeInput.getText().trim();

			if (!phone.isEmpty() && !regionCode.isEmpty()) {
				PhoneNumber pn = new PhoneNumber(Integer.parseInt(regionCode), Integer.parseInt(phone));
				phoneNumbersModel.addElement(pn);
				phoneInput.setText("");
				regionCodeInput.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Please enter both region code and phone number", "Input Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error adding phone number: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveContact(Contact c) {
		c.setNom(firstNameField.getText().trim());
		c.setPrenom(lastNameField.getText().trim());
		c.setVille(cityField.getText().trim());
		try {
			// Add phone numbers
			for (int i = 0; i < phoneNumbersModel.size(); i++) {
				c.addPhoneNumber(phoneNumbersModel.getElementAt(i));
			}

			// Save to file
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat", true))) {
				oos.writeObject(c);
				oos.close();
			}

			JOptionPane.showMessageDialog(this, "Contact saved successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error saving contact: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void clearFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		cityField.setText("");
		regionCodeInput.setText("");
		phoneInput.setText("");
		phoneNumbersModel.clear();
	}
}
