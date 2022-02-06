package com.juergenkleck.android.gameengine.pathfinding.map;

/**
 * A single map tile piece
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class MapTile {

    // the basic terrain
    public int terrain;
    // inmoveable static objects
    public int statics;
    // move(cross-)able static objects
    public int semistatic;
    // movable unit
    public int unit;
    // the terrain rotation;
    public float rotation;
    // the statics rotation
    public float rotationstatics;
}
