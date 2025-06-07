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
	public JButton cancelButton = new JButton("Cancel");
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
		buttonPanel.add(cancelButton);

		JScrollPane scrollPane = new JScrollPane(groupList);

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		updateListModel(groups);

		addGroupButton.addActionListener(e -> new NewGroupView(new Group(), this));
		
		cancelButton.addActionListener(e -> this.dispose());

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

	public void loadGroups() {
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
			new GroupUpdateView(selected, this);
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

			    // Create a panel with checkboxes
			    JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			    List<JCheckBox> checkBoxes = new ArrayList<>();

			    for (Contact contact : contacts) {
			        JCheckBox cb = new JCheckBox(contact.toString());
			        checkBoxes.add(cb);
			        panel.add(cb);
			    }

			    int result = JOptionPane.showConfirmDialog(dialog, panel, "Select contacts to remove",
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			    if (result == JOptionPane.OK_OPTION) {
			        List<Contact> toRemove = new ArrayList<>();
			        for (int i = 0; i < checkBoxes.size(); i++) {
			            if (checkBoxes.get(i).isSelected()) {
			                toRemove.add(contacts.get(i));
			            }
			        }

			        if (toRemove.isEmpty()) {
			            JOptionPane.showMessageDialog(dialog, "No contacts selected.");
			            return;
			        }

			        int confirm = JOptionPane.showConfirmDialog(dialog,
			                "Are you sure you want to delete the selected contact(s)?",
			                "Confirm Remove", JOptionPane.YES_NO_OPTION);

			        if (confirm == JOptionPane.YES_OPTION) {
			            for (Contact contact : toRemove) {
			                selected.deleteContact(contact);
			            }
			            saveContactsToFile();
			            JOptionPane.showMessageDialog(dialog, "Contact(s) removed.");
			            dialog.dispose();
			            viewSelectedGroup(); // Refresh view
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
		try (FileOutputStream fos = new FileOutputStream("GroupsContacts.dat");
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
