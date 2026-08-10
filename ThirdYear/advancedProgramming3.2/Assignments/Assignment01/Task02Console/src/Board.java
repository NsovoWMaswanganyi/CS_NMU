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

    //Returns a list of positions matching the dice total that are currently empty
    public List<Position> getEmptyCells(int diceTotal) {
        List<Position> emptyCells = new ArrayList<>();


        return emptyCells;
    }
}
