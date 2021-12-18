package com.nju.edu.world;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Zyi
 */
public class Thing {

    protected World world;

    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    Thing(BufferedImage image, World world) {
        this.image = image;
        this.world = world;
    }

    private BufferedImage image;

    public BufferedImage getImage() {
        return this.image;
    }
}
