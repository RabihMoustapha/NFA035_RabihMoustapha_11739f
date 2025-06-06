//package views;
//
//import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.*;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//import java.awt.event.*;
//import Models.Contact;
//import Models.Group;
//
//public class GroupsView extends JFrame {
//    private Group g;
//    private List<Group> groups = new ArrayList<>();
//    private DefaultListModel<Group> groupModel = new DefaultListModel<>();
//    private JList<Group> groupList = new JList<>(groupModel);
//    private JButton addGroupButton = new JButton("Add New Group");
//    private JButton updateGroupButton = new JButton("Update Group");
//    private JButton deleteGroupButton = new JButton("Delete Group");
//    public JButton viewGroup = new JButton("View Group");
//    private JTextField searchField = new JTextField(20);
//
//    public GroupsView(Group g) {
//        this.g = g;
//        setTitle("Groups");
//        setSize(500, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel topPanel = new JPanel(new FlowLayout());
//        topPanel.add(new JLabel("Search:"));
//        topPanel.add(searchField);
//
//        JPanel leftPanel = new JPanel(new BorderLayout());
//        leftPanel.add(new JLabel("Groups:"), BorderLayout.NORTH);
//        leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);
//
//        JPanel bottomPanel = new JPanel(new FlowLayout());
//        bottomPanel.add(addGroupButton);
//        bottomPanel.add(updateGroupButton);
//        bottomPanel.add(deleteGroupButton);
//        bottomPanel.add(viewGroup);
//
//        setLayout(new BorderLayout());
//        add(topPanel, BorderLayout.NORTH);
//        add(leftPanel, BorderLayout.CENTER);
//        add(bottomPanel, BorderLayout.SOUTH);
//
//        loadGroupData();
//        displayData();
//
//        addGroupButton.addActionListener(e -> new NewGroupView(g));
//        updateGroupButton.addActionListener(e -> updateGroup(g));
//        deleteGroupButton.addActionListener(e -> deleteGroup(g));
//        viewGroup.addActionListener(e -> viewGroup());
//
//        searchField.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) { searchGroups(); }
//            public void removeUpdate(DocumentEvent e) { searchGroups(); }
//            public void insertUpdate(DocumentEvent e) { searchGroups(); }
//        });
//
//        setVisible(true);
//    }
//
//    private void addContactToGroup() {
//        int index = groupList.getSelectedIndex();
//        if (index >= 0) {
//            Group selectedGroup = groupModel.get(index);
//            List<Contact> allContacts = new ArrayList<>();
//            File file = new File("Contacts.dat");
//
//            if (file.exists()) {
//                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//                    while (true) {
//                        try {
//                            Contact contact = (Contact) ois.readObject();
//                            allContacts.add(contact);
//                        } catch (EOFException eof) {
//                            break;
//                        }
//                    }
//                } catch (IOException | ClassNotFoundException ex) {
//                    JOptionPane.showMessageDialog(this, "Failed to load contacts: " + ex.getMessage());
//                    return;
//                }
//            }
//
//            Contact selectedContact = (Contact) JOptionPane.showInputDialog(
//                this,
//                "Select a contact to add:",
//                "Add Contact to Group",
//                JOptionPane.PLAIN_MESSAGE,
//                null,
//                allContacts.toArray(),
//                null);
//
//            if (selectedContact != null) {
//                selectedGroup.ajouterContact(selectedContact);
//                saveGroups();
//                JOptionPane.showMessageDialog(this, "Contact added to group.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Please select a group first.");
//        }
//    }
//
//    private void searchGroups() {
//        String query = searchField.getText().toLowerCase().trim();
//        groupModel.clear();
//        if (query.isEmpty()) {
//            displayData();
//            return;
//        }
//
//        for (Group group : groups) {
//            if (group.getNom().toLowerCase().contains(query) || group.getDescription().toLowerCase().contains(query)) {
//                groupModel.addElement(group);
//            }
//        }
//    }
//
//    private void loadGroupData() {
//        groups.clear();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))) {
//            while (true) {
//                try {
//                    Group group = (Group) ois.readObject();
//                    groups.add(group);
//                } catch (EOFException eof) {
//                    break;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Could not load groups: " + e.getMessage());
//        }
//    }
//
//    private void displayData() {
//        groupModel.clear();
//        groups.forEach(groupModel::addElement);
//    }
//
//    private void updateGroup(Group g) {
//        int index = groupList.getSelectedIndex();
//        if (index >= 0) {
//            Group selected = groupModel.get(index);
//            new GroupUpdateView(selected);
//        } else {
//            JOptionPane.showMessageDialog(this, "Please select a group to update");
//        }
//    }
//
//    private void deleteGroup(Group g) {
//        int index = groupList.getSelectedIndex();
//        if (index >= 0) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this group?",
//                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
//            if (confirm == JOptionPane.YES_OPTION) {
//                groupModel.remove(index);
//                groups.remove(index);
//                saveGroups();
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Please select a group to delete");
//        }
//    }
//
//    private void saveGroups() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
//            for (Group group : groups) {
//                oos.writeObject(group);
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void viewGroup() {
//        Group selected = groupList.getSelectedValue();
//        if (selected != null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Group Name: ").append(selected.getNom()).append("\n");
//            sb.append("Description: ").append(selected.getDescription()).append("\n\n");
//            sb.append("Contacts:\n");
//
//            List<Contact> contacts = selected.getContacts();
//            if (contacts.isEmpty()) {
//                sb.append("No contacts in this group.");
//            } else {
//                for (Contact contact : contacts) {
//                    sb.append("- ").append(contact.getNom()).append(" ").append(contact.getPrenom()).append("\n");
//                }
//            }
//
//            JTextArea textArea = new JTextArea(sb.toString(), 20, 40);
//            textArea.setEditable(false);
//            JScrollPane scrollPane = new JScrollPane(textArea);
//            JOptionPane.showMessageDialog(this, scrollPane, "Group Details", JOptionPane.INFORMATION_MESSAGE);
//
//        } else {
//            JOptionPane.showMessageDialog(this, "Please select a group to view", "No Selection", JOptionPane.WARNING_MESSAGE);
//        }
//    }
//}

package views;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;
import Models.Group;
import Models.PhoneNumber;

public class GroupsView extends JFrame {
	public List<Group> groups;
	public Group g;

	public JButton addGroupButton = new JButton("Add New Group");
	public JButton updateGroupButton = new JButton("Update Group");
	public JButton deleteGroupButton = new JButton("Delete Group");
	public JButton viewGroupButton = new JButton("View Group");

	public JTextField searchField = new JTextField(20);
	public DefaultListModel<Group> groupModel = new DefaultListModel<>();
	public JList<Group> groupList = new JList<>(groupModel);

	public GroupsView(Group g) {
		this.g = g;
		groups = new ArrayList<>();
		loadGroups();

		setTitle("Groups");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(new JLabel("Search:"));
		topPanel.add(searchField);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(addGroupButton);
		buttonPanel.add(updateGroupButton);
		buttonPanel.add(deleteGroupButton);
		buttonPanel.add(viewGroupButton);

		JScrollPane scrollPane = new JScrollPane(groupList);

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		updateListModel(groups);

		addGroupButton.addActionListener(e -> new NewGroupView(new Group()));

		updateGroupButton.addActionListener(e -> updateSelectedGroup());

		deleteGroupButton.addActionListener(e -> deleteSelectedGroup());

		viewGroupButton.addActionListener(e -> viewSelectedGroup());

		searchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				searchGroups();
			}

			public void removeUpdate(DocumentEvent e) {
				searchGroups();
			}

			public void insertUpdate(DocumentEvent e) {
				searchGroups();
			}
		});

		setVisible(true);
	}

	private void searchGroups() {
		String query = searchField.getText().toLowerCase().trim();
		if (query.isEmpty()) {
			updateListModel(groups);
			return;
		}

		List<Group> filtered = new ArrayList<>();
		for (Group group : groups) {
			if (group.getNom().toLowerCase().contains(query) || group.getDescription().toLowerCase().contains(query)) {
				filtered.add(group);
			}
		}
		updateListModel(filtered);
	}

	private void loadGroups() {
		groups.clear();
		File file = new File("Groups.dat");
		if (!file.exists() || file.length() == 0) {
			return; // no groups yet
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			while (true) {
				try {
					Group group = (Group) ois.readObject();
					groups.add(group);
				} catch (EOFException eof) {
					break;
				}
			}
		} catch (IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "Error loading groups: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateListModel(List<Group> newGroups) {
		groupModel.clear();
		if (newGroups != null && !newGroups.isEmpty()) {
			newGroups.forEach(groupModel::addElement);
		}
	}

	private void updateSelectedGroup() {
		Group selected = groupList.getSelectedValue();
		if (selected != null) {
			new GroupUpdateView(selected);
		} else {
			JOptionPane.showMessageDialog(this, "Please select a group to update");
		}
	}

	private void deleteSelectedGroup() {
		Group selected = groupList.getSelectedValue();
		if (selected != null) {
			int confirm = JOptionPane.showConfirmDialog(this, "Delete group \"" + selected.getNom() + "\"?",
					"Confirm Delete", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				groups.remove(selected);
				updateListModel(groups);
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
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error saving groups: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void viewSelectedGroup() {
		Group selected = groupList.getSelectedValue();
		if (selected != null) {
			JDialog dialog = new JDialog(this, "Contact Details", true);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(this);
			dialog.setLayout(new BorderLayout());

			// Info at top
			JTextArea infoArea = new JTextArea();
			infoArea.setEditable(false);
			infoArea.setText("Name: " + selected.getNom() + "\n" + "Description: " + selected.getDescription() + "\n\n"
					+ "Contacts:\n");

			List<Contact> contacts = selected.getContacts();
			for (Contact c : contacts) {
				infoArea.append(c.toString() + "\n");
			}

			dialog.add(new JScrollPane(infoArea), BorderLayout.CENTER);

			// Button panel
			JPanel buttonPanel = new JPanel(new FlowLayout());
			JButton addContactBtn = new JButton("Add Contact");
			JButton updateContactBtn = new JButton("Update Contact");
			JButton removeContactBtn = new JButton("Remove Contact");

			// Add Contact
			addContactBtn.addActionListener(e -> {
				// Load contacts from Contacts.dat
				List<Contact> allContacts = new ArrayList<>();
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
					while (true) {
						try {
							Contact contact = (Contact) ois.readObject();
							allContacts.add(contact);
						} catch (EOFException eof) {
							break; // End of file reached
						}
					}
				} catch (IOException | ClassNotFoundException ex) {
					JOptionPane.showMessageDialog(dialog, "Error loading contacts: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (allContacts.isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "No contacts found in file.");
					return;
				}

				// Prepare checkboxes for each contact
				JCheckBox[] checkBoxes = new JCheckBox[allContacts.size()];
				JPanel panel = new JPanel(new GridLayout(0, 1));
				for (int i = 0; i < allContacts.size(); i++) {
					Contact c = allContacts.get(i);
					checkBoxes[i] = new JCheckBox(c.toString());
					panel.add(checkBoxes[i]);
				}

				int result = JOptionPane.showConfirmDialog(dialog, new JScrollPane(panel), "Select Contacts to Add",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					boolean added = false;
					for (int i = 0; i < checkBoxes.length; i++) {
						if (checkBoxes[i].isSelected()) {
							Contact c = allContacts.get(i);
							if (!selected.getContacts().contains(c)) {
								selected.ajouterContact(c);
								added = true;
							}
						}
					}
					if (added) {
						saveContactsToFile();
						JOptionPane.showMessageDialog(dialog, "Selected contacts added.");
						dialog.dispose();
						viewSelectedGroup(); // Refresh
					} else {
						JOptionPane.showMessageDialog(dialog, "No new contacts were selected.");
					}
				}
			});

			// Update Contact
			updateContactBtn.addActionListener(e -> {
				if (contacts.isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "No contacts to update.");
					return;
				}
				Contact selectedContact = (Contact) JOptionPane.showInputDialog(dialog, "Select a contact to update:",
						"Update Contact", JOptionPane.PLAIN_MESSAGE, null, contacts.toArray(), contacts.get(0));
				if (selectedContact != null) {
					JTextField nomField = new JTextField(selectedContact.getNom());
					JTextField prenomField = new JTextField(selectedContact.getPrenom());
					JTextField villeField = new JTextField(selectedContact.getVille());
					Object[] fields = { "Nom:", nomField, "Prenom:", prenomField, "Ville:", villeField };
					int result = JOptionPane.showConfirmDialog(dialog, fields, "Update Contact",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						String nom = nomField.getText().trim();
						String prenom = prenomField.getText().trim();
						String ville = villeField.getText().trim();
						if (!nom.isEmpty() && !prenom.isEmpty()) {
							selectedContact.setNom(nom);
							selectedContact.setPrenom(prenom);
							selectedContact.setVille(ville);
							saveContactsToFile();
							dialog.dispose();
							viewSelectedGroup(); // Refresh
						} else {
							JOptionPane.showMessageDialog(dialog, "Nom and Prenom cannot be empty.");
						}
					}
				}
			});

			// Remove Contact
			removeContactBtn.addActionListener(e -> {
				if (contacts.isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "No contacts to remove.");
					return;
				}
				Contact selectedContact = (Contact) JOptionPane.showInputDialog(dialog, "Select a contact to remove:",
						"Remove Contact", JOptionPane.PLAIN_MESSAGE, null, contacts.toArray(), contacts.get(0));
				if (selectedContact != null) {
					int confirm = JOptionPane.showConfirmDialog(dialog,
							"Are you sure you want to delete: " + selectedContact + "?", "Confirm Remove",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						selected.deleteContact(selectedContact);
						saveContactsToFile();
						JOptionPane.showMessageDialog(dialog, "Contact removed.");
						dialog.dispose();
						viewSelectedGroup(); // Refresh
					}
				}
			});

			buttonPanel.add(addContactBtn);
			buttonPanel.add(updateContactBtn);
			buttonPanel.add(removeContactBtn);

			dialog.add(buttonPanel, BorderLayout.SOUTH);
			dialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "Please select a group to view");
		}
	}

	private void saveContactsToFile() {
		try (FileOutputStream fos = new FileOutputStream("Groups.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			for (Contact contact : g.getContacts()) {
				oos.writeObject(contact);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error saving contacts: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
