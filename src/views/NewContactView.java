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
	private List<Contact> contacts;
	private JTextField firstNameField = new JTextField(15);
	private JTextField lastNameField = new JTextField(15);
	private JTextField cityField = new JTextField(15);
	private JButton saveButton = new JButton("Save");

	public NewContactView(Contact c) {
		this.c = c;
		contacts = new ArrayList<>();
		loadContacts(); // Load existing contacts from file
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

	        try (FileOutputStream fos = new FileOutputStream("Contacts.dat", false);
	             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	            for (Contact contact : contacts) {
	                oos.writeObject(contact); // save each contact
	            }

	            JOptionPane.showMessageDialog(null, "Contact saved successfully!");
	        } catch (IOException ioe) {
	            JOptionPane.showMessageDialog(this, "Error saving contact: " + ioe.getMessage(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}


	private void loadContacts() {
	    List<Contact> newContacts = new ArrayList<>();
	    File file = new File("Contacts.dat");

	    if (!file.exists() || file.length() == 0) {
	        contacts = newContacts; // Empty list if file doesn't exist or is empty
	        return;
	    }

	    try (FileInputStream fis = new FileInputStream(file);
	         ObjectInputStream ois = new ObjectInputStream(fis)) {

	        while (true) {
	            try {
	                Contact c = (Contact) ois.readObject();
	                newContacts.add(c);
	            } catch (EOFException eof) {
	                break; // End of file
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    contacts = newContacts;
	}
}