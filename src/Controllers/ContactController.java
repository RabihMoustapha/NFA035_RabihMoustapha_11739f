package Controllers;

import Helpers.ContactsHelper;
import Models.Contact;
import Views.AddContactsFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class ContactController {
    private DefaultListModel<Contact> listModel;
    private JList<Contact> contactList;

    public ContactController(DefaultListModel<Contact> listModel, JList<Contact> contactList) {
        this.listModel = listModel;
        this.contactList = contactList;
    }

    public ActionListener getSortByFirstNameListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contactList = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    contactList.add(listModel.getElementAt(i));
                }
                contactList.sort(Comparator.comparing(Contact::getPrenom));
                listModel.clear();
                for (Contact contact : contactList) {
                    listModel.addElement(contact);
                }
            }
        };
    }

    public ActionListener getSortByLastNameListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contactList = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    contactList.add(listModel.getElementAt(i));
                }
                contactList.sort(Comparator.comparing(Contact::getNom));
                listModel.clear();
                for (Contact contact : contactList) {
                    listModel.addElement(contact);
                }
            }
        };
    }

    public ActionListener getSortByCityListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contacts = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    contacts.add(listModel.getElementAt(i));
                }
                contacts.sort(Comparator.comparing(Contact::getVille));
                listModel.clear();
                for (Contact c : contacts) {
                    listModel.addElement(c);
                }
            }
        };
    }

    private void updateListModel(ArrayList<Contact> contacts) {
        listModel.clear();
        contacts.forEach(listModel::addElement);
    }

    public ActionListener getViewListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = contactList.getSelectedIndex();
                if (index >= 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Détails:\n" + listModel.getElementAt(index),
                            "Fiche contact",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    public ActionListener getUpdateListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = contactList.getSelectedIndex();
                if (index >= 0) {
                    Contact selected = listModel.getElementAt(index);

                    String newPrenom = JOptionPane.showInputDialog(null, "Nouveau prénom :", selected.getPrenom());
                    String newNom = JOptionPane.showInputDialog(null, "Nouveau nom :", selected.getNom());
                    String newVille = JOptionPane.showInputDialog(null, "Nouvelle ville :", selected.getVille());

                    if (newPrenom != null && newNom != null && newVille != null
                            && !newPrenom.trim().isEmpty()
                            && !newNom.trim().isEmpty()
                            && !newVille.trim().isEmpty()) {

                        Contact updated = new Contact(newPrenom.trim(), newNom.trim(), newVille.trim());
                        listModel.set(index, updated);
                    }
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    public ActionListener getAddContactListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AddContactsFrame c = new AddContactsFrame();
            }
        };
    }

    public ActionListener getDeleteListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = contactList.getSelectedIndex();
                if (index >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Supprimer ce contact ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        listModel.remove(index);
                    }
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    private void showSelectionWarning() {
        JOptionPane.showMessageDialog(
                null,
                "Veuillez sélectionner un contact",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE);
    }
}