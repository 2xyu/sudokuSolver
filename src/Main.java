import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean possible(int[][] grid, int[] position, int num){
        for (int[] ints : grid) {
            if (num == ints[position[1]]) {
                return false;
            }
        }


        for (int i = 0; i < grid[0].length; i++){
            if (num == grid[position[0]][i]){
                return false;
            }
        }


        int smallX = position[0] / 3 * 3;
        int smallY = position[1] / 3 * 3;

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (grid[smallX + i][smallY + j] == num){
                    return false;
                }
            }
        }

        return true;
    }

    public static String displayGrid(int[][] grid){
        StringBuilder outP = new StringBuilder();
        for (int i = 0; i < grid.length; i++){
            outP.append("|");
            for (int j = 0; j < grid[0].length; j++){
                outP.append(grid[i][j]);
                if (j % 3 == 2){
                    outP.append("|");
                }
            }
            if (i % 3 == 2){
                outP.append("\n").append("-".repeat(grid[0].length + 4));
            }
            outP.append("\n");
        }
        return outP.toString();
    }

    public static boolean solver(int[][] grid){
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++){
                if (grid[row][col] == 0){
                    for (int i = 1; i <= grid.length; i++){
                        if (possible(grid, new int[]{row, col}, i)) {
                            grid[row][col] = i;
                            if (solver(grid)) {
                                return true;
                            }
                            else {
                                grid[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
//        System.out.println(displayGrid(grid));
        return true;
    }

    public static List[][] ArrayListInitializer(int[][] grid){
        List[][] outP = new List[grid.length][grid[0].length];

        for (int i = 0; i < outP.length*outP[0].length; i++){
            outP[i/outP[0].length][i%outP[0].length] = new ArrayList<Integer>();
        }

        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++){
                if (grid[row][col] == 0){
                    for (int i = 1; i <= 9; i++){
                        if (possible(grid, new int[]{row, col}, i)){
                            outP[row][col].add(i);
                        }
                    }

                }
                else {
                    outP[row][col].add(grid[row][col]);
                }
            }
        }
        return outP;
    }

    public static boolean solverFast(int[][] grid){
        List[][] temp = ArrayListInitializer(grid);

        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++){
                if (grid[row][col] == 0){
                    int finalRow = row;
                    int finalCol = col;
                    int finalRow1 = row;
                    int finalCol1 = col;
                    temp[row][col].stream().filter(i -> possible(grid, new int[]{finalRow1, finalCol1}, (Integer) i)).forEach(i -> {
                        grid[finalRow][finalCol] = (int) i;
                        if (solver(grid)) {
                            return;
                        }
                        else {
                            grid[finalRow][finalCol] = 0;
                        }
                    });
                    return false;
                }
            }
        }
        return true;
    }

    public static void timerSlow(int[][] grid){
        double durationInNS = 0;
        for (int i = 0; i < 100; i++) {
            int[][] temp = new int[grid.length][grid[0].length];
            for (int row = 0; row < grid.length; row++){
                System.arraycopy(grid[row], 0, temp[row], 0, grid[0].length);
            }

            long start = System.nanoTime();
            solver(temp);
            long end = System.nanoTime();
            durationInNS = (end - start) / 1000000.0;
        }
        System.out.println(durationInNS + "ms: for slow method\n");
    }

    public static void timerFast(int[][] grid){
        double durationInNS = 0;
        for (int i = 0; i < 100; i++) {
            int[][] temp = new int[grid.length][grid[0].length];
            for (int row = 0; row < grid.length; row++){
                System.arraycopy(grid[row], 0, temp[row], 0, grid[0].length);
            }

            long start = System.nanoTime();
            solverFast(temp);
            long end = System.nanoTime();
            durationInNS = (end - start) / 1000000.0;
        }
        System.out.println(durationInNS + "ms: for fast method\n");
    }


    public static void main(String[] args) {

        int[][] testEasy = new int[][]{{7,0,2,0,5,0,6,0,0},
                                       {0,0,0,0,0,3,0,0,0},
                                       {1,0,0,0,0,9,5,0,0},
                                       {8,0,0,0,0,0,0,9,0},
                                       {0,4,3,0,0,0,7,5,0},
                                       {0,9,0,0,0,0,0,0,8},
                                       {0,0,9,7,0,0,0,0,5},
                                       {0,0,0,2,0,0,0,0,0},
                                       {0,0,7,0,4,0,2,0,3}};


        int[][] testHARD = new int[][]{{0,0,0,0,0,0,3,0,0},
                                       {0,0,0,0,2,8,0,6,9},
                                       {0,0,0,9,0,0,0,1,0},
                                       {0,0,9,2,0,0,0,7,8},
                                       {0,0,0,7,0,1,0,0,0},
                                       {8,6,0,0,0,5,4,0,0},
                                       {0,8,0,0,0,2,0,0,0},
                                       {1,2,0,5,6,0,0,0,0},
                                       {0,0,4,0,0,0,0,0,0}};


        timerSlow(testEasy);
        timerSlow(testHARD);

        timerFast(testEasy);
        timerFast(testHARD);


    }
}