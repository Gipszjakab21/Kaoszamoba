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

    /**
     * Clears the list while returning its content.
     * @return The copy of the list.
     */
    public List<Coordinate> emptyBlindspots(){
        List<Coordinate> copy=new ArrayList<>(blindspots);
        blindspots.clear();
        return copy;
    }
}
