package controllers;

import Models.Contact;
import views.NewContactView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewContactController {
    private NewContactView view;
    private Contact newContact;

    public NewContactController(NewContactView view) {
        this.view = view;

        view.addPhoneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phone = view.phoneInput.getText().trim();
                if (!phone.isEmpty()) {
                    view.phoneNumbersModel.addElement(phone);
                    view.phoneInput.setText("");
                }
            }
        });

        view.saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String first = view.firstNameField.getText().trim();
                String last = view.lastNameField.getText().trim();
                if (first.isEmpty() || last.isEmpty() || view.phoneNumbersModel.getSize() == 0) {
                    JOptionPane.showMessageDialog(view, "Name and at least one phone number are required.");
                    return;
                }

                newContact = new Contact(first, last, view.cityField.getText().trim());
                for (int i = 0; i < view.phoneNumbersModel.size(); i++) {
                    newContact.ajouterNumero(view.phoneNumbersModel.get(i));
                }

                JOptionPane.showMessageDialog(view, "Contact saved.");
                view.dispose();
            }
        });

        view.cancelButton.addActionListener(e -> view.dispose());
    }
}
