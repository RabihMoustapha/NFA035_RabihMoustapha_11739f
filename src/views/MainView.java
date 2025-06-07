package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Models.Contact;
import Models.Group;
import views.ContactsView;
import views.NewContactView;
import views.NewGroupView;

public class MainView extends JFrame {
	private JButton contactsButton = new JButton("Contacts");
	private JButton groupsButton = new JButton("Groups");

	public MainView(Contact c) {
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
				Contact c = new Contact();
				ContactsView parent = new ContactsView(c);
				new NewContactView(c, parent);
			};
		});

		groupsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group g = new Group();
				GroupsView parent = new GroupsView(g);
				new NewGroupView(g, parent);
			}
		});
	}

	public static void main(String[] args) {
		Contact c = new Contact();
		MainView mainView = new MainView(c);
		mainView.setVisible(true);
	}
}