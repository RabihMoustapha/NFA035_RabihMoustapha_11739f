package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import Models.Contact;
import Models.Group;

public class GroupsView extends JFrame {
    public DefaultListModel<Group> groupModel = new DefaultListModel<>();
    public JList<Group> groupList = new JList<>(groupModel);
    public DefaultListModel<Contact> contactModel = new DefaultListModel<>();
    public JList<Contact> groupContactsList = new JList<>(contactModel);

    public JButton addGroupButton = new JButton("Add New Group");
    public JButton updateGroupButton = new JButton("Update Group");
    public JButton deleteGroupButton = new JButton("Delete Group");

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
        rightPanel.add(new JScrollPane(groupContactsList), BorderLayout.CENTER);

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
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))){
    		groupModel.addElement((Group) ois.readObject());
    	}catch(IOException | ClassNotFoundException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    private void loadContactsData() {
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Groups.dat"))){
    		contactModel.addElement((Contact) ois.readObject());
    	}catch(IOException | ClassNotFoundException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    private void updateGroup(Group g) {
    	
    }
    
    private void deleteGroup(Group g) {
    	
    }
}
