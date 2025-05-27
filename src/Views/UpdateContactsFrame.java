package Views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import Helpers.GroupsHelper;
import Models.Contact;
import Models.Groupe;
import Models.PhoneNumber;

public class UpdateContactsFrame extends JFrame {
    private JButton updateContactButton;
    private JButton backButton;
    private JTable contactsTable;
    private int index; 
    private DefaultListModel listModel;

    public UpdateContactsFrame(Contact c) {
        initializeUI(c, index, listModel);
    }

	private void initializeUI(Contact contact, int index, DefaultListModel listModel) {
        // Design
        setTitle("Gestion des contacts - Modifier Contact");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Modifier le contact");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel namePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du contact"));

//        JTextField firstNameField = new JTextField(contact.getFirstName());
//        JTextField lastNameField = new JTextField(contact.getLastName());
        namePanel.add(new JLabel("Prénom :"));
//        namePanel.add(firstNameField);
        namePanel.add(new JLabel("Nom :"));
//        namePanel.add(lastNameField);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de contact"));

//        JTextField cityField = new JTextField(contact.getCity());
        
        // Get the first phone number (assuming at least one exists)
//        PhoneNumber firstNumber = contact.getPhoneNumbers().iterator().next();
//        JTextField regionCodeField = new JTextField(String.valueOf(firstNumber.getRegionCode()));
//        JTextField phoneNumberField = new JTextField(String.valueOf(firstNumber.getNumber()));
        
        infoPanel.add(new JLabel("Ville :"));
//        infoPanel.add(cityField);
        infoPanel.add(new JLabel("Code Région :"));
//        infoPanel.add(regionCodeField);
        infoPanel.add(new JLabel("Téléphone :"));
//        infoPanel.add(phoneNumberField);
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setBorder(BorderFactory.createTitledBorder("Groupes"));
        Set<Groupe> groups = new HashSet<>();
        GroupsHelper helper = new GroupsHelper();
        JCheckBox[] groupBoxes;

        groups = helper.loadGroupsFromFile();

        groupBoxes = new JCheckBox[groups.size()];

        int i = 0;
        for (Groupe group : groups) {
            groupBoxes[i] = new JCheckBox(group.getNom());
            // Check if the contact belongs to this group
//            if (contact.getGroups().contains(group.getNom())) {
//                groupBoxes[i].setSelected(true);
//            }
            groupPanel.add(groupBoxes[i]);
            i++;
        }

        mainPanel.add(groupPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Mettre à jour");
        JButton cancelButton = new JButton("Annuler");

        // Add the listener
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
//                    String firstName = firstNameField.getText().trim();
//                    String lastName = lastNameField.getText().trim();
//                    String city = cityField.getText().trim();
//                    int regionCode = Integer.parseInt(regionCodeField.getText().trim());
//                    int phoneNumber = Integer.parseInt(phoneNumberField.getText().trim());
                    
//                    if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty()) {
//                        throw new IllegalArgumentException("Tous les champs doivent être remplis.");
//                    }
                    
                    // Update the contact
//                    contact.setFirstName(firstName);
//                    contact.setLastName(lastName);
//                    contact.setCity(city);
                    
                    // Update phone numbers (simplified - assumes single number)
                    contact.getPhoneNumbers().clear();
//                    contact.addPhoneNumber(new PhoneNumber(regionCode, phoneNumber));
                    
                    // Update groups
                    Set<String> selectedGroups = new HashSet<>();
                    for (JCheckBox box : groupBoxes) {
                        if (box.isSelected()) {
                            selectedGroups.add(box.getText());
                        }
                    }
//                    contact.setGroups(selectedGroups);
                    
                    // Here you would typically update the contact in your data storage
                    // For example, load all contacts, find and update this one, then save back
                    
                    JOptionPane.showMessageDialog(null, "Contact mis à jour avec succès !");
                    dispose(); // Close the update window
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le code région et le numéro doivent être des entiers.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        setContentPane(mainPanel);
        setVisible(true);
    }
}