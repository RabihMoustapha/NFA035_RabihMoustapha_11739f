package Views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import Helpers.GroupsHelper;
import Models.Contact;
import Models.Groupe;
import Models.PhoneNumber;

public class AddContactsFrame extends JFrame {
	private JButton addContactButton;
	private JButton backButton;
	private JTable contactsTable;

	public AddContactsFrame() {
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Gestion des contacts - Nouveau Contact");
		setSize(500, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel titleLabel = new JLabel("Ajouter un nouveau contact");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(10));

		JPanel namePanel = new JPanel(new GridLayout(2, 2, 10, 5));
		namePanel.setBorder(BorderFactory.createTitledBorder("Nom du contact"));

		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		namePanel.add(new JLabel("Prénom :"));
		namePanel.add(firstNameField);
		namePanel.add(new JLabel("Nom :"));
		namePanel.add(lastNameField);
		mainPanel.add(namePanel);
		mainPanel.add(Box.createVerticalStrut(10));

		JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de contact"));

		JTextField cityField = new JTextField();
		JTextField regionCodeField = new JTextField();
		JTextField phoneNumberField = new JTextField();
		infoPanel.add(new JLabel("Ville :"));
		infoPanel.add(cityField);
		infoPanel.add(new JLabel("Code Région :"));
		infoPanel.add(regionCodeField);
		infoPanel.add(new JLabel("Téléphone :"));
		infoPanel.add(phoneNumberField);
		mainPanel.add(infoPanel);
		mainPanel.add(Box.createVerticalStrut(10));

		JPanel groupPanel = new JPanel();
		groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
		groupPanel.setBorder(BorderFactory.createTitledBorder("Groupes"));
		Set<Groupe> groups = new HashSet<>();
		GroupsHelper helper = new GroupsHelper();
		JCheckBox[] groupBoxes;
		
		groups = helper.loadGroupsFromFile();
		
		groupBoxes = new JCheckBox[groups.size()];

		int i = 0;
		for (Groupe group : groups) {
			groupBoxes[i] = new JCheckBox(group.getNom());
			groupPanel.add(groupBoxes[i]);
			i++;
		}

		mainPanel.add(groupPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton saveButton = new JButton("Enregistrer");
		JButton cancelButton = new JButton("Annuler");

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String firstName = firstNameField.getText().trim();
					String lastName = lastNameField.getText().trim();
					String city = cityField.getText().trim();
					int regionCode = Integer.parseInt(regionCodeField.getText().trim());
					int phoneNumber = Integer.parseInt(phoneNumberField.getText().trim());
					if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty()) {
						throw new IllegalArgumentException("Tous les champs doivent être remplis.");
					}
					Contact c = new Contact(firstName, lastName, city);
					PhoneNumber number = new PhoneNumber(regionCode, phoneNumber);
					c.addPhoneNumber(number);
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {

					} catch (EOFException eof) {
						JOptionPane.showMessageDialog(null, "Le fichier est vide.");
					} catch (Exception readEx) {
						readEx.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier.");
						return;
					}
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {

					}
					JOptionPane.showMessageDialog(null, "Contact enregistré avec succès !");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Le code région et le numéro doivent être des entiers.");
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement.");
				}
			}
		});

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(buttonPanel);
		setContentPane(mainPanel);
		setVisible(true);
	}
}