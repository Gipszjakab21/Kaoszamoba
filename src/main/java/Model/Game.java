package Model;

import static Model.PieceType.ANCHOR;
import static Model.PieceType.CHAMELEON;
import static Model.PieceType.CONCRETE;
import static Model.PieceType.SIMPLE;

import java.util.Random;
import java.util.Scanner;


public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player current;  //The player to make the next move
    private Direction nextGravDir;  //The direction of the next gravity wave
    private int nextGravSteps;  //The remaining steps until the next gravity wave
    private static final Random random;
    static{
        random=new Random();
    }

    public Game(Player player1, Player player2){
        this.player1=player1;
        this.player2=player2;
        board=new Board();
        this.current=player1;
    }

    public void play(){
        newGravityParameters();
        Scanner s=new Scanner(System.in);
        int i=0;
        do {
                System.out.print("Row: ");
                int row=s.nextInt()-1;
                System.out.print("Column: ");
                int column=s.nextInt()-1;
                s.nextLine();
                System.out.print("piece: ");
                String letter=s.nextLine();
                PieceType type;
                Piece newPiece;
                if (letter.equals("s")){
                    type=SIMPLE;
                    newPiece=new Simple(current);
                }
                else if (letter.equals("c")){
                    type=CONCRETE;
                    newPiece=new Concrete(current);
                }
                else if (letter.equals("x")){
                    type=CHAMELEON;
                    newPiece=new Chameleon(current);
                }
                else{
                    type=ANCHOR;
                    newPiece=new Anchor(current);
                }
                if(!letter.equals("b") && !letter.equals("sb") && (letter.equals("s") || current.usePiece(type)))
                    board.place(row, column, newPiece);
                else if (!letter.equals("b") && !letter.equals("sb")){
                    System.out.println("No more pieces.");
                    continue;
                }
                if (letter.equals("b")){
                    current.addBlindspot(row, column);
                }
                else if (letter.equals("sb")){
                    for (Coordinate c : current.emptyBlindspots()){
                        board.place(c.getRow(), c.getColumn(), new Simple(current));
                    }
                }
            current= current==player1 ? player2 : player1;
            nextGravSteps--;
            if (nextGravSteps==0){
                board.gravity(nextGravDir);
                newGravityParameters();
            }
            System.out.printf("Dir: %s in %d steps%n%n%n", nextGravDir.toString(), nextGravSteps);
            board.print(player1);
            i++;
        } while (i<30);
        s.close();
    }

    private void newGravityParameters(){
        nextGravDir=Direction.values()[random.nextInt(0, 4)];
        nextGravSteps=random.nextInt(4,5);
    }

    public static void main(String[] args){
        Game g=new Game(new Player("Alice", new Army(2,2,2,2)), new Player("Bob", new Army(2, 2, 2, 2)));
        g.play();
    }
}
