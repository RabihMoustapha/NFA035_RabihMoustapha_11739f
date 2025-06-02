package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import Models.Contact;
import Models.Group;

public class GroupsView extends JFrame {
	private Group g;
	private List<Group> groups = new ArrayList<>();
	private DefaultListModel<Group> groupModel = new DefaultListModel<>();
	private JList<Group> groupList = new JList<>(groupModel);
	private JButton addGroupButton = new JButton("Add New Group");
	private JButton updateGroupButton = new JButton("Update Group");
	private JButton deleteGroupButton = new JButton("Delete Group");

	public GroupsView(Group g) {
		this.g = g;
		setTitle("Groups");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new JLabel("Groups:"), BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.add(addGroupButton);
		bottomPanel.add(updateGroupButton);
		bottomPanel.add(deleteGroupButton);

		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		loadGroupData();
		displayData();

		addGroupButton.addActionListener(e -> {
			new NewGroupView(g);
		});

		updateGroupButton.addActionListener(e -> updateGroup(g));
		deleteGroupButton.addActionListener(e -> deleteGroup(g));

		setVisible(true);
	}
	
	private void loadGroupData() {
	groupModel.clear();
	List<Group> newList = new ArrayList<>();
	
	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))){
		Group group = (Group) ois.readObject();
		newList.add(group);
		ois.close();
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(this, "Failed to load groups: " + e.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	groups = newList;
}

	private void displayData() {
		groups.forEach(groupModel::addElement);
	}

	private void updateGroup(Group g) {
		int index = groupList.getSelectedIndex();
		if (index >= 0) {
			Group selected = groupModel.get(index);
			new GroupUpdateView(selected);
		} else {
			JOptionPane.showMessageDialog(this, "Please select a group to update", "No Selection",
					JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "Please select a group to delete", "No Selection",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void saveGroups() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
			for (int i = 0; i < groupModel.size(); i++) {
				oos.writeObject(groupModel.get(i));
				oos.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}