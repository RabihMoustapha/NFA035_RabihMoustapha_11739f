package Views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

	private JButton contactsButton;
	private JButton groupsButton;

	public MainFrame() {
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Project NFA035 - Gestion des contacts");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel titleLabel = new JLabel("Gestion des contacts", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		mainPanel.add(titleLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

		contactsButton = createStyledButton("Contacts");
		groupsButton = createStyledButton("Groupes");

		buttonPanel.add(contactsButton);
		buttonPanel.add(groupsButton);

		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		add(mainPanel);
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		button.setFocusPainted(false);
		button.setBackground(new Color(66, 133, 244));
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(48, 99, 189)),
				BorderFactory.createEmptyBorder(10, 20, 10, 20)));
		return button;
	}

	public JButton getContactsButton() {
		return contactsButton;
	}

	public JButton getGroupsButton() {
		return groupsButton;
	}
}
