package de.kesper.sudoku;

import java.util.Arrays;

/**
 * User: kesper
 * Date: 08.10.2014
 * Time: 16:44
 */
public class Grid {

    private int[] grid;

    public Grid() {
        grid = new int[81];
        Arrays.fill(grid, 0);
    }

    public boolean isFilled() {
        for(int i=0;i<grid.length;++i) {
            if (grid[i]==0) return false;
        }
        return true;
    }

    public void set(int row, int col, int value) {
        grid[row*9+col] = value;
    }

    public void set(int row, String s) {
        for(int i=0;i<9;++i) {
            char c = s.charAt(i);
            int v = (int)(c-'0');
            grid[row*9+i] = v;
        }
    }

    public int get(int row, int col) {
        return grid[row*9+col];
    }

    public boolean isSet(int row, int col) {
        return grid[row * 9 + col]>0;
    }

    public boolean rowContains(int row,int value) {
        for(int i=0;i<9;++i) {
            if (grid[row*9+i] == value) return true;
        }
        return false;
    }

    public boolean blockContains(int block, int value) {
        int rowOff = (block/3)*3;
        int colOff = (block%3)*3;
        for(int i=0;i<3;++i) {
            for(int j=0;j<3;++j) {
                if (grid[(rowOff+i)*9+(colOff+j)] == value) return true;
            }
        }
        return false;
    }

    public boolean colContains(int col, int value) {
        for(int i=0;i<9;++i) {
            if (grid[i*9+col] == value) return true;
        }
        return false;
    }

    public void print() {
        for(int i=0;i<9;++i) {
            if (i%3==0) System.out.println("-----------------------------");
            for(int j=0;j<9;++j) {
                if (j>0 && j%3==0) System.out.print("|");
                int v = get(i,j);
                if (v>0) {
                    System.out.print(" "+v+" ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println("");
        }
        System.out.println("-----------------------------");
    }

    public static void main(String[] args) throws Exception {
        int block = 5;

        int rowOff = (block/3)*3;
        int colOff = (block%3)*3;
        for(int i=0;i<3;++i) {
            for(int j=0;j<3;++j) {
                int ii = (rowOff+i);
                int jj = (colOff+j);
                System.out.println("block "+block+" "+ii+", "+jj);
            }
        }

    }
}
