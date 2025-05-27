package controllers;

import views.MainView;
import views.ContactsView;
import views.GroupsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;

        this.view.contactsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ContactsView().setVisible(true);
            }
        });

        this.view.groupsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GroupsView().setVisible(true);
            }
        });
    }
}
