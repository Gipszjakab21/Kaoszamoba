package Model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * Loads the army configuration from the file.
     * @param sourceFile An army config file
     * @throws IOException
     */
    public void load(File sourceFile) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(sourceFile));
        br.readLine();
        int anchorNum=Integer.parseInt(br.readLine());
        br.readLine();
        int concreteNum=Integer.parseInt(br.readLine());
        br.readLine();
        int chameleonNum=Integer.parseInt(br.readLine());
        br.readLine();
        int blindspotNum=Integer.parseInt(br.readLine());
        br.close();
        pieces.put(ANCHOR, anchorNum);
        pieces.put(CONCRETE, concreteNum);
        pieces.put(CHAMELEON, chameleonNum);
        pieces.put(BLINDSPOT, blindspotNum);
    }

    /**
     ** Writes the current army into the file.
     * @param targetFile The file to override (created now if nonexistent)
     * @throws IOException
     */
    public void save(File targetFile) throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter(targetFile));
        bw.write("Vasmacska\n"+pieces.get(ANCHOR)+"\n");
        bw.write("Betontömb\n"+pieces.get(CONCRETE)+"\n");
        bw.write("Kaméleon\n"+pieces.get(CHAMELEON)+"\n");
        bw.write("Vakfolt\n"+pieces.get(BLINDSPOT));
        bw.close();
    }
}
