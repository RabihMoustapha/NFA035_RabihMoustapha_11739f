package views;

import javax.swing.*;
import java.awt.*;

public class UpdateGroupView extends JFrame {
    public JTextField groupNameField = new JTextField(15);
    public JTextArea descriptionArea = new JTextArea(3, 20);
    public DefaultListModel<String> contactListModel = new DefaultListModel<>();
    public JList<String> contactList = new JList<>(contactListModel);
    public JButton saveButton = new JButton("Save Group");
    public JButton cancelButton = new JButton("Cancel");

    public UpdateGroupView() {
        setTitle("Update Group");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Group Name:"));
        panel.add(groupNameField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Modify Contacts:"));
        panel.add(new JScrollPane(contactList));
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
    }
}
