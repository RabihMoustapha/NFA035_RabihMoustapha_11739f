package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Models.PhoneNumber;
import java.io.*;
import Models.Contact;

public class NewContactView extends JFrame {
	public Contact c;
	public JTextField firstNameField = new JTextField(15);
	public JTextField lastNameField = new JTextField(15);
	public JTextField cityField = new JTextField(15);
	public DefaultListModel<PhoneNumber> phoneNumbersModel = new DefaultListModel<>();
	public JList<PhoneNumber> phoneNumbersList = new JList<>();
	public JTextField phoneInput = new JTextField(15);
	public JTextField regionCodeInput = new JTextField(15);
	public JButton addPhoneButton = new JButton("Add Phone");
	public JComboBox<String> groupsComboBox = new JComboBox<>();
	public JButton saveButton = new JButton("Save");

	public NewContactView(Contact c) {
		this.c = c;
		setTitle("New Contact");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
		form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Form fields
		form.add(new JLabel("First Name:"));
		form.add(firstNameField);
		form.add(new JLabel("Last Name:"));
		form.add(lastNameField);
		form.add(new JLabel("City:"));
		form.add(cityField);
		form.add(new JLabel("Region Code:"));
		form.add(regionCodeInput);
		form.add(new JLabel("Phone Number:"));
		form.add(phoneInput);
		form.add(addPhoneButton);
		form.add(new JLabel("Phone Numbers:"));
		form.add(new JScrollPane(phoneNumbersList));
		form.add(new JLabel("Group:"));
		form.add(groupsComboBox);

		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(saveButton);
		form.add(buttonPanel);

		// Initialize controller
		addPhoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.addPhoneNumber(new PhoneNumber(Integer.parseInt(regionCodeInput.getText()), Integer.parseInt(phoneInput.getText())));
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		add(form);
	}

	public void clearFields() {
		firstNameField.setText("");
		lastNameField.setText("");
		cityField.setText("");
		regionCodeInput.setText("");
		phoneInput.setText("");
		phoneNumbersModel.clear();
	}
}
