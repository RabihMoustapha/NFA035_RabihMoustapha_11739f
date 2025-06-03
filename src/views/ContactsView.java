package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;

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
                new NewContactView(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to update");
            }
        });

        sortByFirstName.addActionListener(e -> {
            List<Contact> sorted = new ArrayList<>(contacts);
            sorted.sort(Comparator.comparing(Contact::getPrenom));
            updateListModel(sorted);
        });

        sortByLastName.addActionListener(e -> {
            List<Contact> sorted = new ArrayList<>(contacts);
            sorted.sort(Comparator.comparing(Contact::getNom));
            updateListModel(sorted);
        });

        sortByCity.addActionListener(e -> {
            List<Contact> sorted = new ArrayList<>(contacts);
            sorted.sort(Comparator.comparing(Contact::getVille));
            updateListModel(sorted);
        });

        setVisible(true);
    }

    private void loadContacts() {
        listModel.clear();
        contacts.clear();

        File file = new File("Contacts.dat");
        if (!file.exists()) {
            return; // No contacts to load
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Contact contact = (Contact) ois.readObject();
                    contacts.add(contact);
                    listModel.addElement(contact);
                } catch (EOFException eof) {
                    break; // reached end of file
                }
            }
        } catch (IOException | ClassNotFoundException ioe) {
            JOptionPane.showMessageDialog(this, "Error loading contacts: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateListModel(List<Contact> newContacts) {
        listModel.clear();
        newContacts.forEach(listModel::addElement);
    }

    private void deleteSelectedContact() {
        Contact selected = contactsList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Delete " + selected.getNom() + "?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                contacts.remove(selected);
                updateListModel(contacts);
                // Overwrite the file with updated contacts
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"))) {
                    for (Contact contact : contacts) {
                        oos.writeObject(contact);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting contact: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete");
        }
    }

    private void viewContact() {
        Contact selected = contactsList.getSelectedValue();
        if (selected != null) {
            JOptionPane.showMessageDialog(this,
                    "First Name: " + selected.getPrenom() + "\n" +
                    "Last Name: " + selected.getNom() + "\n" +
                    "City: " + selected.getVille());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to view");
        }
    }
}