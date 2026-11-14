package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Direction;

//Functions with no index parameters are 0starters.
public class Board {
    public static int SIZE=15;
    private Piece[][] board;

    public Board(){
        board=new Piece[SIZE][SIZE];
    }

    //Call with 1starter
    public void place(int row, int column, Piece newPiece){
        board[row-1][column-1]=newPiece;
    }

    public void gravity(Direction dir){
        switch(dir){
            case UP:
                gravityUP();
                break;
            case DOWN:
                gravityDOWN();
                break;
            case RIGHT:
                gravityRIGHT();
                break;
            case LEFT:
                gravityLEFT();
                break;
        }
    }

    private void gravityUP(){
        Piece[][] newBoard=new Piece[SIZE][SIZE];
        for (int i=0; i<SIZE; i++){       //I run through the columns
            Piece[] newColumn=upGravityForColumn(getColumn(i));
            setColumn(newBoard, newColumn, i);
        }
        this.board=newBoard;
    }

    private void gravityDOWN(){

    }

    private void gravityRIGHT(){

    }

    private void gravityLEFT(){

    }

    private Piece[] getColumn(int colNum){
        Piece[] column=new Piece[SIZE];
        for (int i=0; i<SIZE; i++)
            column[i]=board[i][colNum];
        return column;
    }

    private static void setColumn(Piece[][] grid, Piece[] newColumn, int colNum){
        for (int i=0; i<SIZE; i++)
            grid[i][colNum]=newColumn[i];
    }

    private static List<Piece[]> segment(Piece[] column){
        List<Piece[]> segmented=new ArrayList<>();
        int segmentStart=0;  //
        int segmentEnd=0;    //These are inclusive    [end,start] is a segment
        while (segmentStart<SIZE){
            for (int i=segmentStart; i<SIZE; i++){
                if (column[i]!=null && !column[i].isAffectedByGravity()){
                    break;
                }
                segmentEnd=i;
            }
            //Here I know the segment's start and end
            int segmentLength=segmentEnd-segmentStart+1;
            Piece[] segment=new Piece[segmentLength];
            for (int i=0; i<segmentLength; i++){
                segment[i]=column[segmentStart+i];
            }
            segmented.add(segment);
            segmentStart=segmentEnd+1; //The new segment starts from the next spot
            segmentEnd=segmentStart;
        }
        return segmented;
    }

    private static Piece[] upGravityForColumn(Piece[] column){
        List<Piece[]> oldSegmented=segment(column);  //I segment the column by the concretes
        int nextFreeSpot=0;
        Piece[] newColumn=new Piece[SIZE];
        for (Piece[] j : oldSegmented){  
            for (int k=0; k<j.length; k++){  //First I slide up the anchors
                if (j[k]!=null && j[k].fallsToEdge()){
                    newColumn[nextFreeSpot]=j[k];
                    nextFreeSpot++;
                }
            }
            for (int k=0; k<j.length; k++){   //Then the rest. This also handles the concretes.
                if (j[k]!=null && !j[k].fallsToEdge()){
                    newColumn[nextFreeSpot]=j[k];
                    nextFreeSpot++;
                }
            }
            int nullCounter=0;
            for (int k=0; k<j.length; k++){  //I count the free spots
                if (j[k]==null)
                    nullCounter++;
            }
            for (int k=0; k<nullCounter; k++){  //Finally, I fill up the rest of the segment with free spaces
                newColumn[nextFreeSpot]=null;
                nextFreeSpot++;
            }
        }
        return newColumn;
    }
}
