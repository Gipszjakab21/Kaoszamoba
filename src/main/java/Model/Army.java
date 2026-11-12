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
}
