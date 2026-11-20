package Model;


import java.util.EnumMap;
import java.util.Map;
import static Model.PieceType.*;

public class Army {
    private final Map<PieceType, Integer> pieces;

    public Army(int numOfAnchors, int numOfConcretes, int numOfChameleons, int numOfBlindspots) {
        pieces=new EnumMap<>(PieceType.class);
        pieces.put(ANCHOR, numOfAnchors);
        pieces.put(CONCRETE, numOfConcretes);
        pieces.put(CHAMELEON, numOfChameleons);
        pieces.put(BLINDSPOT, numOfBlindspots);
    }

    public boolean usePiece(PieceType type){
        int count=pieces.get(type);
        if (count==0)
            return false;
        pieces.put(type, count-1);
        return true;
    }
}
