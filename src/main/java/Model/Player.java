package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final Army army;
    private List<Coordinate> blindspots;

    public Player(String name, Army army){
        this.name=name;
        this.army=army;
        blindspots=new ArrayList<>();
    }
    
    public boolean usePiece(PieceType type){
        return army.usePiece(type);
    }

    public void addBlindspot(int row, int column){
        blindspots.add(new Coordinate(row, column));
    }

    //nested class, holds the coordinates for the Blindspots. Indexing starts with 0.
    public class Coordinate{
        private final int row;
        private final int column;
        public Coordinate(int row, int column){
            this.row=row;
            this.column=column;
        }
    }
}
