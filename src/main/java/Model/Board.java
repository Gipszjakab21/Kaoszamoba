package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Piece[][] newBoard=new Piece[SIZE][SIZE];
        Piece[] newArray; //Row or column
        for (int i=0; i<SIZE; i++){
            switch(dir){
                case UP:
                    newArray=rearrangeArray(getColumn(i));
                    setColumn(newBoard, newArray, i);
                    break;
                case DOWN:
                    newArray=reverse(rearrangeArray(reverse(getColumn(i))));
                    setColumn(newBoard, newArray, i);
                    break;
                case RIGHT:
                    newArray=reverse(rearrangeArray(reverse(getRow(i))));
                    setRow(newBoard, newArray, i);
                    break;
                case LEFT:
                    newArray=rearrangeArray(getRow(i));
                    setRow(newBoard, newArray, i);
                    break;
            }
        }
        board=newBoard;
    }

    private Piece[] getColumn(int colNum){
        Piece[] column=new Piece[SIZE];
        for (int i=0; i<SIZE; i++)
            column[i]=board[i][colNum];
        return column;
    }

    private Piece[] getRow(int rowNum) {
        return Arrays.copyOf(board[rowNum], SIZE);
    }

    private static void setColumn(Piece[][] grid, Piece[] newColumn, int colNum){
        for (int i=0; i<SIZE; i++)
            grid[i][colNum]=newColumn[i];
    }

    private static void setRow(Piece[][] grid, Piece[] newRow, int rowNum){
        grid[rowNum]=newRow;
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

    /**
     * Rearranges an array as gravity to the left.
     * If right gravity is needed, reverse the array, call this function, then reverse it again.
     * @param column The array to be rearranged
     * @return A rearranged copy of the array.
     */
    private static Piece[] rearrangeArray(Piece[] array){
        List<Piece[]> oldSegmented=segment(array);  //I segment the array by the concretes
        int nextFreeSpot=0;
        Piece[] newArray=new Piece[SIZE];
        for (Piece[] j : oldSegmented){  
            for (int k=0; k<j.length; k++){  //First I slide up the anchors
                if (j[k]!=null && j[k].fallsToEdge()){
                    newArray[nextFreeSpot]=j[k];
                    nextFreeSpot++;
                }
            }
            for (int k=0; k<j.length; k++){   //Then the rest. This also handles the concretes.
                if (j[k]!=null && !j[k].fallsToEdge()){
                    newArray[nextFreeSpot]=j[k];
                    nextFreeSpot++;
                }
            }
            int nullCounter=0;
            for (int k=0; k<j.length; k++){  //I count the free spots
                if (j[k]==null)
                    nullCounter++;
            }
            for (int k=0; k<nullCounter; k++){  //Finally, I fill up the rest of the segment with free spaces
                newArray[nextFreeSpot]=null;
                nextFreeSpot++;
            }
        }
        return newArray;
    }

    /**
     * Reverses an array.
     * @param array Original arry
     * @return A reversed copy
     */
    private static Piece[] reverse(Piece[] array){
        Piece[] reversedArray=new Piece[array.length];
        for (int i=0; i<array.length; i++)
            reversedArray[array.length-i-1]=array[i];
        return reversedArray;
    }
}
