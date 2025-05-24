package Helpers;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Models.Contact;

public class ContactsHelper {
	public Set<Contact> readData(){
		Set<Contact> contacts = new HashSet<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
			Object obj = ois.readObject();
			contacts.addAll((Collection<? extends Contact>) obj);
			ois.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier de contacts.");
		}
		return contacts;
	}
}