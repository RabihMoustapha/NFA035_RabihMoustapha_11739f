package views;

import java.util.*;
import java.util.List;
import Models.DataClass;
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
	private JButton saveButton = new JButton("Save");
	public DataClass cl = new DataClass();

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
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
				if (cl.contacts.contains(ois.readObject())) {
					JOptionPane.showMessageDialog(null, "Is the list already contain it");
				} else {
					cl.contacts.add(c);

					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat", true))) {
						oos.writeObject(c);
						JOptionPane.showMessageDialog(this, "Contact saved successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
						oos.close();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(this, "Error saving contact: " + e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}