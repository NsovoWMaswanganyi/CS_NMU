import java.util.Random;

public class Program {
    static void main() {
        new Program();
    }

    public Program() {

        int[][] testGrid1 = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        int[][] testGrid2 = {
                {2, 3, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        int[][] grid = generateGrid();

        boolean[][] visited = new boolean[grid.length][grid.length];
        char[][] directions = new char[grid.length][grid.length];

        boolean found = findPath(grid, 0, 0, visited, directions);

        if (found) {
            int pathLength = printGridWithPath(grid, directions);
            System.out.println();
            System.out.println("Path length = " + pathLength);
        } else {
//            testDisplayGrid(grid);
            System.out.println("No path exists.");
        }
    }


//    void testDisplayGrid(int [] [] grid) {
//        //i = row ; j = col
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

    private int [] [] generateGrid() {
        Random random = new Random();

        int n = random.nextInt(1,10);

        int [][] grid = new int [n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = random.nextInt(1,n);
            }
        }

        return grid;
    }

    boolean findPath(int [][] grid, int row, int col, boolean[][] visited, char [] [] directions) {

        //check if we are getting out the grid

        if(row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            return false;
        }

        //Check if we've been in this cell before
        if(visited[row][col]) {
            return false;
        }

        if(row == grid.length - 1 && col == grid.length - 1) {
            return true;
        }

        visited[row][col] = true;

        //Determines the number of steps we'll go next
        int step = grid[row][col];

        if(findPath(grid, row - step, col, visited, directions)) {
            directions[row][col] = 'U';
            return true;
        }

        if(findPath(grid, row + step, col, visited, directions)) {
            directions[row][col] = 'D';
            return true;
        }

        if(findPath(grid, row, col - step, visited, directions)) {
            directions[row][col] = 'L';
            return true;
        }

        if(findPath(grid, row , col + step, visited, directions)) {
            directions[row][col] = 'R';
            return true;
        }

        return false;
    }

    //To print the resultant grid
    int printGridWithPath(int[][] grid, char[][] directions) {

        int count = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {

                //Check if cell in directions grid actually has a value
                if (directions[i][j] != '\0') {
                    System.out.print(grid[i][j] + "" + directions[i][j] + " ");
                    count+= grid[i][j];
                } else {
                    System.out.print(grid[i][j] + "  ");
                }
            }
            System.out.println();
        }

        return count + 1;
    }
}
