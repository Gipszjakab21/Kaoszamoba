package Model;

import static Model.PieceType.ANCHOR;
import static Model.PieceType.CHAMELEON;
import static Model.PieceType.CONCRETE;
import static Model.PieceType.SIMPLE;

import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public Game(Player player1, Player player2){
        this.player1=player1;
        this.player2=player2;
        board=new Board();
    }

    public void play(){
        Scanner s=new Scanner(System.in);
        for (int i=0; i<20; i++){
            if (i%2==1){
                System.out.print("Row: ");
                int row=s.nextInt();
                System.out.print("Column: ");
                int column=s.nextInt();
                s.nextLine();
                System.out.print("piece: ");
                String letter=s.nextLine();
                PieceType type;
                Piece newPiece;
                if (letter.equals("s")){
                    type=SIMPLE;
                    newPiece=new Simple(player1);
                }
                else if (letter.equals("c")){
                    type=CONCRETE;
                    newPiece=new Concrete(player1);
                }
                else if (letter.equals("x")){
                    type=CHAMELEON;
                    newPiece=new Chameleon(player1);
                }
                else{
                    type=ANCHOR;
                    newPiece=new Anchor(player1);
                }
                if(letter.equals("s") || player1.usePiece(type))
                    board.place(row, column, newPiece);
                else{
                    i--;
                System.out.println("No more pieces.");
                }
            }
            else{
                System.out.print("Row: ");
                int row=s.nextInt();
                System.out.print("Column: ");
                int column=s.nextInt();
                s.nextLine();
                System.out.print("Piece: ");
                String letter=s.nextLine();
                PieceType type;
                Piece newPiece;
                if (letter.equals("s")){
                    type=SIMPLE;
                    newPiece=new Simple(player2);
                }
                else if (letter.equals("c")){
                    type=CONCRETE;
                    newPiece=new Concrete(player2);
                }
                else if (letter.equals("x")){
                    type=CHAMELEON;
                    newPiece=new Chameleon(player2);
                }
                else{
                    type=ANCHOR;
                    newPiece=new Anchor(player2);
                }
                if(letter.equals("s") || player2.usePiece(type))
                    board.place(row, column, newPiece);
                else{
                    i--;
                    System.out.println("No more pieces.");
                }
            }
            board.print(player1);
        }
        s.close();
    }

    public static void main(String[] args){
        Game g=new Game(new Player("Alice", new Army(2,2,2,2)), new Player("Bob", new Army(2, 2, 2, 2)));
        g.play();
    }
}
