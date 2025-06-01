package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import Models.DataClass;
import Models.Groupe;

public class NewGroupView extends JFrame {
	public DataClass dc = new DataClass();
    public JTextField groupNameField = new JTextField(15);
    public JTextArea descriptionArea = new JTextArea(3, 20);
    public DefaultListModel<String> contactListModel = new DefaultListModel<>();
    public JList<String> contactList = new JList<>(contactListModel);
    public JButton saveButton = new JButton("Save Group");

    public NewGroupView(Groupe g) {
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
        
        saveButton.addActionListener(e -> addGroup(g));
    }
    
    private void addGroup(Groupe g) {
    	g = new Groupe();
    	g.setNom(groupNameField.getText());
    	g.setDescription(descriptionArea.getText());
    	
    	if(dc.groups.contains(g))
    		JOptionPane.showMessageDialog(null, "The Group is already created");
    	
    	dc.groups.add(g);
    	
    	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Groups.dat", true))){
    		oos.writeObject(g);
    		JOptionPane.showMessageDialog(null, "Yes its entered");
    		oos.close();
    	}catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
}
