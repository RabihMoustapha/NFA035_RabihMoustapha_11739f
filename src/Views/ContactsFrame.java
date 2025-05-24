package Views;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import Controllers.ContactController;
import Helpers.ContactsHelper;
import Models.Contact;

public class ContactsFrame extends JFrame {
	public ContactsFrame() {
		initialiseUI();
	}

	private void initialiseUI() {
		setTitle("Liste des Contacts");
		setSize(900, 550);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		mainPanel.setBackground(Color.WHITE);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		leftPanel.setBackground(new Color(245, 245, 245));

		JLabel contextLabel = new JLabel("Tri des contacts");
		contextLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton sortByFirstName = new JButton("Trier par prénom");
		JButton sortByLastName = new JButton("Trier par nom");
		JButton sortByCity = new JButton("Trier par ville");
		JButton addContactBtn = new JButton("Ajouter un contact");
		leftPanel.add(contextLabel);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(sortByFirstName);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortByLastName);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortByCity);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(addContactBtn);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel("Gestion des contacts", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		centerPanel.add(titleLabel, BorderLayout.NORTH);

		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchPanel.setBackground(Color.WHITE);

		JLabel searchLabel = new JLabel("Rechercher : ");
		JTextField searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(120, 25));
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		centerPanel.add(searchPanel, BorderLayout.CENTER);

		DefaultListModel<Contact> listModel = new DefaultListModel<>();
		ContactsHelper helper = new ContactsHelper();
		Set<Contact> contacts = new HashSet<>();

		contacts = helper.readData();

		for (Contact c : contacts) {
			listModel.addElement(c);
		}

		JList<Contact> contactList = new JList<>(listModel);
		contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contactList.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollPane = new JScrollPane(contactList);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		centerPanel.add(scrollPane, BorderLayout.SOUTH);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewBtn = new JButton("Voir");
		JButton updateBtn = new JButton("Modifier");
		JButton deleteBtn = new JButton("Supprimer");
		bottomPanel.add(viewBtn);
		bottomPanel.add(updateBtn);
		bottomPanel.add(deleteBtn);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		setVisible(true);

		ContactController contactCtrl = new ContactController(listModel, contactList);
		sortByFirstName.addActionListener(contactCtrl.getSortByFirstNameListener());
		sortByLastName.addActionListener(contactCtrl.getSortByLastNameListener());
		sortByCity.addActionListener(contactCtrl.getSortByCityListener());
		addContactBtn.addActionListener(contactCtrl.getAddContactListener());
		viewBtn.addActionListener(contactCtrl.getViewListener());
		updateBtn.addActionListener(contactCtrl.getUpdateListener());
		deleteBtn.addActionListener(contactCtrl.getDeleteListener());
	}
}
