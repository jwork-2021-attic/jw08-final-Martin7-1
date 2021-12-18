package com.nju.edu.world;

import com.nju.edu.screen.GameScreen;

/**
 * @author Zyi
 */
public class World {

    public static final int WIDTH = GameScreen.getWid() / 10;
    public static final int HEIGHT = GameScreen.getHei() / 10;

    private static World theWorld;

    public static World getWorld() {
        if (theWorld == null) {
            theWorld = new World();
        }
        return theWorld;
    }

    private Tile<Thing>[][] tiles;

    @SuppressWarnings("unchecked")
    private World() {
        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        setMap();
    }

    /**
     * 设置地图
     */
    private void setMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }
}
