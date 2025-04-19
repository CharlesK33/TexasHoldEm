package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class WaitingRoomPanel extends JPanel {

    private DefaultListModel<String> playerListModel;
    private JList<String> playerList;
    private JButton startButton;
    private JLabel infoLabel;

    public WaitingRoomPanel(ActionListener listener) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(30, 92, 58));

        infoLabel = new JLabel("Waiting for players to join...", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        infoLabel.setForeground(new Color(255, 215, 0));
        this.add(infoLabel, BorderLayout.NORTH);

        // Player list section
        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);
        playerList.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        playerList.setBackground(new Color(30, 92, 58));
        playerList.setForeground(new Color(255, 215, 0));
        JScrollPane scrollPane = new JScrollPane(playerList);
        this.add(scrollPane, BorderLayout.CENTER);

        // Start button section
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(new Color(255, 215, 0));
        startButton.addActionListener(listener);
        startButton.setActionCommand("StartGame");
        startButton.setVisible(false); // Only visible for host
        this.add(startButton, BorderLayout.SOUTH);
    }

    public void updatePlayerList(List<String> players) {
        System.out.println("updatePlayerList() called with: " + players);
        playerListModel.clear();
        for (String player : players) {
            playerListModel.addElement(player);
        }
        
        this.revalidate();
        this.repaint();
    }


    public void setAsHost(boolean isHost) {
        startButton.setVisible(isHost);
    }
}
