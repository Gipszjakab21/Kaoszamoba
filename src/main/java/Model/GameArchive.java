package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameArchive {
    private final List<GameRecord> gameList;

    public GameArchive(){
        gameList=new ArrayList<>();
    }

    public void saveArchive(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("archive.txt"))){
            for (GameRecord i : gameList){
                bw.write(i.player1);
                bw.newLine();
                bw.write(i.player2);
                bw.newLine();
                bw.write(i.date.getYear()+"-"+i.date.getMonthValue()+"-"+i.date.getDayOfMonth());
                bw.newLine();
                bw.write(i.winner ? "1" : "0");
                bw.newLine();
                bw.write("------------------------------------");
                bw.newLine();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void loadArchive(){

    }

    public void addRecord(GameRecord newRecord){
        gameList.add(newRecord);
    }

    //Nested class, contains the info of one game for statistical analysis (e.g. rankings)
    public static class GameRecord{
        private final String player1;
        private final String player2;
        private final LocalDate date;
        private final boolean winner;   //0->player1 won       1->player2 won

        public GameRecord(String name1, String name2, boolean winner){
            this.player1=name1;
            this.player2=name2;
            this.date=LocalDate.now();
            this.winner=winner;
        }
    }

    public static void main(String[] args){
        GameRecord g1=new GameRecord("alma a ló", "Mindent is\nKi lehet írni", true);
        GameRecord g2=new GameRecord("a", "b", false);
        GameRecord g3=new GameRecord("c", "d", false);
        GameRecord g4=new GameRecord("e", "f", true);
        GameArchive a=new GameArchive();
        a.addRecord(g1);
        a.addRecord(g2);
        a.addRecord(g3);
        a.addRecord(g4);

        a.saveArchive();
    }
}
