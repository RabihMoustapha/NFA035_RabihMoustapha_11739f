package controllers;

import Models.Contact;
import views.NewContactView;
import Models.PhoneNumber;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import controllers.ContactsController;

public class NewContactController {
	private NewContactView view;
	private Contact newContact;
	private ContactsController ctrl = new ContactsController();

	public NewContactController() {
		initView();
	}

	private void initView() {
		view.phoneNumbersList.setModel(view.phoneNumbersModel);
	}

	public ActionListener addNumberToList() {
		return e -> {
			try {
				String regionCodeText = view.regionCodeInput.getText().trim();
				String phoneText = view.phoneInput.getText().trim();

				if (regionCodeText.isEmpty() || phoneText.isEmpty()) {
					JOptionPane.showMessageDialog(view, "Region code and phone number cannot be empty");
					return;
				}

				int regionCode = Integer.parseInt(regionCodeText);
				int phoneNumber = Integer.parseInt(phoneText);

				PhoneNumber phone = new PhoneNumber(regionCode, phoneNumber);
				view.phoneNumbersModel.addElement(phone);

				view.regionCodeInput.setText("");
				view.phoneInput.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(view, "Please enter valid numbers for region code and phone number");
			}
		};
	}

	public ActionListener saveContact() {
		return e -> {
			String first = view.firstNameField.getText().trim();
			String last = view.lastNameField.getText().trim();

			if (first.isEmpty() || last.isEmpty() || view.phoneNumbersModel.getSize() == 0) {
				JOptionPane.showMessageDialog(view,
						"First name, last name and at least one phone number are required.");
				return;
			}

			newContact = new Contact(first, last, view.cityField.getText().trim());
			newContact.addPhoneNumber(view.phoneNumbersModel.getElementAt(0));

			ctrl.contacts.add(newContact);

			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat"));
				oos.writeObject(newContact);
				oos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			JOptionPane.showMessageDialog(view, "Contact saved successfully!");
			view.dispose();
		};
	}
}
