package controllers;

import Models.Contact;
import Models.Groupe;
import views.GroupsView;
import views.NewGroupView;
import views.UpdateGroupView;
import views.ViewContactView;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GroupsController {
    private GroupsView view;
    private ArrayList<Groupe> groupes;

    public GroupsController(GroupsView view, ArrayList<Groupe> groupes) {
        this.view = view;
        this.groupes = groupes;

        refreshGroupList();

        view.groupList.addListSelectionListener(e -> showGroupContacts());
        view.groupContactsList.addListSelectionListener(e -> showContactDetails());

        view.addGroupButton.addActionListener(e -> new NewGroupView().setVisible(true));
        view.updateGroupButton.addActionListener(e -> new UpdateGroupView().setVisible(true));
        view.deleteGroupButton.addActionListener(e -> deleteSelectedGroup());
    }

    private void refreshGroupList() {
        view.groupModel.clear();
        for (Groupe g : groupes) {
            view.groupModel.addElement(g.getNom());
        }
    }

    private void showGroupContacts() {
        int index = view.groupList.getSelectedIndex();
        view.contactModel.clear();
        if (index >= 0) {
            Groupe g = groupes.get(index);
            for (Contact c : g.getContacts()) {
                view.contactModel.addElement(c.getPrenom() + " " + c.getNom());
            }
        }
    }

    private void showContactDetails() {
        if (!view.groupContactsList.isSelectionEmpty()) {
            new ViewContactView().setVisible(true);
        }
    }

    private void deleteSelectedGroup() {
        int index = view.groupList.getSelectedIndex();
        if (index >= 0) {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure to delete this group?");
            if (confirm == JOptionPane.YES_OPTION) {
                groupes.remove(index);
                refreshGroupList();
                view.contactModel.clear();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select a group to delete.");
        }
    }
}
