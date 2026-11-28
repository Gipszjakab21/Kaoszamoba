package View;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Controller.Actions;

public class MenuPanel extends JPanel {
    private Image backgroundImage;

    public MenuPanel(Actions actions){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(250, 60, 50, 60));

        try {
            backgroundImage=ImageIO.read(new File("res/icon.jpeg"));
        } catch (IOException _) {
            backgroundImage=null;
        }

        JButton leaderboardButton=new JButton("Toplista");
        leaderboardButton.addActionListener( e->actions.toLeaderBoard() );
        leaderboardButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton armyButton=new JButton("Sereg beállítása");
        armyButton.addActionListener( e->actions.toArmy() );
        armyButton.setAlignmentX(CENTER_ALIGNMENT);

        add(leaderboardButton);
        add(Box.createVerticalStrut(50));
        add(armyButton);
        add(Box.createVerticalStrut(50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
