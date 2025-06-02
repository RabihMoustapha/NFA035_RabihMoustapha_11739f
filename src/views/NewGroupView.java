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
		} else {
			groups.add(g);

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat", true))) {
				oos.writeObject(g);
				JOptionPane.showMessageDialog(null, "Yes its entered");
				oos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private void loadData() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
			contactListModel.addElement((Contact) ois.readObject());
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
	}
}
