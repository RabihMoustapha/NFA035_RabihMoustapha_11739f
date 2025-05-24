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
	private Contact c;
	private ContactsHelper helper = new ContactsHelper();

	public ContactController(DefaultListModel<Contact> listModel, JList<Contact> contactList, Contact c) {
		this.listModel = listModel;
		this.contactList = contactList;
		this.c = c;
	}

	public ActionListener getSortByFirstNameListener() {
		return e -> helper.sortContacts(Comparator.comparing(Contact::getPrenom), listModel);
	}

	public ActionListener getSortByLastNameListener() {
		return e -> helper.sortContacts(Comparator.comparing(Contact::getNom), listModel);
	}

	public ActionListener getSortByCityListener() {
		return e -> helper.sortContacts(Comparator.comparing(Contact::getVille), listModel);
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
					JOptionPane.showMessageDialog(null, "Détails:\n" + listModel.getElementAt(index), "Fiche contact",
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

					if (newPrenom != null && newNom != null && newVille != null && !newPrenom.trim().isEmpty()
							&& !newNom.trim().isEmpty() && !newVille.trim().isEmpty()) {

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
		return e -> new AddContactsFrame();
	}

	public ActionListener getDeleteListener() {
		return e -> {
			int index = contactList.getSelectedIndex();
			if (index >= 0) {
				int confirm = JOptionPane.showConfirmDialog(null, "Supprimer ce contact ?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					listModel.remove(index);
				}
			} else {
				showSelectionWarning();
			}
		};
	}

	private void showSelectionWarning() {
		JOptionPane.showMessageDialog(null, "Veuillez sélectionner un contact", "Aucune sélection",
				JOptionPane.WARNING_MESSAGE);
	}
}