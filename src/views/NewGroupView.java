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
    private JButton saveButton = new JButton("Save Group");

    public NewGroupView(Group g) {
        this.g = g;
        setTitle("New Group");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Group Name:"));
        panel.add(groupNameField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(saveButton);

        add(panel);

        loadGroupsData();

        saveButton.addActionListener(e -> addGroup(g));

        setVisible(true);
    }

    private void addGroup(Group g) {
        g.setNom(groupNameField.getText());
        g.setDescription(descriptionArea.getText());

        if (groups.contains(g)) {
            JOptionPane.showMessageDialog(null, "The Group is already created");
            return;
        }

        groups.add(g);

        // Save all groups, overwriting the file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat"))) {
            for (Group group : groups) {
                oos.writeObject(group);
            }
            JOptionPane.showMessageDialog(null, "Group saved successfully!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving group: " + ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGroupsData() {
        List<Group> newGroups = new ArrayList<>();
        File file = new File("Groups.dat");
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Group g = (Group) ois.readObject();
                    newGroups.add(g);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading groups: " + ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        groups = newGroups;
    }
}