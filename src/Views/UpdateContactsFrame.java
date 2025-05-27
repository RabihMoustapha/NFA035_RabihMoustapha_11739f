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
        
        namePanel.add(new JLabel("Prénom :"));
        namePanel.add(new JLabel("Nom :"));
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de contact"));
        
        infoPanel.add(new JLabel("Ville :"));
        infoPanel.add(new JLabel("Code Région :"));
        infoPanel.add(new JLabel("Téléphone :"));
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
                    contact.getPhoneNumbers().clear();
                    
                    // Update groups
                    Set<String> selectedGroups = new HashSet<>();
                    for (JCheckBox box : groupBoxes) {
                        if (box.isSelected()) {
                            selectedGroups.add(box.getText());
                        }
                    }
                    
                    JOptionPane.showMessageDialog(null, "Contact mis à jour avec succès !");
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