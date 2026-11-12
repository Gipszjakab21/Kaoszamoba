package Model;

public class Chameleon extends Piece {

    public Chameleon(Player owner){
        super(owner);
    }

    public void setOwner(Player owner){
        this.owner=owner;
    }
}
