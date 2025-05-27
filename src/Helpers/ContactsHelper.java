package Helpers;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Models.Contact;

public class ContactsHelper {
	public Set<Contact> readData(DefaultListModel listModel) {
		Set<Contact> contacts = new HashSet<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
			// Read object from file
			Contact obj = (Contact)ois.readObject();
			contacts.add(obj);

			// Refresh page with this function
			addDataToList(contacts, listModel);
			ois.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier de contacts.");
		}
		return contacts;
	}

	public void sortContacts(Comparator<Contact> comparator, DefaultListModel listModel) {
		Contact[] contacts = new Contact[listModel.getSize()];
		listModel.copyInto(contacts);

		// Sort the array
		Arrays.sort(contacts, comparator);

		// Update the listModel
		listModel.clear();
		for (Contact contact : contacts) {
			listModel.addElement(contact);
		}
	}

	private void addDataToList(Set<Contact> contacts, DefaultListModel listModel) {
		for (Contact c : contacts) {
			listModel.addElement(c);
		}
	}
}