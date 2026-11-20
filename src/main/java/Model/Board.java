package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Functions with no index parameters are 0starter indexing.
public class Board {
    public static int SIZE=15;
    private Piece[][] board;

    public Board(){
        board=new Piece[SIZE][SIZE];
    }

    /**
     * Places the given piece on the board. As it is called by an outside object, indexing starts at 1.
     * @param row row of location
     * @param column column of location
     * @param newPiece the piece
     */
    public void place(int row, int column, Piece newPiece) throws IllegalArgumentException{
        if (row>SIZE || row<1 || column>SIZE || column<1)
            throw new IllegalArgumentException();
        board[row-1][column-1]=newPiece;
    }

    /**
     * Rearranges the board, simulating gravity.
     * @param dir direction of gravity
     */
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

    /**
     * Returns a copy of the board's column.
     * @param colNum index of the column
     * @return a COPY
     */
    private Piece[] getColumn(int colNum){
        Piece[] column=new Piece[SIZE];
        for (int i=0; i<SIZE; i++)
            column[i]=board[i][colNum];
        return column;
    }

    /**
     * Returns a copy of the board's column.
     * @param rowNum index of the row
     * @return A COPY
     */
    private Piece[] getRow(int rowNum) {
        return Arrays.copyOf(board[rowNum], SIZE);
    }

    /**
     * Copies the content of the given column to the given part of the given grid.
     * @param grid the grid to be altered
     * @param newColumn the values to be copied
     * @param colNum the index of the altered column
     */
    private static void setColumn(Piece[][] grid, Piece[] newColumn, int colNum){
        for (int i=0; i<SIZE; i++)
            grid[i][colNum]=newColumn[i];
    }

    /**
     * Sets the given row to be the given part of the given grid.
     * @param grid the grid to be altered
     * @param newRow new row array
     * @param rowNum the index of the altered row
     */
    private static void setRow(Piece[][] grid, Piece[] newRow, int rowNum){
        grid[rowNum]=newRow;
    }

    /**
     * Segments the given array.
     * @param array
     * @return A list of arrays. A segment is an interval between one concrete and another. Each concrete gets their own segment.
     */
    private static List<Piece[]> segment(Piece[] array){
        List<Piece[]> segmented=new ArrayList<>();
        int segmentStart=0;  //
        int segmentEnd=0;    //These are inclusive    [end,start] is a segment
        while (segmentStart<SIZE){
            for (int i=segmentStart; i<SIZE; i++){
                if (array[i]!=null && !array[i].isAffectedByGravity()){
                    break;
                }
                segmentEnd=i;
            }
            //Here I know the segment's start and end
            int segmentLength=segmentEnd-segmentStart+1;
            Piece[] segment=new Piece[segmentLength];
            for (int i=0; i<segmentLength; i++){
                segment[i]=array[segmentStart+i];
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
     * @param array The array to be rearranged
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
     * @param array Original array
     * @return A reversed copy
     */
    private static Piece[] reverse(Piece[] array){
        Piece[] reversedArray=new Piece[array.length];
        for (int i=0; i<array.length; i++)
            reversedArray[array.length-i-1]=array[i];
        return reversedArray;
    }

    //TODO
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public void print(Player player1){
        System.out.println("---------------------");
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                if (board[i][j]==null){
                    System.out.print(" ");
                    continue;
                }
                char letter;
                switch (board[i][j].getType()){
                    case SIMPLE:
                        letter='s';
                        break;
                    case CONCRETE:
                        letter='c';
                        break;
                    case CHAMELEON:
                        letter='x';
                        break;
                    default:
                        letter='a';
                        break;
                }
                if(board[i][j].getOwner()==player1)
                    System.out.print(RED+letter+RESET);
                else
                    System.out.print(GREEN+letter+RESET);

            }
            System.out.print('\n');
        }
        System.out.println("---------------------");
    }
}
