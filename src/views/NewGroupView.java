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
		g = new Group();
		g.setNom(groupNameField.getText());
		g.setDescription(descriptionArea.getText());

		if (groups.contains(g)) {
			JOptionPane.showMessageDialog(null, "The Group is already created");
			return;
		} else {
			groups.add(g);

			try (FileOutputStream fos = new FileOutputStream("Groups.dat", true); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(g);
				JOptionPane.showMessageDialog(null, "Yes its entered");
				fos.write("\n".getBytes());
				oos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private void loadData() {
	    contactListModel.clear();

	    try (FileInputStream fis = new FileInputStream("Contacts.dat"); ObjectInputStream ois = new ObjectInputStream(fis)) {
	        while (true) {
	            Contact contact = (Contact) ois.readObject();
	            contactListModel.addElement(contact);
	            if (fis.available() > 0) {
	                fis.skip(1);
	            }
	        }
	    } 
	    catch (EOFException e) {
	        // This is normal - we've reached end of file
	    }
	    catch (FileNotFoundException e) {
	        System.out.println("Contacts file not found. It will be created when you save contacts.");
	    }
	    catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading contacts: " + e.getMessage(),
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
}
