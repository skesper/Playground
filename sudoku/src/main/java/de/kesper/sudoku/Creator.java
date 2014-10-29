package de.kesper.sudoku;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 * User: kesper
 * Date: 28.10.2014
 * Time: 15:33
 */
public class Creator {

    public Grid createUniqueSudoku(int numberOfClues) {

        Clock clock = Clock.systemDefaultZone();

        int cnt = 0;
        while(true) {
            Grid g = createSudoku(numberOfClues);
//            System.out.println("candidate:");
//            g.print();
            Solver s = new Solver(g);
            s.setFirstSolutionOnly(false);
            s.setQuiet(true);

            Instant start = clock.instant();
            s.run();
            Instant end = clock.instant();
            Duration duration = Duration.between(start, end);

            if (s.getSolutions()==1) {
                System.out.println("\n\nUnique solution: "+duration);
                g.print();
                s.getSolution().print();
                System.out.println(g.toIndexString());
                return g;
            }
            System.out.println(String.format("%d [%2d] %s %s", cnt, s.getSolutions(), g.toIndexString(), duration.toString()));
            cnt++;
        }
    }

    private Solver createAnySudoku(int numberOfClues) {
        Grid g = new Grid();

        Random r = new Random();

        while(true) {
            int cnt = 0;

            while(true) {
                if (cnt >= numberOfClues) break;

                int row = r.nextInt(9);
                int col = r.nextInt(9);
                int val = r.nextInt(9) + 1;

                if (g.isSet(row, col)) continue;

                int row3 = row / 3;
                int block = row3 * 3 + col / 3;

                boolean contained = g.colContains(col, val) | g.blockContains(block, val) | g.rowContains(row, val);
                if (!contained) {
                    g.set(row, col, val);
                    cnt++;
                }
            }

            Solver s = new Solver(g);
            s.setFirstSolutionOnly(false);
            s.setQuiet(true);
            s.run();
            if (s.getSolutions()>0) {
                return s;
            }
        }
    }


    private Grid createSudoku(int numberOfClues) {

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

        Solver s = c.createAnySudoku(18);

        System.out.println("Solutions: "+s.getSolutions());
        for(String index : s.getSolutionIndex()) {
            System.out.println("  "+index);
        }

    }
}
