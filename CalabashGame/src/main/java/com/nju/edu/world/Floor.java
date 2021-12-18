package com.nju.edu.world;

import com.nju.edu.util.ReadImage;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Zyi
 */
public class Floor extends Thing {

    private static BufferedImage image = ReadImage.wall;

    public Floor(World world) {
        super(image, world);
    }
}
