//package Controllers;
//
//import Helpers.GroupsHelper;
//import Models.Groupe;
//import java.util.List;
//import java.util.ArrayList;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class GroupController {
//	private DefaultListModel<Groupe> listModel;
//	private JList<Groupe> groupeList;
//	protected GroupsHelper helper = new GroupsHelper();
//
//	public GroupController(DefaultListModel<Groupe> listModel, JList<Groupe> groupeList) {
//		this.listModel = listModel;
//		this.groupeList = groupeList;
//	}
//
//	public ActionListener getViewListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int index = groupeList.getSelectedIndex();
//				if (index >= 0) {
//					Groupe selected = listModel.getElementAt(index);
//					String details = "Nom: " + selected.getNom() + "\n" +
//							"Description: " + selected.getDescription() + "\n" +
//							"Nombre de contacts: " + selected.getNombreContacts();
//					JOptionPane.showMessageDialog(null, details, "Détails du Groupe",
//							JOptionPane.INFORMATION_MESSAGE);
//				} else {
//					showSelectionWarning();
//				}
//			}
//		};
//	}
//
//	public ActionListener getAddGroupListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JFrame groupFrame = new JFrame("Nouveau Groupe");
//				groupFrame.setSize(400, 300);
//				groupFrame.setLocationRelativeTo(null);
//
//				JPanel mainPanel = new JPanel();
//				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//				mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//
//				JLabel titleLabel = new JLabel("Ajouter un nouveau groupe");
//				titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
//				mainPanel.add(titleLabel);
//				mainPanel.add(Box.createVerticalStrut(10));
//
//				JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
//				JTextField nameField = new JTextField();
//				JTextField descField = new JTextField();
//
//				formPanel.add(new JLabel("Nom du groupe:"));
//				formPanel.add(nameField);
//				formPanel.add(new JLabel("Description:"));
//				formPanel.add(descField);
//
//				mainPanel.add(formPanel);
//				mainPanel.add(Box.createVerticalStrut(20));
//
//				JButton saveButton = new JButton("Enregistrer");
//				saveButton.addActionListener(ev -> {
//					String name = nameField.getText().trim();
//					String desc = descField.getText().trim();
//
//					if (name.isEmpty() || desc.isEmpty()) {
//						JOptionPane.showMessageDialog(groupFrame,
//								"Veuillez remplir tous les champs",
//								"Erreur", JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//
//					Groupe newGroup = new Groupe(name, desc);
//					listModel.addElement(newGroup);
//					helper.saveGroupsToFile(listModel);
//					groupFrame.dispose();
//				});
//
//				JButton cancelButton = new JButton("Annuler");
//				cancelButton.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						groupFrame.dispose();
//					}
//				});
//
//				JPanel buttonPanel = new JPanel();
//				buttonPanel.add(saveButton);
//				buttonPanel.add(cancelButton);
//				mainPanel.add(buttonPanel);
//
//				groupFrame.setContentPane(mainPanel);
//				groupFrame.setVisible(true);
//			}
//		};
//	}
//	
//	public ActionListener getSortByNameListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				List<Groupe> SortedList = new ArrayList<>();
//				List<Groupe> groups = new ArrayList<>();
//				try {
//					groups = helper.loadGroupsFromFile();
//					for(int i=0; i < groups.size(); i++) {
//						for(int j=0; j < groups.size(); j++) {
//							if(groups.get(i).getNom().charAt(0) < groups.get(j).getNom().charAt(0)) {
//								SortedList.add(groups.get(i));
//							}
//						}
//					}
//				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
//				}
//			}
//		};
//	}
//	
//	public ActionListener getSortBySizeListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				List<Groupe> SortedList = new ArrayList<>();
//				List<Groupe> groups = new ArrayList<>();
//				try {
//					groups = helper.loadGroupsFromFile();
//					for(int i=0; i < groups.size(); i++) {
//						for(int j=0; j < groups.size(); j++) {
//							if(groups.get(i).getNombreContacts() < groups.get(j).getNombreContacts()) {
//								SortedList.add(groups.get(i));
//							}
//						}
//					}
//				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
//				}
//			}
//		};
//	}
//
//	public ActionListener getUpdateListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int index = groupeList.getSelectedIndex();
//				if (index >= 0) {
//					Groupe selected = listModel.getElementAt(index);
//
//					String newName = JOptionPane.showInputDialog(null,
//							"Nouveau nom:", selected.getNom());
//					String newDesc = JOptionPane.showInputDialog(null,
//							"Nouvelle description:", selected.getDescription());
//
//					if (newName != null && newDesc != null &&
//							!newName.trim().isEmpty() && !newDesc.trim().isEmpty()) {
//
//						selected.setNom(newName.trim());
//						selected.setDescription(newDesc.trim());
//						listModel.set(index, selected);
//						helper.saveGroupsToFile(listModel);
//					}
//				} else {
//					showSelectionWarning();
//				}
//			}
//		};
//	}
//
//	public ActionListener getDeleteListener() {
//		return new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int index = groupeList.getSelectedIndex();
//				if (index >= 0) {
//					int confirm = JOptionPane.showConfirmDialog(null,
//							"Voulez-vous vraiment supprimer ce groupe?", "Confirmation",
//							JOptionPane.YES_NO_OPTION);
//
//					if (confirm == JOptionPane.YES_OPTION) {
//						helper.removeFromList(listModel, index);
//						helper.saveGroupsToFile(listModel);
//					}
//				} else {
//					showSelectionWarning();
//				}
//			}
//		};
//	}
//
//	private void showSelectionWarning() {
//		JOptionPane.showMessageDialog(null,
//				"Veuillez sélectionner un groupe", "Aucune sélection",
//				JOptionPane.WARNING_MESSAGE);
//	}
//}