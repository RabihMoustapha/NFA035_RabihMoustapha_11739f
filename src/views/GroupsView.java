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
		setTitle("Groups");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new JLabel("Groups:"), BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(groupList), BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(new JLabel("Group Contacts:"), BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.add(addGroupButton);
		bottomPanel.add(updateGroupButton);
		bottomPanel.add(deleteGroupButton);

		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		loadGroupData();

		addGroupButton.addActionListener(e -> {
			new NewGroupView(g);
		});

		updateGroupButton.addActionListener(e -> updateGroup(g));
		deleteGroupButton.addActionListener(e -> deleteGroup(g));
	}

	private void loadGroupData() {
		groupModel.clear();
		try (FileInputStream fis = new FileInputStream("Groups.dat"); ObjectInputStream ois = new ObjectInputStream(fis)) {
			while (true) {
				groupModel.addElement((Group) ois.readObject());
	            if (fis.available() > 0) {
	                fis.skip(1);
	            }
			}
		} catch (EOFException e) {
			// This is normal - we've reached end of file
		} catch (FileNotFoundException e) {
			System.out.println("Contacts file not found. It will be created when you save contacts.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading contacts: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateGroup(Group g) {
	    int index = groupList.getSelectedIndex();
	    if (index >= 0) {
	        Group selected = groupModel.get(index);
	        // Open edit dialog here
	    }
	}

	private void deleteGroup(Group g) {
	    int index = groupList.getSelectedIndex();
	    if (index >= 0) {
	        groupModel.remove(index);
	        groups.remove(g);
	        saveGroups(); // Need to implement this
	    }
	}
	
	private void saveGroups() {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
	        for (int i = 0; i < groupModel.size(); i++) {
	            oos.writeObject(groupModel.get(i));
	        }
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Save failed!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}