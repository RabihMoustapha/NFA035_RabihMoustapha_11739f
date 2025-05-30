package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Models.Contact;
import views.ContactsView;
import views.NewContactView;

public class MainView extends JFrame {

	public Contact c;
	public JButton contactsButton = new JButton("Contacts");
	public JButton groupsButton = new JButton("Groups");

	public MainView() {
		this.c = null;
		initializeUI();
	}

	public MainView(Contact c) {
		this.c = c;
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Contact Management System");
		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(contactsButton);
		panel.add(groupsButton);

		add(panel);

		contactsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ContactsView(c);
				new NewContactView(c);
			};
		});

		groupsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GroupsView();
			}
		});
	}

	public static void main(String[] args) {
		MainView mainView = new MainView();
		mainView.setVisible(true);
	}
}