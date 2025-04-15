package ClientUserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class CreateAccountPanel extends JPanel
{
	private JTextField usernameField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JLabel errorLabel;
	
	public CreateAccountPanel(CreateAccountControl cac)
	{
		this.setBackground(new Color(30, 92, 58));
		this.setLayout(new GridBagLayout());
		
		// Instructional message panel
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(new Color(255, 0, 0));
		JLabel instructionLabel = new JLabel("Please Create Username and Password", JLabel.CENTER);
		instructionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		instructionLabel.setForeground(new Color(255, 215, 0));
		labelPanel.setBackground(new Color(30, 92, 58));
		labelPanel.add(errorLabel);
		labelPanel.add(instructionLabel);
		
		// Data entry panel
		JPanel entryPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		JLabel userLabel = new JLabel("Username: ", JLabel.RIGHT);
		userLabel.setForeground(new Color(255, 215, 0));
		usernameField = new JTextField(10);
		JLabel passLabel = new JLabel("Password: ", JLabel.RIGHT);
		passLabel.setForeground(new Color(255, 215, 0));
		passwordField1 = new JPasswordField(10);
		JLabel passLabel2 = new JLabel("Re-enter Password: ", JLabel.RIGHT);
		passLabel2.setForeground(new Color(255, 215, 0));
		passwordField2 = new JPasswordField(10);
		entryPanel.setBackground(new Color(30, 92, 58));
		entryPanel.add(userLabel);
		entryPanel.add(usernameField);
		entryPanel.add(passLabel);
		entryPanel.add(passwordField1);
		entryPanel.add(passLabel2);
		entryPanel.add(passwordField2);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(cac);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(cac);
		buttonPanel.setBackground(new Color(30, 92, 58));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
		
		// Grid panel
		JPanel grid = new JPanel(new GridLayout(3, 1, 20, 20));
		grid.setBackground(new Color(30, 92, 58));
		grid.add(labelPanel);
		grid.add(entryPanel);
		grid.add(buttonPanel);
		
		this.add(grid);
	}
	
	public String getUsername()
	{
		return usernameField.getText();
	}
	
	public String getPassword1()
	{
		return new String(passwordField1.getPassword());
	}
	
	public String getPassword2()
	{
		return new String(passwordField2.getPassword());
	}
	
	public void setError(String error)
	{
		errorLabel.setText(error);
	}
}
