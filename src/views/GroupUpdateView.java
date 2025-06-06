package views;

import Models.Group;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GroupUpdateView extends JFrame {

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JButton saveButton;

    private Group group;

    public GroupUpdateView(Group group) {
        this.group = group;

        setTitle("Update Group");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nameField = new JTextField(group.getNom(), 20);
        descriptionArea = new JTextArea(group.getDescription(), 5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        saveButton = new JButton("Save");

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Group Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(saveButton);

        add(formPanel);

        saveButton.addActionListener(e -> saveUpdatedGroup());

        setVisible(true);
    }

    private void saveUpdatedGroup() {
        String newName = nameField.getText().trim();
        String newDesc = descriptionArea.getText().trim();

        if (newName.isEmpty() || newDesc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update current group instance
        group.setNom(newName);
        group.setDescription(newDesc);

        // Load all groups from file
        List<Group> allGroups = new ArrayList<>();
        File file = new File("Groups.dat");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Group g = (Group) ois.readObject();
                    allGroups.add(g);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load groups: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Replace by matching the original name
        for (int i = 0; i < allGroups.size(); i++) {
            if (allGroups.get(i).getNom().equalsIgnoreCase(group.getNom())) {
                allGroups.set(i, group);
                break;
            }
        }

        // Save updated list back to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Group g : allGroups) {
                oos.writeObject(g);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save group: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Group updated successfully.");
        dispose();
    }
}