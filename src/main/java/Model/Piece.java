package Model;

public abstract class Piece {
    Player owner;

    Piece(Player owner){
        this.owner=owner;
    }

    public boolean isAffectedByGravity(){
        return true;
    }

    public boolean fallsToEdge(){
        return false;
    }

    public abstract PieceType getType();

    //TODO
    public Player getOwner(){
        return owner;
    }
}
