package Model;

import java.time.LocalDate;

public class GameRecord {
    private final String name1;
    private final String name2;
    private final LocalDate date;
    private final boolean winner;

    public GameRecord(String name1, String name2, boolean winner){
        this.name1=name1;
        this.name2=name2;
        this.date=LocalDate.now();
        this.winner=winner;
    }
}
