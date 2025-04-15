package ClientUserInterface;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel
{
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel errorLabel;
	
	public LoginPanel(LoginControl lc)
	{
		this.setBackground(new Color(30, 92, 58));
		this.setLayout(new GridBagLayout());
		
		// Instructional message panel
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(new Color(255, 0, 0));
		JLabel instructionLabel = new JLabel("Please Enter Username and Password", JLabel.CENTER);
		instructionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		instructionLabel.setForeground(new Color(255, 215, 0));
		labelPanel.setBackground(new Color(30, 92, 58));
		labelPanel.add(errorLabel);
		labelPanel.add(instructionLabel);
		
		// Data entry panel
		JPanel entryPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel userLabel = new JLabel("Username: ", JLabel.RIGHT);
		userLabel.setForeground(new Color(255, 215, 0));
		usernameField = new JTextField(10);
		JLabel passLabel = new JLabel("Password: ", JLabel.RIGHT);
		passLabel.setForeground(new Color(255, 215, 0));
		passwordField = new JPasswordField(10);
		entryPanel.setBackground(new Color(30, 92, 58));
		entryPanel.add(userLabel);
		entryPanel.add(usernameField);
		entryPanel.add(passLabel);
		entryPanel.add(passwordField);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(lc);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(lc);
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
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
	
	public void setError(String error)
	{
		errorLabel.setText(error);
	}
	
}
