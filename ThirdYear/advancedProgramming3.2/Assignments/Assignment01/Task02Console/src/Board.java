import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    Cell[][] grid;

    //To the one Peer assessing, You're in third year now, you must know what Map Does
    //It saves you a lot of time for 2D Arrays

    Map<Integer, List<Position>> numberMap;

    //Come back to check
    public Cell getCell(Position pos) {
        return grid[pos.getRow()][pos.getCol()];
    }

    //Exit Code 1
    //Returns a list of positions matching the dice total that are currently empty
    public List<Position> getEmptyCells(int diceTotal) {
        List<Position> emptyCells = new ArrayList<>();


        return emptyCells;
    }

    //Exit Code 1
    //Returns matching cells occupied by an opposing team
    public List<Position> getOpponentCells(int diceTotal, Player currentPlayer) {
        List<Position> oppCells = new ArrayList<>();


        return oppCells;
    }

    //Exit Code 1
    //Returns all opponent tokens on the board, EXCLUDING those on grey cells (2s and 12s)
    public List<Position> getRemovableOpponentCells(int diceTotal, Player currentPlayer) {
        List<Position> removableOppCells = new ArrayList<>();


        return removableOppCells;
    }

    //Exit Code 1
    //Used when an 11 is rolled
    public List<Position> getAllEmptyCells(int diceTotal, Player currentPlayer) {
        List<Position> oppCells = new ArrayList<>();


        return oppCells;
    }


    public void placeToken(Position position, Player player) {

    }

    public void removeToken(Position position){

    }

    //Checks rows, cols, and diagonals passing through the lastPlaced position to see if a winning sequence was formed
    public boolean checkForWin(Position lastPlaced, int teamId, int requiredSequenceLength) {
        return false;
    }





}
