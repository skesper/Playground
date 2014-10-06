package de.kesper.gameoflife;

import javax.swing.*;
import java.util.Random;

/**
 * User: kesper
 * Date: 29.09.2014
 * Time: 12:02
 */
public class GameOfLife {

    public static void main(String[] args) throws Exception {
        int maxx = 600;
        int maxy = 600;
        final Grid g = new Grid(maxx,maxy);


        final Frame f = new Frame(g);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setVisible(true);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            g.nextGeneration();
                            f.draw();
                        }
                    }
                });
                t.start();
            }
        });
    }
}
