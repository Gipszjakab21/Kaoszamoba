package View;

import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.Actions;
import Model.Army;
import Model.GameArchive;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout=new CardLayout();
    private final JPanel panelDeck=new JPanel(cardLayout);
    private final Actions actions;

    private final MenuPanel menuPanel;
    private final LeaderboardPanel leaderboardPanel;
    private final ArmyPanel armyPanel;

    public MainFrame(GameArchive archive, Army defArmy, Actions actions){
        super("Nemnewtoni Káoszamőba");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        this.actions=actions;        

        menuPanel=new MenuPanel(actions);

        leaderboardPanel=new LeaderboardPanel(actions);

        armyPanel=new ArmyPanel(actions);

        panelDeck.add(menuPanel, "MENU");
        panelDeck.add(leaderboardPanel, "LEADERBOARD");
        panelDeck.add(armyPanel, "ARMY");

        setContentPane(panelDeck);
        showPanel("MENU");
    }

    public void showPanel(String nameOfPanel){
        if (nameOfPanel.equals("LEADERBOARD"))
            leaderboardPanel.refresh(actions.getLeaderboard());
        else if (nameOfPanel.equals("ARMY"))
            armyPanel.refresh(actions.getDefArmy());
        
        cardLayout.show(panelDeck, nameOfPanel);
    }

    public void armyPanelIOFeedback(boolean success){
        armyPanel.showIOFeedback(success);
    }
}