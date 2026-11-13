package Model;


public class Board {
    public static int SIZE=15;
    private final Player[][] board;

    public Board(){
        board=new Player[SIZE][SIZE];
    }
}
