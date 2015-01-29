package de.kesper.gameoflife;

import java.util.Arrays;
import java.util.Random;

/**
 * User: kesper
 * Date: 29.09.2014
 * Time: 11:39
 */
public class Grid {
    private volatile int maxx;
    private volatile int maxy;
    private volatile int pmaxx;
    private volatile int pmaxy;
    private volatile int[] grid;
    private volatile int[] tmp;
    private volatile long generation = 0;

    public Grid(int maxx, int maxy) {
        resize(maxx, maxy);
    }

    public synchronized void resize(int maxx, int maxy) {
        this.maxx = maxx;
        this.maxy = maxy;
        grid = new int[maxx * maxy];
        tmp = new int[maxx * maxy];
        randomize();
    }

    public void randomize() {
        int n = maxx*maxy/5;
        generation = 0;
        Random r = new Random();
        for(int i = 0; i < n; ++i) {
            set(r.nextInt(maxx), r.nextInt(maxy));
        }
    }

    public synchronized void randomizeBall() {
        Arrays.fill(grid, 0);
        int n = maxx*maxy/10;

        Random r = new Random();
        for(int i = 0; i < n; ++i) {
            double phi = r.nextDouble()*Math.PI;
            double rr = r.nextDouble();
            double radx = maxx/2.;
            double rady = maxy/2.;
            int s = (int)(radx+rr*radx*Math.sin(phi));
            int ss = (int)(radx-rr*radx*Math.sin(phi));
            int c = (int)(rady+rr*rady*Math.cos(phi));
            set(s,c);
            set(ss,c);
        }
    }


    public synchronized void randomizeBall2() {
        Arrays.fill(grid, 0);
        int n = maxx*maxy/10;

        Random r = new Random();
        for(int i = 0; i < n; ++i) {
            double phi = r.nextDouble()*Math.PI;
            double rr = r.nextDouble();
            double radx = maxx/2.;
            double rady = maxy/2.;
            int s = (int)(radx+rr*radx*Math.sin(phi));
            int c = (int)(rady+rr*rady*Math.cos(phi));
            set(s,c);
        }
    }

    public int getMaxx() {
        return maxx;
    }

    public int getMaxy() {
        return maxy;
    }

    public void set(int x,int y) {
        grid[maxx*y+x] = 1;
    }

    public int getSet(int x, int y) {
        return grid[maxx*y+x];
    }

    public long getGeneration() {
        return generation;
    }

    public int f(int i) {
        return i>0 ? 1 : 0;
    }

    public synchronized void nextGeneration() {
        generation++;

        Arrays.fill(tmp, 0);

        // torus topology
        for(int x = 0; x < maxx; ++x) {
            int xminor = (x + maxx - 1) % maxx;
            int xmajor = (x + 1) % maxx;
            for(int y = 0; y < maxy; ++y) {
                int yminor = (y + maxy - 1) % maxy;
                int ymajor = (y + 1) % maxy;
                int here = maxx * y + x;
                int neighbors =
                        f(grid[maxx * yminor + xminor]) +
                        f(grid[maxx * yminor + x]) +
                        f(grid[maxx * yminor + xmajor]) +
                        f(grid[maxx * y + xminor]) +
                        f(grid[maxx * y + xmajor]) +
                        f(grid[maxx * ymajor + xminor]) +
                        f(grid[maxx * ymajor + x]) +
                        f(grid[maxx * ymajor + xmajor]); // 8 neighbor cells.
                // Conway rules.
                if (grid[here] > 0) {
                    // alive
                    if (neighbors < 2) {
                        tmp[here] = 0;
                    } else if (neighbors == 2 || neighbors == 3) {
                        tmp[here] = grid[here] + 1;
                    } else {
                        tmp[here] = 0;
                    }
                } else {
                    // dead
                    if (neighbors==3) {
                        tmp[here] = 1;
                    }
                }
            }
        }
        // swap
        int[] t = grid;
        grid = tmp;
        tmp = t;
    }

    // For debug purposes only. Do not print big grids!
    public void print() {
        for(int x = 0; x < maxx; ++x) {
            StringBuilder sb = new StringBuilder();
            for(int y = 0; y < maxy; ++y) {
                if (getSet(x,y)>0) {
                    sb.append("O");
                } else {
                    sb.append(" ");
                }
            }
            System.out.println(sb.toString());
        }
        System.out.println("  --  ");
    }

}
