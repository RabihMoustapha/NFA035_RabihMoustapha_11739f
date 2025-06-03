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
	private Contact c;
	private List<Contact> contacts = new ArrayList<>();
	private JTextField firstNameField = new JTextField(15);
	private JTextField lastNameField = new JTextField(15);
	private JTextField cityField = new JTextField(15);
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

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(saveButton);
		form.add(buttonPanel);

		saveButton.addActionListener(e -> saveContact(c));
		add(form);
	}

	private void saveContact(Contact c) {
		c.setNom(firstNameField.getText());
		c.setPrenom(lastNameField.getText());
		c.setVille(cityField.getText());

		if (contacts.contains(c)) {
			JOptionPane.showMessageDialog(null, "The contact is already entered");
		} else {
			contacts.add(c);

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat", true))) {
				oos.writeObject(c);
				oos.writeUTF("\n");
				JOptionPane.showMessageDialog(null, "Contact saved successfully!");
				oos.close();
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(this, "Error saving contact: " + ioe.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void loadContacts() {
		try {
			FileInputStream fis = new FileInputStream("Contacts.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			while(fis.available() > 0) {
				contacts.add((Contact) ois.readObject());
				ois.readUTF();
			}
			ois.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
	}
}