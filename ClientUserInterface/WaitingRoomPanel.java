package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoomPanel extends JPanel {

    private DefaultListModel<String> playerListModel;
    private String[] playerLabels = {"", "", "", "", ""};
    private JLabel player1;
    private JLabel player2;
    private JLabel player3;
    private JLabel player4;
    private JLabel player5;
    private JList<String> playerList;
    private JButton startHand;
    private JLabel infoLabel;

    public WaitingRoomPanel(WaitingRoomControl wrc) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(30, 92, 58));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(30, 92, 58));
        infoLabel = new JLabel("Waiting for players to join...", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        infoLabel.setForeground(new Color(255, 215, 0));
        infoPanel.add(infoLabel, BorderLayout.NORTH);

        // Player list section
        JPanel playerPanel = new JPanel(new GridLayout(5, 1));
        playerPanel.setBackground(new Color(30, 92, 58));
        player1 = new JLabel("", JLabel.CENTER);
        player1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        player1.setForeground(new Color(255, 215, 0));
        player2 = new JLabel("", JLabel.CENTER);
        player2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        player2.setForeground(new Color(255, 215, 0));
        player3 = new JLabel("", JLabel.CENTER);
        player3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        player3.setForeground(new Color(255, 215, 0));
        player4 = new JLabel("", JLabel.CENTER);
        player4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        player4.setForeground(new Color(255, 215, 0));
        player5 = new JLabel("", JLabel.CENTER);
        player5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        player5.setForeground(new Color(255, 215, 0));
        playerPanel.add(player1);
        playerPanel.add(player2);
        playerPanel.add(player3);
        playerPanel.add(player4);
        playerPanel.add(player5);
        
        // Start button section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 92, 58));
        startHand = new JButton("Start Hand");
        startHand.addActionListener(wrc);
        buttonPanel.add(startHand);
        
        JPanel grid = new JPanel(new GridLayout(3, 1));
        grid.setBackground(new Color(30, 92, 58));
        grid.add(infoPanel);
        grid.add(playerPanel);
        grid.add(buttonPanel);
        
        this.add(grid);
    }
    
    public void setPlayer1Label(String player1)
    {
    	this.player1.setText(player1);
    }
    
    public void setPlayer2Label(String player2)
    {
    	this.player2.setText(player2);
    }
    
    public void setPlayer3Label(String player3)
    {
    	this.player3.setText(player3);
    }
    
    public void setPlayer4Label(String player4)
    {
    	this.player4.setText(player4);
    }
    
    public void setPlayer5Label(String player5)
    {
    	this.player5.setText(player5);
    }

    public void updatePlayerList(List<String> players) {
        System.out.println("updatePlayerList() called with: " + players);
        playerListModel.clear();
        for (String player : players) {
            playerListModel.addElement(player);
            System.out.println("📋 [UI] Updating player list with: " + players);

        }
        
        this.revalidate();
        this.repaint();
    }


    public void setAsHost(boolean isHost) {
        System.out.println("setAsHost(" + isHost + ") called");
        startHand.setVisible(isHost);
    }

}
