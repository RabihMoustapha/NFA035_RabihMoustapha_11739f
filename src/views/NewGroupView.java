package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;
import Models.Group;

public class NewGroupView extends JFrame {
	private Group g;
	private List<Group> groups = new ArrayList<>();
	private List<Contact> contacts = new ArrayList<>();
	private JTextField groupNameField = new JTextField(15);
	private JTextArea descriptionArea = new JTextArea(3, 20);
	private DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
	private JList<Contact> contactList = new JList<>(contactListModel);
	private JButton saveButton = new JButton("Save Group");

	public NewGroupView(Group g) {
		this.g = g;
		setTitle("New Group");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Group Name:"));
		panel.add(groupNameField);
		panel.add(new JLabel("Description:"));
		panel.add(new JScrollPane(descriptionArea));
		panel.add(new JLabel("Add Contacts:"));
		panel.add(new JScrollPane(contactList));
		panel.add(saveButton);

		add(panel);
		loadContactsData();
		loadGroupsData();
		displayData();
		saveButton.addActionListener(e -> addGroup(g));
	}

	private void addGroup(Group g) {
		g.setNom(groupNameField.getText());
		g.setDescription(descriptionArea.getText());

		if (groups.contains(g)) {
			JOptionPane.showMessageDialog(null, "The Group is already created");
			return;
		} else {
			groups.add(g);

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat", true))) {
				oos.writeObject(g);
				oos.close();
				JOptionPane.showMessageDialog(null, "Yes its entered");
			} catch (IOException ioe) {
		        ioe.printStackTrace();
		        JOptionPane.showMessageDialog(this, 
		            "Error saving group: " + ioe.getMessage(),
		            "Error", 
		            JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void displayData() {
	    contactListModel.clear();
	    contacts.forEach(contactListModel::addElement);
	}
	
	private void loadContactsData() {
		List<Contact> newContacts = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
			contacts.add((Contact) ois.readObject());
		} catch (IOException | ClassNotFoundException ioe) {
	        ioe.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading contacts: " + ioe.getMessage(),
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
		contacts = newContacts;
	}
	
	private void loadGroupsData() {
		List<Group> newGroup = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))){
			newGroup.add((Group) ois.readObject());
		} catch (IOException | ClassNotFoundException ioe) {
	        ioe.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading contacts: " + ioe.getMessage(),
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
		groups = newGroup;
	}
}