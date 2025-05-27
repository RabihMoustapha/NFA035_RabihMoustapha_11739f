package views;

import javax.swing.*;
import java.awt.*;

public class UpdateContactView extends JFrame {
    public JTextField firstNameField = new JTextField(15);
    public JTextField lastNameField = new JTextField(15);
    public JTextField cityField = new JTextField(15);
    public DefaultListModel<String> phoneNumbersModel = new DefaultListModel<>();
    public JList<String> phoneNumbersList = new JList<>(phoneNumbersModel);
    public JTextField phoneInput = new JTextField(15);
    public JButton addPhoneButton = new JButton("Add Phone");

    public JComboBox<String> groupsComboBox = new JComboBox<>();
    public JButton saveButton = new JButton("Save");
    public JButton cancelButton = new JButton("Cancel");

    public UpdateContactView() {
        setTitle("Update Contact");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 1));
        form.add(new JLabel("First Name:"));
        form.add(firstNameField);
        form.add(new JLabel("Last Name:"));
        form.add(lastNameField);
        form.add(new JLabel("City:"));
        form.add(cityField);
        form.add(new JLabel("Phone Number:"));
        form.add(phoneInput);
        form.add(addPhoneButton);
        form.add(new JScrollPane(phoneNumbersList));
        form.add(new JLabel("Group:"));
        form.add(groupsComboBox);
        form.add(saveButton);
        form.add(cancelButton);

        add(form);
    }
}
