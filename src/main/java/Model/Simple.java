package Model;

public class Simple extends Piece {
    Simple(Player owner){
        super(owner);
    }

    @Override
    public PieceType getType(){
        return PieceType.SIMPLE;
    }
}
