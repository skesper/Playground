package de.kesper.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * The Sudoku solver.
 */
public class Solver implements Runnable {
    private Grid grid;
    private Grid solution;
    private List<String> solutionIndex = new ArrayList<>();

    private boolean firstSolutionOnly = true;
    private int solutions = 0;
    private boolean quiet = false;


    /**
     * Creates a Solver for a specific problem.
     * @param g The grid
     */
    public Solver(Grid g) {
        grid = new Grid(g);
    }

    public boolean isFirstSolutionOnly() {
        return firstSolutionOnly;
    }

    public void setFirstSolutionOnly(boolean firstSolutionOnly) {
        this.firstSolutionOnly = firstSolutionOnly;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public Grid getSolution() {
        return solution;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public int getSolutions() {
        return solutions;
    }

    public List<String> getSolutionIndex() {
        return solutionIndex;
    }

    /**
     * Starts the solution finding. Could be started in a Thread.
     */
    @Override
    public void run() {
        if (!quiet) {
            System.out.println("Solving");
            grid.print();
        }

        long start = System.currentTimeMillis();
        int min = findNextCell();
        int iii = min/10;
        int jjj = min%10;

        int block = (iii/3)*3 + jjj/3;
        for(int k=1;k<10;++k) {
            boolean contained = grid.blockContains(block, k) | grid.rowContains(iii, k) | grid.colContains(jjj, k);
            if (!contained) {
                boolean result = tentative(iii,jjj,k, 0);
                if (result) {
                    long end = System.currentTimeMillis();
                    if (!quiet) {
                        System.out.println("Solved in " + ((end - start) / 1000.) + " sec.");
                    }
                    return;
                }
            }
        }
        if (!quiet) {
            System.out.println("Not solvable");
        }
    }

    /**
     * Defines a tentative value in a cell. That will also generate the whole subtree of decisions necessary
     * for the solution.
     * @param ii The row 0..8
     * @param jj The column 0..8
     * @param value The value
     * @param depth The maximum search depth. If it is larger than 100, the algorithm breaks due to conceptual errors.
     * @return true if the sudoku was successfully solved, false otherwise.
     */
    private boolean tentative(int ii, int jj, int value, int depth) {

        if (depth>100) throw new RuntimeException("Search Depth too high. Algorithm is wrong.");

        //System.out.println("setting "+ii+", "+jj+" to "+value);

        grid.set(ii, jj, value);

        // find cell with minimal options
        int minOpt = findNextCell();
        if (minOpt<0) {
            if (!quiet) {
                grid.print();
            }

            if (grid.isFilled()) {
                if (!quiet) {
                    System.out.println("Success.");
                }
                solution = new Grid(grid);
                solutions++;
                solutionIndex.add(solution.toIndexString());
                return true;
            }
            if (!quiet) {
                System.out.println("Subtree unsuccessful.");
            }
            return false;
        }

        int iii = minOpt/10;
        int jjj = minOpt%10;

        // try for each option a subtree.
        int block = (iii/3)*3 + jjj/3;
        for(int k=1;k<10;++k) {
            boolean contained = grid.blockContains(block, k) | grid.rowContains(iii, k) | grid.colContains(jjj, k);
            if (!contained) {
                boolean result = tentative(iii,jjj,k, depth+1);
                if (result && firstSolutionOnly) {
                    return true;
                }
            }
        }

        // remove any tentative value
        grid.set(ii,jj,0);

        return false;
    }

    /**
     * Generates a "model" of the grid. In each cell this model contains the number of possible choices as value. But not
     * the values them self.
     * @return the model.
     */
    private int[][] populateModel() {
        int[][] model = new int[9][9];
        for(int i=0;i<9;++i) {
            for(int j=0;j<9;++j) {
                int block = (i/3)*3 + j/3;
                if (!grid.isSet(i,j)) {
                    model[i][j] = 0;
                    for(int k = 1; k < 10; ++k) {
                        boolean contained = grid.blockContains(block, k) | grid.rowContains(i, k) | grid.colContains(j, k);
                        if (!contained) {
                            model[i][j] ++;
                        }
                    }
                    //System.out.println(i+", "+j+" : "+model[i][j]);
                }
            }
        }
        return model;
    }

    /**
     * This is here just for runtime analysis. Exchange the call to {#findNextCell} by this method call, to have a
     * brute force solution.
     * @return the grid cell index, which is row*10+column.
     */
    private int findNextCell_bruteforce() {
        // brute force
        for(int i=0;i<9;++i) {
            for(int j=0;j<9;++j) {
                if (!grid.isSet(i,j)) return i*10+j;
            }
        }
        return -1;
    }

    /**
     * This method finds the a cell with the minimum of options in the model. Hence the algorithm search options are
     * reduced.
     * @return the grid cell index, which is row*10+column.
     */
    private int findNextCell() {
        // Find cell with minimum options
        int[][] model = populateModel();
        int iii = -1;
        int jjj = -1;
        int min = 10;
        for(int i=0;i<9;++i) {
            for(int j = 0; j < 9; ++j) {
                if (grid.isSet(i,j)) continue;
                if (model[i][j]<min) {
                    iii = i;
                    jjj = j;
                    min = model[i][j];
                }
            }
        }
        if (min>9) return -1;

        return iii*10+jjj;
    }
}
