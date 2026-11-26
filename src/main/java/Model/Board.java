package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public static int SIZE=15;  //Has to be at least five
    private Piece[][] board;

    public Board(){
        board=new Piece[SIZE][SIZE];
    }

    /**
     * Places the given piece on the board. Indexing starts at 0.
     * @param row row of location
     * @param column column of location
     * @param newPiece the piece
     */
    public void place(int row, int column, Piece newPiece) throws IllegalArgumentException{
        if (row>=SIZE || row<0 || column>=SIZE || column<0)
            throw new IllegalArgumentException();
        board[row][column]=newPiece;
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
     * Returns a reversed version of an array.
     * @param array Original array
     * @return A reversed copy
     */
    private static Piece[] reverse(Piece[] array){
        Piece[] reversedArray=new Piece[array.length];
        for (int i=0; i<array.length; i++)
            reversedArray[array.length-i-1]=array[i];
        return reversedArray;
    }

    /**
     * Checks if a player won. 
     * @param player1 The first player of the game
     * @param player2 The second player of the game
     * @return The reference of the winning player or none
     */
    public Player checkWin(Player player1, Player player2){
        int p1_counter=0;
        int p2_counter=0;
        int row=0;
        int column=0;
        //horizontal check
        for (; row<SIZE; row++){
            column=0;
            for(; column<SIZE; column++){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
            }
            p1_counter=0;
            p2_counter=0;
        }
        //vertical check (We learned about this being inefficient because of memory layout, but I cannot think of anything better.)
        row=0;
        column=0;
        for (; column<SIZE; column++){
            row=0;
            for (; row<SIZE; row++){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
            }
            p1_counter=0;
            p2_counter=0;
        }
        //bottom left to upper right
        for (int startingRow=SIZE-1; startingRow>=0;startingRow--){
            row=startingRow;
            column=0;
            while (row>=0){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
                row--;
                column++;
            }
            p1_counter=0;
            p2_counter=0;
        }
        for (int startingColumn=1; startingColumn<SIZE; startingColumn++){
            row=SIZE-1;
            column=startingColumn;
            while (column<SIZE){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
                row--;
                column++;
            }
            p1_counter=0;
            p2_counter=0;
        }
        //Top left to bottom right
        for (int startingColumn=0; startingColumn<SIZE; startingColumn++){
            row=0;
            column=startingColumn;
            while (column<SIZE){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
                row++;
                column++;
            }
            p1_counter=0;
            p2_counter=0;
        }
        for (int startingRow=1; startingRow<SIZE; startingRow++){
            row=startingRow;
            column=0;
            while (row<SIZE){
                if (board[row][column]==null){
                    p1_counter=0;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player1){
                    p1_counter++;
                    p2_counter=0;
                }
                else if (board[row][column].getOwner()==player2){
                    p1_counter=0;
                    p2_counter++;
                }
                if (p1_counter==5)
                    return player1;
                else if (p2_counter==5)
                    return player2;
                row++;
                column++;
            }
            p1_counter=0;
            p2_counter=0;
        }
        return null;
    }

    /**
     * Sets new owner for all chameleons on the board.
     */
    public void chameleonSwitch(Player player1, Player player2){
        for (int row=0; row<SIZE; row++){
            for (int column=0; column<SIZE; column++){
                if (board[row][column]!=null)
                    board[row][column].changeOwner( board[row][column].getOwner()==player1 ? player2 : player1 );
            }
        }
    }

    //TODO
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public void print(Player player1){
        System.out.println("---------------");
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
        System.out.println("---------------");
    }
}
