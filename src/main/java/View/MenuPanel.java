package View;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Actions;

public class MenuPanel extends JPanel {
    public MenuPanel(Actions actions){
        
        JButton leaderboardButton=new JButton("Toplista");
        leaderboardButton.addActionListener( e->actions.toLeaderBoard() );

        JButton armyButton=new JButton("Sereg beállítása");
        armyButton.addActionListener( e->actions.toArmy() );

        add(leaderboardButton);
        add(armyButton);
    }
}
