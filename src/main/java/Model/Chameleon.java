package Model;

public class Chameleon extends Piece {

    public Chameleon(Player owner){
        super(owner);
    }

    public void setOwner(Player owner){
        this.owner=owner;
    }

    @Override
    public PieceType getType(){
        return PieceType.CHAMELEON;
    }

    @Override
    public void changeOwner(Player newOwner){
        owner=newOwner;
    }
}
