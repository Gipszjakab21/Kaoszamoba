package Model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(){
        GameArchive archive=new GameArchive();
        try{
            archive.loadArchive();
        } catch (IOException e){
            System.out.println("Hiba az archívum betöltésénél.");
        }
        Leaderboard lb=new Leaderboard(archive);
        Army defArmy=new Army(0,0,0,0);
        try {
            defArmy.load(new File("defArmyConfig.txt"));
        } catch (IOException e) {
            System.out.println("Hiba a sereg betöltésénél. Alapértelmezett értékek lépnek életbe.");
            defArmy=new Army(2,2,2,2);
        }
        boolean running=true;
        Scanner scan=new Scanner(System.in);
        while (running){
            System.out.print("1. Új játék\n2.Sereg\n3.Ranglista\n4. Kilépés\n\nVálassz: ");
            int option=scan.nextInt();
            scan.nextLine();
            while (option<1 || option>4) {
                System.out.print("Nem megfelelő bemenet. Válassz újra: ");
                option=scan.nextInt();
                scan.nextLine();
            }
            switch (option){
                case 1:
                    String name1;
                    String name2;
                    System.out.println("Az első játékos neve: ");
                    name1=scan.nextLine();
                    System.out.println("A második játékos neve: ");
                    name2=scan.nextLine();
                    Player player1=new Player(name1, new Army(defArmy));
                    Player player2=new Player(name2, new Army(defArmy));
                    Game g=new Game(player1, player2);
                    boolean winner=g.play();
                    archive.addRecord(new GameArchive.GameRecord(name1, name2, LocalDate.now(), winner));
                    break;
                case 2:

                    break;
                case 3:
                    lb.build();
                    lb.print();
                    break;
                case 4:
                    running=false;
                    break;
            }
        }
        try {
            defArmy.save(new File("defArmyConfig.txt"));
            archive.saveArchive();
        } catch (IOException e) {
            System.out.println("Hiba a kiíráskor.");
        }
    }
}
