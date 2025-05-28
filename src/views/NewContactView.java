package views;

import javax.swing.*;
import java.awt.*;
import Models.PhoneNumber;
import controllers.NewContactController;

public class NewContactView extends JFrame {
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

	public NewContactView() {
		setTitle("New Contact");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JPanel form = new JPanel(new GridLayout(0, 1));
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
		form.add(new JScrollPane(phoneNumbersList));
		form.add(new JLabel("Group:"));
		form.add(groupsComboBox);
		form.add(saveButton);
		
		NewContactController ctrl = new NewContactController(this);
		addPhoneButton.addActionListener(e -> ctrl.addNumberToList());
		saveButton.addActionListener(e -> ctrl.saveContact());
		
		add(form);
	}
}
