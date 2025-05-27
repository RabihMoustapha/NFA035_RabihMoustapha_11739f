package controllers;

import Models.Contact;
import views.ContactsView;
import views.NewContactView;
import views.UpdateContactView;
import views.ViewContactView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ContactsController {
    private ContactsView view;
    private ArrayList<Contact> contacts;

    public ContactsController(ContactsView view, ArrayList<Contact> contacts) {
        this.view = view;
        this.contacts = contacts;

        refreshList();

        view.addNewContact.addActionListener(e -> new NewContactView().setVisible(true));
        view.updateContact.addActionListener(e -> new UpdateContactView().setVisible(true));
        view.viewContact.addActionListener(e -> new ViewContactView().setVisible(true));
        view.deleteContact.addActionListener(e -> deleteSelectedContact());
        view.sortByFirstName.addActionListener(e -> sortAndDisplay("first"));
        view.sortByLastName.addActionListener(e -> sortAndDisplay("last"));
        view.sortByCity.addActionListener(e -> sortAndDisplay("city"));

        view.searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterContacts(view.searchField.getText().toLowerCase());
            }
        });
    }

    private void refreshList() {
        view.listModel.clear();
        for (Contact c : contacts) {
            view.listModel.addElement(c.getPrenom() + " " + c.getNom());
        }
    }

    private void deleteSelectedContact() {
        int index = view.contactsList.getSelectedIndex();
        if (index >= 0) {
            contacts.remove(index);
            refreshList();
        } else {
            JOptionPane.showMessageDialog(view, "Please select a contact to delete.");
        }
    }

    private void sortAndDisplay(String criteria) {
        contacts.sort((c1, c2) -> {
            switch (criteria) {
                case "first": return c1.getPrenom().compareToIgnoreCase(c2.getPrenom());
                case "last": return c1.getNom().compareToIgnoreCase(c2.getNom());
                case "city": return c1.getVille().compareToIgnoreCase(c2.getVille());
                default: return 0;
            }
        });
        refreshList();
    }

    private void filterContacts(String prefix) {
        view.listModel.clear();
        for (Contact c : contacts) {
            if (c.getNom().toLowerCase().startsWith(prefix)) {
                view.listModel.addElement(c.getPrenom() + " " + c.getNom());
            }
        }
    }
}
