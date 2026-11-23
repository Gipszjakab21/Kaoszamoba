package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class GameArchive {
    private final List<GameRecord> gameList;

    public GameArchive() {
        gameList = new LinkedList<>();
    }

    /**
     * Saves the records into archives.txt
     * @throws IOException
     */
    public void saveArchive() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("archive.txt"));
        for (GameRecord i : gameList) {
            bw.write(i.player1);
            bw.newLine();
            bw.write(i.player2);
            bw.newLine();
            bw.write(i.date.getYear() + "-" + i.date.getMonthValue() + "-" + i.date.getDayOfMonth());
            bw.newLine();
            bw.write(i.winner ? "1" : "0");
            bw.newLine();
            bw.write("------------------------------------");
            bw.newLine();
            }
        bw.close();
    }

    /**
     * Loads the records from archive.txt
     * @throws IOException When the file does not exist
     */
    public void loadArchive() throws IOException{
        BufferedReader br=new BufferedReader(new FileReader("archive.txt"));
        String name1;
        String name2;
        String date;
        String winner;
        GameRecord newRecord;
        while(true){
            name1=br.readLine();
            name2=br.readLine();
            date=br.readLine();
            winner=br.readLine();
            if (name1==null || name2==null || date==null || winner==null)
                break;
            br.readLine();  //for the separator line
            try{
                newRecord=new GameRecord(name1, name2, LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE), winner.equals("1"));
                gameList.add(newRecord);
            } catch (Exception _){
                System.out.println("A record could not be processed, skipping");
            }
        }
        br.close();
    }

    /**
     * Adds the new record to the end of the list.
     * @param newRecord
     */
    public void addRecord(GameRecord newRecord) {
        gameList.add(newRecord);
    }

    /**
     * Returns the reference of the game archive.
     * @return Reference to the list of games
     */
    public List<GameRecord> getGameList(){
        return gameList;
    }

    // Nested class, contains the info of one game for statistical analysis (e.g.
    // rankings)
    public static class GameRecord {
        private final String player1;
        private final String player2;
        private final LocalDate date;
        private final boolean winner; // false->player1 won   true->player2 won

        public GameRecord(String name1, String name2, LocalDate date, boolean winner) {
            this.player1 = name1;
            this.player2 = name2;
            this.date = date;
            this.winner = winner;
        }

        public String getPlayer1(){
            return player1;
        }

        public String getPlayer2(){
            return player2;
        }

        public boolean getWinner(){
            return winner;
        }
    }
}
