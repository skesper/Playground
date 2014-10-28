package de.kesper.gameoflife;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


/**
 * User: kesper
 * Date: 29.09.2014
 * Time: 12:07
 */
public class Frame extends JFrame implements MouseListener, KeyListener, ComponentListener {
    protected javax.swing.JPanel drawPanel;
    private volatile Grid grid;
    private volatile long lastResized = Long.MAX_VALUE;
    private volatile int resizeW;
    private volatile int resizeH;
    private volatile Timer resizeTimer;


    public Frame(Grid grit) {
        this.grid = grit;
        initComponents();
        drawPanel.addMouseListener(this);
        this.addKeyListener(this);
        this.addComponentListener(this);
    }

    public synchronized void draw() {
        BufferedImage bi = new BufferedImage(grid.getMaxx(), grid.getMaxy(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D)bi.getGraphics();

        Graphics2D dd = (Graphics2D)drawPanel.getGraphics();

        int alive = 0;
        FColor cc = new FColor(0,0,0);
        try {
            for(int x = 0; x < grid.getMaxx(); ++x) {
                for(int y = 0; y < grid.getMaxy(); ++y) {
                    int c = grid.getSet(x, y);
                    if (c > 0) {
                        c = 60 + (c > 190 ? 190 : c);
                        cc.set(c, 255, c, 255);
                        g2d.setColor(cc);
                        alive++;
                    } else {
                        g2d.setColor(Color.BLACK);
                    }
                    g2d.drawLine(x, y, x, y);
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            // Shit happens.
            return;
        }
        g2d.setColor(Color.YELLOW);
//        g2d.drawString("Gen "+grid.getGeneration()+", alive "+alive, 10,10);
        if (grid.getGeneration()%10==0) {
            setTitle("Gen " + grid.getGeneration() + ", alive " + alive);
        }

        dd.drawImage(bi, 0, 0, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return true;
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        drawPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        drawPanel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
                drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, grid.getMaxx(), Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
                drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, grid.getMaxy(), Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    @Override
    public synchronized void mouseClicked(MouseEvent e) {
        grid.randomizeBall();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource()==this) {
            System.out.println("DEBUG: Window resize in progress");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource()==this) {
            System.out.println("DEBUG: Window resize finalized");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void componentResized(ComponentEvent e) {
        if (resizeTimer==null) {
            resizeTimer = new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resizeW = drawPanel.getWidth();
                    resizeH = drawPanel.getHeight();
                    grid.resize(resizeW, resizeH);
                }
            });
            resizeTimer.setRepeats(false);
        } else {
            if (resizeTimer.isRunning()) {
                resizeTimer.restart();
            } else {
                resizeTimer.start();
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char typed = e.getKeyChar();

        switch(typed) {
            case 'r': {
                grid.randomize();
            }break;

            case 'b': {
                grid.randomizeBall();
            }break;

            case 'n': {
                grid.randomizeBall2();
            }break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
