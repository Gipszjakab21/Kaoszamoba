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

    public void changeOwner(Player newOwner){
        //Void, it is a polymorph method, only the Chameleon changes owner
    }

    public Player getOwner(){
        return owner;
    }
}
