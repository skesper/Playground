package de.kesper.sudoku;

/**
 * User: kesper
 * Date: 09.10.2014
 * Time: 08:55
 */
public class Solver implements Runnable {
    private Grid grid;

    public Solver(Grid g) {
        grid = g;
    }

    @Override
    public void run() {
        System.out.println("Solving");
        grid.print();

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
                    System.out.println("Solved in "+((end-start)/1000.)+" sec.");
                    return;
                }
            }
        }
        System.err.println("Not solvable");
    }

    private boolean tentative(int ii, int jj, int value, int depth) {

        if (depth>100) throw new RuntimeException("Search Depth too high. Algorithm is wrong.");

        //System.out.println("setting "+ii+", "+jj+" to "+value);

        grid.set(ii, jj, value);

        // find cell with minimal options
        int minOpt = findNextCell();
        if (minOpt<0) {
            grid.print();
            if (grid.isFilled()) {
                System.out.println("Success.");
                return true;
            }
            System.out.println("Subtree unsuccessful.");
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
                if (result) {
                    return true;
                }
            }
        }

        // remove any tentative value
        grid.set(ii,jj,0);

        return false;
    }

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

    private int findNextCell_bruteforce() {
        // brute force
        for(int i=0;i<9;++i) {
            for(int j=0;j<9;++j) {
                if (!grid.isSet(i,j)) return i*10+j;
            }
        }
        return -1;
    }

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
