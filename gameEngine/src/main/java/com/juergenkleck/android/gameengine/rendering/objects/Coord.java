package com.juergenkleck.android.gameengine.rendering.objects;

import android.graphics.Point;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Coord extends Point {

    // the z coordinate to create a 3d space
    // needed for scaling
    public float z;

    public Coord() {
    }

    public Coord(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

}
