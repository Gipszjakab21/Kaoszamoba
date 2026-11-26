package View;

import java.awt.LayoutManager;
import java.awt.Menu;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutFocusTraversalPolicy;

import Model.GameArchive;
import Model.GameArchive.GameRecord;

public class MenuPanel extends JPanel {
    public MenuPanel(Runnable toLeaderBoard){
        
        JButton leaderboardButton=new JButton("Toplista");
        leaderboardButton.addActionListener(e->toLeaderBoard.run());

        add(leaderboardButton);
    }
}
