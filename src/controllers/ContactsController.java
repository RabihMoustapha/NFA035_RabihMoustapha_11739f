package controllers;

import Models.Contact;
import views.ContactsView;
import views.NewContactView;
import views.UpdateContactView;
import views.ViewContactView;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class ContactsController {
	public Set<Contact> contacts;

	public ContactsController() {
		this.contacts = new HashSet<>();
	}
	
	public void search(String searchField, DefaultListModel<Contact> listModel) {
		filterContacts(searchField.toLowerCase(), listModel);
	}

	public void deleteSelectedContact(int index) {
		if (index >= 0) {
			contacts.remove(index);
		} else {
			JOptionPane.showMessageDialog(null, "Please select a contact to delete.");
		}
	}

	public void sortAndDisplay(String criteria) {
		contacts.sort((c1, c2) -> {
			switch (criteria) {
			case "first":
				return c1.getPrenom().compareToIgnoreCase(c2.getPrenom());
			case "last":
				return c1.getNom().compareToIgnoreCase(c2.getNom());
			case "city":
				return c1.getVille().compareToIgnoreCase(c2.getVille());
			default:
				return 0;
			}
		});
	}

	public void filterContacts(String prefix, DefaultListModel<Contact> listModel) {
		listModel.clear();
		for (Contact c : contacts) {
			if (c.getNom().toLowerCase().startsWith(prefix)) {
				listModel.addElement(c.getPrenom() + " " + c.getNom());
			}
		}
	}
}
