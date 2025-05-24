package Views;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.SwingConstants;
//import Controllers.GroupController;
import Helpers.GroupsHelper;
import Models.Groupe;

public class GroupsFrame extends JFrame {
	public GroupsFrame() {
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Gestion des groupes");
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
		
		JLabel groupActionLabel = new JLabel("Tri des groupes");
		groupActionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		groupActionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton sortByNameButton = new JButton("Trier par nom");
		JButton sortBySizeButton = new JButton("Trier par taille");
		JButton addGroupButton = new JButton("Ajouter un groupe");
		leftPanel.add(groupActionLabel);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(sortByNameButton);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortBySizeButton);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(addGroupButton);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBackground(Color.WHITE);
		
		JLabel titleLabel = new JLabel("Gestion des groupes", SwingConstants.CENTER);
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
		
		DefaultListModel<Groupe> listModel = new DefaultListModel<>();
		JList<Groupe> groupList = new JList<>(listModel);
		groupList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		groupList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		Set<Groupe> groups = new HashSet<>();
		GroupsHelper helper = new GroupsHelper();

		groups = helper.loadGroupsFromFile();
		listModel.clear();
		for (Groupe g : groups) {
			listModel.addElement(g);
		}

		JScrollPane scrollPane = new JScrollPane(groupList);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		centerPanel.add(scrollPane, BorderLayout.SOUTH);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewButton = new JButton("Voir");
		JButton editButton = new JButton("Modifier");
		JButton deleteButton = new JButton("Supprimer");
		bottomPanel.add(viewButton);
		bottomPanel.add(editButton);
		bottomPanel.add(deleteButton);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		setContentPane(mainPanel);
		setVisible(true);
		
//		GroupController groupCtrl = new GroupController(listModel, groupList);
//		addGroupButton.addActionListener(groupCtrl.getAddGroupListener());
//		sortByNameButton.addActionListener(groupCtrl.getSortByNameListener());
//		sortBySizeButton.addActionListener(groupCtrl.getSortBySizeListener());
//		viewButton.addActionListener(groupCtrl.getViewListener());
//		editButton.addActionListener(groupCtrl.getUpdateListener());
//		deleteButton.addActionListener(groupCtrl.getDeleteListener());
	}
}