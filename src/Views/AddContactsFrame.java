package Views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Models.Contact;
import java.io.*;

public class AddContactsFrame extends JFrame {
	private JTextField nomField;
	private JTextField prenomField;
	private JTextField villeField;
	private JTextField phoneField;
	private JButton addButton;

	public AddContactsFrame() {
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Ajouter un Contact");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Main panel with BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		mainPanel.setBackground(Color.WHITE);

		// Title panel
		JLabel titleLabel = new JLabel("Ajouter un nouveau contact", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		mainPanel.add(titleLabel, BorderLayout.NORTH);

		// Form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBorder(BorderFactory.createTitledBorder("Informations du contact"));
		formPanel.setBackground(new Color(245, 245, 245));

		// Fields with labels
		JPanel fieldPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		fieldPanel.setBackground(new Color(245, 245, 245));

		nomField = createStyledTextField();
		prenomField = createStyledTextField();
		villeField = createStyledTextField();
		phoneField = createStyledTextField();

		fieldPanel.add(createStyledLabel("Nom :"));
		fieldPanel.add(nomField);
		fieldPanel.add(createStyledLabel("Prénom :"));
		fieldPanel.add(prenomField);
		fieldPanel.add(createStyledLabel("Ville :"));
		fieldPanel.add(villeField);
		fieldPanel.add(createStyledLabel("Téléphone :"));
		fieldPanel.add(phoneField);

		formPanel.add(Box.createVerticalStrut(10));
		formPanel.add(fieldPanel);
		formPanel.add(Box.createVerticalStrut(10));

		mainPanel.add(formPanel, BorderLayout.CENTER);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(Color.WHITE);

		addButton = createStyledButton("Ajouter", new Color(70, 130, 180));

		buttonPanel.add(addButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Add action listeners
		addButton.addActionListener(e -> saveContact());

		setContentPane(mainPanel);
		setVisible(true);
	}

	private JLabel createStyledLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		return label;
	}

	private JTextField createStyledTextField() {
		JTextField field = new JTextField();
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		return field;
	}

	private JButton createStyledButton(String text, Color bgColor) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setBackground(bgColor);
		button.setForeground(bgColor.equals(new Color(220, 220, 220)) ? Color.BLACK : Color.WHITE);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(120, 30));
		return button;
	}

	private ActionListener saveContact() {
		return e -> {
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
		};
	}

	private void saveContactToFile(Contact contacts) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {
			oos.writeObject(contacts);
			oos.writeUTF("\n");
			JOptionPane.showConfirmDialog(addButton, oos);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ActionListener clearFields() {
		return e -> {
			nomField.setText("");
			prenomField.setText("");
			villeField.setText("");
			phoneField.setText("");
			nomField.requestFocus();
		};
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
}