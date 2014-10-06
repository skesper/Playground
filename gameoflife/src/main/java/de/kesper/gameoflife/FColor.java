package de.kesper.gameoflife;

import java.awt.*;

/**
 * User: kesper
 * Date: 29.09.2014
 * Time: 14:28
 */
public class FColor extends Color {
    private int value;

    public FColor(int r, int g, int b) {
        this(r,g,b,255);
    }

    public FColor(int r, int g, int b, int a) {
        super(r,g,b,a);
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
    }

    public void set(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
    }

    @Override
    public int getRed() {
        return (value >> 16) & 0xFF;
    }

    @Override
    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    @Override
    public int getBlue() {
        return (getRGB() >> 0) & 0xFF;
    }

    @Override
    public int getAlpha() {
        return (getRGB() >> 24) & 0xff;
    }

    @Override
    public int getRGB() {
        return value;
    }
}
