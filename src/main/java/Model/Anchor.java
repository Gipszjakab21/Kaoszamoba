package Model;

public class Anchor extends Piece {

    Anchor(Player owner){
        super(owner);
    }

    @Override
    public boolean fallsToEdge(){
        return true;
    }
}
