package Controllers;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import Helpers.ContactsHelper;
import Helpers.GroupsHelper;
import Models.Contact;
import Models.Groupe;
import Views.AddContactsFrame;
import Views.ContactsFrame;
import Views.MainFrame;
import Views.GroupsFrame;

public class MainController {
	private MainFrame view;
	private Contact c;

	public MainController(MainFrame view) {
		this.view = view;
		initController();
	}

	private void initController() {
		view.getContactsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openContactsWindow();
			}
		});

		view.getGroupsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openGroupsWindow();
			}
		});
	}

	private void openContactsWindow() {
		ContactsFrame contacts = new ContactsFrame(c);
		AddContactsFrame addContacts = new AddContactsFrame();
	}

	private void openGroupsWindow() {
		GroupsFrame g = new GroupsFrame();
	}
}