package views;

import Models.Contact;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ContactUpdateView extends JFrame {
	private ContactsView parent;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField cityField;
    private JButton saveButton, cancelButton;
    private Contact originalContact;

    public ContactUpdateView(Contact contact, ContactsView parent) {
        this.originalContact = contact;
        this.parent = parent;
        setTitle("Update Contact");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        firstNameField = new JTextField(contact.getPrenom(), 20);
        lastNameField = new JTextField(contact.getNom(), 20);
        cityField = new JTextField(contact.getVille(), 20);

        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        add(formPanel);

        saveButton.addActionListener(e -> updateContact());

        cancelButton.addActionListener(e -> this.dispose());
        
        setVisible(true);
    }

    private void updateContact() {
        String prenom = firstNameField.getText().trim();
        String nom = lastNameField.getText().trim();
        String ville = cityField.getText().trim();

        if (prenom.isEmpty() || nom.isEmpty() || ville.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Contact> contacts = new ArrayList<>();
        File file = new File("Contacts.dat");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Contact c = (Contact) ois.readObject();
                    contacts.add(c);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error reading contacts: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Replace the original contact with updated one
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.equals(originalContact)) {
                contacts.set(i, new Contact(prenom, nom, ville));
                break;
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Contact c : contacts) {
                oos.writeObject(c);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving contact: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Contact updated successfully.");
        parent.loadContacts();
    }
}
