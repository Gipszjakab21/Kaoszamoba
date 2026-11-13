package Model;

public class Game {
    private final Board board;
    private final String name1;
    private final String name2;

    public Game(String name1, String name2){
        this.name1=name1;
        this.name2=name2;
        board=new Board();
    }
}
