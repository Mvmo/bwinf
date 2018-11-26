package de.mvmo.bwinf.schrebergaerten.geometry;

import java.awt.*;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Rect {

    private int width;
    private int height;

    public Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics graphics, int x, int y) {
        graphics.drawRect(x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
