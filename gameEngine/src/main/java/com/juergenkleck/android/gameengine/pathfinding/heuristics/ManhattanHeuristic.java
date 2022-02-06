package com.juergenkleck.android.gameengine.pathfinding.heuristics;

import com.juergenkleck.android.gameengine.pathfinding.interfaces.AStarHeuristic;
import com.juergenkleck.android.gameengine.pathfinding.interfaces.Mover;
import com.juergenkleck.android.gameengine.pathfinding.interfaces.TileBasedMap;

/**
 * A heuristic that drives the search based on the Manhattan distance
 * between the current location and the target
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class ManhattanHeuristic implements AStarHeuristic {
    /**
     * The minimum movement cost from any one square to the next
     */
    private int minimumCost;

    /**
     * Create a new heuristic
     *
     * @param minimumCost The minimum movement cost from any one square to the next
     */
    public ManhattanHeuristic(int minimumCost) {
        this.minimumCost = minimumCost;
    }

    /**
     * @see AStarHeuristic#getCost(TileBasedMap, Mover, int, int, int, int)
     */
    public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx,
                         int ty) {
        return minimumCost * (Math.abs(x - tx) + Math.abs(y - ty));
    }

}
