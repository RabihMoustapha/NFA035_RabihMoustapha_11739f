package controllers;

import Models.Contact;
import views.NewContactView;
import Models.PhoneNumber;
import javax.swing.*;
import java.awt.event.*;

public class NewContactController {
	private NewContactView view;
	private Contact newContact;

	public NewContactController(NewContactView view) {
		this.view = view;
	}

	public ActionListener addNumberToList() {
		return e -> {
			PhoneNumber phone = new PhoneNumber(Integer.parseInt(view.regionCodeInput.getText().trim()), Integer.parseInt(view.phoneInput.getText().trim()));
			view.phoneNumbersModel.addElement(phone);
			view.regionCodeInput.setText("");
			view.phoneInput.setText("");
		};
	}

	public ActionListener saveContact() {
		return e -> {
			String first = view.firstNameField.getText().trim();
			String last = view.lastNameField.getText().trim();
			if (first.isEmpty() || last.isEmpty() || view.phoneNumbersModel.getSize() == 0) {
				JOptionPane.showMessageDialog(view, "Name and at least one phone number are required.");
				return;
			}

			newContact = new Contact(first, last, view.cityField.getText().trim());
			for (int i = 0; i < view.phoneNumbersModel.size(); i++) {
				addNumberToList();
			}

			JOptionPane.showMessageDialog(view, "Contact saved.");
			view.dispose();
		};
	}
}
