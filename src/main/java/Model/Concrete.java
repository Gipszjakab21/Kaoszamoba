package Model;

public class Concrete extends Piece {

    public Concrete(Player owner) {
        super(owner);
    }

    @Override
    public boolean isAffectedByGravity(){
        return false;
    }

    @Override
    public PieceType getType(){
        return PieceType.CONCRETE;
    }
}
