package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Army;
import Model.GameArchive;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout=new CardLayout();
    private final JPanel panelDeck=new JPanel(cardLayout);
    private final MenuPanel menuPanel;
    private final LeaderboardPanel leaderboardPanel;

    public MainFrame(GameArchive archive, Army defArmy){
        super("Nemnewtoni Káoszamőba");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);

        menuPanel=new MenuPanel(
            ()->showPanel("LEADERBOARD")
        );

        leaderboardPanel=new LeaderboardPanel(
            archive,
            ()->showPanel("MENU")
        );

        panelDeck.add(menuPanel, "MENU");
        panelDeck.add(leaderboardPanel, "LEADERBOARD");

        setContentPane(panelDeck);
        showPanel("MENU");
    }

    public void showPanel(String nameOfPanel){
        if (nameOfPanel.equals("LEADERBOARD"))
            leaderboardPanel.refresh();

        cardLayout.show(panelDeck, nameOfPanel);
    }
}