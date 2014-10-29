package de.kesper.sudoku;

import java.util.Arrays;

/**
 * The sudoku grid is a 9 by 9 cell grid. With 9 3x3 blocks.
 *
 * +---+---+---+
 * |123|123|123|
 * |456|456|456|
 * |789|789|789|
 * +---+---+---+
 * |123|123|123|
 * |456|456|456|
 * |789|789|789|
 * +---+---+---+
 * |123|123|123|
 * |456|456|456|
 * |789|789|789|
 * +---+---+---+
 *
 * Rows and Columns are numbered 0 to 8 as well as the blocks. They are numbered from top left 0 to bottom right 8.
 */
public class Grid {

    private int[] grid;

    /**
     * Constructor
     */
    public Grid() {
        grid = new int[81];
        Arrays.fill(grid, 0);
    }

    /**
     * Copy-Constructor
     * @param g
     */
    public Grid(Grid g) {
        grid = Arrays.copyOf(g.grid, g.grid.length);
    }

    /**
     * Determines whether the whole grid is filled with numbers other than 0.
     * @return true if no 0 is in the grid, false otherwise.
     */
    public boolean isFilled() {
        for(int i=0;i<grid.length;++i) {
            if (grid[i]==0) return false;
        }
        return true;
    }

    /**
     * Defines the value for a specific cell
     * @param row The row 0..8
     * @param col The column 0..8
     * @param value The value 1..9
     */
    public void set(int row, int col, int value) {
        grid[row*9+col] = value;
    }

    /**
     * Defines a whole row at once. The string must consist of exactly 9 digits 0..9.
     * While 0 means that the cell is empty. For instance:
     * <pre>
     * "000010430"
     * </pre>
     * @param row The row to be defined.
     * @param s The string representation of the row.
     */
    public void set(int row, String s) {
        for(int i=0;i<9;++i) {
            char c = s.charAt(i);
            int v = (int)(c-'0');
            grid[row*9+i] = v;
        }
    }

    public void set(String s) {
        if (s==null || s.length()!=81) throw new RuntimeException("invalid argument: "+s);

        for(int i=1;i<=9;++i) {
            set(i-1, s.substring((i-1)*9, i*9));
        }
    }

    /**
     * Retrieves the value of the cell. If this method returns a 0 (zero) value, the
     * cell is empty.
     * @param row The row 0..8
     * @param col The column 0..8
     * @return The value
     */
    public int get(int row, int col) {
        return grid[row*9+col];
    }

    /**
     * Returns true if the value is not 0 (zero).
     * @param row The row 0..8
     * @param col The column 0..8
     * @return true if the value is not 0.
     */
    public boolean isSet(int row, int col) {
        return grid[row * 9 + col]>0;
    }

    /**
     * Determines if the row contains a specific value in any of its cells.
     * @param row The row 0..8
     * @param value The value 1..9
     * @return true if the row contains the specified value in any of its cells.
     */
    public boolean rowContains(int row,int value) {
        for(int i=0;i<9;++i) {
            if (grid[row*9+i] == value) return true;
        }
        return false;
    }

    /**
     * Determines if the block contains a specific value in any of its cells.
     * @param block The block 0..8
     * @param value The value 1..9
     * @return true if the block contains the specified value in any of its cells.
     */
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

    /**
     * Determines if the column contains a specific value in any of its cells.
     * @param col The column 0..8
     * @param value The value 1..9
     * @return true if the column contains the specified value in any of its cells.
     */
    public boolean colContains(int col, int value) {
        for(int i=0;i<9;++i) {
            if (grid[i*9+col] == value) return true;
        }
        return false;
    }

    public String toIndexString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<9;++i) {
            for(int j=0;j<9;++j) {
                int v = get(i,j);
                if (v>0) {
                    sb.append(v);
                } else {
                    sb.append("0");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Print out the current state of the grid.
     */
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
}
