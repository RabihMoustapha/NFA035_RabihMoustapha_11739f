package views;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import Models.Contact;
import Models.Group;

public class GroupsView extends JFrame {
    private Group g;
    private List<Group> groups = new ArrayList<>();
    private DefaultListModel<Group> groupModel = new DefaultListModel<>();
    private JList<Group> groupList = new JList<>(groupModel);
    private JButton addContactButton = new JButton("Add Contact to Group");
    private JButton addGroupButton = new JButton("Add New Group");
    private JButton updateGroupButton = new JButton("Update Group");
    private JButton deleteGroupButton = new JButton("Delete Group");
    public JButton viewGroup = new JButton("View Group");
    private JTextField searchField = new JTextField(20);

    public GroupsView(Group g) {
        this.g = g;
        setTitle("Groups");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Groups:"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(addContactButton);
        bottomPanel.add(addGroupButton);
        bottomPanel.add(updateGroupButton);
        bottomPanel.add(deleteGroupButton);
        bottomPanel.add(viewGroup);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadGroupData();
        displayData();

        addContactButton.addActionListener(e -> addContactToGroup());
        addGroupButton.addActionListener(e -> new NewGroupView(g));
        updateGroupButton.addActionListener(e -> updateGroup(g));
        deleteGroupButton.addActionListener(e -> deleteGroup(g));
        viewGroup.addActionListener(e -> viewGroup());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { searchGroups(); }
            public void removeUpdate(DocumentEvent e) { searchGroups(); }
            public void insertUpdate(DocumentEvent e) { searchGroups(); }
        });

        setVisible(true);
    }

    private void addContactToGroup() {
        int index = groupList.getSelectedIndex();
        if (index >= 0) {
            Group selectedGroup = groupModel.get(index);
            List<Contact> allContacts = new ArrayList<>();
            File file = new File("Contacts.dat");

            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    while (true) {
                        try {
                            Contact contact = (Contact) ois.readObject();
                            allContacts.add(contact);
                        } catch (EOFException eof) {
                            break;
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to load contacts: " + ex.getMessage());
                    return;
                }
            }

            Contact selectedContact = (Contact) JOptionPane.showInputDialog(
                this,
                "Select a contact to add:",
                "Add Contact to Group",
                JOptionPane.PLAIN_MESSAGE,
                null,
                allContacts.toArray(),
                null);

            if (selectedContact != null) {
                selectedGroup.ajouterContact(selectedContact);
                saveGroups();
                JOptionPane.showMessageDialog(this, "Contact added to group.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a group first.");
        }
    }

    private void searchGroups() {
        String query = searchField.getText().toLowerCase().trim();
        groupModel.clear();
        if (query.isEmpty()) {
            displayData();
            return;
        }

        for (Group group : groups) {
            if (group.getNom().toLowerCase().contains(query) || group.getDescription().toLowerCase().contains(query)) {
                groupModel.addElement(group);
            }
        }
    }

    private void loadGroupData() {
        groups.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))) {
            while (true) {
                try {
                    Group group = (Group) ois.readObject();
                    groups.add(group);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load groups: " + e.getMessage());
        }
    }

    private void displayData() {
        groupModel.clear();
        groups.forEach(groupModel::addElement);
    }

    private void updateGroup(Group g) {
        int index = groupList.getSelectedIndex();
        if (index >= 0) {
            Group selected = groupModel.get(index);
            new GroupUpdateView(selected);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a group to update");
        }
    }

    private void deleteGroup(Group g) {
        int index = groupList.getSelectedIndex();
        if (index >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this group?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                groupModel.remove(index);
                groups.remove(index);
                saveGroups();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a group to delete");
        }
    }

    private void saveGroups() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
            for (Group group : groups) {
                oos.writeObject(group);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewGroup() {
        Group selected = groupList.getSelectedValue();
        if (selected != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Group Name: ").append(selected.getNom()).append("\n");
            sb.append("Description: ").append(selected.getDescription()).append("\n\n");
            sb.append("Contacts:\n");

            List<Contact> contacts = selected.getContacts();
            if (contacts.isEmpty()) {
                sb.append("No contacts in this group.");
            } else {
                for (Contact contact : contacts) {
                    sb.append("- ").append(contact.getNom()).append(" ").append(contact.getPrenom()).append("\n");
                }
            }

            JTextArea textArea = new JTextArea(sb.toString(), 20, 40);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(this, scrollPane, "Group Details", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "Please select a group to view", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}