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
	private List<Group> groups = loadGroupsData();
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
		loadData();
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

			try (FileOutputStream fos = new FileOutputStream("Groups.dat", true); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
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

	private void loadData() {
	    contactListModel.clear();
	    contactListModel.addAll(loadContactsData());
	}
	
	private List loadContactsData() {
		List<Contact> contacts = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))){
			contacts.add((Contact) ois.readObject());
			ois.close();
		} catch (IOException | ClassNotFoundException ioe) {
	        ioe.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading contacts: " + ioe.getMessage(),
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
		return contacts;
	}
	
	private List loadGroupsData() {
		List<Group> groups = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))){
			groups.add((Group) ois.readObject());
		} catch (IOException | ClassNotFoundException ioe) {
	        ioe.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading contacts: " + ioe.getMessage(),
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
		return groups;
	}
}

//package views;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//import Models.Contact;
//import Models.Group;
//
//public class NewGroupView extends JFrame {
//    private Group g;
//    private List<Group> groups;
//    private JTextField groupNameField = new JTextField(15);
//    private JTextArea descriptionArea = new JTextArea(3, 20);
//    private DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
//    private JList<Contact> contactList = new JList<>(contactListModel);
//    private JButton saveButton = new JButton("Save Group");
//
//    public NewGroupView(Group g) {
//        this.g = g;
//        this.groups = loadGroupsData();
//        
//        setTitle("New Group");
//        setSize(400, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        // Main panel with GridLayout
//        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        // Add components with proper spacing
//        panel.add(new JLabel("Group Name:"));
//        panel.add(groupNameField);
//        panel.add(new JLabel("Description:"));
//        descriptionArea.setLineWrap(true);
//        panel.add(new JScrollPane(descriptionArea));
//        panel.add(new JLabel("Add Contacts:"));
//        panel.add(new JScrollPane(contactList));
//        panel.add(saveButton);
//
//        add(panel);
//        loadData();
//        
//        saveButton.addActionListener(e -> addGroup(g));
//        
//        setVisible(true);
//    }
//
//    private void addGroup(Group g) {
//        String groupName = groupNameField.getText().trim();
//        String description = descriptionArea.getText().trim();
//        
//        if (groupName.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Group name cannot be empty", 
//                "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        g.setNom(groupName);
//        g.setDescription(description);
//
//        // Check if group with same name already exists
//        for (Group existing : groups) {
//            if (existing.getNom().equalsIgnoreCase(groupName)) {
//                JOptionPane.showMessageDialog(this, "A group with this name already exists", 
//                    "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat", true))) {
//            oos.writeObject(g);
//            groups.add(g);
//            JOptionPane.showMessageDialog(this, "Group created successfully", 
//                "Success", JOptionPane.INFORMATION_MESSAGE);
//            this.dispose(); // Close the window after successful save
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error saving group: " + ioe.getMessage(),
//                "Error", 
//                JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadData() {
//        contactListModel.clear();
//        List<Contact> contacts = loadContactsData();
//        if (contacts != null) {
//            contacts.forEach(contactListModel::addElement);
//        }
//    }
//    
//    private List<Contact> loadContactsData() {
//        List<Contact> contacts = new ArrayList<>();
//        File file = new File("Contacts.dat");
//        if (!file.exists()) {
//            return contacts;
//        }
//        
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            while (true) {
//                try {
//                    Contact contact = (Contact) ois.readObject();
//                    contacts.add(contact);
//                } catch (EOFException e) {
//                    break; // End of file reached
//                }
//            }
//        } catch (IOException | ClassNotFoundException ioe) {
//            ioe.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error loading contacts: " + ioe.getMessage(),
//                "Error", 
//                JOptionPane.ERROR_MESSAGE);
//        }
//        return contacts;
//    }
//    
//    private List<Group> loadGroupsData() {
//        List<Group> groups = new ArrayList<>();
//        File file = new File("Groups.dat");
//        if (!file.exists()) {
//            return groups;
//        }
//        
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            while (true) {
//                try {
//                    Group group = (Group) ois.readObject();
//                    groups.add(group);
//                } catch (EOFException e) {
//                    break; // End of file reached
//                }
//            }
//        } catch (IOException | ClassNotFoundException ioe) {
//            ioe.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error loading groups: " + ioe.getMessage(),
//                "Error", 
//                JOptionPane.ERROR_MESSAGE);
//        }
//        return groups;
//    }
//}

//package views;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//import Models.Contact;
//import Models.Group;
//
//public class NewGroupView extends JFrame {
//    private Group g;
//    private List<Group> groups;
//    private JTextField groupNameField = new JTextField(15);
//    private JTextArea descriptionArea = new JTextArea(3, 20);
//    private DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
//    private JList<Contact> contactList = new JList<>(contactListModel);
//    private JButton saveButton = new JButton("Save Group");
//    private JButton cancelButton = new JButton("Cancel");
//
//    public NewGroupView(Group g) {
//        this.g = g;
//        this.groups = loadGroupsData();
//        
//        setTitle("New Group");
//        setSize(500, 500);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//
//        // Input Panel
//        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        inputPanel.add(new JLabel("Group Name:"));
//        inputPanel.add(groupNameField);
//        
//        inputPanel.add(new JLabel("Description:"));
//        descriptionArea.setLineWrap(true);
//        descriptionArea.setWrapStyleWord(true);
//        inputPanel.add(new JScrollPane(descriptionArea));
//        
//        inputPanel.add(new JLabel("Available Contacts:"));
//        contactList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        inputPanel.add(new JScrollPane(contactList));
//
//        // Button Panel
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
//        buttonPanel.add(cancelButton);
//        buttonPanel.add(saveButton);
//
//        add(inputPanel, BorderLayout.CENTER);
//        add(buttonPanel, BorderLayout.SOUTH);
//
//        loadContactData();
//        
//        saveButton.addActionListener(e -> saveGroup());
//        cancelButton.addActionListener(e -> dispose());
//        
//        setVisible(true);
//    }
//
//    private void saveGroup() {
//        String groupName = groupNameField.getText().trim();
//        String description = descriptionArea.getText().trim();
//        
//        if (groupName.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Group name cannot be empty", 
//                "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        // Check for duplicate group names
//        for (Group existing : groups) {
//            if (existing.getNom().equalsIgnoreCase(groupName)) {
//                JOptionPane.showMessageDialog(this, 
//                    "A group with this name already exists", 
//                    "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//
//        // Set group properties
//        g.setNom(groupName);
//        g.setDescription(description);
//        
//        // Add selected contacts to group
//        Contact selectedContacts = contactList.getSelectedValue();
//        g.ajouterContact(selectedContacts);
//
//        // Save to file
//        if (saveGroupToFile(g)) {
//            JOptionPane.showMessageDialog(this, "Group created successfully", 
//                "Success", JOptionPane.INFORMATION_MESSAGE);
//            dispose();
//        }
//    }
//
//    private boolean saveGroupToFile(Group newGroup) {
//        // Read existing groups first
//        List<Group> allGroups = new ArrayList<>(loadGroupsData());
//        allGroups.add(newGroup);
//        
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
//            for (Group group : allGroups) {
//                oos.writeObject(group);
//            }
//            return true;
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error saving group: " + ioe.getMessage(),
//                "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//    }
//
//    private void loadContactData() {
//        contactListModel.clear();
//        List<Contact> contacts = loadContactsData();
//        if (contacts != null) {
//            contacts.forEach(contactListModel::addElement);
//        }
//    }
//    
//    private List<Contact> loadContactsData() {
//        List<Contact> contacts = new ArrayList<>();
//        File file = new File("Contacts.dat");
//        
//        if (!file.exists()) {
//            return contacts;
//        }
//        
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            while (true) {
//                try {
//                    Contact contact = (Contact) ois.readObject();
//                    contacts.add(contact);
//                } catch (EOFException e) {
//                    break; // End of file reached
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error loading contacts: " + e.getMessage(),
//                "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return contacts;
//    }
//    
//    private List<Group> loadGroupsData() {
//        List<Group> groups = new ArrayList<>();
//        File file = new File("Groups.dat");
//        
//        if (!file.exists()) {
//            return groups;
//        }
//        
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            while (true) {
//                try {
//                    Group group = (Group) ois.readObject();
//                    groups.add(group);
//                    ois.close();
//                } catch (EOFException e) {
//                    break; // End of file reached
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error loading groups: " + e.getMessage(),
//                "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return groups;
//    }
//}