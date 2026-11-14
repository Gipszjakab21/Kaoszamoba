package Model;

import java.time.LocalDate;

public class GameRecord {
    private final String player1;
    private final String player2;
    private final LocalDate date;
    private final boolean winner;   //0->na

    public GameRecord(String name1, String name2, boolean winner){
        this.player1=name1;
        this.player2=name2;
        this.date=LocalDate.now();
        this.winner=winner;
    }
}
