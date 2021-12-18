package com.nju.edu.world;

import java.awt.Color;

/**
 * @author Zyi
 */
public class Floor extends Thing {

    public Floor(World world, boolean isReach) {
        super(Color.gray, (char) 250, world);
        if (isReach) {
            setColor(Color.yellow);
        }
    }
}
