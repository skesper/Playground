package de.kesper.sudoku;

import java.util.Random;

/**
 * User: kesper
 * Date: 28.10.2014
 * Time: 15:33
 */
public class Creator {

    private int numberOfClues;

    public Grid createUniqueSudoku(int numberOfClues) {
        this.numberOfClues = numberOfClues;

        int cnt = 0;
        while(true) {
            Grid g = createSudoku();
//            System.out.println("candidate:");
//            g.print();
            Solver s = new Solver(g);
            s.setFirstSolutionOnly(false);
            s.setQuiet(true);

            s.run();
            if (s.getSolutions()==1) {
                System.out.println("\n\nUnique solution:");
                g.print();
                s.getSolution().print();
                return g;
            }
            System.out.print("["+s.getSolutions()+"] ");
            cnt++;
            if (cnt%40==0) {
                System.out.println("");
            }

        }
    }

    private Grid createSudoku() {

        Grid g = new Grid();

        Random r = new Random();

        int cnt = 0;
        while(true) {
            if (cnt>=numberOfClues) break;

            int row = r.nextInt(9);
            int col = r.nextInt(9);
            int val = r.nextInt(9)+1;

            if (g.isSet(row, col)) continue;

            int row3 = row/3;
            int block = row3*3 + col/3;

            boolean contained = g.colContains(col, val) | g.blockContains(block, val) | g.rowContains(row, val);
            if (!contained) {
                g.set(row, col, val);
                cnt++;
            }
        }

        return g;
    }

    public static void main(String[] args) throws Exception {
        Creator c = new Creator();

        Grid g = c.createUniqueSudoku(22);
    }
}
