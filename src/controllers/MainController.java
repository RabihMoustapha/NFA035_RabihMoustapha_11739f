package controllers;

import views.MainView;
import views.ContactsView;
import views.GroupsView;
import views.NewContactView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
	private MainView view;

	public MainController(MainView view) {
		this.view = view;

		this.view.contactsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ContactsView();
				new NewContactView();
			};
		});

		this.view.groupsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GroupsView();
			}
		});
	}
}
