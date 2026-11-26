package Controller;

import java.io.File;
import java.io.IOException;

import Model.Army;
import Model.GameArchive;
import View.MainFrame;

public class Controller {
    private final GameArchive archive;
    private Army defaultArmy;
    private MainFrame mf;

    public Controller(GameArchive archive, Army defaultArmy){
        this.archive=archive;
        this.defaultArmy=defaultArmy;
    }

    public void start(){
        mf=new MainFrame(archive, defaultArmy);
        mf.setVisible(true);
    }

    public static void main(){
        GameArchive archive=new GameArchive();
        try {
            archive.loadArchive(new File("archive.txt"));
        } catch (IOException e) {
            System.out.println("Error while loading in the game archive.");
        }

        Army defArmy=new Army(2,2,2,2);
        try {
            defArmy.load(new File("defArmyConfig.txt"));
        } catch (IOException e) {
            System.out.println("Error while loading in the default army configuration.");
        }

        Controller c=new Controller(archive, defArmy);
        c.start();
    }
}
