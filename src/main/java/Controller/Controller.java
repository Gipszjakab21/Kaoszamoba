package Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.Army;
import Model.GameArchive;
import Model.Leaderboard;
import Model.Leaderboard.LeaderboardRecord;
import View.MainFrame;

public class Controller implements Actions {
    private final GameArchive archive;
    private final Leaderboard leaderboard;
    private Army defaultArmy;
    private MainFrame mf;

    public Controller(GameArchive archive, Army defaultArmy){
        this.archive=archive;
        this.leaderboard=new Leaderboard(archive);
        this.defaultArmy=defaultArmy;
    }

    public void start(){
        mf=new MainFrame(archive, defaultArmy, this); //the third parameter is the interface side of Controller
        mf.setVisible(true);
    }

    @Override
    public void toMenu(){
        mf.showPanel("MENU");
    }

    @Override
    public void toLeaderBoard(){
        mf.showPanel("LEADERBOARD");
    }

    @Override
    public void toArmy(){
        mf.showPanel("ARMY");
    }

    @Override
    public Army getDefArmy(){
        return defaultArmy;
    }

    @Override
    public List<LeaderboardRecord> getLeaderboard(){
        leaderboard.build();
        return leaderboard.getLeaderboard();
    }

    @Override
    public void saveDefArmy(Army newArmy){
        defaultArmy=newArmy;
    }

    @Override
    public void loadArmy(String fileName){
        try {
            defaultArmy.load(new File(fileName+".txt"));
            mf.showPanel("ARMY"); //The panel is already ARMY, but it needs to refresh
            mf.armyPanelIOFeedback(true);
        } catch (IOException _) {
            mf.armyPanelIOFeedback(false);
        }
    }

    public static void main(){
        GameArchive archive=new GameArchive();
        try {
            archive.loadArchive(new File("archive.txt"));
        } catch (IOException _) {
            System.out.println("Error while loading in the game archive.");
        }

        Army defArmy=new Army(2,2,2,2);
        try {
            defArmy.load(new File("defArmyConfig.txt"));
        } catch (IOException _) {
            System.out.println("Error while loading in the default army configuration.");
        }

        Controller c=new Controller(archive, defArmy);
        c.start();
    }
}
