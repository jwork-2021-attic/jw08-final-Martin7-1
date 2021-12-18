package com.nju.edu.world;

import com.nju.edu.screen.GameScreen;

/**
 * @author Zyi
 */
public class World {

    public static final int WIDTH = GameScreen.getWid();
    public static final int HEIGHT = GameScreen.getHei();

    private Tile<Thing>[][] tiles;

    @SuppressWarnings("unchecked")
    public World() {
        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        setMap();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this, true));
            }
        }
    }

    /**
     * 设置地图
     */
    private void setMap() {

    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }
}
