package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Models.Contact;
import Models.PhoneNumber;

public class AddContactsFrame extends JFrame {
	private JTextField nomField;
	private JTextField prenomField;
	private JTextField villeField;
	private JTextField phoneField;
	private JComboBox<String> phoneTypeCombo;
	private JButton addButton;
	private JButton clearButton;
	private JPanel mainPanel;

	public AddContactsFrame() {
		setContentPane(mainPanel);
		setTitle("Add New Contact");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		addButton.addActionListener(this::saveContact);
		clearButton.addActionListener(e -> clearFields());

		setVisible(true);
	}

	private void saveContact(ActionEvent e) {
		String nom = nomField.getText().trim();
		String prenom = prenomField.getText().trim();
		String ville = villeField.getText().trim();
		String phoneNumber = phoneField.getText().trim();

		if (nom.isEmpty() || prenom.isEmpty() || phoneNumber.isEmpty()) {
			showError("Nom, Prénom, and Phone Number are required!");
			return;
		}

		if (!phoneNumber.matches("\\d+")) {
			showError("Phone number should contain only digits!");
			return;
		}

		try {
			Contact newContact = new Contact(nom, prenom, ville);
			saveContactToFile(newContact);
			showSuccess("Contact saved successfully!");
			clearFields();
		} catch (Exception ex) {
			showError("Error saving contact: " + ex.getMessage());
		}
	}

//    private List<Contact> loadExistingContacts() {
//        File file = new File(FILE_NAME);
//        if (!file.exists()) return new ArrayList<>();
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
//            return (List<Contact>) ois.readObject();
//        } catch (Exception e) {
//            showWarning("Couldn't load contacts, starting fresh");
//            return new ArrayList<>();
//        }
//    }

	private void saveContactToFile(Contact contacts) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {
			oos.writeObject(contacts);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearFields() {
		nomField.setText("");
		prenomField.setText("");
		villeField.setText("");
		phoneField.setText("");
		nomField.requestFocus();
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showSuccess(String message) {
		JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showWarning(String message) {
		JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(AddContactsFrame::new);
	}
}